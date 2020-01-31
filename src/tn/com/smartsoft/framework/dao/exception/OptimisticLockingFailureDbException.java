package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class OptimisticLockingFailureDbException extends DaoFunctionalException {
	private static final long serialVersionUID = 4932293294070944836L;

	public OptimisticLockingFailureDbException(String appErrorCode, Throwable cause) {
		super(appErrorCode, cause, null);
	}
}