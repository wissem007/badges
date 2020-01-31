package tn.com.smartsoft.framework.dao.exception;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;

public class DataPropertyValueDbException extends DaoFunctionalException {
	private static final long serialVersionUID = 4932293294070944836L;

	public DataPropertyValueDbException(String appErrorCode, String entite, String field, Throwable cause) {
		super(appErrorCode, entite, field, cause, null);
	}
}
