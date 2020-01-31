package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionFailureControleur;
import tn.com.smartsoft.framework.presentation.view.action.controleur.DefaultActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIOnOpenMediaEventRender;

public class UIOnOpenMediaEvent extends UIPartialRequestEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String extention;
	
	public UIOnOpenMediaEvent() {
		super();
	}
	
	public UIOnOpenMediaEvent(ClientEvent name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UIOnOpenMediaEvent(ClientEvent name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UIOnOpenMediaEvent(String name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UIOnOpenMediaEvent(String name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UIOnOpenMediaEvent(ClientEvent name, Object delegateAction, String method) {
		super(name, new DefaultActionControleur(delegateAction, method, ActionFailureControleur.STANDAR));
	}
	
	public UIOnOpenMediaEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UIOnOpenMediaEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public UIOnOpenMediaEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UIOnOpenMediaEvent(String name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public String getExtention() {
		return extention;
	}
	
	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	public UIRender getRender() {
		return new UIOnOpenMediaEventRender(this);
	}
	
	public HttpRequestType httpRequestType() {
		return HttpRequestType.STANDAR;
	}
	
}
