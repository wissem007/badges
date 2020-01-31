package tn.com.smartsoft.framework.presentation.view.action;

import java.io.Serializable;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.view.action.controleur.ActionControlerConfig;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public interface UserActionControleur extends Serializable{
	public void runAction(ActionControleur action, ListenerContext context);
	
	public ActionControleur createActionControleur(ActionControlerConfig actionConfig, List<ActionValidatorControleur> validators, ActionFailureControleur failure);
	
	public ActionControleur createActionControleur(Object contolerBean, String methodName, List<ActionValidatorControleur> validators, ActionFailureControleur failure);
	
	public ActionControleur createActionControleur(String methodName, List<ActionValidatorControleur> validators, ActionFailureControleur failure);
	
	public ActionControleur createAjaxActionControleur(String methodName, List<ActionValidatorControleur> validators);
	
	public ActionControleur createStandarActionControleur(String methodName, List<ActionValidatorControleur> validators);
	
	public ActionControleur createActionControleur(ActionControlerConfig actionConfig, ActionFailureControleur failure);
	
	public ActionControleur createActionControleur(Object contolerBean, String methodName, ActionFailureControleur failure);
	
	public ActionControleur createActionControleur(String methodName, ActionFailureControleur failure);
	
	public ActionControleur createAjaxActionControleur(String methodName);
	
	public ActionControleur createStandarActionControleur(String methodName);
	
	public ActionValidatorControleur createActionValidatorControleur(String methodName);
	
	public ActionValidatorControleur createActionValidatorControleur(ActionControlerConfig actionConfig);
	
	public void runAction(ActionControlerConfig actionConfig, ListenerContext context) throws TechnicalException, FunctionalException;
	
	public void runAction(Object contolerBean, String methodName, ListenerContext context) throws TechnicalException, FunctionalException;
	
	public void runAction(String methodName, ListenerContext context) throws TechnicalException, FunctionalException;
}
