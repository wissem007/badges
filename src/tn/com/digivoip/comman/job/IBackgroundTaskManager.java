package tn.com.digivoip.comman.job;

public interface IBackgroundTaskManager{

	/** Register new background task.
	 * @param runnable background task */
	public abstract void register(Runnable runnable);
}