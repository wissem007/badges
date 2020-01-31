package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.utlis;

import tn.com.smartsoft.framework.presentation.UIObject;

public class UILinkedField implements UIObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String property;
	private Object value;
	
	public UILinkedField(String property, Object value) {
		super();
		this.property = property;
		this.value = value;
	}
	
	public String getProperty() {
		return property;
	}
	
	public Object getValue() {
		return value;
	}
	
}
