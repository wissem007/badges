package tn.com.smartsoft.framework.dao.definition;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DbDialectDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dialectClassName;
	private String productName;
	private boolean useSqlStateForTranslation = false;
	private Map<String, DbCustomSqlErrorDefinition> sqlErrors = new HashMap<String, DbCustomSqlErrorDefinition>();
	private DataBaseDefinition dataBaseDefinition;

	public DbDialectDefinition() {
		super();
	}

	public DbDialectDefinition(String dialectClassName, String productName, boolean useSqlStateForTranslation) {
		super();
		this.dialectClassName = dialectClassName;
		this.productName = productName;
		this.useSqlStateForTranslation = useSqlStateForTranslation;

	}

	public String getProductName() {
		return productName;
	}

	public DataBaseDefinition getDataBaseDefinition() {
		return dataBaseDefinition;
	}

	public void setDataBaseDefinition(DataBaseDefinition dataBaseDefinition) {
		this.dataBaseDefinition = dataBaseDefinition;
	}

	public void addSqlErrorValue(String sqlErrorCode, String applicationErrorCode, Class<?> exceptionClass) {
		String[] sqlErrorCodes = sqlErrorCode.split(",");
		for (int i = 0; i < sqlErrorCodes.length; i++) {
			sqlErrors.put(sqlErrorCodes[i].trim(), new DbCustomSqlErrorDefinition(sqlErrorCode, applicationErrorCode, exceptionClass));
		}
	}

	public void addSqlErrorValue(DbCustomSqlErrorDefinition value) {
		addSqlErrorValue(value.getSqlErrorCode(), value.getApplicationErrorCode(), value.getExceptionClass());
	}

	public DbCustomSqlErrorDefinition getCustomSqlErrorValue(String sqlErrorCode) {
		return (DbCustomSqlErrorDefinition) sqlErrors.get(sqlErrorCode);
	}

	public boolean isUseSqlStateForTranslation() {
		return useSqlStateForTranslation;
	}

	public String getDialectClassName() {
		return dialectClassName;
	}

	public Map<String, DbCustomSqlErrorDefinition> getSqlErrors() {
		return sqlErrors;
	}

	public void setSqlErrors(Map<String, DbCustomSqlErrorDefinition> sqlErrors) {
		this.sqlErrors = sqlErrors;
	}

	public void setDialectClassName(String dialectClassName) {
		this.dialectClassName = dialectClassName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setUseSqlStateForTranslation(boolean useSqlStateForTranslation) {
		this.useSqlStateForTranslation = useSqlStateForTranslation;
	}
}
