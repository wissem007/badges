package tn.com.smartsoft.framework.presentation.view.desktop.login;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.utils.ResolveEventPathUtils;
import tn.com.smartsoft.framework.presentation.view.desktop.DefaultUserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;

public class UIDefaultAccesApplication implements UIAccesApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultUserDesktop userDesktop;
	private ComponentId loginUserActionId;
	private boolean isLogin = false;
	private boolean isSessionExpired = false;

	public UIDefaultAccesApplication(DefaultUserDesktop userDesktop, ComponentId loginUserActionId) {
		super();
		this.userDesktop = userDesktop;
		this.loginUserActionId = loginUserActionId;
	}

	public boolean isSessionExpired() {
		return isSessionExpired;
	}

	public void setSessionExpired(boolean isSessionExpired) {
		this.isSessionExpired = isSessionExpired;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void start(WebContext context) {
		userDesktop.setUserContext(null);
		userDesktop.setModuleExplorer(null);
		if (isSessionExpired()) {
			userDesktop.messagesHandler().addMessage("0120005");
			isSessionExpired = false;
		}
		userDesktop.userDesktopNavigation().goToUserAction(loginUserActionId, context);
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public void fireActionEvent(WebContext context) {
		start(context);
	}

	public DesktopPartType desktopPartType() {
		return DesktopPartType.ACCES_APPLICATION_PART;
	}

	public String resolvePath(WebContext context, String page, String componentId, String event) {
		return ResolveEventPathUtils.resolveEventPath(this, context, page, componentId, event);
	}
}
