package tn.com.digivoip.framework.exception;

public class DataAccessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public DataAccessException() {}
	public DataAccessException(String message) {
		super(message);
	}
	public DataAccessException(Throwable cause) {
		super(cause);
	}
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
