package tn.com.digivoip.comman.job;

import tn.com.digivoip.comman.Lock;

public class DefaultCommandReference implements ICommandReference{

	protected Lock	lock	= new Lock();

	public boolean tryToGetLock(Object locker) {
		return lock.tryToGetLock(locker);
	}
	public void releaseLock(Object locker) {
		lock.release(locker);
	}
}