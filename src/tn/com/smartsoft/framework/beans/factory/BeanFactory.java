package tn.com.smartsoft.framework.beans.factory;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;

public interface BeanFactory {

	public abstract Object createBean(ComponentId beanId) throws DaoFunctionalException;

}