package tn.com.smartsoft.framework.presentation.view.tags.parser;

import tn.com.smartsoft.commons.xml.exception.ParseException;

public class UIParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIParseException() {
		super();
	}

	public UIParseException(String message, Throwable cause) {
		super(message, cause instanceof UIParseException ? cause.getCause() : cause);
	}

	public UIParseException(String message) {
		super(message);
	}

	public UIParseException(Throwable cause) {
		super(cause.getMessage(), cause instanceof ParseException ? cause.getCause() : cause);
	}

}
