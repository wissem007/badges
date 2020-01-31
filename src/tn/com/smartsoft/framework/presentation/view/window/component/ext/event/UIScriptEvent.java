package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIScriptEventRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;

public class UIScriptEvent extends UIEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UIScriptJs script;

	public UIScriptEvent() {
		super();
	}

	public UIScriptEvent(ClientEvent name, UIEventListener listener) {
		super(name, listener);
	}

	public UIScriptEvent(ClientEvent name, String actionMethod) {
		super(name, actionMethod);
	}

	public UIScriptEvent(String name, UIEventListener listener) {
		super(name, listener);
	}

	public UIScriptEvent(String name, String actionMethod) {
		super(name, actionMethod);
	}

	public UIScriptEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}

	public UIScriptEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}

	public UIScriptEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		super(name, actionMethod, contolerBean, delegateMethod);
	}

	public UIScriptEvent(String name, String actionMethod, Object contolerBean) {
		super(name, actionMethod, contolerBean);
	}
	public String getScript() {
		if (script != null)
			return script.getScript();
		return null;
	}

	public void setScript(String script) {
		if (this.script == null) {
			this.script = new UIScriptJs();
			this.script.setParent(this);
		}
		this.script.setScript(script);
	}

	
	public UIScriptJs getScriptCmp() {
		return script;
	}

	public void setScriptCmp(UIScriptJs script) {
		this.script = script;
	}

	public UIRender getRender() {
		return new UIScriptEventRender(this);
	}

	public HttpRequestType httpRequestType() {
		return HttpRequestType.STANDAR;
	}

}
