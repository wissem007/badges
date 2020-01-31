package tn.com.digivoip.comman.job;

public interface IStatusObservable{

	public void setCurrent(int i);
	public void setMax(int i);
	public void resetCurrent();
	public boolean isCancelled();
	public void cancel(boolean b);
	public void setMessage(String string);
	public void clearMessage();
	public void clearMessageWithDelay();
}
