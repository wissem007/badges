package tn.com.smartsoft.framework.presentation.view.action;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public interface ActionValidatorControleur {
	public abstract boolean run(ListenerContext context) throws FunctionalException, TechnicalException;
}
