package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class IntegrityViolationDbException extends DaoFunctionalException {
	private static final long serialVersionUID = 4932293294070944836L;

	public IntegrityViolationDbException(String appErrorCode, Throwable cause, String sql) {
		super(appErrorCode, cause, sql);
	}
}