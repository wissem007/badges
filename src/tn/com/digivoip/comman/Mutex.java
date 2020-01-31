package tn.com.digivoip.comman;

public class Mutex{

	private boolean	mutex;

	public Mutex() {
		mutex = false;
	}
	public synchronized void lock() {
		while (mutex) {
			try {
				wait();
			} catch (InterruptedException e) {
				if (Thread.currentThread().isInterrupted()) {
					// gota go now
					throw new RuntimeException(e);
				}
				// else keep waiting
			}
		}
		mutex = true;
	}
	public synchronized void release() {
		mutex = false;
		notify();
	}
}
