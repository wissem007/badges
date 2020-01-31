package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class ConnectionFailureDbException extends DaoFunctionalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5385749231897299255L;

	public ConnectionFailureDbException(String appErrorCode, Throwable cause, String sql) {
		super(appErrorCode, cause, sql);
	}

}
