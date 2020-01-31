package tn.com.smartsoft.commons.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface Nestable {

	public Throwable getCause();

	public String getMessage();

	public String getMessage(int index);

	public String[] getMessages();

	public Throwable getThrowable(int index);

	public int getThrowableCount();

	public Throwable[] getThrowables();

	public int indexOfThrowable(Class type);

	public int indexOfThrowable(Class type, int fromIndex);

	public void printStackTrace(PrintWriter out);

	public void printStackTrace(PrintStream out);

	public void printPartialStackTrace(PrintWriter out);

}
