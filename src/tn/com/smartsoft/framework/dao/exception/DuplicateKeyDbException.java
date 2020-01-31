package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class DuplicateKeyDbException extends DaoFunctionalException {
	private static final long serialVersionUID = 4932293294070944836L;

	public DuplicateKeyDbException(String appErrorCode, Throwable cause, String sql) {
		super(appErrorCode, cause, sql);
	}
}