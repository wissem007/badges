package tn.com.digivoip.comman.job;

public interface IWorkerStatusController{

	public void setDisplayText(String text);
	public String getDisplayText();
	public void clearDisplayText();
	public void clearDisplayTextWithDelay();
	public void setProgressBarMaximum(int max);
	public void setProgressBarValue(int value);
	public void resetProgressBar();
	public int getProgessBarMaximum();
	public int getProgressBarValue();
	public void cancel();
	public boolean cancelled();
	public void addWorkerStatusChangeListener(IWorkerStatusChangeListener l);
	public int getTimeStamp();
	/** @param listener */
	public void removeWorkerStatusChangeListener(IWorkerStatusChangeListener listener);
}
