package tn.com.smartsoft.framework.presentation.view.action.controleur;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.DefauldInvokerMethod;
import tn.com.smartsoft.commons.utils.InvokerMethod;
import tn.com.smartsoft.framework.presentation.view.action.ActionValidatorControleur;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class DefaultActionValidatorControleur implements ActionValidatorControleur {
	private ActionControlerConfig actionControlerConfig;
	
	public DefaultActionValidatorControleur(ActionControlerConfig actionControlerConfig) {
		this.actionControlerConfig = actionControlerConfig;
	}
	
	public DefaultActionValidatorControleur(Object contolerBean, String methodName) {
		this(new DefaultActionControlerConfig(contolerBean, methodName));
	}
	
	public boolean run(ListenerContext context) throws FunctionalException, TechnicalException {
		InvokerMethod invokerMethod = new DefauldInvokerMethod(actionControlerConfig.getContolerBean(), actionControlerConfig.getMethodName());
		return (Boolean) invokerMethod.run(context, ListenerContext.class);
	}
}
