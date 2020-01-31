package tn.com.smartsoft.framework.business.factory;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;

public interface BusinessBeanFactory {

	public abstract Object createBusinessBean(ComponentId beanId) throws FunctionalException;

}