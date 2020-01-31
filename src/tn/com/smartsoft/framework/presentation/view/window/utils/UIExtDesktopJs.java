package tn.com.smartsoft.framework.presentation.view.window.utils;

import tn.com.smartsoft.commons.web.js.JSEncodeUtil;
import tn.com.smartsoft.commons.web.js.JsClass;
import tn.com.smartsoft.commons.web.js.JsFunction;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderStringValue;

public class UIExtDesktopJs extends JsClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DESKTOP_INSTANCE_JS = "desktop";
	private static final String DSK_FNC_SET_FIRST_FOCUS_FIELD_ID = "setFirstFocusFieldId";

	private static final String DSK_FNC_ADD = "addToContainer";
	private static final String DSK_FNC_ADD_TOOLS_BAR_ITEM = "addToolsBarItem";
	private static final String DSK_FNC_ADD_MODULE = "addModule";
	private static final String DSK_FNC_ADD_SHORTCUT_MENU = "addShortcutMenu";

	private static final String DSK_FNC_ADD_ACTION = "addAction";
	private static final String DSK_FNC_ADD_LABLE_ACTION = "addLabelAction";
	private static final String DSK_FNC_ADD_START_UPACTION = "addStartUpAction";

	private static final String DSK_FNC_ADD_STATUS_LABEL = "addStatusLabel";
	private static final String DSK_FNC_ADD_MENU = "addMenu";
	private static final String DSK_FNC_ADD_MESSAGE = "addMessage";
	private static final String DSK_FNC_ADD_TOOL_ITEM_CONTAINER = "addToolItem";

	private static final String DSK_FNC_ADD_LISTENER_CONTAINER = "addListenerContainer";
	private static final String DSK_FNC_DO_INIT_DESKTOP = "doInitDesktop";
	private static final String DSK_FNC_ALERT = "alert";
	private static final String DSK_FNC_CREATE_TOOL_SHEET = "createToolSheet";
	private static final String DSK_FNC_CREATE_SERVER_ACTION = "createServerAction";
	private static final String DSK_FNC_CREATE_HANDEL_SERVER_ACTION = "createHandelServerAction";
	private String insatanceName = DESKTOP_INSTANCE_JS;
	protected JsMap configs = new JsMap();
	private String addMethodeContainer = DSK_FNC_ADD;

	public UIExtDesktopJs() {
		super("Ext.sss.Desktop");
		this.addMethod(new JsFunction(DSK_FNC_SET_FIRST_FOCUS_FIELD_ID, "fieldId"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_TOOLS_BAR_ITEM, "toolsBarItem"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_MENU, "menu"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_LISTENER_CONTAINER, "listener"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_MODULE, "module"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_TOOL_ITEM_CONTAINER, "action"));
		this.addMethod(new JsFunction(DSK_FNC_ALERT, "msg", "type"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_ACTION, "action"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_STATUS_LABEL, "statusLabel"));
		this.addMethod(new JsFunction(DSK_FNC_ADD, "component"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_MESSAGE, "key", "messageValue", "messageType"));
		this.addMethod(new JsFunction(DSK_FNC_DO_INIT_DESKTOP));
		this.addMethod(new JsFunction(DSK_FNC_CREATE_TOOL_SHEET, "icon", "id"));
		this.addMethod(new JsFunction(DSK_FNC_CREATE_SERVER_ACTION, "action"));
		this.addMethod(new JsFunction(DSK_FNC_CREATE_HANDEL_SERVER_ACTION, "action"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_LABLE_ACTION, "action"));
		this.addMethod(new JsFunction(DSK_FNC_ADD_START_UPACTION, "addStartUpAction", "item", "action"));
		this.addMethod(new JsFunction("intNavBar", "action"));

	}

	public void setAddMethodeContainer(String addMethodeContainer) {
		this.addMethod(new JsFunction(addMethodeContainer, "component"));
	}

	public String getInsatanceName() {
		return insatanceName;
	}

	public void setInsatanceName(String insatanceName) {
		this.insatanceName = insatanceName;
	}

	public void setLabelsAction(String labelsAction) {
		configs.addObjectValue("labelsAction", labelsAction);
	}

	public void setDisplayToolbar(boolean displayToolbar) {
		configs.addBooleanValue("isDisplayToolbar", displayToolbar);
	}

	public void setCollapsedModule(boolean collapsedModule) {
		configs.addBooleanValue("collapsedModule", collapsedModule);
	}

	public void setTitelModule(String titelModule) {
		configs.addStringValue("titelModule", titelModule);
	}

	public void setDisplayedMenuBar(boolean isDisplayedMenuBar) {
		configs.addBooleanValue("isDisplayedMenuBar", isDisplayedMenuBar);
	}

	public void setDisplayedModuleBar(boolean isDisplayedModuleBar) {
		configs.addBooleanValue("isDisplayedModuleBar", isDisplayedModuleBar);
	}

	public void setDisplayedStatusBar(boolean isDisplayedStatusBar) {
		configs.addBooleanValue("isDisplayedStatusBar", isDisplayedStatusBar);
	}

	public void setAppContainerLayout(String appContainerLayout) {
		configs.addStringValue("appContainerLayout", appContainerLayout);
	}

	public void setAppContainerLayoutConfig(JsMap appContainerLayoutConfig) {
		configs.addObjectValue("appContainerLayoutConfig", appContainerLayoutConfig.toJs());
	}

	public void setTitelShortcutMenu(String titelShortcutMenu) {
		configs.addStringValue("titelShortcutMenu", titelShortcutMenu);
	}

	public UIRenderStringValue addNavBarItem(JsMap navBar) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, "intNavBar", navBar.toJs(), false));
	}

	public void setDisplayedShortcutMenu(boolean isShortcutMenuExplorer) {
		configs.addBooleanValue("isShortcutMenuExplorer", isShortcutMenuExplorer);
	}

	public void setPath(String path) {
		configs.addStringValue("path", path);
	}

	public void setSociteLabel(String sociteLabel) {
		configs.addStringValue("sociteLabel", sociteLabel);
	}

	public JsMap getConfigs() {
		return configs;
	}

	public UIRenderStringValue newInstance() {
		return new UIRenderStringValue(this.invokeNewInstance(this.insatanceName, true, getConfigs().toJs(), false));
	}

	public UIRenderStringValue addMessage(String key, String messageValue, String messageType) {
		JsTable params = new JsTable();
		params.addStringOption(key);
		params.addStringOption(messageValue);
		params.addStringOption(messageType);
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_MESSAGE, params));
	}

	public UIRenderStringValue doInitDesktop() {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_DO_INIT_DESKTOP));
	}

	public UIRenderStringValue addOnStartUpAction(String cmpId, String actionId) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_START_UPACTION, cmpId, actionId, true, true));
	}

	public UIRenderStringValue addToContainer(String component) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, addMethodeContainer, component, false));
	}

	public UIRenderStringValue addToolItem(String name) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_TOOL_ITEM_CONTAINER, name, false));
	}

	public UIRenderStringValue addListenerContainer(String name, String handler) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_LISTENER_CONTAINER, name, handler, true, false));
	}

	public UIRenderStringValue addModule(JsMap module) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_MODULE, module.toJs(), false));

	}

	public UIRenderStringValue addShortcutMenu(JsMap shortcutMenu) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_SHORTCUT_MENU, shortcutMenu.toJs(), false));

	}

	public UIRenderStringValue setFirstFocusFieldId(String fieldId) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_SET_FIRST_FOCUS_FIELD_ID, fieldId, true));
	}

	public UIRenderStringValue addToolsBarItem(JsMap toolsBarItem) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_TOOLS_BAR_ITEM, toolsBarItem.toJs(), false));
	}

	public UIRenderStringValue addStatusLabel(String statusLabel) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_STATUS_LABEL, statusLabel, false));
	}

	public UIRenderStringValue addMenu(String menu) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_MENU, menu, false));
	}

	public UIRenderStringValue createServerAction(JsMap action) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_CREATE_SERVER_ACTION, action.toJs(), false));
	}

	public UIRenderStringValue createHandelServerAction(JsMap action) {
		return new UIRenderStringValue(this.createInvokeFunction(this.insatanceName, DSK_FNC_CREATE_HANDEL_SERVER_ACTION, action.toJs(), false));
	}

	public UIRenderStringValue alert(String msg, String type, boolean isVar) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ALERT, JSEncodeUtil.encodeJavaScript(msg), type, isVar, true));

	}

	public UIRenderStringValue addObjectAction(String action) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_ACTION, action, false));
	}

	public UIRenderStringValue addLabelAction(String action) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_LABLE_ACTION, action, true));
	}

	public UIRenderStringValue addStringAction(String action) {
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_ADD_ACTION, action, true));
	}

	public UIRenderStringValue createToolSheet(String icon, String id) {
		JsTable params = new JsTable();
		params.addStringOption(icon);
		params.addStringOption(id);
		return new UIRenderStringValue(this.invokeMethod(this.insatanceName, DSK_FNC_CREATE_TOOL_SHEET, icon, id, true, true));

	}

}
