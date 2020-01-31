package tn.com.smartsoft.framework.presentation.view.desktop.login;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopElement;

public interface UIAccesApplication extends UIObject, DesktopElement {
	public boolean isSessionExpired();

	public void setSessionExpired(boolean isSessionExpired);

	public abstract boolean isLogin();

	public abstract void setLogin(boolean isLogin);

	public abstract void fireActionEvent(WebContext context);

	public abstract DesktopPartType desktopPartType();

}