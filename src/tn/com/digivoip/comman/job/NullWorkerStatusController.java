package tn.com.digivoip.comman.job;

public class NullWorkerStatusController implements IWorkerStatusController{

	public void removeWorkerStatusChangeListener(IWorkerStatusChangeListener listener) {}

	private static NullWorkerStatusController	myInstance;

	protected NullWorkerStatusController() {}
	public static NullWorkerStatusController getInstance() {
		if (NullWorkerStatusController.myInstance == null) {
			NullWorkerStatusController.myInstance = new NullWorkerStatusController();
		}
		return NullWorkerStatusController.myInstance;
	}
	public void setDisplayText(String text) {
		// nothing to do
	}
	public void clearDisplayText() {
		// nothing to do
	}
	public void clearDisplayTextWithDelay() {
		// nothing to do
	}
	public String getDisplayText() {
		return null;
	}
	public void setProgressBarMaximum(int max) {
		// nothing to do
	}
	public void setProgressBarValue(int value) {
		// nothing to do
	}
	public void resetProgressBar() {
		// nothing to do
	}
	public void incProgressBarValue() {
		// nothing to do
	}
	public void incProgressBarValue(int increment) {
		// nothing to do
	}
	public int getProgessBarMaximum() {
		return 0;
	}
	public int getProgressBarValue() {
		return 0;
	}
	public void cancel() {
		// nothing to do
	}
	public boolean cancelled() {
		return false;
	}
	public void addWorkerStatusChangeListener(IWorkerStatusChangeListener l) {
		// nothing to do
	}
	public int getTimeStamp() {
		return 0;
	}
}
