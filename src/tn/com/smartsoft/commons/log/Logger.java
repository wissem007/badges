package tn.com.smartsoft.commons.log;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class Logger implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEBUG_LEVEL = 0;
	public static final int INFO_LEVEL = 1;
	public static final int WARN_LEVEL = 2;
	public static final int ERROR_LEVEL = 3;
	public static final int FATAL_LEVEL = 4;

	private final Log log;

	public static Logger getLogger(final Object obj) {
		return new Logger(obj);
	}

	private Logger(final Object obj) {
		if (obj instanceof String) {
			log = LogFactory.getLog((String) obj);
		} else if (obj instanceof Class) {
			log = LogFactory.getLog((Class<?>) obj);
		} else {
			log = LogFactory.getLog(obj.getClass());
		}
	}

	public final boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public final void debug(Object message, Throwable throwable) {
		if (isDebugEnabled()) {
			log.debug(message, throwable);
		}
	}

	public final void debug(Object message) {
		if (isDebugEnabled()) {
			log.debug(message);
		}
	}

	public final boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public final void info(Object message, Throwable throwable) {
		if (isInfoEnabled()) {
			log.info(message, throwable);
		}
	}

	public final void info(Object message) {
		if (isInfoEnabled()) {
			log.info(message);
		}
	}

	public final void warn(Object message, Throwable throwable) {
		log.warn(message, throwable);
	}

	public final void warn(Object message) {
		log.warn(message);
	}

	public final void error(Object message, Throwable throwable) {
		log.error(message, throwable);
	}

	public final void error(Object message) {
		log.error(message);
	}

	public final void error(Throwable throwable) {
		new TechnicalException(throwable).printLogTrace(this);
	}

	public final void fatal(Object message, Throwable throwable) {
		log.fatal(message, throwable);
	}

	public final void fatal(Object message) {
		log.fatal(message);
	}

	public final void log(Throwable throwable) {
		error(throwable.getMessage(), throwable);
	}

	public final void log(String message, char messageType) {
		log(message, messageType, null);
	}

	public final void log(String msg, int messageType, Object[] arguments, Throwable cause) {
		log(MessageFormat.format(msg, arguments), messageType, cause);
	}

	public final void log(String msg, int messageType, Object arg0, Throwable cause) {
		log(msg, messageType, new Object[] { arg0 }, cause);
	}

	public final void log(String msg, int messageType, Object arg0, Object arg1, Throwable cause) {
		log(msg, messageType, new Object[] { arg0, arg1 }, cause);
	}

	public final void log(String msg, int messageType, Object arg0, Object arg1, Object arg2, Throwable cause) {
		log(msg, messageType, new Object[] { arg0, arg1, arg2 }, cause);
	}

	public final void log(String message, int messageType, Throwable throwable) {
		if (isEnabledFor(messageType)) {
			switch (messageType) {
			case DEBUG_LEVEL:
				debug(message, throwable);
				break;
			case INFO_LEVEL:
				info(message, throwable);
				break;
			case WARN_LEVEL:
				warn(message, throwable);
				break;
			case ERROR_LEVEL:
				error(message, throwable);
				break;
			case FATAL_LEVEL:
				fatal(message, throwable);
				break;
			default:
				throw new IllegalArgumentException(String.valueOf(messageType));
			}
		}
	}

	public boolean isEnabledFor(int messageType) {
		switch (messageType) {
		case DEBUG_LEVEL:
			return log.isDebugEnabled();
		case INFO_LEVEL:
			return log.isInfoEnabled();
		case WARN_LEVEL:
			return log.isWarnEnabled();
		case ERROR_LEVEL:
			return log.isErrorEnabled();
		case FATAL_LEVEL:
			return log.isFatalEnabled();
		default:
			throw new IllegalArgumentException(String.valueOf(messageType));
		}
	}

}