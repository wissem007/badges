package tn.com.digivoip.comman.job;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackgroundTaskManager extends TimerTask implements IBackgroundTaskManager{

	private static final Logger				LOG			= Logger.getLogger("tn.com.digivoip.comman.job.backgroundtask");	//$NON-NLS-1$
	// one second (=1000 ms)
	private static final int				ONE_SECOND	= 1000;
	// sleep 5 minutes
	private static final int				SLEEP_TIME	= BackgroundTaskManager.ONE_SECOND * 60 * 5;
	private Timer							timer;
	private List<Runnable>					list;
	private static BackgroundTaskManager	instance	= new BackgroundTaskManager();

	public BackgroundTaskManager() {
		super();
		this.list = new Vector<Runnable>();
		this.timer = new Timer();
		timer.schedule(this, BackgroundTaskManager.SLEEP_TIME);
	}
	public static BackgroundTaskManager getInstance() {
		return BackgroundTaskManager.instance;
	}
	public void register(Runnable runnable) {
		this.list.add(runnable);
	}
	public void run() {
		if (BackgroundTaskManager.LOG.isLoggable(Level.FINE)) {
			BackgroundTaskManager.LOG.fine("Starting background tasks..."); //$NON-NLS-1$
		}
		runTasks();
	}
	public void runTasks() {
		for (Runnable task : this.list) {
			task.run();
		}
	}
	public void stop() {
		this.timer.cancel();
	}
}
