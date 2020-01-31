package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionFailureControleur;
import tn.com.smartsoft.framework.presentation.view.action.controleur.DefaultActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UILinkedEventRender;

public class UILinkedEvent extends UIPartialRequestEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UILinkedEvent() {
		super();
	}
	
	public UILinkedEvent(ClientEvent name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UILinkedEvent(ClientEvent name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UILinkedEvent(String name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UILinkedEvent(String name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UILinkedEvent(ClientEvent name, Object delegateAction, String method) {
		super(name, new DefaultActionControleur(delegateAction, method, ActionFailureControleur.STANDAR));
	}
	
	public UILinkedEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UILinkedEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public UILinkedEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UILinkedEvent(String name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public HttpRequestType httpRequestType() {
		return HttpRequestType.STANDAR;
	}
	
	public UIRender getRender() {
		return new UILinkedEventRender(this);
	}
	
}
