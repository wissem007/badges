package tn.com.smartsoft.framework.presentation.view.window;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.framework.presentation.view.script.UIGroupScript;
import tn.com.smartsoft.framework.presentation.view.script.UIScript;
import tn.com.smartsoft.framework.presentation.view.window.render.UIWindowScriptRender;

public class UIWindowScript extends UIComponent implements UIRendrableComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIScript> scripts = new ArrayList<UIScript>();

	public UIWindowScript() {
		super();
		addScript(new UIGroupScript("all"));
	}

	public void setRendred(boolean rendred) {
	}

	public void addScript(UIScript javaScript) {
		scripts.add(javaScript);
	}

	public List<UIScript> getScripts() {
		return scripts;
	}

	public UIWindow getWindow() {
		return (UIWindow) getParent();
	}

	public boolean isRendred() {
		return true;
	}

	public UIRender getRender() {
		return new UIWindowScriptRender(this);
	}
}
