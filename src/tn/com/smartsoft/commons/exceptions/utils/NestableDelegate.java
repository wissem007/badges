package tn.com.smartsoft.commons.exceptions.utils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.Nestable;

public class NestableDelegate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MUST_BE_THROWABLE = "The Nestable implementation passed to the NestableDelegate(Nestable) "
			+ "constructor must extend java.lang.Throwable";

	private Throwable nestable = null;

	private static boolean topDown = true;

	private static boolean trimStackFrames = true;

	public NestableDelegate(Nestable nestable) {
		if (nestable instanceof Throwable) {
			this.nestable = (Throwable) nestable;
		} else {
			throw new IllegalArgumentException(MUST_BE_THROWABLE);
		}
	}

	public String getMessage(int index) {
		Throwable t = this.getThrowable(index);
		if (Nestable.class.isInstance(t)) {
			return ((Nestable) t).getMessage(0);
		} else {
			return t.getMessage();
		}
	}

	public String getMessage(String baseMsg) {
		StringBuffer msg = new StringBuffer();
		if (baseMsg != null) {
			msg.append(baseMsg);
		}

		Throwable nestedCause = ExceptionUtils.getCause(this.nestable);
		if (nestedCause != null) {
			String causeMsg = nestedCause.getMessage();
			if (causeMsg != null) {
				if (baseMsg != null) {
					msg.append(": ");
				}
				msg.append(causeMsg);
			}

		}
		return (msg.length() > 0 ? msg.toString() : null);
	}

	public String[] getMessages() {
		Throwable[] throwables = this.getThrowables();
		String[] msgs = new String[throwables.length];
		for (int i = 0; i < throwables.length; i++) {
			msgs[i] = Nestable.class.isInstance(throwables[i]) ? ((Nestable) throwables[i]).getMessage(0) : throwables[i].getMessage();
		}
		return msgs;
	}

	public Throwable getThrowable(int index) {
		if (index == 0) {
			return this.nestable;
		}
		Throwable[] throwables = this.getThrowables();
		return throwables[index];
	}

	public int getThrowableCount() {
		return ExceptionUtils.getThrowableCount(this.nestable);
	}

	public Throwable[] getThrowables() {
		return ExceptionUtils.getThrowables(this.nestable);
	}

	public int indexOfThrowable(Class type, int fromIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("The start index was out of bounds: " + fromIndex);
		}
		Throwable[] throwables = ExceptionUtils.getThrowables(this.nestable);
		if (fromIndex >= throwables.length) {
			throw new IndexOutOfBoundsException("The start index was out of bounds: " + fromIndex + " >= " + throwables.length);
		}
		for (int i = fromIndex; i < throwables.length; i++) {
			if (throwables[i].getClass().equals(type)) {
				return i;
			}
		}
		return -1;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream out) {
		synchronized (out) {
			PrintWriter pw = new PrintWriter(out, false);
			printStackTrace(pw);
			// Flush the PrintWriter before it's GC'ed.
			pw.flush();
		}
	}

	public void printStackTrace(PrintWriter out) {
		Throwable throwable = this.nestable;
		// if running on jre1.4 or higher, use default printStackTrace
		if (ExceptionUtils.isThrowableNested()) {
			if (throwable instanceof Nestable) {
				((Nestable) throwable).printPartialStackTrace(out);
			} else {
				throwable.printStackTrace(out);
			}
			return;
		}

		// generating the nested stack trace
		List stacks = new ArrayList();
		while (throwable != null) {
			String[] st = getStackFrames(throwable);
			stacks.add(st);
			throwable = ExceptionUtils.getCause(throwable);
		}

		// If NOT topDown, reverse the stack
		String separatorLine = "Caused by: ";
		if (!topDown) {
			separatorLine = "Rethrown as: ";
			Collections.reverse(stacks);
		}

		// Remove the repeated lines in the stack
		if (trimStackFrames)
			trimStackFrames(stacks);

		synchronized (out) {
			for (Iterator iter = stacks.iterator(); iter.hasNext();) {
				String[] st = (String[]) iter.next();
				for (int i = 0, len = st.length; i < len; i++) {
					out.println(st[i]);
				}
				if (iter.hasNext())
					out.print(separatorLine);
			}
		}
	}

	protected String[] getStackFrames(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);

		// Avoid infinite loop between decompose() and printStackTrace().
		if (t instanceof Nestable) {
			((Nestable) t).printPartialStackTrace(pw);
		} else {
			t.printStackTrace(pw);
		}
		return ExceptionUtils.getStackFrames(sw.getBuffer().toString());
	}

	protected void trimStackFrames(List stacks) {
		for (int size = stacks.size(), i = size - 1; i > 0; i--) {
			String[] curr = (String[]) stacks.get(i);
			String[] next = (String[]) stacks.get(i - 1);

			List currList = new ArrayList(Arrays.asList(curr));
			List nextList = new ArrayList(Arrays.asList(next));
			ExceptionUtils.removeCommonFrames(currList, nextList);

			int trimmed = curr.length - currList.size();
			if (trimmed > 0) {
				currList.add("\t... " + trimmed + " more");
				stacks.set(i, currList.toArray(new String[currList.size()]));
			}
		}
	}
}
