package tn.com.smartsoft.framework.dao.definition;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DaoQueryMethodDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<?> classResultat;
	private String query;
	protected String name;
	private DbQueryAccessorDefinition accessor;
	private boolean isUpdateQuery = false;
	private String daoParseBeanRef;
	private int sessionArgRang = 0;
	private String prefixArgName;

	public String getName() {
		return name;
	}

	public int getSessionArgRang() {
		return sessionArgRang;
	}

	public void setSessionArgRang(int sessionArgRang) {
		this.sessionArgRang = sessionArgRang;
	}

	public String getPrefixArgName() {
		return prefixArgName;
	}

	public void setPrefixArgName(String prefixArgName) {
		this.prefixArgName = prefixArgName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDaoParseBeanRef() {
		return daoParseBeanRef;
	}

	public void setDaoParseBeanRef(String daoParseBeanRef) {
		this.daoParseBeanRef = daoParseBeanRef;
	}

	public boolean isUpdateQuery() {
		return isUpdateQuery;
	}

	public void setUpdateQuery(boolean isUpdateQuery) {
		this.isUpdateQuery = isUpdateQuery;
	}

	public Class<?> getClassResultat() {
		return classResultat;
	}

	public void setClassResultat(Class<?> classResultat) {
		this.classResultat = classResultat;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public DbQueryAccessorDefinition getAccessor() {
		return accessor;
	}

	public void setAccessor(DbQueryAccessorDefinition accessor) {
		this.accessor = accessor;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
