package tn.com.smartsoft.framework.dao.definition;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Table;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DataSourceDefinition extends Configuration implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String productName;
	private DataBaseDefinition dataBaseDefinition;

	private Map<String, DbConstraintDefinition> constraintDefinitions = new HashMap<String, DbConstraintDefinition>();
	private SessionFactory sessionFactory;

	public void addConstraintDefinition(DbConstraintDefinition constraintDefinition) {
		constraintDefinitions.put(constraintDefinition.getId(), constraintDefinition);
	}

	public Map<String, DbConstraintDefinition> getConstraintDefinitions() {
		return constraintDefinitions;
	}

	public DbDialectDefinition getDialectDefinition() {
		return dataBaseDefinition.getDbDialectDefinition(productName);
	}

	public String getCustomSqlErrorCategories(String name) {
		return dataBaseDefinition.getCustomSqlErrorCategories(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public SessionFactory buildSessionFactory() throws HibernateException {
		if (getDialectDefinition() != null && getDialectDefinition().getDialectClassName() != null)
			setProperty("hibernate.dialect", getDialectDefinition().getDialectClassName());
		this.sessionFactory = super.buildSessionFactory();
		return this.sessionFactory;
	}

	public Configuration setProperty(String propertyName, String value) {
		if (propertyName.startsWith("hibernate"))
			return super.setProperty(propertyName, value);
		else
			return super.setProperty("hibernate." + propertyName, value);
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public Map<?, ?> getMapedTableMappings() {
		return tables;
	}

	public Table getTableMapping(String tableName) {
		return (Table) tables.get(tableName);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public DataBaseDefinition getDataBaseDefinition() {
		return dataBaseDefinition;
	}

	public void setDataBaseDefinition(DataBaseDefinition dataBaseDefinition) {
		this.dataBaseDefinition = dataBaseDefinition;
	}

}
