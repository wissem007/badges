package tn.com.smartsoft.framework.configuration.impl;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.business.definition.BusinessBeanDefinition;
import tn.com.smartsoft.framework.business.definition.BusinessBeansDefinition;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SubModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SujetDefinition;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;

public class DefaultBusinessBeansDefinition implements BusinessBeansDefinition, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationDefinition applicationDefinition;

	public DefaultBusinessBeansDefinition(ApplicationDefinition applicationDefinition) {
		super();
		this.applicationDefinition = applicationDefinition;
	}

	public BusinessBeanDefinition getBusinessBeanDefinition(ComponentId serviceId) {
		BusinessBeanDefinition businessBeanDefinition = getSujetDefinition(serviceId.getSujetId()).getBusinessBeanDefinition(serviceId.getId());
		if (businessBeanDefinition == null)
			throw new TechnicalException("no BusinessBean whith  " + serviceId);
		return businessBeanDefinition;
	}

	public SujetDefinition getSujetDefinition(SujetBeanId sujetId) {
		ModuleDefinition moduleDefinition = applicationDefinition.getModuleDefinition(sujetId.getModuleId());
		SubModuleDefinition subModuleDefinition = moduleDefinition.getSubModuleDefinition(sujetId.getSubModuleId());
		SujetDefinition sujetDefinition = subModuleDefinition.getSujetDefinition(sujetId.getSujetId());
		if (sujetDefinition == null)
			throw new TechnicalException("no Sujet whith  " + sujetId);
		return sujetDefinition;
	}

}
