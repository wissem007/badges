package tn.com.smartsoft.framework.presentation.view.action.model.utils;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class PropertyDesc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NESTED_DELIM = ".";
	public static final String MAPPED_DELIM = "[";
	public static final String MAPPED_DELIM2 = "]";
	private String property;
	private String propertyIndex;
	private boolean isIndexed;
	private String propertyOregin;

	public PropertyDesc(String property, String propertyOregin) {
		super();
		this.buildPrperty(property);
		this.propertyOregin = propertyOregin;
	}

	public boolean isIndexed() {
		return isIndexed;
	}

	public String getPropertyOregin() {
		return propertyOregin;
	}

	public String getProperty() {
		return property;
	}

	public String getKeyPoperty() {
		return propertyIndex;
	}

	public int getIndexProperty() {
		return Integer.parseInt(propertyIndex);
	}

	public String toString() {
		if (isIndexed)
			return "Property(" + property + "," + propertyIndex + ", From property :" + propertyOregin + ")";
		return "Property(" + property + ", From property :" + propertyOregin + ")";
	}

	public void buildPrperty(String property) {
		this.property = property;
		int delimNested = StringUtils.indexOf(property, NESTED_DELIM);
		int delimMap1 = StringUtils.indexOf(property, MAPPED_DELIM);
		int delimMap2 = StringUtils.indexOf(property, MAPPED_DELIM2);
		if (delimMap2 >= 0 && delimMap1 >= 0 && (delimNested < 0 || delimNested > delimMap1)) {
			delimNested = StringUtils.indexOf(property, NESTED_DELIM, delimMap2);
		} else {
			delimMap1 = -1;
			delimMap2 = -1;
		}
		if (delimMap1 >= 0) {
			isIndexed = true;
			this.property = StringUtils.substring(property, 0, delimMap1);
			this.propertyIndex = StringUtils.substring(property, delimMap1 + 1, delimMap2);
		}
	}
}