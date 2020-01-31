package tn.com.smartsoft.framework.presentation.view.window.utils;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsClass;
import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderStringValue;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIAttributeHandler;

public class UIExtComponentJs extends JsClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String instanceName;
	protected JsMap configs = new JsMap();
	protected JsMap events = new JsMap();
	protected JsTable items = new JsTable();
	protected JsTable buttons = new JsTable();
	protected JsTable tools = new JsTable();

	public UIExtComponentJs(String name, String instanceName) {
		super(name);
		this.addMethod(new JsFunction("addListener", "event", "listener"));
		this.addMethod(new JsFunction("addButton", "button"));
		this.addMethod(new JsFunction("addTool", "button"));
		this.addMethod(new JsFunction("add", "item"));
		this.addMethod(new JsFunction("addItem", "item"));
		this.instanceName = instanceName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public JsMap getConfigs() {
		return configs;
	}

	public void addValues(UIAttributeHandler attributeHandler) {
		String[] atts = attributeHandler.geAttributesName();
		for (int i = 0; i < atts.length; i++) {
			String value = attributeHandler.getAttribut(atts[i]);
			if (StringUtils.isNotBlank(value)) {
				addValue(atts[i], value);
			}
		}
	}

	public void addEventHandler(String name, String handeler) {
		events.addObjectValue(name, handeler);
	}

	public void addButton(UIExtComponentJs componentJs, boolean isVar) {
		if (isVar)
			buttons.addObjectValue(componentJs.getInstanceName());
		else
			buttons.addObjectValue(componentJs.newInstance(false));
	}

	public void addButton(UIExtComponentJs componentJs) {
		addButton(componentJs, true);
	}

	public void addButton(String name) {
		buttons.addObjectValue(name);
	}

	public void addButton(JsMap componentJs) {
		buttons.addObjectValue(componentJs.toJs());
	}

	public void addTools(UIExtComponentJs componentJs, boolean isVar) {
		if (isVar)
			tools.addObjectValue(componentJs.getInstanceName());
		else
			tools.addObjectValue(componentJs.newInstance(false));
	}

	public void addTools(UIExtComponentJs componentJs) {
		addTools(componentJs, true);
	}

	public void addTools(String name) {
		tools.addObjectValue(name);
	}

	public void addTools(JsMap componentJs) {
		tools.addObjectValue(componentJs.toJs());
	}

	public void addItem(UIExtComponentJs componentJs, boolean isVar) {
		if (isVar)
			items.addObjectValue(componentJs.getInstanceName());
		else
			items.addObjectValue(componentJs.newInstance(false).getScript());
	}

	public void addItem(JsMap componentJs) {
		items.addObjectValue(componentJs.toJs());
	}

	public void addItem(UIExtComponentJs componentJs) {
		addItem(componentJs, true);
	}

	public void addItem(String name) {
		items.addObjectValue(name);
	}

	public void addBooleanValue(String name, boolean value) {
		configs.addBooleanValue(name, value);
	}

	public void addBooleanValue(String name, Boolean value) {
		configs.addBooleanValue(name, value);
	}

	public void addIntValue(String name, int value) {
		configs.addIntValue(name, value);
	}

	public void addObjectValue(String name, Object value) {
		configs.addObjectValue(name, value);
	}

	public void addStringValue(String name, String value) {
		configs.addStringValue(name, value);
	}

	public void addUrlValue(String name, String event, UIComponent component, UIRenderContext context) {
		this.addStringValue(name, component.resolveEventPath(context.webContext(), event));
	}

	public void addUrlValue(String name, ClientEvent event, UIComponent component, UIRenderContext context) {
		this.addUrlValue(name, event.toString(), component, context);
	}

	public void addValue(String name, String value) {
		configs.addValue(name, value, (!StringUtils.isNumeric(value) && !StringUtils.equalsIgnoreCase(value, "false") && !StringUtils.equalsIgnoreCase(value, "true")));
	}

	public void setBottomBar(UIExtComponentJs componentJs) {
		addObjectValue("bbar", componentJs.getInstanceName());
	}

	public void setTopBar(UIExtComponentJs componentJs) {
		addObjectValue("tbar", componentJs.getInstanceName());
	}

	public UIRenderStringValue newInstance(boolean isVarInstance, boolean isClassInstance) {
		if (!items.isEmpty())
			configs.addObjectValue("items", items.toJs());
		if (!events.isEmpty())
			configs.addObjectValue("listeners", events.toJs());
		if (!buttons.isEmpty())
			configs.addObjectValue("buttons", buttons.toJs());
		if (!tools.isEmpty())
			configs.addObjectValue("tools", tools.toJs());
		if (isClassInstance) {
			configs.setVarName(null);
			if (isVarInstance)
				return new UIRenderStringValue(this.invokeNewInstance(this.instanceName, isVarInstance, configs.toJs(), false));
			else {
				String script = this.invokeNewInstance(new String[] { configs.toJs() }, new boolean[] { false });
				return new UIRenderStringValue(script.substring(0, script.length() - 2));
			}
		} else {
			if (isVarInstance) {
				configs.setVarName(this.instanceName);
				return new UIRenderStringValue(configs.toJs() + ";");
			} else {
				configs.setVarName(null);
				return new UIRenderStringValue(configs.toJs());
			}
		}
	}

	public UIRenderStringValue newInstance(boolean isVarInstance) {
		return newInstance(false, true);
	}

	public UIRenderStringValue newInstance() {
		return newInstance(true, true);
	}

}
