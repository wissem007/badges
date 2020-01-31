package tn.com.smartsoft.framework.beans.factory;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.beans.definition.BeansDefinition;
import tn.com.smartsoft.framework.configuration.ComponentId;

public class BeanFactoryImpl implements Serializable, BeanFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BeansDefinition beansDefinition;

	public BeanFactoryImpl(BeansDefinition beansDefinition) {
		super();
		this.beansDefinition = beansDefinition;
	}

	public Object createBean(ComponentId beanId) throws DaoFunctionalException {
		BeanDefinition beanDef = beansDefinition.getBeanDefinition(beanId);
		Object bean = BeanFactoryUtils.createBean(this, beanId, beanDef, new CreateInstanceBeanFactory() {
			public Object create(Object bean) {
				try {
					return bean;
				} catch (Exception e) {
					throw new TechnicalException(e);
				}
			}
		});
		return bean;
	}

}
