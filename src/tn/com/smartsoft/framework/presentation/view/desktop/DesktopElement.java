package tn.com.smartsoft.framework.presentation.view.desktop;

import tn.com.smartsoft.framework.presentation.context.WebContext;

public interface DesktopElement {
	public void fireActionEvent(WebContext context);

	public DesktopPartType desktopPartType();

	public String resolvePath(WebContext context, String page, String componentId, String event);
}
