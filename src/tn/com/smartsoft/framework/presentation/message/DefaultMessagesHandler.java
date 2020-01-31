package tn.com.smartsoft.framework.presentation.message;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.UIObject;

public class DefaultMessagesHandler implements UIObject, MessagesHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIMessage> messages = new ArrayList<UIMessage>();

	public List<UIMessage> getMessages() {
		if (messages == null)
			messages = new ArrayList<UIMessage>();
		return messages;
	}

	public void addMessage(List<UIMessage> messages) {
		this.getMessages().addAll(messages);
	}

	public void addMessage(UIMessage message) {
		this.getMessages().add(message);
	}

	public void addMessage(String code) {
		addMessage(new UIMessage(code));
	}

	public void addMessage(String code, String arg0) {
		addMessage(new UIMessage(code, arg0));
	}

	public void addMessage(String code, String arg0, String arg1) {
		addMessage(new UIMessage(code, arg0, arg1));
	}

	public void addMessage(String code, String arg0, String arg1, String arg2) {
		addMessage(new UIMessage(code, arg0, arg1, arg2));
	}

	public void addMessage(String code, String[] arguments) {
		addMessage(new UIMessage(code, arguments));
	}

	public void resetMessage() {
		messages = new ArrayList<UIMessage>();
	}

	public void addMessage(FunctionalException functionalException) {
		addMessage(functionalException.getErrorCode(), functionalException.getArgs());
	}

	public void addMessage(TechnicalException functionalException) {
		addMessage("0300001", functionalException.getMessage());
	}
}
