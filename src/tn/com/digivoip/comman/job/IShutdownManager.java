package tn.com.digivoip.comman.job;

public interface IShutdownManager{

	public abstract void register(Runnable plugin);
	public abstract void shutdown(final int status);
}