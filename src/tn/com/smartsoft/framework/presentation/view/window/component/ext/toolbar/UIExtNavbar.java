package tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIAjaxEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.render.UIExtNavbarRender;

public class UIExtNavbar extends UIExtToolbar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String SUCCESS_ACTION = "navBarAction(response,options);";
	private String doFirstAction;
	private String doPreviousAction;
	private String doNextAction;
	private String doLastAction;
	private Boolean isLabelAction = Boolean.FALSE;
	private String listProperty;
	private String indexProperty;
	
	public UIExtNavbar() {
		super();
		addEvent(new UIAjaxEvent("doFirst", "doFirstAction", null, this, SUCCESS_ACTION));
		addEvent(new UIAjaxEvent("doPrevious", "doPreviousAction", null, this, SUCCESS_ACTION));
		addEvent(new UIAjaxEvent("doNext", "doNextAction", null, this, SUCCESS_ACTION));
		addEvent(new UIAjaxEvent("doLast", "doLastAction", null, this, SUCCESS_ACTION));
	}
	
	public String getIndexProperty() {
		return indexProperty;
	}
	
	public void setIndexProperty(String indexProperty) {
		this.indexProperty = indexProperty;
	}
	
	public String getListProperty() {
		return listProperty;
	}
	
	public void setListProperty(String listProperty) {
		this.listProperty = listProperty;
	}
	
	public Boolean isLabelAction() {
		return isLabelAction;
	}
	
	public void setLabelAction(Boolean isLabelAction) {
		this.isLabelAction = isLabelAction;
	}
	
	public String getDoFirstAction() {
		return doFirstAction;
	}
	
	public void setDoFirstAction(String doFirstAction) {
		this.doFirstAction = doFirstAction;
	}
	
	public String getDoPreviousAction() {
		return doPreviousAction;
	}
	
	public void setDoPreviousAction(String doPreviousAction) {
		this.doPreviousAction = doPreviousAction;
	}
	
	public String getDoNextAction() {
		return doNextAction;
	}
	
	public void setDoNextAction(String doNextAction) {
		this.doNextAction = doNextAction;
	}
	
	public String getDoLastAction() {
		return doLastAction;
	}
	
	public void setDoLastAction(String doLastAction) {
		this.doLastAction = doLastAction;
	}
	
	@Override
	public UIRender getRender() {
		return new UIExtNavbarRender(this);
	}
	
}
