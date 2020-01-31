package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import java.util.Hashtable;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public class UIDefaultEventHandler implements UIObject, UIEventHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, UIEvent> handlerList = new Hashtable<String, UIEvent>();
	private UIComponent source;
	
	public UIDefaultEventHandler(UIComponent source) {
		super();
		this.source = source;
	}
	
	public UIEvent getEvent(String name) {
		return (UIEvent) handlerList.get(name.toLowerCase().toString());
	}
	
	public void addEvent(UIEvent event) {
		event.setParent(source);
		handlerList.put(event.getName().toLowerCase().toString(), event);
	}
	
	public void removeEvent(String event) {
		handlerList.remove(event.toLowerCase());
	}
	
	public String[] getEventNames() {
		String eventNames[] = new String[handlerList.size()];
		return (String[]) handlerList.keySet().toArray(eventNames);
	}
	
	public void fireActionEvent(WebContext webContext, ClientEvent eventName) {
		fireActionEvent(webContext, eventName.toString());
	}
	
	public void fireActionEvent(WebContext webContext, String eventName) {
		ListenerContext context = new ListenerContext(eventName, source, webContext);
		UIEvent event = getEvent(eventName);
		if (event == null) {
			Logger.getLogger(UIDefaultEventHandler.class).error("invalid event name whith component : " + source.getId() + "  and event name :" + eventName);
			context.userDesktop().messagesHandler().addMessage("0300001");
			return;
		}
		ActionControleur listener = event.getListener();
		if (listener == null) {
			Logger.getLogger(UIDefaultEventHandler.class).error("no listener whith component : " + source.getId() + "  and event name :" + eventName);
			context.userDesktop().messagesHandler().addMessage("0300001");
			return;
		}
		context.userAction().getControleur().runAction(listener, context);
	}
	
	public void fireActionEvent(WebContext context) {
		this.fireActionEvent(context, context.request().requestEventInfo().getEvent());
	}
	
}
