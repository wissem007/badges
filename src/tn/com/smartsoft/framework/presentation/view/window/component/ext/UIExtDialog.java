package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtDialogRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;

public class UIExtDialog extends UIExtPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UILinkedEvent actionLoad;
	public static final String DIALOG_RESPONSE = "dialog";
	public Boolean minimizable;
	public Boolean maximizable;
	private UIScriptJs callbackOnOpenJs;
	private String callbackOnOpenHandeler;
	public Boolean dynamiqueOnOpen;
	private String beforeActionMethodOnOpen;

	public UIExtDialog() {
		super();
		actionLoad = new UILinkedEvent(ClientEvent.ON_LOAD, this, "onLoad");
		actionLoad.setRendred(false);
		addEvent(actionLoad);
		setAddedToParent(false);
	}

	public void onLoad(ListenerContext context) throws FunctionalException {
		if (StringUtils.isNotBlank(getBeforeActionMethodOnOpen())) {
			context.userAction().getControleur().runAction(getBeforeActionMethodOnOpen(), context);
		}
		context.webContext().response(DIALOG_RESPONSE, this);
	}

	public String getBeforeActionMethodOnOpen() {
		return beforeActionMethodOnOpen;
	}

	public void setBeforeActionMethodOnOpen(String beforeActionMethodOnOpen) {
		this.beforeActionMethodOnOpen = beforeActionMethodOnOpen;
	}

	public Boolean getDynamiqueOnOpen() {
		return dynamiqueOnOpen;
	}

	public void setDynamiqueOnOpen(Boolean dynamiqueOnOpen) {
		this.dynamiqueOnOpen = dynamiqueOnOpen;
	}

	public String getCallbackOnOpenHandeler() {
		return callbackOnOpenHandeler;
	}

	public void setCallbackOnOpenHandeler(String callbackOnOpenHandeler) {
		this.callbackOnOpenHandeler = callbackOnOpenHandeler;
	}

	public UIScriptJs getCallbackOnOpenJs() {
		return callbackOnOpenJs;
	}

	public void setCallbackOnOpenJs(UIScriptJs callbackOnOpenJs) {
		this.callbackOnOpenJs = callbackOnOpenJs;
	}

	public void addRequestFieldOnOpen(String field, Boolean expected) {
		actionLoad.addRequestField(field, expected);
	}

	public void addRequestParamOnOpen(String name, String value, Boolean expected) {
		actionLoad.addRequestParam(name, value, expected);
	}

	public Boolean getMinimizable() {
		return minimizable;
	}

	public void setMinimizable(Boolean minimizable) {
		this.minimizable = minimizable;
	}

	public Boolean getMaximizable() {
		return maximizable;
	}

	public void setMaximizable(Boolean maximizable) {
		this.maximizable = maximizable;
	}

	public UILinkedEvent getActionLoad() {
		return actionLoad;
	}

	public UIRender getRender() {
		return new UIExtDialogRender(this);
	}
}
