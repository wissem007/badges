package tn.com.smartsoft.framework.presentation.view.action.utils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.action.DefaultUserAction;
import tn.com.smartsoft.framework.presentation.view.action.exception.CreateWindowException;

public class UserActionExceptionUtils {
	public static void loadCreateWindowException(CreateWindowException e, WebContext context) {
		if (e.getCauseCreateWindow() instanceof FunctionalException) {
			context.userDesktop().messagesHandler().addMessage((FunctionalException) e.getCauseCreateWindow());
		} else {
			TechnicalException technicalExcep = new TechnicalException(e);
			technicalExcep.printLogTrace(Logger.getLogger(DefaultUserAction.class));
			context.userDesktop().messagesHandler().addMessage(technicalExcep);
		}
	}
}
