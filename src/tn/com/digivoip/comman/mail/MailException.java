package tn.com.digivoip.comman.mail;

import tn.com.digivoip.framework.exception.TechnicalException;

/** Mailing exception. */
public class MailException extends TechnicalException{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public MailException(String message) {
		super(message);
	}
	public MailException(String message, Throwable t) {
		super(message, t);
	}
	public MailException(Throwable t) {
		super("", t);
	}
}
