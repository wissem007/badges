package tn.com.smartsoft.commons.exceptions;

import java.sql.SQLException;

public interface DaoException {
	public String getSql();

	public String getSQLState();

	public int getSQLErrorCode();

	public SQLException getSQLException();
}
