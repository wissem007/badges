package tn.com.smartsoft.commons.exceptions;

import java.sql.SQLException;

public class DaoTechnicalException extends TechnicalException implements DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sql;

	public DaoTechnicalException(Throwable sqle, String sql) {
		super(sqle);
		this.sql = sql;
	}

	public DaoTechnicalException(Throwable sqle) {
		super(sqle);
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
