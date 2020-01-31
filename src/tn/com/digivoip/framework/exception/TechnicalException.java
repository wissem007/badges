package tn.com.digivoip.framework.exception;

public class TechnicalException extends RuntimeException{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public TechnicalException(String string) {
		super(string);
	}
	public TechnicalException(Exception e) {
		super(e);
	}
	public TechnicalException(String string, Throwable e) {
		super(string, e);
	}
}
