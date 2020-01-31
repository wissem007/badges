package tn.com.smartsoft.commons.report;

import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class RException extends TechnicalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RException(String msg) {
		super(ERROR_TYPE, msg);
	}

	public RException(int type, String msg) {
		super(type, msg);
	}

}
