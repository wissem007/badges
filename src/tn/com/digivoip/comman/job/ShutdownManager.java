package tn.com.digivoip.comman.job;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ShutdownManager implements IShutdownManager{

	private static final Logger		LOG				= Logger.getLogger("org.digivoip.core.shutdown");
	private static IShutdownManager	instance;
	private boolean					shutdownHook	= false;
	protected final Thread			shutdownThread;
	protected List<Runnable>		list			= new LinkedList<Runnable>();
	private boolean					shuttingDown;

	protected ShutdownManager() {
		shutdownThread = new Thread(new Runnable(){

			public void run() {
				BackgroundTaskManager.getInstance().stop();
				CommandProcessor.getInstance().stop();
				Iterator<Runnable> iterator = list.iterator();
				Runnable plugin;
				while (iterator.hasNext()) {
					plugin = iterator.next();
					try {
						plugin.run();
					} catch (Exception e) {
						ShutdownManager.LOG.severe(e.getMessage());
					}
				}
			}
		}, "ShutdownManager");
		setShutdownHook(true);
		shuttingDown = false;
	}
	public void register(Runnable plugin) {
		list.add(0, plugin);
	}
	public synchronized boolean isShutdownHook() {
		return shutdownHook;
	}
	protected synchronized void setShutdownHook(boolean b) {
		if (shutdownHook == b) { return; }
		if (b) {
			Runtime.getRuntime().addShutdownHook(shutdownThread);
		} else {
			Runtime.getRuntime().removeShutdownHook(shutdownThread);
		}
		shutdownHook = b;
	}
	public synchronized void shutdown(final int status) {
		if (!shuttingDown) {
			setShutdownHook(false);
			new Thread(new Runnable(){

				public void run() {
					shutdownThread.run();
					System.exit(status);
				}
			}, "ShutdownManager").start();
			shuttingDown = true;
		}
	}
	public static synchronized IShutdownManager getInstance() {
		if (ShutdownManager.instance == null) {
			ShutdownManager.instance = new ShutdownManager();
		}
		return ShutdownManager.instance;
	}
}
