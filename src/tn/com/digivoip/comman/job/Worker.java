package tn.com.digivoip.comman.job;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import tn.com.digivoip.comman.exception.IExceptionListener;

public class Worker extends SwingWorker implements IWorkerStatusController{

	private static final Logger					LOG				= Logger.getLogger("org.columba.core.command"); //$NON-NLS-1$
	private static final int					CLEAR_DELAY		= 500;
	protected Command							op;
	protected int								operationMode;
	protected CommandProcessor					boss;
	protected String							displayText;
	protected int								progressBarMax;
	protected int								progressBarValue;
	protected boolean							cancelled;
	protected List<IWorkerStatusChangeListener>	workerStatusChangeListeners;
	private int									timeStamp;
	protected EventListenerList					listenerList	= new EventListenerList();

	public Worker(CommandProcessor parent) {
		super();
		this.boss = parent;
		displayText = ""; //$NON-NLS-1$
		progressBarValue = 0;
		progressBarMax = 0;
		cancelled = false;
		workerStatusChangeListeners = new Vector<IWorkerStatusChangeListener>();
	}
	public void process(Command theCommand, int theOperationMode, int theTimeStamp) {
		this.op = theCommand;
		this.operationMode = theOperationMode;
		this.timeStamp = theTimeStamp;
	}
	public int getPriority() {
		return op.getPriority();
	}
	private void returnLocks(int opMode) {
		op.releaseAllFolderLocks();
	}
	public Object construct() {
		try {
			op.process(this);
		} catch (CommandCancelledException e) {
			Worker.LOG.info("Command cancelled: " + this); //$NON-NLS-1$
		} catch (Exception e) {
			// exception handler should handle all error handling stuff
			fireExceptionOccured(e);
		}
		returnLocks(operationMode);
		return null;
	}
	public void finished() {
		try {
			op.finish();
		} catch (Exception e) {
			// Must create a ExceptionProcessor
			e.printStackTrace();
		}
		unregister();
		boss.operationFinished(op, this);
	}
	private void unregister() {
		TaskManager.getInstance().unregister(threadVar);
		WorkerStatusChangedEvent e = new WorkerStatusChangedEvent(this, getTimeStamp());
		e.setType(WorkerStatusChangedEvent.FINISHED);
		fireWorkerStatusChanged(e);
		workerStatusChangeListeners.clear();
		displayText = ""; //$NON-NLS-1$
		progressBarValue = 0;
		progressBarMax = 0;
	}
	/** Sets the maximum value for the progress bar.
	 * @param max New max. value for progress bar */
	public void setProgressBarMaximum(int max) {
		WorkerStatusChangedEvent e = new WorkerStatusChangedEvent(this, getTimeStamp());
		e.setType(WorkerStatusChangedEvent.PROGRESSBAR_MAX_CHANGED);
		e.setOldValue(new Integer(progressBarMax));
		progressBarMax = max;
		e.setNewValue(new Integer(progressBarMax));
		fireWorkerStatusChanged(e);
	}
	/** Sets the current value of the progress bar.
	 * @param aValue New current value of progress bar */
	public void setProgressBarValue(int aValue) {
		WorkerStatusChangedEvent e = new WorkerStatusChangedEvent(this, getTimeStamp());
		e.setType(WorkerStatusChangedEvent.PROGRESSBAR_VALUE_CHANGED);
		e.setOldValue(new Integer(progressBarValue));
		progressBarValue = aValue;
		e.setNewValue(new Integer(progressBarValue));
		fireWorkerStatusChanged(e);
	}
	/** Sets the progress bar value to zero, i.e. clears the progress bar. This is the same as calling setProgressBarValue(0) */
	public void resetProgressBar() {
		setProgressBarValue(0);
	}
	/** Returns the max. value for the progress bar */
	public int getProgessBarMaximum() {
		return progressBarMax;
	}
	/** Returns the current value for the progress bar */
	public int getProgressBarValue() {
		return progressBarValue;
	}
	/** Returns the text currently displayed in the status bar */
	public String getDisplayText() {
		return displayText;
	}
	/** Set the text to be displayed in the status bar
	 * @param text Text to display in status bar */
	public void setDisplayText(String text) {
		WorkerStatusChangedEvent e = new WorkerStatusChangedEvent(this, getTimeStamp());
		e.setType(WorkerStatusChangedEvent.DISPLAY_TEXT_CHANGED);
		e.setOldValue(displayText);
		displayText = text;
		e.setNewValue(displayText);
		fireWorkerStatusChanged(e);
	}
	/** Clears the text displayed in the status bar - without any delay */
	public void clearDisplayText() {
		clearDisplayText(0);
	}
	/** Clears the text displayed in the status bar - with a given delay. The delay used is 500 ms. <br>
	 * If a new text is set within this delay, the text is not cleared. */
	public void clearDisplayTextWithDelay() {
		clearDisplayText(Worker.CLEAR_DELAY);
	}
	/** Clears the text displayed in the status bar - with a given delay. If a new text is set within this delay, the text is not cleared.
	 * @param delay Delay in milliseconds before clearing the text */
	private void clearDisplayText(int delay) {
		// init event
		WorkerStatusChangedEvent e = new WorkerStatusChangedEvent(this, getTimeStamp());
		e.setType(WorkerStatusChangedEvent.DISPLAY_TEXT_CLEARED);
		// "new value" is used to pass on the delay
		e.setNewValue(new Integer(delay));
		// set display text stored here to an empty string (~ cleared)
		displayText = ""; //$NON-NLS-1$
		// fire event
		fireWorkerStatusChanged(e);
	}
	public void addWorkerStatusChangeListener(IWorkerStatusChangeListener l) {
		workerStatusChangeListeners.add(l);
	}
	public void removeWorkerStatusChangeListener(IWorkerStatusChangeListener l) {
		workerStatusChangeListeners.remove(l);
	}
	protected void fireWorkerStatusChanged(WorkerStatusChangedEvent e) {
		for (int i = 0; i < workerStatusChangeListeners.size(); i++) {
			workerStatusChangeListeners.get(i).workerStatusChanged(e);
		}
	}
	public void cancel() {
		cancelled = true;
	}
	public boolean cancelled() {
		return cancelled;
	}
	public int getTimeStamp() {
		return timeStamp;
	}
	public void addExceptionListener(IExceptionListener l) {
		listenerList.add(IExceptionListener.class, l);
	}
	public void removeExceptionListener(IExceptionListener l) {
		listenerList.remove(IExceptionListener.class, l);
	}
	private void fireExceptionOccured(Exception e) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IExceptionListener.class) {
				((IExceptionListener) listeners[i + 1]).exceptionOccured(e);
			}
		}
	}
	public Thread start() {
		TaskManager.getInstance().register(this);
		return super.start();
	}
}