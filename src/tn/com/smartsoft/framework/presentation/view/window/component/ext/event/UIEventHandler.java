package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;

public interface UIEventHandler {
	public abstract String[] getEventNames();

	public UIEvent getEvent(String event);

	public void removeEvent(String event);

	public void addEvent(UIEvent event);

	public void fireActionEvent(WebContext context, ClientEvent eventName);

	public void fireActionEvent(WebContext context, String eventName);

	public void fireActionEvent(WebContext context);
}
