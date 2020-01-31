package tn.com.digivoip.framework.exception;

public class FunctionelException extends RuntimeException{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public FunctionelException() {}
	public FunctionelException(String message) {
		super(message);
	}
	public FunctionelException(Throwable cause) {
		super(cause);
	}
	public FunctionelException(String message, Throwable cause) {
		super(message, cause);
	}
}
