package tn.com.smartsoft.framework.presentation.message;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;

public interface MessagesHandler {
	public void addMessage(List<UIMessage> messages);

	public abstract List<UIMessage> getMessages();

	public abstract void addMessage(UIMessage message);

	public abstract void addMessage(String code);

	public abstract void addMessage(String code, String arg0);

	public abstract void addMessage(String code, String arg0, String arg1);

	public abstract void addMessage(String code, String arg0, String arg1, String arg2);

	public abstract void addMessage(String code, String[] arguments);

	public abstract void addMessage(FunctionalException functionalException);

	public abstract void addMessage(TechnicalException technicalException);

	public abstract void resetMessage();

}