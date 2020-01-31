package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;

public abstract class UIPartialRequestEvent extends UIEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsTable requestFields = new JsTable();
	private Boolean allField;

	public UIPartialRequestEvent() {
		super();
	}

	public UIPartialRequestEvent(ClientEvent name, String actionMethod) {
		super(name, actionMethod);
	}

	public UIPartialRequestEvent(String name, String actionMethod) {
		super(name, actionMethod);
	}

	public UIPartialRequestEvent(ClientEvent name, ActionControleur listener) {
		super(name, listener);
	}

	public UIPartialRequestEvent(String name, ActionControleur listener) {
		super(name, listener);
	}

	public UIPartialRequestEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}

	public UIPartialRequestEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}

	public UIPartialRequestEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}

	public UIPartialRequestEvent(String name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}

	public Boolean isAllField() {
		return allField;
	}

	public void setAllField(Boolean allField) {
		this.allField = allField;
	}

	public JsTable getRequestFields() {
		return requestFields;
	}

	public void addRequestField(String field, Boolean expected) {
		this.requestFields.addValue(field, expected);
	}
}
