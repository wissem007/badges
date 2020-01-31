package tn.com.digivoip.comman.job;

public abstract class SwingWorker{

	protected Object	value;		// see getValue(), setValue()
	protected Thread	thread;
	protected boolean	cancel;
	protected ThreadVar	threadVar;

	public SwingWorker() {
		cancel = false;
		new Runnable(){

			public void run() {
				finished();
			}
		};
		Runnable doConstruct = new Runnable(){

			public void run() {
				try {
					setValue(construct());
				} finally {
					// threadVar;
				}
			}
		};
		Thread t = new Thread(doConstruct);
		t.setPriority(Thread.MIN_PRIORITY);
		threadVar = new ThreadVar(t);
	}
	protected synchronized Object getValue() {
		return value;
	}
	synchronized void setValue(Object x) {
		value = x;
	}
	public Thread getThread() {
		return threadVar.get();
	}
	public ThreadVar getThreadVar() {
		return threadVar;
	}
	public boolean getCancel() {
		return cancel;
	}
	public void setCancel(boolean b) {
		cancel = b;
	}
	protected boolean isCanceled() {
		return cancel;
	}
	public abstract Object construct();
	public void finished() {
		// nothing to do
	}
	public void interrupt() {
		Thread t = threadVar.get();
		if (t != null) {
			t.interrupt();
		}
		threadVar.clear();
	}
	public Object get() {
		while (true) {
			Thread t = threadVar.get();
			if (t == null) { return getValue(); }
			try {
				t.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // propagate
				return null;
			}
		}
	}
	/** Start the worker thread. */
	public Thread start() {
		thread = threadVar.get();
		if (thread != null) {
			thread.start();
			return thread;
		}
		return null;
	}

	public static class ThreadVar{

		private Thread	thread;

		ThreadVar(Thread t) {
			thread = t;
		}
		synchronized Thread get() {
			return new Thread(thread);
		}
		synchronized void clear() {
			thread = null;
		}
	}
}
