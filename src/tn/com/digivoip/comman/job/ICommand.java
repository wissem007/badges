package tn.com.digivoip.comman.job;

public interface ICommand{

	public abstract void updateGUI() throws Exception;
	public abstract void execute(IWorkerStatusController worker) throws Exception;
	public abstract ICommandReference getReference();
	public abstract void releaseAllFolderLocks();
	public abstract boolean canBeProcessed();
}