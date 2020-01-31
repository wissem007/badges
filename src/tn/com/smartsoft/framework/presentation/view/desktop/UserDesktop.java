package tn.com.smartsoft.framework.presentation.view.desktop;

import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.message.MessagesHandler;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.desktop.login.UIAccesApplication;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuBar;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.framework.presentation.view.desktop.statusbars.UIStatusbars;

public interface UserDesktop{
	
	public boolean isDisplayedMenuBar();
	public boolean isDisplayedModuleBar();
	public boolean isDisplayedStatusBar();
	public abstract MessagesHandler messagesHandler();
	public abstract UserContext userContext();
	public UserDesktopNavigation userDesktopNavigation();
	public UIUserModule curentUserModule();
	public UserAction curentUserAction();
	public abstract UIStatusbars statusbars();
	public abstract UIModuleExplorer moduleExplorer();
	public abstract UIMenuBar menuBar();
	public abstract UIAccesApplication accesApplication();
	public abstract UIRessourceManager ressourceManager();
	public void setCurentUserAction(UserAction curentUserAction);
}