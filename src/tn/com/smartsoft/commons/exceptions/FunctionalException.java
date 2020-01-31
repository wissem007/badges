package tn.com.smartsoft.commons.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.ArrayUtils;

import tn.com.smartsoft.commons.exceptions.utils.NestableDelegate;
import tn.com.smartsoft.commons.log.Logger;

public class FunctionalException extends Exception implements Nestable {
	private static final long serialVersionUID = 6573344628407841861L;
	private String errorCode;
	private String[] args;
	protected NestableDelegate delegate = new NestableDelegate(this);
	protected Throwable cause = null;

	public FunctionalException(String errorCode, String[] args, Throwable cause) {
		this.errorCode = errorCode;
		this.args = args;
		this.cause = cause;
	}

	public FunctionalException(String errorCode, Throwable cause) {
		this(errorCode, (String[]) null, cause);
	}

	public FunctionalException(String errorCode, String arg0, Throwable cause) {
		this(errorCode, new String[] { arg0 }, cause);
	}

	public FunctionalException(String errorCode, String arg0, String arg1, Throwable cause) {
		this(errorCode, new String[] { arg0, arg1 }, cause);
	}

	public FunctionalException(String errorCode, String arg0, String arg1, String arg2, Throwable cause) {
		this(errorCode, new String[] { arg0, arg1, arg2 }, cause);
	}

	public FunctionalException(String errorCode) {
		this(errorCode, (String[]) null, null);
	}

	public FunctionalException(String errorCode, String arg0) {
		this(errorCode, new String[] { arg0 }, null);
	}

	public FunctionalException(String errorCode, String arg0, String arg1) {
		this(errorCode, new String[] { arg0, arg1 }, null);
	}

	public FunctionalException(String errorCode, String arg0, String arg1, String arg2) {
		this(errorCode, new String[] { arg0, arg1, arg2 }, null);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		return "FunctionalException --> Error Code :" + getErrorCode() + ((args == null) ? "" : " Args: " + ArrayUtils.toString(args));
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

	public void printLogTrace(int messageType, Logger log) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		delegate.printStackTrace(pw);
		log.log(getMessage(), messageType, getCause());
	}

}
