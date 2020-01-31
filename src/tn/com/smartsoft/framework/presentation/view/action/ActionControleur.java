package tn.com.smartsoft.framework.presentation.view.action;

import java.io.Serializable;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public interface ActionControleur extends Serializable {
	
	public abstract void run(ListenerContext context) throws FunctionalException, TechnicalException;
	
	public abstract ActionFailureControleur failure();
	
	public List<ActionValidatorControleur> validators();
}