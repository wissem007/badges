package tn.com.smartsoft.framework.dao.definition;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DbCustomSqlErrorDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String sqlErrorCode;
	protected String applicationErrorCode;
	protected Class<?> exceptionClass;

	public DbCustomSqlErrorDefinition() {
		super();
	}

	public DbCustomSqlErrorDefinition(String sqlErrorCode, String applicationErrorCode, Class<?> exceptionClass) {
		super();
		this.sqlErrorCode = sqlErrorCode;
		this.applicationErrorCode = applicationErrorCode;
		this.exceptionClass = exceptionClass;
	}

	public String sqlErrorCode() {
		return sqlErrorCode;
	}

	public String applicationErrorCode() {
		return applicationErrorCode;
	}

	public Class<?> exceptionClass() {
		return exceptionClass;
	}

	public String getSqlErrorCode() {
		return sqlErrorCode;
	}

	public void setSqlErrorCode(String sqlErrorCode) {
		this.sqlErrorCode = sqlErrorCode;
	}

	public String getApplicationErrorCode() {
		return applicationErrorCode;
	}

	public void setApplicationErrorCode(String applicationErrorCode) {
		this.applicationErrorCode = applicationErrorCode;
	}

	public Class<?> getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(Class<?> exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
}
