package tn.com.smartsoft.framework.beans.definition;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ComponentIdDefinition extends GenericPropertyDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	public ComponentIdDefinition() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
