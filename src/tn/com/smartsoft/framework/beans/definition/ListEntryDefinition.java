package tn.com.smartsoft.framework.beans.definition;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class ListEntryDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;
	private String type;
	private String ref;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
}
