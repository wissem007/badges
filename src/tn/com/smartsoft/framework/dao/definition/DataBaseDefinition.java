package tn.com.smartsoft.framework.dao.definition;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DataBaseDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, DataSourceDefinition> dataSourceDefinitions = new HashMap<String, DataSourceDefinition>();
	private Map<String, DbDialectDefinition> dbDialectDefinitions = new HashMap<String, DbDialectDefinition>();
	private Map<String, String> customSqlErrorCategories = new HashMap<String, String>();

	public void addCustomSqlErrorCategories(String name, String applicationErrorCode) {
		customSqlErrorCategories.put(name, applicationErrorCode);
	}

	public String getCustomSqlErrorCategories(String name) {
		return (String) customSqlErrorCategories.get(name);
	}

	public void addDbDialectDefinition(DbDialectDefinition dbDialectDefinition) {
		dbDialectDefinition.setDataBaseDefinition(this);
		dbDialectDefinitions.put(dbDialectDefinition.getProductName(), dbDialectDefinition);
	}

	public DbDialectDefinition getDbDialectDefinition(String productName) {
		return (DbDialectDefinition) dbDialectDefinitions.get(productName);
	}

	public void addDataSourceDefinition(DataSourceDefinition dataSourceDefinition) {
		dataSourceDefinition.setDataBaseDefinition(this);
		dataSourceDefinitions.put(dataSourceDefinition.getName(), dataSourceDefinition);
	}

	public DataSourceDefinition getDataSourceDefinition(String name) {
		return (DataSourceDefinition) dataSourceDefinitions.get(name);
	}

	public Map<String, DataSourceDefinition> getDataSourceDefinitions() {
		return dataSourceDefinitions;
	}

}
