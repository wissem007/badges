package tn.com.smartsoft.framework.presentation.view.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.definition.ReportDefinition;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.presentation.utils.ReportType;
import tn.com.smartsoft.framework.presentation.utils.ResolveEventPathUtils;
import tn.com.smartsoft.framework.presentation.view.action.exception.CreateWindowException;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.action.utils.UserActionExceptionUtils;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.report.ReportModel;
import tn.com.smartsoft.framework.presentation.view.window.UIDefaultWindow;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public class DefaultUserAction implements UIObject, UserAction {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private ComponentId				userActionId;
	private PresentationBeanFactory	presentationBeanFactory;
	private ActionBean				actionBean;
	private Map<String, UIWindow>	windows				= new HashMap<String, UIWindow>();
	private UserActionDefinition	userActionDefinition;
	private UserDesktop				userDesktop;
	private UserActionControleur	actionControleur;
	private UserActionModel			model;
	private String					currentWindowId;
	private String					previousWindowId;
	
	public DefaultUserAction() {
		super();
	}
	
	public String getCurrentWindowId() {
		return currentWindowId;
	}
	
	public String getPreviousWindowId() {
		return previousWindowId;
	}
	
	public void setCurrentWindowId(String currentWindowId) {
		this.previousWindowId = this.currentWindowId;
		this.currentWindowId = currentWindowId;
	}
	
	public Class<?> getModelClass() {
		return userActionDefinition.getModelClass();
	}
	
	public UserActionControleur getControleur() {
		return actionControleur;
	}
	
	public void setControleur(UserActionControleur actionControleur) {
		this.actionControleur = actionControleur;
	}
	
	public void setModel(UserActionModel model) {
		this.model = model;
	}
	
	public void addFieldModel(ItemModel value) {
		model.addChildModel(value);
	}
	
	public PropertyModel findPropertyModel(String name) {
		PropertyModel modelChild = (PropertyModel) model.findFieldModel(name);
		if (modelChild == null)
			throw new TechnicalException("invalid field model for name:" + name);
		return modelChild;
	}
	
	public UserActionModel getModel() {
		return model;
	}
	
	public boolean isExistFieldModel(String name) {
		return model.findFieldModel(name) != null;
	}
	
	public UIWindow currentWindow() {
		return (UIWindow) windows.get(getCurrentWindowId());
	}
	
	public void goToWindow(String windowId, WebContext context) {
		try {
			UIWindow uiWindow = createWindowIfNecessary(context, windowId);
			uiWindow.onRender(context);
			setCurrentWindowId(windowId);
		} catch (CreateWindowException e) {
			UserActionExceptionUtils.loadCreateWindowException(e, context);
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		} catch (Exception e) {
			TechnicalException technicalExcep = new TechnicalException(e);
			technicalExcep.printLogTrace(Logger.getLogger(getClass()));
			context.userDesktop().messagesHandler().addMessage(technicalExcep);
		}
		if (StringUtils.isNotBlank(getCurrentWindowId())) {
			context.sendRedirect(userActionId.getId(), getCurrentWindowId());
		} else if (StringUtils.isNotBlank(getPreviousWindowId())) {
			context.sendRedirect(userActionId.getId(), getPreviousWindowId());
		} else {
			userDesktop.userDesktopNavigation().goToDefaultUserAction(context);
		}
	}
	
	public void goToReport(String reportId, WebContext context) {
		ReportDefinition reportDefinition = userActionDefinition.getReportDefinition(reportId);
		ReportType reportType = ReportType.parse(reportDefinition.getType());
		ReportModel reportModel = new ReportModel(reportDefinition.getDataSourceProperty(), reportDefinition.getId(), reportDefinition.getLocation());
		context.response(reportType.getResponseType(), reportModel);
	}
	
	public ReportModel getReport(String reportId, Map<String, Object> paramValues) {
		ReportDefinition reportDefinition = userActionDefinition.getReportDefinition(reportId);
		ReportModel reportModel = new ReportModel(reportDefinition.getDataSourceProperty(), reportDefinition.getId(), reportDefinition.getLocation());
		reportModel.getParamValues().putAll(paramValues);
		return reportModel;
	}
	
	public void goToReport(String reportId, Map<String, Object> paramValues, WebContext context) {
		ReportDefinition reportDefinition = userActionDefinition.getReportDefinition(reportId);
		ReportType reportType = ReportType.parse(reportDefinition.getType());
		ReportModel reportModel = new ReportModel(reportDefinition.getDataSourceProperty(), reportDefinition.getId(), reportDefinition.getLocation());
		reportModel.getParamValues().putAll(paramValues);
		context.response(reportType.getResponseType(), reportModel);
	}
	
	public void goToCurrentWindow(WebContext context) {
		goToWindow(getCurrentWindowId(), context);
	}
	
	public void goToHomeWindow(WebContext webContext) {
		try {
			if (StringUtils.isNotBlank(userActionDefinition.getHomeWindowId()))
				goToWindow(userActionDefinition.getHomeWindowId(), webContext);
			else if (StringUtils.isNotBlank(userActionDefinition.getOnHomeMethode())) {
				ListenerContext listenerContext = new ListenerContext("onHomeMethode", null, webContext);
				getControleur().runAction(userActionDefinition.getOnHomeMethode(), listenerContext);
			}
		} catch (FunctionalException e) {
			goToCurrentWindow(webContext);
		}
	}
	
	public UIWindow getWindow(String id) {
		return (UIWindow) windows.get(id);
	}
	
	public UIWindow createWindowIfNecessary(WebContext webContext, String id) throws CreateWindowException {
		if (windows.containsKey(id)) {
			return (UIWindow) windows.get(id);
		}
		try {
			UIWindow window = presentationBeanFactory.createWindow(id, userActionId, userActionDefinition);
			((UIDefaultWindow) window).setUserAction(this);
			windows.put(id, window);
			((UIDefaultWindow) window).onInit(webContext);
			return window;
		} catch (Exception e) {
			throw new CreateWindowException(e);
		}
	}
	
	public void removeWindow(String id) {
		windows.remove(id);
	}
	
	public void clearWindow() {
		windows.clear();
	}
	
	public UserDesktop userDesktop() {
		return userDesktop;
	}
	
	public void setUserDesktop(UserDesktop userDesktop) {
		this.userDesktop = userDesktop;
	}
	
	public void setUserActionDefinition(UserActionDefinition userActionDefinition) {
		this.userActionDefinition = userActionDefinition;
	}
	
	public ActionBean getActionBean() {
		return actionBean;
	}
	
	public ComponentId getUserActionId() {
		return userActionId;
	}
	
	public ActionMode userActionMode() {
		return new ActionMode(actionBean.getActionTypeId().intValue());
	}
	
	public PresentationBeanFactory getPresentationBeanFactory() {
		return presentationBeanFactory;
	}
	
	public void setPresentationBeanFactory(PresentationBeanFactory presentationBeanFactory) {
		this.presentationBeanFactory = presentationBeanFactory;
	}
	
	public Map<String, UIWindow> getWindows() {
		return windows;
	}
	
	public void setWindows(Map<String, UIWindow> windows) {
		this.windows = windows;
	}
	
	public void setUserActionId(ComponentId userActionId) {
		this.userActionId = userActionId;
	}
	
	public void setActionBean(ActionBean actionBean) {
		this.actionBean = actionBean;
	}
	
	public DesktopPartType desktopPartType() {
		return DesktopPartType.ACTION_PART;
	}
	
	public void fireActionEvent(WebContext context) {
		RequestEventInfo requestEventInfo = context.request().requestEventInfo();
		String windowSourceEvent = requestEventInfo.windowSourceEvent();
		if (!userActionDefinition.isExistWindowDefinition(windowSourceEvent)) {
			goToCurrentWindow(context);
			return;
		}
		setCurrentWindowId(windowSourceEvent);
		UIWindow window = (UIWindow) windows.get(getCurrentWindowId());
		if (window == null) {
			try {
				window = createWindowIfNecessary(context, windowSourceEvent);
			} catch (CreateWindowException e) {
				if (e.getCauseCreateWindow() instanceof FunctionalException) {
					context.userDesktop().messagesHandler().addMessage((FunctionalException) e.getCauseCreateWindow());
				} else {
					TechnicalException technicalExcep = new TechnicalException(e);
					technicalExcep.printLogTrace(Logger.getLogger(getClass()));
					context.userDesktop().messagesHandler().addMessage(technicalExcep);
				}
			}
		}
		if (window == null) {
			context.userDesktop().messagesHandler().addMessage("0120012");
			goToCurrentWindow(context);
		}
		UIEventHandler comp = (UIEventHandler) window.findChild(requestEventInfo.componentSourceEvent());
		if (comp != null) {
			comp.fireActionEvent(context);
		} else {
			Logger.getLogger(DefaultUserAction.class).error("no component from id :" + requestEventInfo.componentSourceEvent());
			goToCurrentWindow(context);
		}
	}
	
	public String resolvePath(WebContext context, String page, String componentId, String event) {
		return ResolveEventPathUtils.resolveEventPath(this, context, page, componentId, event);
	}
	
	public Object getDataObject() {
		return model.getValue();
	}
	
	public ActionBeanId getActionBeanId() {
		return getUserActionId().getActionBeanId();
	}
	
}