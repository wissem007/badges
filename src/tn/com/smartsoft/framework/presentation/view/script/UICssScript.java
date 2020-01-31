package tn.com.smartsoft.framework.presentation.view.script;

import tn.com.smartsoft.framework.presentation.view.script.render.UICssScriptRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UICssScript extends UIScript {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UICssScript() {
		super();
	}

	public UICssScript(String groupName) {
		super();
		this.groupName = groupName;
	}

	public UIRender getRender() {
		return new UICssScriptRender(this);
	}

	public boolean isRendred() {
		return true;
	}

}
