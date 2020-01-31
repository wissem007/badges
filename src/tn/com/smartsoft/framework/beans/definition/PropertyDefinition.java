package tn.com.smartsoft.framework.beans.definition;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class PropertyDefinition extends GenericPropertyDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;
	private String ref;

	public PropertyDefinition() {
		super();
	}

	public String getValue() {
		return value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
