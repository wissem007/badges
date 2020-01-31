package tn.com.smartsoft.framework.test;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.beans.factory.BeanFactory;
import tn.com.smartsoft.framework.business.factory.BusinessBeanFactory;
import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.framework.dao.factory.DaoFactory;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;

public abstract class AbstractTestBean {
	protected Logger log = Logger.getLogger(getClass());
	protected ApplicationManager applicationManager;

	public AbstractTestBean(ApplicationManager applicationManager) {
		super();
		this.applicationManager = applicationManager;
	}

	public ApplicationManager getApplicationManager() {
		return applicationManager;
	}

	public BeanFactory getBeanFactory() {
		return applicationManager.beanFactory();
	}

	public DaoFactory getDaoFactory() {
		return applicationManager.daoFactory();
	}

	public BusinessBeanFactory getBusinessBeanFactory() {
		return applicationManager.businessBeanFactory();
	}

	public PresentationBeanFactory getPresentationBeanFactory() {
		return applicationManager.presentationBeanFactory();
	}

	public WebContext getTestContext() {
		return new TestContext(applicationManager);
	}

	public Object createBusinessBean(String module, String subModule, String sujet, String id) throws FunctionalException {
		return getBusinessBeanFactory().createBusinessBean(new ComponentIdImpl(module, subModule, sujet, id));
	}
}
