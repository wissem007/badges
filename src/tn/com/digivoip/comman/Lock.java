package tn.com.digivoip.comman;

public class Lock{

	private boolean	locked;
	private Object	locker;
	private Mutex	mutex;

	public Lock() {
		locked = false;
		mutex = new Mutex();
	}
	public synchronized void getLock(Object theLocker) {
		mutex.lock();
		if (this.locker == theLocker) {
			mutex.release();
			return;
		}
		mutex.release();
		while (!tryToGetLock(theLocker)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// nothing to do
			}
		}
	}
	public boolean tryToGetLock(Object theLocker) {
		mutex.lock();
		// Is it already locked from locker ?
		if ((this.locker == theLocker) && (theLocker != null)) {
			mutex.release();
			return true;
		}
		// Check if locked
		if (locked) {
			mutex.release();
			return false;
		}
		locked = true;
		this.locker = theLocker;
		mutex.release();
		return true;
	}
	public synchronized void release(Object theLocker) {
		mutex.lock();
		if ((this.locker == theLocker) || (this.locker == null)) {
			locked = false;
		}
		notify();
		mutex.release();
	}
}
