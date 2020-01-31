package tn.com.smartsoft.framework.presentation.view.window;

import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.script.UIScript;
import tn.com.smartsoft.framework.presentation.view.tags.UIParserContext;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;

public interface UIWindow extends UIComponentHandler<UIComponent> {
	public Object evalExpression(String expression);
	
	public String evalExpressionToString(String expression);
	
	public abstract ViewBean viewBean();
	
	public abstract boolean isStateful();
	
	public UserDesktop userDesktop();
	
	public abstract UserAction userAction();
	
	public abstract String getId();
	
	public String resolvePath(WebContext context, String componentId, String event);
	
	public abstract void addScript(UIScript javaScript);
	
	public abstract List<UIScript> getScripts();
	
	public String getFirstFocus();
	
	public void setFirstFocus(String firstFocus);
	
	public String getLayout();
	
	public void setLayout(String layout);
	
	public void addLayoutConfig(String name, String value, Boolean expected);
	
	public JsMap getLayoutConfigs();
	
	public UIRender getScriptRender();
	
	public UIRender getRender();
	
	public abstract boolean hasChild(String id);
	
	public abstract UIComponent findChild(String id);
	
	public Map<String, String> getFieldValues();
	
	public abstract void onInit(WebContext context) throws FunctionalException;
	
	public abstract void onRender(WebContext context) throws FunctionalException;
	
	public abstract void onDestroy(WebContext context) throws FunctionalException;
	
	public abstract void onSecurityCheck(WebContext context) throws FunctionalException;
	
	public abstract UIParserContext parserContext();
	
	public UIWindowRoot getRoot();
	
	public UserActionModel getModel();
}