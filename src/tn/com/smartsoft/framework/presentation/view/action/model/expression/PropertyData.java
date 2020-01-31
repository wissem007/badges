package tn.com.smartsoft.framework.presentation.view.action.model.expression;

import java.io.Serializable;

import tn.com.smartsoft.framework.presentation.view.report.RValue;

public class PropertyData implements Serializable ,RValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionModelContext actionModelContext;
	private String property;

	public PropertyData(ActionModelContext actionModelContext, String property) {
		super();
		this.actionModelContext = actionModelContext;
		this.property = property;
	}

	public Object toObject() {
		return actionModelContext.getValue(property);
	}

	public String toString() {
		return actionModelContext.getCustomValue(property);
	}
}
