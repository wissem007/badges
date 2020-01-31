package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionFailureControleur;
import tn.com.smartsoft.framework.presentation.view.action.ActionValidatorControleur;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class UIEventListener implements ActionControleur {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIEvent event;
	private String afterActionMethod;
	private String actionMethod;
	private Object contolerBean;
	private Object delegateMethod;
	
	public UIEventListener(UIEvent event, String actionMethod, Object contolerBean, Object delegateMethod, String afterActionMethod) {
		super();
		this.event = event;
		this.actionMethod = actionMethod;
		this.contolerBean = contolerBean;
		this.delegateMethod = delegateMethod;
		this.afterActionMethod = afterActionMethod;
	}
	
	public UIEventListener(UIEvent event, String actionMethod, Object contolerBean, String afterActionMethod) {
		this(event, actionMethod, contolerBean, null, afterActionMethod);
	}
	
	public UIEventListener(UIEvent event, String actionMethod, String afterActionMethod) {
		this(event, actionMethod, null, afterActionMethod);
	}
	
	public UIEventListener(UIEvent event, String actionMethod, Object contolerBean, Object delegateMethod) {
		this(event, actionMethod, contolerBean, delegateMethod, null);
	}
	
	public UIEventListener(UIEvent event, String actionMethod, Object contolerBean) {
		this(event, actionMethod, contolerBean, (Object) null);
	}

	public UIEventListener(UIEvent event, String actionMethod) {
		this(event, actionMethod, (Object) null);
	}

	public void run(ListenerContext context) throws FunctionalException, TechnicalException {
		if (StringUtils.isNotBlank(getAfterActionMethod())) {
			context.userAction().getControleur().runAction(getAfterActionMethod(), context);
		}
		if (StringUtils.isBlank(getMethodName()))
			return;
		if (contolerBean != null)
			context.userAction().getControleur().runAction(contolerBean, getMethodName(), context);
		else
			context.userAction().getControleur().runAction(getMethodName(), context);
	}
	
	public String getAfterActionMethod() {
		return afterActionMethod;
	}
	
	public void setAfterActionMethod(String afterActionMethod) { 
		this.afterActionMethod = afterActionMethod;
	}
	
	private String getMethodName() {
		if (delegateMethod != null)
			return (String) BeanObjectUtils.getPropertyValue(delegateMethod, actionMethod);
		return actionMethod;
	}
	
	public ActionFailureControleur failure() {
		return ActionFailureControleur.valueOf(event.httpRequestType().getType().toUpperCase());
	}
	
	public List<ActionValidatorControleur> validators() {
		return null;
	}
	
}
