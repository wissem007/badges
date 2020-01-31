package tn.com.smartsoft.framework.presentation.view.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.RequestParameters;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.message.DefaultMessagesHandler;
import tn.com.smartsoft.framework.presentation.message.MessagesHandler;
import tn.com.smartsoft.framework.presentation.message.UIMessage;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DefauldDataRequestField;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;

public class ListenerContext implements UIObject, MessagesHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String event;
	private UIComponent source;
	private WebContext webContext;
	private DefaultMessagesHandler messagesDelegate = new DefaultMessagesHandler();
	private Map<String, DataRequestField> dataRequestFields = new HashMap<String, DataRequestField>();
	private boolean isValidRequest = true;

	public ListenerContext(String event, UIComponent source, WebContext webContext) {
		super();
		this.event = event;
		this.source = source;
		this.webContext = webContext;
	}

	public boolean isValidRequest() {
		return isValidRequest;
	}

	public Map<String, DataRequestField> getDataRequestFields() {
		return dataRequestFields;
	}

	public DataRequestField getDataRequestField(String id) {
		return dataRequestFields.get(id);
	}

	public void addDataRequestField(DefauldDataRequestField defauldDataRequestField) {
		if (!defauldDataRequestField.isValidRequestValue())
			isValidRequest = false;
		dataRequestFields.put(defauldDataRequestField.getId(), defauldDataRequestField);
	}

	public boolean isDataInputFields() {
		return dataRequestFields.isEmpty();
	}

	public void addMessage(FunctionalException functionalException) {
		messagesDelegate.addMessage(functionalException);
	}

	public void addMessage(List<UIMessage> messages) {
		messagesDelegate.addMessage(messages);
	}

	public void addMessage(String code, String arg0, String arg1, String arg2) {
		messagesDelegate.addMessage(code, arg0, arg1, arg2);
	}

	public void addMessage(String code, String arg0, String arg1) {
		messagesDelegate.addMessage(code, arg0, arg1);
	}

	public void addMessage(String code, String arg0) {
		messagesDelegate.addMessage(code, arg0);
	}

	public void addMessage(String code, String[] arguments) {
		messagesDelegate.addMessage(code, arguments);
	}

	public void addMessage(String code) {
		messagesDelegate.addMessage(code);
	}

	public void addMessage(TechnicalException functionalException) {
		messagesDelegate.addMessage(functionalException);
	}

	public void addMessage(UIMessage message) {
		messagesDelegate.addMessage(message);
	}

	public List<UIMessage> getMessages() {
		return messagesDelegate.getMessages();
	}

	public void resetMessage() {
		messagesDelegate.resetMessage();
	}

	public boolean isEmptyMessage() {
		return getMessages().isEmpty();
	}

	public String event() {
		return event;
	}

	public UIComponent source() {
		return source;
	}

	public UserContext userContext() {
		return userDesktop().userContext();
	}

	public WebContext webContext() {
		return webContext;
	}

	public UserDesktop userDesktop() {
		return webContext().userDesktop();
	}

	public UserAction userAction() {
		return userDesktop().curentUserAction();
	}

	public UIWindow uiWindow() {
		return userAction().currentWindow();
	}

	public Object getDataObject() {
		return userAction().getDataObject();
	}

	public RequestParameters requestParameters() {
		return webContext.request().requestParameter();
	}

	public PayBean getPays() {
		return ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getPays();
	}

	public Long getPaysId() {
		if (getPays() != null)
			return getPays().getPaysId();
		return null;
	}
}
