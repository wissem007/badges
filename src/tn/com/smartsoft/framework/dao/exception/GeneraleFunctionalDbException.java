package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class GeneraleFunctionalDbException extends DaoFunctionalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4606621137119912510L;

	public GeneraleFunctionalDbException(String appErrorCode, Throwable cause, String sql) {
		super(appErrorCode, cause, sql);
	}

}
