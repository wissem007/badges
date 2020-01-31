package tn.com.smartsoft.framework.presentation.view.action.request.data;

import tn.com.smartsoft.framework.presentation.message.MessagesHandler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public interface DataRequestField {
	
	public abstract boolean isValidRequestValue();
	
	public abstract Object getValue();
	
	public abstract Object getRequestValue();
	
	public abstract String getLibelle();
	
	public abstract MessagesHandler getMessagesHandler();
	
	public abstract ListenerContext getListenerContext();
	
	public abstract String getId();
	
	public abstract void setValue(Object value);
	
	public void setValidRequestValue(boolean validRequestValue);
}