package tn.com.smartsoft.framework.presentation.view.action.controleur;

import tn.com.smartsoft.commons.utils.BeanObjectUtils;

public class ActionControlerPropertyMethodConfig implements ActionControlerConfig {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object contolerBean;
	private Object delegatePropertyMethod;
	private String propertyMethodName;
	
	public ActionControlerPropertyMethodConfig(Object contolerBean, Object delegatePropertyMethod, String propertyMethodName) {
		super();
		this.contolerBean = contolerBean;
		this.delegatePropertyMethod = delegatePropertyMethod;
		this.propertyMethodName = propertyMethodName;
	}
	
	public Object getContolerBean() {
		return contolerBean;
	}
	
	public String getMethodName() {
		return (String) BeanObjectUtils.getPropertyValue(delegatePropertyMethod, propertyMethodName);
	}
	
}
