package tn.com.digivoip.comman.job;

import tn.com.digivoip.comman.Lock;

public abstract class Command implements ICommand{

	public static final int		NORMAL_OPERATION					= 1;
	/** Priorities: Commands that are started by an automated process, e.g. auto-check for new messages */
	public static final int		DAEMON_PRIORITY						= -10;
	/** Normal priority for e.g. copying (default) */
	public static final int		NORMAL_PRIORITY						= 0;
	/** Commands that the user waits for to finish, e.g. view message */
	public static final int		REALTIME_PRIORITY					= 10;
	/** Never Use this!! - internally highest priority */
	public static final int		DEFINETLY_NEXT_OPERATION_PRIORITY	= 20;
	/** Never use these!!! - for internal state control only */
	public static final int		FIRST_EXECUTION						= 0;
	protected int				priority;
	protected int				commandType;
	protected boolean			synchronize;
	protected int				timeStamp;
	protected Lock[]			folderLocks;
	private ICommandReference	reference;

	public Command(ICommandReference theReference) {
		this.reference = theReference;
		commandType = Command.NORMAL_OPERATION;
		priority = Command.NORMAL_PRIORITY;
	}
	public void process(Worker worker) throws Exception {
		setTimeStamp(worker.getTimeStamp());
		execute(worker);
	}
	public void updateGUI() throws Exception {}
	public abstract void execute(IWorkerStatusController worker) throws Exception;
	public boolean canBeProcessed() {
		boolean success = reference.tryToGetLock(this);
		if (!success) {
			releaseAllFolderLocks();
		}
		return success;
	}
	public void releaseAllFolderLocks() {
		reference.releaseLock(this);
	}
	/** *********** Methods for interacting with the Operator ************ */
	public int getCommandType() {
		return commandType;
	}
	public int getPriority() {
		return priority;
	}
	public void incPriority() {
		priority++;
	}
	public boolean isSynchronize() {
		return synchronize;
	}
	public void setSynchronize(boolean isSynchronize) {
		this.synchronize = isSynchronize;
	}
	public void setPriority(int thePriority) {
		this.priority = thePriority;
	}
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int theTimeStamp) {
		this.timeStamp = theTimeStamp;
	}
	public ICommandReference getReference() {
		return reference;
	}
	public void finish() throws Exception {
		updateGUI();
	}
}