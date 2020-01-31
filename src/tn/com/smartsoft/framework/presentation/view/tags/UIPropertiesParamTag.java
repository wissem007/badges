package tn.com.smartsoft.framework.presentation.view.tags;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class UIPropertiesParamTag implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attribute;
	private Class<?> type;
	private String defaultValue;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Class<?> getType() {
		if (type == null)
			type = String.class;
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

}
