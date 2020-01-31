package tn.com.smartsoft.framework.presentation.view.action.controleur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.DefauldInvokerMethod;
import tn.com.smartsoft.commons.utils.InvokerMethod;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionFailureControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionValidatorControleur;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class DefaultActionControleur implements Serializable, ActionControleur {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionFailureControleur failure;
	private ActionControlerConfig actionControlerConfig;
	protected List<ActionValidatorControleur> validators = new ArrayList<ActionValidatorControleur>();
	
	public DefaultActionControleur(ActionControlerConfig actionControlerConfig, List<ActionValidatorControleur> validators, ActionFailureControleur failure) {
		this.actionControlerConfig = actionControlerConfig;
		this.failure = failure;
		this.validators = validators;
	}
	
	public DefaultActionControleur(Object contolerBean, String methodName, List<ActionValidatorControleur> validators, ActionFailureControleur failure) {
		this(new DefaultActionControlerConfig(contolerBean, methodName), validators, failure);
	}
	
	public DefaultActionControleur(ActionControlerConfig actionControlerConfig, ActionFailureControleur failure) {
		this.actionControlerConfig = actionControlerConfig;
		this.failure = failure;
	}
	
	public DefaultActionControleur(Object contolerBean, String methodName, ActionFailureControleur failure) {
		this(new DefaultActionControlerConfig(contolerBean, methodName), failure);
	}
	
	public void run(ListenerContext context) throws FunctionalException, TechnicalException {
		InvokerMethod invokerMethod = new DefauldInvokerMethod(actionControlerConfig.getContolerBean(), actionControlerConfig.getMethodName());
		invokerMethod.run(context, ListenerContext.class);
	}
	
	public ActionFailureControleur failure() {
		return failure;
	}
	
	public void addValidator(ActionValidatorControleur validator) {
		validators.add(validator);
	}
	
	public void setValidators(List<ActionValidatorControleur> validators) {
		this.validators = validators;
	}
	
	public List<ActionValidatorControleur> validators() {
		return validators;
	}
}
