package tn.com.smartsoft.commons.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import tn.com.smartsoft.commons.exceptions.utils.NestableDelegate;
import tn.com.smartsoft.commons.log.Logger;

public class ConfigurationException extends Exception implements Nestable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected NestableDelegate delegate = new NestableDelegate(this);
	protected Throwable cause = null;
	private int type;
	public static final int FATAL_TYPE = 0;
	public static final int ERROR_TYPE = 1;
	public static final int WARN_TYPE = 2;
	public static final String MESSAGE_CODE_SYSTEM = "0300001";

	public ConfigurationException(int type, String msg) {
		super(msg);
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public String getMessageCode() {
		return MESSAGE_CODE_SYSTEM;
	}

	public ConfigurationException(int type, Throwable cause) {
		super(cause.getMessage());
		if (cause instanceof ConfigurationException) {
			ConfigurationException ConfigurationException = (ConfigurationException) cause;
			this.cause = ConfigurationException.getCause();
			this.type = ConfigurationException.getType();
		} else {
			this.cause = cause;
			this.type = type;
		}
	}

	public ConfigurationException(int type, String msg, Throwable cause) {
		super(msg);
		if (cause instanceof ConfigurationException) {
			ConfigurationException ConfigurationException = (ConfigurationException) cause;
			this.cause = ConfigurationException.getCause();
			this.type = ConfigurationException.getType();
		} else {
			this.cause = cause;
			this.type = type;
		}
	}

	public ConfigurationException(String msg) {
		this(ERROR_TYPE, msg);
	}

	public ConfigurationException(Throwable cause) {
		this(ERROR_TYPE, cause);
	}

	public ConfigurationException(String msg, Throwable cause) {
		this(ERROR_TYPE, msg, cause);
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		if (super.getMessage() != null) {
			return super.getMessage();
		} else if (cause != null) {
			return cause.toString();
		} else {
			return null;
		}
	}

	public String getMessage(int index) {
		if (index == 0) {
			return super.getMessage();
		} else {
			return delegate.getMessage(index);
		}
	}

	public String[] getMessages() {
		return delegate.getMessages();
	}

	public Throwable getThrowable(int index) {
		return delegate.getThrowable(index);
	}

	public int getThrowableCount() {
		return delegate.getThrowableCount();
	}

	public Throwable[] getThrowables() {
		return delegate.getThrowables();
	}

	public int indexOfThrowable(Class type) {
		return delegate.indexOfThrowable(type, 0);
	}

	public int indexOfThrowable(Class type, int fromIndex) {
		return delegate.indexOfThrowable(type, fromIndex);
	}

	public void printStackTrace() {
		delegate.printStackTrace();
	}

	public void printStackTrace(PrintStream out) {
		delegate.printStackTrace(out);
	}

	public void printStackTrace(PrintWriter out) {
		delegate.printStackTrace(out);
	}

	public final void printPartialStackTrace(PrintWriter out) {
		super.printStackTrace(out);
	}

	public void printLogTrace(Logger log) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		delegate.printStackTrace(pw);
		log.log(getMessage(), getType(), getCause());
	}

}
