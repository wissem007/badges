package tn.com.smartsoft.framework.presentation.view.action.model.accessor;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.DefauldInvokerMethod;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanAccessorModel;
import tn.com.smartsoft.framework.presentation.view.action.model.CompositeModel;

public class GenericBeanAccessorModel implements BeanAccessorModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<?> javaType;
	private int type;
	private CompositeModel beanModel;

	public GenericBeanAccessorModel(CompositeModel beanModel, Class<?> javaType, int type) {
		super();
		this.javaType = javaType;
		this.type = type;
		this.beanModel = beanModel;
	}

	public Object newValue() {
		try {
			return javaType.newInstance();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public void setPropertyValue(Object bean, String property, Object value) {
		if (beanModel.containsPropertyDescriptor(property))
			BeanObjectUtils.setPropertyValue(bean, property, value);
		else if (beanModel.containsChildModel(property)) {
			DefauldInvokerMethod invokerMethod = new DefauldInvokerMethod(bean, "setDynamicPropertyValue");
			try {
				invokerMethod.run(property, value, String.class, Object.class);
			} catch (TechnicalException e) {
				throw new TechnicalException("no property '" + property + "' whith bean:'" + bean);
			} catch (FunctionalException e) {
				throw new TechnicalException("no property '" + property + "' whith bean:'" + bean);
			}
		} else
			throw new TechnicalException("no property '" + property + "' whith bean:'" + bean);
	}

	public Object getPropertyValue(Object bean, String property) {
		if (beanModel.containsPropertyDescriptor(property))
			return BeanObjectUtils.getPropertyValue(bean, property);
		else if (beanModel.containsChildModel(property)) {
			DefauldInvokerMethod invokerMethod = new DefauldInvokerMethod(bean, "getDynamicPropertyValue");
			try {
				return invokerMethod.run(property, String.class);
			} catch (TechnicalException e) {
				throw new TechnicalException("no getDynamicPropertyValue '" + property + "' whith bean:'" + bean);
			} catch (FunctionalException e) {
				throw new TechnicalException("no getDynamicPropertyValue '" + property + "' whith bean:'" + bean);
			}
		}
		throw new TechnicalException("no property '" + property + "' whith bean:'" + bean);
	}

	public int getType() {
		return type;
	}

	public boolean isReadableProperty(Object bean, String property) {
		return (beanModel.containsChildModel(property));
	}

	public boolean isWriteableProperty(Object bean, String property) {
		return (beanModel.containsChildModel(property));
	}

}
