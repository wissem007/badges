package tn.com.digivoip.comman.job;

/** A Semaphore class to be used by multiple threads that needs to be notified when a thread has released this semaphore. Depending threads uses one of the waitUntilReleased() method to wait for the
 * semaphore, then when the Thread that owns the Semaphore (called the hold() method) calls the release method all other threads are awaken. All threads that invoke waitUntilReleased() waits until a
 * thread invokes the release()
 * @author erma */
public final class Semaphore{

	private Object	lockObject	= new Object();
	private boolean	isReleased	= false;

	/** Creates a new instance of Semaphore */
	public Semaphore() {
		this(true);
	}
	/** Creates a new instance of Semaphore
	 * @param isHolding if the semaphore should hold all threads from the begining or not. */
	public Semaphore(boolean isHolding) {
		if (isHolding) {
			hold();
		} else {
			release();
		}
	}
	/** Holds this semaphores. Threads that call the waitUntilReleased stops until the release() method is called.
	 * @see #release() */
	public void hold() {
		isReleased = false;
	}
	/** Waits the current thread until another thread invokes the relase() or continues if another thread already has invoked the release(). This method behaves exactly as if it simply performs the
	 * call waitUntilReleased(0).
	 * @throws InterruptedException if another thread has interrupted the current thread. */
	public void waitUntilReleased() throws InterruptedException {
		waitUntilReleased(0);
	}
	/** Waits the current thread until another thread invokes the relase() or continues if another thread already has invoked the release().
	 * @param timeout the maximum time to wait in milliseconds.
	 * @throws InterruptedException if another thread has interrupted the current thread. */
	public void waitUntilReleased(long timeout) throws InterruptedException {
		if (!isReleased) {
			synchronized (lockObject) {
				lockObject.wait(timeout);
			}
		}
	}
	/** Release all threads that has invoked the waitUntilReleased(). */
	public void release() {
		isReleased = true;
		synchronized (lockObject) {
			lockObject.notifyAll();
		}
	}
	/** Returns if the semaphore holds all threads that invoke the waitUntilReleased() methods.
	 * @return true or false */
	public boolean isHolding() {
		return !isReleased;
	}
}
