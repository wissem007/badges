package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class UniqueKeyDbException extends DaoFunctionalException {
	private static final long serialVersionUID = 4932293294070944836L;

	public UniqueKeyDbException(String appErrorCode, Throwable cause, String sql) {
		super(appErrorCode, cause, sql);
	}
}