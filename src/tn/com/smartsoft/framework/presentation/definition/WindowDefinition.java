package tn.com.smartsoft.framework.presentation.definition;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class WindowDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String location;
	private String viewId;
	private Map<String, String> parameter = new HashMap<String, String>();

	private String onInit;
	private String onRender;
	private String onDestroy;
	private String onSecurityCheck;

	public WindowDefinition() {
		super();
	}

	public void copyTo(WindowDefinition windowDefinition) {
		windowDefinition.setId(id);
		windowDefinition.setLocation(location);
		windowDefinition.setViewId(viewId);
		windowDefinition.setOnInit(onInit);
		windowDefinition.setOnRender(onRender);
		windowDefinition.setOnDestroy(onDestroy);
		windowDefinition.setOnSecurityCheck(onSecurityCheck);
		windowDefinition.parameter.putAll(parameter);
	}

	public String getLocation() {
		return location;
	}

	public String getOnInit() {
		return onInit;
	}

	public void setOnInit(String onInit) {
		this.onInit = onInit;
	}

	public String getOnRender() {
		return onRender;
	}

	public void setOnRender(String onRender) {
		this.onRender = onRender;
	}

	public String getOnDestroy() {
		return onDestroy;
	}

	public void setOnDestroy(String onDestroy) {
		this.onDestroy = onDestroy;
	}

	public String getOnSecurityCheck() {
		return onSecurityCheck;
	}

	public void setOnSecurityCheck(String onSecurityCheck) {
		this.onSecurityCheck = onSecurityCheck;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public void addParameter(String key, String value) {
		parameter.put(key, value);
	}

	public Map<String, String> getParameter() {
		return parameter;
	}

}
