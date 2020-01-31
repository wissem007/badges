package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtAttrebutScriptRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;

public class UIExtAttrebutScript extends UIExtAttrebutComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIScriptJs script;
	private String handleName;
	private ArrayList<String> handleParams = new ArrayList<String>();

	public UIScriptJs getScript() {
		return script;
	}

	public void setScript(UIScriptJs script) {
		this.script = script;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public void addHandleParam(String name) {
		this.handleParams.add(name);
	}

	public ArrayList<String> getHandleParams() {
		return handleParams;
	}

	public void clearHandleParamValue() {
		this.handleParams.clear();
	}

	public void setHandleParamValue(String params) {
		if (StringUtils.isNotBlank(params)) {
			String[] ps = params.split(",");
			for (int i = 0; i < ps.length; i++) {
				addHandleParam(ps[i]);
			}
		}
	}

	public UIRender getRender() {
		return new UIExtAttrebutScriptRender(this);
	}
}
