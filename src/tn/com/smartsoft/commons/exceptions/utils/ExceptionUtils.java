package tn.com.smartsoft.commons.exceptions.utils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.Nestable;
import tn.com.smartsoft.commons.exceptions.TechnicalException;

public final class ExceptionUtils {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	static final String WRAPPED_MARKER = " [wrapped] ";

	private static final String[] CAUSE_METHOD_NAMES = { "getCause", "getNextException", "getTargetException", "getException", "getSourceException",
			"getRootCause", "getCausedByException", "getNested" };

	private static final Method THROWABLE_CAUSE_METHOD;

	static {
		Method getCauseMethod;
		try {
			getCauseMethod = Throwable.class.getMethod("getCause", null);
		} catch (Exception e) {
			getCauseMethod = null;
		}
		THROWABLE_CAUSE_METHOD = getCauseMethod;
	}

	private ExceptionUtils() {
	}

	public static Throwable getCause(Throwable throwable) {
		return getCause(throwable, CAUSE_METHOD_NAMES);
	}

	public static Throwable getCause(Throwable throwable, String[] methodNames) {
		if (throwable == null) {
			return null;
		}
		Throwable cause = getCauseUsingWellKnownTypes(throwable);
		if (cause == null) {
			if (methodNames == null) {
				methodNames = CAUSE_METHOD_NAMES;
			}
			for (int i = 0; i < methodNames.length; i++) {
				String methodName = methodNames[i];
				if (methodName != null) {
					cause = getCauseUsingMethodName(throwable, methodName);
					if (cause != null) {
						break;
					}
				}
			}
			if (cause == null) {
				cause = getCauseUsingFieldName(throwable, "detail");
			}
		}
		return cause;
	}

	public static Throwable getRootCause(Throwable throwable) {
		Throwable cause = getCause(throwable);
		if (cause != null) {
			throwable = cause;
			while ((throwable = getCause(throwable)) != null) {
				cause = throwable;
			}
		}
		return cause;
	}

	private static Throwable getCauseUsingWellKnownTypes(Throwable throwable) {
		if (throwable instanceof Nestable) {
			return ((Nestable) throwable).getCause();
		} else if (throwable instanceof SQLException) {
			return ((SQLException) throwable).getNextException();
		} else if (throwable instanceof InvocationTargetException) {
			return ((InvocationTargetException) throwable).getTargetException();
		} else {
			return null;
		}
	}

	private static Throwable getCauseUsingMethodName(Throwable throwable, String methodName) {
		Method method = null;
		try {
			method = throwable.getClass().getMethod(methodName, null);
		} catch (NoSuchMethodException ignored) {
		} catch (SecurityException ignored) {
		}

		if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
			try {
				return (Throwable) method.invoke(throwable, new Object[] {});
			} catch (IllegalAccessException ignored) {
			} catch (IllegalArgumentException ignored) {
			} catch (InvocationTargetException ignored) {
			}
		}
		return null;
	}

	private static Throwable getCauseUsingFieldName(Throwable throwable, String fieldName) {
		Field field = null;
		try {
			field = throwable.getClass().getField(fieldName);
		} catch (NoSuchFieldException ignored) {
		} catch (SecurityException ignored) {
		}

		if (field != null && Throwable.class.isAssignableFrom(field.getType())) {
			try {
				return (Throwable) field.get(throwable);
			} catch (IllegalAccessException ignored) {
			} catch (IllegalArgumentException ignored) {
			}
		}
		return null;
	}

	public static boolean isThrowableNested() {
		return (THROWABLE_CAUSE_METHOD != null);
	}

	public static boolean isNestedThrowable(Throwable throwable) {
		if (throwable == null) {
			return false;
		}
		if (throwable instanceof Nestable) {
			return true;
		} else if (throwable instanceof SQLException) {
			return true;
		} else if (throwable instanceof InvocationTargetException) {
			return true;
		} else if (isThrowableNested()) {
			return true;
		}
		Class cls = throwable.getClass();
		for (int i = 0, isize = CAUSE_METHOD_NAMES.length; i < isize; i++) {
			try {
				Method method = cls.getMethod(CAUSE_METHOD_NAMES[i], null);
				if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
					return true;
				}
			} catch (NoSuchMethodException ignored) {
			} catch (SecurityException ignored) {
			}
		}
		try {
			Field field = cls.getField("detail");
			if (field != null) {
				return true;
			}
		} catch (NoSuchFieldException ignored) {
		} catch (SecurityException ignored) {
		}

		return false;
	}

	public static int getThrowableCount(Throwable throwable) {
		int count = 0;
		while (throwable != null) {
			count++;
			throwable = ExceptionUtils.getCause(throwable);
		}
		return count;
	}

	public static Throwable[] getThrowables(Throwable throwable) {
		List list = new ArrayList();
		while (throwable != null) {
			list.add(throwable);
			throwable = ExceptionUtils.getCause(throwable);
		}
		return (Throwable[]) list.toArray(new Throwable[list.size()]);
	}

	public static int indexOfThrowable(Throwable throwable, Class type) {
		return indexOfThrowable(throwable, type, 0);
	}

	public static int indexOfThrowable(Throwable throwable, Class type, int fromIndex) {
		if (throwable == null) {
			return -1;
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		Throwable[] throwables = ExceptionUtils.getThrowables(throwable);
		if (fromIndex >= throwables.length) {
			return -1;
		}
		for (int i = fromIndex; i < throwables.length; i++) {
			if (throwables[i].getClass().equals(type)) {
				return i;
			}
		}
		return -1;
	}

	public static void printRootCauseStackTrace(Throwable throwable) {
		printRootCauseStackTrace(throwable, System.err);
	}

	public static void printRootCauseStackTrace(Throwable throwable, PrintStream stream) {
		if (throwable == null) {
			return;
		}
		if (stream == null) {
			throw new IllegalArgumentException("The PrintStream must not be null");
		}
		String trace[] = getRootCauseStackTrace(throwable);
		for (int i = 0; i < trace.length; i++) {
			stream.println(trace[i]);
		}
		stream.flush();
	}

	public static void printRootCauseStackTrace(Throwable throwable, PrintWriter writer) {
		if (throwable == null) {
			return;
		}
		if (writer == null) {
			throw new IllegalArgumentException("The PrintWriter must not be null");
		}
		String trace[] = getRootCauseStackTrace(throwable);
		for (int i = 0; i < trace.length; i++) {
			writer.println(trace[i]);
		}
		writer.flush();
	}

	public static String[] getRootCauseStackTrace(Throwable throwable) {
		if (throwable == null) {
			return new String[] {};
		}
		Throwable throwables[] = getThrowables(throwable);
		int count = throwables.length;
		ArrayList frames = new ArrayList();
		List nextTrace = getStackFrameList(throwables[count - 1]);
		for (int i = count; --i >= 0;) {
			List trace = nextTrace;
			if (i != 0) {
				nextTrace = getStackFrameList(throwables[i - 1]);
				removeCommonFrames(trace, nextTrace);
			}
			if (i == count - 1) {
				frames.add(throwables[i].toString());
			} else {
				frames.add(WRAPPED_MARKER + throwables[i].toString());
			}
			for (int j = 0; j < trace.size(); j++) {
				frames.add(trace.get(j));
			}
		}
		return (String[]) frames.toArray(new String[0]);
	}

	public static void removeCommonFrames(List causeFrames, List wrapperFrames) {
		if (causeFrames == null || wrapperFrames == null) {
			throw new IllegalArgumentException("The List must not be null");
		}
		int causeFrameIndex = causeFrames.size() - 1;
		int wrapperFrameIndex = wrapperFrames.size() - 1;
		while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
			// Remove the frame from the cause trace if it is the same
			// as in the wrapper trace
			String causeFrame = (String) causeFrames.get(causeFrameIndex);
			String wrapperFrame = (String) wrapperFrames.get(wrapperFrameIndex);
			if (causeFrame.equals(wrapperFrame)) {
				causeFrames.remove(causeFrameIndex);
			}
			causeFrameIndex--;
			wrapperFrameIndex--;
		}
	}

	public static void throwException(Throwable e) throws FunctionalException {
		Throwable cause = e.getCause();
		if (FunctionalException.class.isAssignableFrom(e.getClass())) {
			throw (FunctionalException) e;
		} else if (cause != null && FunctionalException.class.isAssignableFrom(cause.getClass())) {
			throw (FunctionalException) cause;
		}
		if (TechnicalException.class.isAssignableFrom(e.getClass())) {
			throw (TechnicalException) e;
		} else if (cause != null && TechnicalException.class.isAssignableFrom(cause.getClass())) {
			throw (TechnicalException) cause;
		}
		throw new TechnicalException(e);
	}

	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public static String getFullStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		Throwable[] ts = getThrowables(throwable);
		for (int i = 0; i < ts.length; i++) {
			ts[i].printStackTrace(pw);
			if (isNestedThrowable(ts[i])) {
				break;
			}
		}
		return sw.getBuffer().toString();
	}

	public static String[] getStackFrames(Throwable throwable) {
		if (throwable == null) {
			return new String[] {};
		}
		return getStackFrames(getStackTrace(throwable));
	}

	static String[] getStackFrames(String stackTrace) {
		String linebreak = LINE_SEPARATOR;
		StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
		List list = new LinkedList();
		while (frames.hasMoreTokens()) {
			list.add(frames.nextToken());
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	static List getStackFrameList(Throwable t) {
		String stackTrace = getStackTrace(t);
		String linebreak = LINE_SEPARATOR;
		StringTokenizer frames = new StringTokenizer(stackTrace, linebreak);
		List list = new LinkedList();
		boolean traceStarted = false;
		while (frames.hasMoreTokens()) {
			String token = frames.nextToken();
			// Determine if the line starts with <whitespace>at
			int at = token.indexOf("at");
			if (at != -1 && token.substring(0, at).trim().length() == 0) {
				traceStarted = true;
				list.add(token);
			} else if (traceStarted) {
				break;
			}
		}
		return list;
	}

}
