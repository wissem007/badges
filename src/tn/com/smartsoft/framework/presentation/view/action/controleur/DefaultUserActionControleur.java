package tn.com.smartsoft.framework.presentation.view.action.controleur;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionFailureControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionValidatorControleur;
import tn.com.smartsoft.framework.presentation.view.action.UserActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.utils.FormRequestUtils;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class DefaultUserActionControleur implements UserActionControleur {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object contolerBean;
	
	public DefaultUserActionControleur(Object contolerBean) {
		super();
		this.contolerBean = contolerBean;
	}
	
	public ActionControleur createActionControleur(ActionControlerConfig actionConfig, List<ActionValidatorControleur> validators, ActionFailureControleur failure) {
		return new DefaultActionControleur(actionConfig, validators, failure);
	}
	
	public ActionControleur createActionControleur(Object contolerBean, String methodName, List<ActionValidatorControleur> validators, ActionFailureControleur failure) {
		return new DefaultActionControleur(contolerBean, methodName, validators, failure);
	}
	
	public ActionControleur createActionControleur(String methodName, List<ActionValidatorControleur> validators, ActionFailureControleur failure) {
		return new DefaultActionControleur(this.contolerBean, methodName, validators, failure);
	}
	
	public ActionControleur createAjaxActionControleur(String methodName, List<ActionValidatorControleur> validators) {
		return new DefaultActionControleur(this.contolerBean, methodName, validators, ActionFailureControleur.AJAX);
	}
	
	public ActionControleur createStandarActionControleur(String methodName, List<ActionValidatorControleur> validators) {
		return new DefaultActionControleur(this.contolerBean, methodName, validators, ActionFailureControleur.STANDAR);
	}
	
	public ActionControleur createActionControleur(ActionControlerConfig actionConfig, ActionFailureControleur failure) {
		return new DefaultActionControleur(actionConfig, failure);
	}
	
	public ActionControleur createActionControleur(Object contolerBean, String methodName, ActionFailureControleur failure) {
		return new DefaultActionControleur(contolerBean, methodName, failure);
	}
	
	public ActionControleur createActionControleur(String methodName, ActionFailureControleur failure) {
		return new DefaultActionControleur(this.contolerBean, methodName, failure);
	}
	
	public ActionControleur createAjaxActionControleur(String methodName) {
		return new DefaultActionControleur(this.contolerBean, methodName, ActionFailureControleur.AJAX);
	}
	
	public ActionControleur createStandarActionControleur(String methodName) {
		return new DefaultActionControleur(this.contolerBean, methodName, ActionFailureControleur.STANDAR);
	}
	
	public ActionValidatorControleur createActionValidatorControleur(String methodName) {
		return new DefaultActionValidatorControleur(this.contolerBean, methodName);
	}
	
	public ActionValidatorControleur createActionValidatorControleur(ActionControlerConfig actionConfig) {
		return new DefaultActionValidatorControleur(actionConfig);
	}
	
	public void runAction(ActionControlerConfig actionConfig, ListenerContext context) throws TechnicalException, FunctionalException {
		DefaultActionControleur actionControleur = new DefaultActionControleur(actionConfig, null);
		actionControleur.run(context);
	}
	
	public void runAction(Object contolerBean, String methodName, ListenerContext context) throws TechnicalException, FunctionalException {
		runAction(new DefaultActionControlerConfig(contolerBean, methodName), context);
	}
	
	public void runAction(String methodName, ListenerContext context) throws TechnicalException, FunctionalException {
		runAction(contolerBean, methodName, context);
	}
	
	public void runAction(ActionControleur action, ListenerContext context) {
		try {
			FormRequestUtils.handlerRequestData(context);
			boolean isValid = context.isValidRequest();
			if (!isValid) {
				action.failure().run(context);
				return;
			}
			if (action.validators() != null)
				for (int i = 0; i < action.validators().size(); i++) {
					ActionValidatorControleur validator = action.validators().get(i);
					isValid = validator.run(context);
					if (!isValid) {
						action.failure().run(context);
						return;
					}
				}
			action.run(context);
		} catch (FunctionalException e) {
			context.addMessage(e);
			action.failure().run(context);
		} catch (Exception e) {
			new TechnicalException(e).printLogTrace(Logger.getLogger(getClass()));
			context.addMessage(new TechnicalException(e));
			action.failure().run(context);
		}
	}
	
}
