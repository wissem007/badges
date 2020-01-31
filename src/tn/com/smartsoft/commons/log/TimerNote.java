package tn.com.smartsoft.commons.log;

import java.io.Serializable;

public class TimerNote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startTime;
	private long endTime;

	private TimerNote() {
		super();
	}

	public void startTime() {
		this.startTime = System.currentTimeMillis();
	}

	public void endTime() {
		this.endTime = System.currentTimeMillis();
	}

	public void dispalyExecuteTime(String message) {
		long executeTime = (this.endTime - this.startTime) / 1000;
		System.out.println(message + " :" + executeTime+ "(s)");
	}

	public void dispalyExecuteTime() {
		dispalyExecuteTime("Execute Time is");
	}

	public void dispalyExecuteTime(Logger log, String message) {
		long executeTime = (this.endTime - this.startTime) / 1000;
		log.info(message + " :" + executeTime + "(s)");
	}

	public void dispalyExecuteTime(Logger log) {
		dispalyExecuteTime(log, "Execute Time is");
	}

	public static TimerNote createTimerNote() {
		return new TimerNote();
	}
}
