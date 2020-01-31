package tn.com.smartsoft.framework.presentation.view.window.component.js;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.js.render.UIFunctionJsRender;

public class UIFunctionJs extends UIGenericRendrableContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<String> params = new ArrayList<String>();
	private String script;

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getId() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addParam(String name) {
		this.params.add(name);
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public void setListParam(String params) {
		if (StringUtils.isNotBlank(params)) {
			String[] ps = params.split(",");
			for (int i = 0; i < ps.length; i++) {
				addParam(ps[i]);
			}
		}
	}

	public String getListParam() {
		String listParam = "";
		for (int i = 0; i < getParams().size(); i++) {
			if (i != 0)
				listParam = listParam + ",";
			listParam = listParam + getParams().get(i);
		}
		return listParam;
	}

	public UIRender getRender() {
		return new UIFunctionJsRender(this);
	}

}
