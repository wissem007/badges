package tn.com.smartsoft.framework.presentation.view.window;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.action.DefaultUserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.script.UIScript;
import tn.com.smartsoft.framework.presentation.view.tags.UIParserContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.framework.presentation.view.window.render.UIWindowRender;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;

public class UIDefaultWindow extends UIGenericRendrableContainerComponent implements UIWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UIWindowScript windowScript;
	private UIWindowRoot windowRoot;

	private ViewBean viewBean;
	private boolean isStateful;
	private UserAction userAction;
	private String onInit;
	private String onRender;
	private String onDestroy;
	private String onSecurityCheck;
	private boolean isInit = false;
	private String id;
	private UIParserContext parserContext;

	public UIDefaultWindow(UIParserContext parserContext) {
		super();
		this.parserContext = parserContext;
	}

	public UIDefaultWindow() {
		super();
	}

	public void setWindowScript(UIWindowScript windowScript) {
		if (windowScript == null)
			return;
		windowScript.setParent(this);
		this.windowScript = windowScript;
	}

	public void setWindowRoot(UIWindowRoot windowRoot) {
		windowRoot.setParent(this);
		this.windowRoot = windowRoot;
	}

	public boolean hasChild(String id) {
		return parserContext.hasChild(id);
	}

	public UIComponent findChild(String id) {
		return parserContext.findChild(id);
	}

	public ViewBean viewBean() {
		return viewBean;
	}

	public void setViewBean(ViewBean viewBean) {
		this.viewBean = viewBean;
	}

	public boolean isStateful() {
		return isStateful;
	}

	public void setStateful(boolean isStateful) {
		this.isStateful = isStateful;
	}

	public UserAction userAction() {
		return userAction;
	}

	public void setUserAction(UserAction userAction) {
		this.userAction = userAction;
	}

	public String getId() {
		return id;
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

	public void setId(String id) {
		this.id = id;
	}

	public String resolvePath(WebContext context, String componentId, String event) {
		return userAction.resolvePath(context, getId(), componentId, event);
	}

	public void addScript(UIScript javaScript) {
		windowScript.addScript(javaScript);
	}

	public List<UIScript> getScripts() {
		return windowScript.getScripts();
	}

	public void addItem(UIComponent component) {
		windowRoot.addItem(component);
	}

	public void addItem(String contenu) {
		windowRoot.addItem(contenu);
	}

	public int itemSize() {
		return windowRoot.itemSize();
	}

	public List<String> itemIds() {
		return windowRoot.itemIds();
	}

	public UIComponent getItem(String id) {
		return windowRoot.getItem(id);
	}

	public boolean hasItem(String id) {
		return windowRoot.hasItem(id);
	}

	public void removeItem(String id) {
		windowRoot.removeItem(id);
	}

	public void addLayoutConfig(String name, String value, Boolean expected) {
		windowRoot.addLayoutConfig(name, value, expected);

	}

	public void clearItems() {
		windowRoot.clearItems();
	}

	public String getFirstFocus() {
		return windowRoot.getFirstFocus();
	}

	public String getLayout() {
		return windowRoot.getLayout();
	}

	public JsMap getLayoutConfigs() {
		return windowRoot.getLayoutConfigs();
	}

	public void setFirstFocus(String firstFocus) {
		windowRoot.setFirstFocus(firstFocus);
	}

	public void setLayout(String layout) {
		windowRoot.setLayout(layout);
	}

	public UIRender getScriptRender() {
		return windowScript.getRender();
	}

	public UIRender getRootRender() {
		return windowRoot.getRender();
	}

	public List<UIComponent> getChild(Class<?> type) {
		return parserContext.getChild(type);
	}

	public Map<String, String> getFieldValues() {
		return parserContext.getFieldValues();
	}

	public void onInit(WebContext context) throws FunctionalException {
		if (isInit)
			return;
		if (StringUtils.isNotBlank(this.getOnInit()))
			userAction().getControleur().runAction(getOnInit(), new ListenerContext("onInit", this, context));
		isInit = true;
	}

	public void onRender(WebContext context) throws FunctionalException {
		if (StringUtils.isNotBlank(this.getOnRender()))
			userAction().getControleur().runAction(getOnRender(), new ListenerContext("onRender", this, context));
	}

	public void onDestroy(WebContext context) throws FunctionalException {
		if (StringUtils.isNotBlank(this.getOnDestroy()))
			userAction().getControleur().runAction(getOnDestroy(), new ListenerContext("onDestroy", this, context));
	}

	public void onSecurityCheck(WebContext context) throws FunctionalException {
		if (StringUtils.isNotBlank(this.getOnSecurityCheck()))
			userAction().getControleur().runAction(getOnSecurityCheck(), new ListenerContext("onSecurityCheck", this, context));
	}

	public void fireActionEvent(WebContext context) {
		RequestEventInfo requestEventInfo = context.request().requestEventInfo();
		UIEventHandler comp = (UIEventHandler) this.getItem(requestEventInfo.componentSourceEvent());
		if (comp != null) {
			comp.fireActionEvent(context);
		} else {
			Logger.getLogger(DefaultUserAction.class).error("no component from id :" + requestEventInfo.componentSourceEvent());
			this.userAction().goToCurrentWindow(context);
		}
	}

	public UIRender getRender() {
		return new UIWindowRender(this);
	}

	public UIWindowRoot getRoot() {
		return windowRoot;
	}

	public Object evalExpression(String expression) {
		return getModel().evalExpression(expression, getId());
	}

	public String evalExpressionToString(String expression) {
		return getModel().evalExpressionToString(expression, getId());
	}

	public UserActionModel getModel() {
		return userAction().getModel();
	}

	public UIParserContext parserContext() {
		return parserContext;
	}

	public UserDesktop userDesktop() {
		return userAction().userDesktop();
	}
}
