package tn.com.smartsoft.framework.presentation.definition;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class ReportDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String location;
	private String type;
	private String dataSourceProperty;

	public String getDataSourceProperty() {
		return dataSourceProperty;
	}

	public void setDataSourceProperty(String dataSourceProperty) {
		this.dataSourceProperty = dataSourceProperty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ReportDefinition() {
		super();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
