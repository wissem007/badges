package tn.com.smartsoft.framework.presentation.view.window.component.js;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIScriptJsRender;

public class UIScriptJs extends UIGenericRendrableContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String script;

	public UIScriptJs() {
		super();
	}

	public UIScriptJs(String script) {
		super();
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public UIRender getRender() {
		return new UIScriptJsRender(this);
	}

}
