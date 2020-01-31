package tn.com.smartsoft.framework.presentation.view.desktop;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;

public interface UserDesktopNavigation {
	public PresentationBeanFactory presentationBeanFactory();

	public abstract void goToModule(String moduleId, WebContext context);

	public abstract void goToDefaultModule(WebContext context);

	public abstract void goToUserAction(ComponentId userActionId, WebContext context);

	public abstract void doStartSession(WebContext context, UserContext userContextBean);

	public abstract void doEndSession(WebContext context);

	public void goToDefaultUserAction(WebContext context);

	public void fireActionEvent(WebContext context);
}