package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class CannotAcquireLockDbException extends DaoFunctionalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4606621137119912510L;

	public CannotAcquireLockDbException(String appErrorCode, Throwable cause, String sql) {
		super(appErrorCode, cause, sql);
	}
}