package tn.com.smartsoft.framework.presentation.view.window.component.template;

import tn.com.smartsoft.framework.presentation.UIObject;

public class UIModelTemplate implements UIObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
