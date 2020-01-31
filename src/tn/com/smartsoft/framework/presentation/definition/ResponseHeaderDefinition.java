package tn.com.smartsoft.framework.presentation.definition;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class ResponseHeaderDefinition implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;

	public ResponseHeaderDefinition(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
