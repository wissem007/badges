package tn.com.digivoip.comman.job;

public interface ICommandReference{

	boolean tryToGetLock(Object locker);
	void releaseLock(Object locker);
}