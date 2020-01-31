package tn.com.smartsoft.framework.presentation.view.script;

import tn.com.smartsoft.framework.presentation.view.script.render.UIGroupScriptRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UIGroupScript extends UIScript {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIGroupScript() {
		super();
	}

	public UIGroupScript(String groupName) {
		super();
		this.groupName = groupName;
	}

	public UIRender getRender() {
		return new UIGroupScriptRender(this);
	}

	public boolean isRendred() {
		return true;
	}
}
