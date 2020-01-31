package tn.com.smartsoft.framework.presentation.view.script;

import tn.com.smartsoft.framework.presentation.view.script.render.UIJavaScriptRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UIJavaScript extends UIScript {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIJavaScript() {
		super();
	}

	public UIJavaScript(String groupName) {
		super();
		this.groupName = groupName;
	}

	public UIRender getRender() {
		return new UIJavaScriptRender(this);
	}

	public boolean isRendred() {
		return true;
	}
}
