package tn.com.digivoip.comman.job;

public class StatusObservableImpl implements IStatusObservable{

	private IWorkerStatusController	worker;

	public StatusObservableImpl() {
		// nothing to do
	}
	public StatusObservableImpl(IWorkerStatusController theWorker) {
		this.worker = theWorker;
	}
	public void setCurrent(int i) {
		if (worker != null) {
			worker.setProgressBarValue(i);
		}
	}
	public void setMax(int i) {
		if (worker != null) {
			worker.setProgressBarMaximum(i);
		}
	}
	public void resetCurrent() {
		setCurrent(0);
	}
	public void setMessage(String string) {
		if (worker != null) {
			worker.setDisplayText(string);
		}
	}
	public void clearMessage() {
		if (worker != null) {
			worker.clearDisplayText();
		}
	}
	public void clearMessageWithDelay() {
		if (worker != null) {
			worker.clearDisplayTextWithDelay();
		}
	}
	public IWorkerStatusController getWorker() {
		return worker;
	}
	public void setWorker(IWorkerStatusController theWorker) {
		this.worker = theWorker;
	}
	public boolean isCancelled() {
		return worker.cancelled();
	}
	public void cancel(boolean b) {
		if (b) {
			worker.cancel();
		}
	}
}
