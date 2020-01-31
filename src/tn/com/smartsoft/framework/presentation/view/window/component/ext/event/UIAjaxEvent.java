package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIAjaxEventRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;

public class UIAjaxEvent extends UIPartialRequestEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsMap headers = new JsMap();
	private String scope;
	
	private UIScriptJs success;
	private UIScriptJs failure;
	private String successHandle;
	private String failureHandle;
	
	public UIAjaxEvent() {
		super();
	}
	
	public UIAjaxEvent(ClientEvent name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UIAjaxEvent(ClientEvent name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UIAjaxEvent(String name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UIAjaxEvent(String name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UIAjaxEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod, String successHandle) {
		super(name, actionMethod, contolerBean, delegateMethod);
		this.successHandle = successHandle;
	}
	
	public UIAjaxEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UIAjaxEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public UIAjaxEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UIAjaxEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod, String successScript) {
		super(name, actionMethod, contolerBean, delegateMethod);
		this.setSuccess(new UIScriptJs(successScript));
	}
	
	public UIAjaxEvent(String name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public HttpRequestType httpRequestType() {
		return HttpRequestType.AJAX;
	}
	
	public JsMap getHeaders() {
		return headers;
	}
	
	public void setHeaders(JsMap headers) {
		this.headers = headers;
	}
	
	public UIScriptJs getSuccess() {
		return success;
	}
	
	public void setSuccess(UIScriptJs success) {
		this.success = success;
	}
	
	public UIScriptJs getFailure() {
		return failure;
	}
	
	public void setFailure(UIScriptJs failure) {
		this.failure = failure;
	}
	
	public String getSuccessHandle() {
		return successHandle;
	}
	
	public void setSuccessHandle(String successHandle) {
		this.successHandle = successHandle;
	}
	
	public String getFailureHandle() {
		return failureHandle;
	}
	
	public void setFailureHandle(String failureHandle) {
		this.failureHandle = failureHandle;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public UIRender getRender() {
		return new UIAjaxEventRender(this);
	}
}
