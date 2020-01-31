package tn.com.smartsoft.framework.presentation.view.response.json;

import java.io.Serializable;

public class DataJsonItemResponseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;
	private String[] include;
	private String[] exclude;
	private String property;

	public DataJsonItemResponseModel(String property, Object value, String[] exclude, String[] include) {
		super();
		this.property = property;
		this.value = value;
		this.exclude = exclude;
		this.include = include;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String[] getInclude() {
		return include;
	}

	public void setInclude(String[] include) {
		this.include = include;
	}

	public String[] getExclude() {
		return exclude;
	}

	public void setExclude(String[] exclude) {
		this.exclude = exclude;
	}

}
