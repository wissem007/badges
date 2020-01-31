package tn.com.smartsoft.commons.exceptions;

import java.sql.SQLException;

public class DaoFunctionalException extends FunctionalException implements DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sql;

	public DaoFunctionalException(String errorCode, Throwable cause, String sql) {
		super(errorCode, cause);
		this.sql = sql;
	}

	public DaoFunctionalException(String errorCode, String arg0, String arg1, String arg2, Throwable cause, String sql) {
		super(errorCode, arg0, arg1, arg2, cause);
		this.sql = sql;
	}

	public DaoFunctionalException(String errorCode, String arg0, String arg1, Throwable cause, String sql) {
		super(errorCode, arg0, arg1, cause);
		this.sql = sql;
	}

	public DaoFunctionalException(String errorCode, String arg0, Throwable cause, String sql) {
		super(errorCode, arg0, cause);
		this.sql = sql;
	}

	public DaoFunctionalException(String errorCode, String[] args, Throwable cause, String sql) {
		super(errorCode, args, cause);
		this.sql = sql;
	}

	public String getSQLState() {
		if (cause == null)
			return null;
		if (cause instanceof SQLException) {
			return ((SQLException) cause).getSQLState();
		}
		return null;
	}

	public int getSQLErrorCode() {
		if (cause == null)
			return -1;
		if (cause instanceof SQLException) {
			return ((SQLException) cause).getErrorCode();
		}
		return -1;
	}

	public SQLException getSQLException() {
		if (cause == null)
			return null;
		if (cause instanceof SQLException) {
			return ((SQLException) cause);
		}
		return null;
	}

	public String getSql() {
		return sql;
	}
}