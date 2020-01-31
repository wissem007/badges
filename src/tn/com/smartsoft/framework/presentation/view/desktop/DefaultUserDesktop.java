package tn.com.smartsoft.framework.presentation.view.desktop;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.message.DefaultMessagesHandler;
import tn.com.smartsoft.framework.presentation.message.MessagesHandler;
import tn.com.smartsoft.framework.presentation.view.action.DefaultUserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.desktop.login.UIAccesApplication;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.framework.presentation.view.desktop.statusbars.UIStatusbars;

public class DefaultUserDesktop implements UIObject, UserDesktop {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserContext userContext;
	private DefaultUserAction curentUserAction;
	private UIModuleExplorer moduleExplorer = null;
	private UIStatusbars statusbars = null;
	private ComponentId defaultUserActionId;
	private String defaultModule;
	private UIUserModule curentUserModule;
	private UIAccesApplication accesApplication;
	private UIRessourceManager ressource = new UIRessourceManager();
	private UserDesktopNavigation userDesktopNavigation;

	public UIUserModule curentUserModule() {
		return curentUserModule;
	}

	private MessagesHandler messagesHandler = new DefaultMessagesHandler();

	public void resetMessage() {
		messagesHandler.resetMessage();
	}

	public MessagesHandler messagesHandler() {
		return messagesHandler;
	}

	public UIAccesApplication accesApplication() {
		return accesApplication;
	}

	public void setAccesApplication(UIAccesApplication accesApplication) {
		this.accesApplication = accesApplication;
	}

	public void setCurentUserModule(UIUserModule curentUserModule) {
		this.curentUserModule = curentUserModule;
	}

	public UIRessourceManager ressourceManager() {
		return ressource;
	}

	public UserContext getUserContext() {
		return userContext;
	}

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public String getDefaultModule() {
		return defaultModule;
	}

	public void setUserDesktopNavigation(UserDesktopNavigation userDesktopNavigation) {
		this.userDesktopNavigation = userDesktopNavigation;
	}

	public void setDefaultModule(String defaultModule) {
		this.defaultModule = defaultModule;
	}

	public UserContext userContext() {
		return userContext;
	}

	public UserAction curentUserAction() {
		return curentUserAction;
	}

	public void setCurentUserAction(UserAction curentUserAction) {
		this.curentUserAction = (DefaultUserAction) curentUserAction;
	}

	public UIStatusbars statusbars() {
		if (statusbars == null)
			statusbars = new UIStatusbars();
		return statusbars;
	}

	public void setStatusbars(UIStatusbars statusbars) {
		this.statusbars = statusbars;
	}

	public UIModuleExplorer moduleExplorer() {
		if (moduleExplorer == null)
			moduleExplorer = new UIModuleExplorer();
		return moduleExplorer;
	}

	public void setModuleExplorer(UIModuleExplorer objectExplorer) {
		if (objectExplorer == null) {
			curentUserModule = null;
		}
		this.moduleExplorer = objectExplorer;
	}

	public UIMenuBar menuBar() {
		if (curentUserModule == null)
			return null;
		return curentUserModule.getMenuBar();
	}

	public void setDefaultUserActionId(ComponentId defaultUserActionId) {
		this.defaultUserActionId = defaultUserActionId;
	}

	public ComponentId getDefaultUserActionId() {
		return defaultUserActionId;
	}

	public UserDesktopNavigation userDesktopNavigation() {
		return userDesktopNavigation;
	}

	public boolean isDisplayedMenuBar() {
		return menuBar() != null ? menuBar().isDisplayed() : false;
	}

	public boolean isDisplayedModuleBar() {
		return moduleExplorer() != null ? moduleExplorer().isDisplayed() : false;
	}

	public boolean isDisplayedStatusBar() {
		return statusbars() != null ? statusbars().isDisplayed() : false;
	}

}
