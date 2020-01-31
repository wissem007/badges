package tn.com.smartsoft.commons.xml.exception;

public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseException() {
		super();
	}

	public ParseException(String message, Throwable cause) {
		super(message, cause instanceof ParseException ? cause.getCause() : cause);

	}

	public ParseException(String message) {
		super(message);
	}

	public ParseException(Throwable cause) {
		super(cause.getMessage(), cause instanceof ParseException ? cause.getCause() : cause);
	}

}
