package tn.com.smartsoft.framework.presentation.view.action;

import java.util.Map;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.action.exception.CreateWindowException;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopElement;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.report.ReportModel;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public interface UserAction extends DesktopElement {
	public void goToReport(String reportId, WebContext context);
	
	public void goToReport(String reportId, Map<String, Object> paramValues, WebContext context);
	
	public ReportModel getReport(String reportId, Map<String, Object> paramValues);
	
	public void addFieldModel(ItemModel value);
	
	public abstract Object getDataObject();
	
	public UIWindow getWindow(String id);
	
	public abstract PropertyModel findPropertyModel(String modelName);
	
	public abstract boolean isExistFieldModel(String modelName);
	
	public abstract void removeWindow(String id);
	
	public abstract UIWindow createWindowIfNecessary(WebContext webContext, String id) throws CreateWindowException;
	
	public UIWindow currentWindow();
	
	public void goToHomeWindow(WebContext context);
	
	public void goToWindow(String windowId, WebContext context);
	
	public void goToCurrentWindow(WebContext context);
	
	public UserDesktop userDesktop();
	
	public ComponentId getUserActionId();
	
	public ActionBeanId getActionBeanId();
	
	public UserActionControleur getControleur();
	
	public ActionBean getActionBean();
	
	public UserActionModel getModel();
}