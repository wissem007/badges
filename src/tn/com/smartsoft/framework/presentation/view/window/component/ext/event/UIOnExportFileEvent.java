package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionFailureControleur;
import tn.com.smartsoft.framework.presentation.view.action.controleur.DefaultActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIOnExportFileEventRender;

public class UIOnExportFileEvent extends UIPartialRequestEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIOnExportFileEvent() {
		super();
	}
	
	public UIOnExportFileEvent(ClientEvent name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UIOnExportFileEvent(ClientEvent name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UIOnExportFileEvent(String name, ActionControleur listener) {
		super(name, listener);
	}
	
	public UIOnExportFileEvent(String name, String actionMethod) {
		super(name, actionMethod);
	}
	
	public UIOnExportFileEvent(ClientEvent name, Object delegateAction, String method) {
		super(name, new DefaultActionControleur(delegateAction, method, ActionFailureControleur.STANDAR));
	}
	
	public UIOnExportFileEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UIOnExportFileEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public UIOnExportFileEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}
	
	public UIOnExportFileEvent(String name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	
	public HttpRequestType httpRequestType() {
		return HttpRequestType.STANDAR;
	}
	
	public UIRender getRender() {
		return new UIOnExportFileEventRender(this);
	}
	
}
