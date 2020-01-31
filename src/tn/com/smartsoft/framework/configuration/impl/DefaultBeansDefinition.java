package tn.com.smartsoft.framework.configuration.impl;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.beans.definition.BeansDefinition;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SubModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SujetDefinition;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;

public class DefaultBeansDefinition implements BeansDefinition, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationDefinition applicationDefinition;

	public DefaultBeansDefinition(ApplicationDefinition applicationDefinition) {
		super();
		this.applicationDefinition = applicationDefinition;
	}

	public BeanDefinition getBeanDefinition(ComponentId beanId) {
		SujetDefinition sujetDefinition = getSujetDefinition(beanId.getSujetId());
		BeanDefinition beanDefinition = sujetDefinition.getBeanDefinition(beanId.getId());
		if (beanDefinition == null)
			throw new TechnicalException("no Bean whith  " + beanId);
		return beanDefinition;
	}

	public SujetDefinition getSujetDefinition(SujetBeanId sujetId) {
		System.out.println(sujetId.getSujetId());
		ModuleDefinition moduleDefinition = applicationDefinition.getModuleDefinition(sujetId.getModuleId());
		SubModuleDefinition subModuleDefinition = moduleDefinition.getSubModuleDefinition(sujetId.getSubModuleId());
		SujetDefinition sujetDefinition = subModuleDefinition.getSujetDefinition(sujetId.getSujetId());
		if (sujetDefinition == null)
			throw new TechnicalException("no Sujet whith  " + sujetId);
		return sujetDefinition;
	}

}
