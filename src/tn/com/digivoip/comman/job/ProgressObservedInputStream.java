package tn.com.digivoip.comman.job;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressObservedInputStream extends FilterInputStream{

	private IWorkerStatusController	status;
	private int						read;

	public ProgressObservedInputStream(InputStream arg0, IWorkerStatusController theStatusController) {
		this(arg0, theStatusController, false);
	}
	public ProgressObservedInputStream(InputStream arg0, IWorkerStatusController theStatusController, boolean relative) {
		super(arg0);
		this.status = theStatusController;
		if (!relative) {
			try {
				theStatusController.setProgressBarMaximum(arg0.available());
			} catch (IOException e) {
				// nothing to do
			}
			read = 0;
		} else {
			read = theStatusController.getProgressBarValue();
		}
	}
	public int read() throws IOException {
		int result = super.read();
		if (result != -1) {
			status.setProgressBarValue(++read);
		}
		return result;
	}
	public int read(byte[] arg0, int arg1, int arg2) throws IOException {
		int result = super.read(arg0, arg1, arg2);
		read += result;
		status.setProgressBarValue(read);
		return result;
	}
}