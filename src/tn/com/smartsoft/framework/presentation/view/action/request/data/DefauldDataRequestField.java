package tn.com.smartsoft.framework.presentation.view.action.request.data;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.message.DefaultMessagesHandler;
import tn.com.smartsoft.framework.presentation.message.MessagesHandler;
import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class DefauldDataRequestField implements UIObject, DataRequestField, DataControlerRequestField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ControlerRequestField controlerRequestField;
	protected ListenerContext listenerContext = null;
	protected boolean validRequestValue = true;
	protected Object value = null;
	protected Object requestValue = null;
	protected MessagesHandler messagesHandler = new DefaultMessagesHandler();
	
	public DefauldDataRequestField(ControlerRequestField controlerRequestField, Object requestValue, ListenerContext listenerContext) {
		super();
		this.controlerRequestField = controlerRequestField;
		this.listenerContext = listenerContext;
		this.requestValue = requestValue;
	}
	
	public boolean isValidRequestValue() {
		return validRequestValue;
	}
	
	public Object getValue() {
		return value;
	}
	
	public Object getRequestValue() {
		return requestValue;
	}
	
	public String getLibelle() {
		return controlerRequestField.getLibelle();
	}
	
	public MessagesHandler getMessagesHandler() {
		return messagesHandler;
	}
	
	public ListenerContext getListenerContext() {
		return listenerContext;
	}
	
	public String getId() {
		return controlerRequestField.getId();
	}
	
	public void setValidRequestValue(boolean validRequestValue) {
		this.validRequestValue = validRequestValue;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public void validateDataType() {
		controlerRequestField.validateDataType(this);
	}
	
	public void validate() {
		controlerRequestField.validate(this);
	}
	
	public void commit() {
		controlerRequestField.commit(this);
	}
}
