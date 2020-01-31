package tn.com.digivoip.comman.job;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import tn.com.digivoip.comman.Mutex;
import tn.com.digivoip.comman.exception.ExceptionHandler;

public class CommandProcessor implements Runnable{

	/** JDK 1.4+ logging framework logger, used for logging. */
	private static final Logger		LOG			= Logger.getLogger("tn.com.digivoip.comman.job.command"); //$NON-NLS-1$
	public final static int			MAX_WORKERS	= 5;
	List<OperationItem>				operationQueue;
	List<Worker>					worker;
	private Mutex					oneMutex;
	private int						timeStamp;
	private boolean					stopped		= false;
	private static CommandProcessor	instance	= new CommandProcessor();

	public CommandProcessor() {
		this(true);
	}
	public static CommandProcessor getInstance() {
		return CommandProcessor.instance;
	}
	/** Constructs a DefaultProcessor. */
	public CommandProcessor(boolean start) {
		operationQueue = new ArrayList<OperationItem>(10);
		worker = new ArrayList<Worker>(CommandProcessor.MAX_WORKERS);
		// Create the workers
		for (int i = 0; i < CommandProcessor.MAX_WORKERS; i++) {
			Worker w = new Worker(this);
			w.addExceptionListener(new ExceptionHandler());
			worker.add(w);
		}
		oneMutex = new Mutex();
		timeStamp = 0;
		if (start) {
			new Thread(this).start();
		}
	}
	/** Add a Command to the Queue. Calls {@link #addOp(Command, int)}with Command.FIRST_EXECUTION.
	 * @param op the command to add */
	public void addOp(final Command op) {
		addOp(op, Command.FIRST_EXECUTION);
	}
	/** Adds a Command to the queue.
	 * @param op the command
	 * @param operationMode the mode in wich the command should be processed */
	public void addOp(final Command op, final int operationMode) {
		try {
			oneMutex.lock();
			CommandProcessor.LOG.finest("Command " + op.toString() + " added"); //$NON-NLS-1$ //$NON-NLS-2$
			int p = operationQueue.size() - 1;
			OperationItem nextOp;
			while (p != -1) {
				nextOp = operationQueue.get(p);
				if ((nextOp.getOperation().getPriority() < op.getPriority()) && !nextOp.getOperation().isSynchronize()) {
					p--;
				} else {
					break;
				}
			}
			operationQueue.add(p + 1, new OperationItem(op, operationMode));
		} finally {
			oneMutex.release();
		}
		wakeUp();
	}
	/** Checks if the command can be processed. This is true if all references are not blocked.
	 * @param opItem the internal command structure
	 * @return true if the operation will not be blocked */
	private boolean canBeProcessed(final OperationItem opItem) {
		return opItem.getOperation().canBeProcessed();
	}
	/** Get the next Operation from the queue.
	 * @return the next non-blocking operation or null if none found. */
	private OperationItem nextOpItem() {
		OperationItem nextOp = null;
		for (int i = 0; i < operationQueue.size() && nextOp == null; i++) {
			nextOp = operationQueue.get(i);
			if ((i != 0) && (nextOp.getOperation().isSynchronize())) {
				nextOp = null;
				// We have to process this command first
				// -> break here!
				break;
			}
			try {
				if (!canBeProcessed(nextOp)) {
					nextOp = null;
				}
			} catch (RuntimeException e) {
				// Remove bogus Operation
				operationQueue.remove(nextOp);
				nextOp = null;
				CommandProcessor.LOG.warning("Operation failed: " + e.getMessage()); //$NON-NLS-1$
			}
		}
		return nextOp;
	}
	/** Called by the worker to signal that his operation has finished.
	 * @param op the command the worker has processed
	 * @param w the worker himself */
	public void operationFinished(final ICommand op, final Worker w) {
		try {
			oneMutex.lock();
			worker.add(w);
		} finally {
			oneMutex.release();
		}
		// notify that a new worker is available
		wakeUp();
	}
	/** Get an available Worker from the workerpool. Reserve one worker for Real-Time Priority tasks
	 * @param priority
	 * @return an available worker or null if none available. */
	Worker getWorker(int priority) {
		Worker result = null;
		if (worker.size() > 1) {
			result = worker.remove(0);
		} else if (worker.size() > 0 && priority >= Command.REALTIME_PRIORITY) {
			result = worker.remove(0);
		}
		return result;
	}
	/** Wait until a worker is available or a new command is added. */
	private synchronized void waitForNotify() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private synchronized void wakeUp() {
		notifyAll();
	}
	/** @see java.lang.Runnable#run() */
	public void run() {
		boolean sleep;
		while (true && !stopped) {
			sleep = startOperation();
			if (sleep) {
				waitForNotify();
				sleep = false;
			}
		}
	}
	/** @param sleep
	 * @return */
	boolean startOperation() {
		boolean sleep = false;
		try {
			oneMutex.lock();
			OperationItem _opItem;
			Worker _worker;
			_opItem = nextOpItem();
			if (_opItem != null && !stopped) {
				_worker = getWorker(_opItem.getOperation().getPriority());
				if (_worker != null && !stopped) {
					operationQueue.remove(_opItem);
					_worker.process(_opItem.getOperation(), _opItem.getOperationMode(), timeStamp++);
					_worker.start();
				} else {
					sleep = true;
				}
			} else {
				sleep = true;
			}
		} finally {
			oneMutex.release();
		}
		return sleep;
	}
	public synchronized void stop() {
		stopped = true;
		notify();
	}
}

/** Intern represenation of the Commands.
 * @author Timo Stich <tstich@users.sourceforge.net> */
class OperationItem{

	private Command	operation;
	private int		operationMode;

	public OperationItem(Command op, int opMode) {
		operation = op;
		operationMode = opMode;
	}
	public Command getOperation() {
		return operation;
	}
	public int getOperationMode() {
		return operationMode;
	}
}