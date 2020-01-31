package tn.com.smartsoft.framework.configuration.impl;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SubModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SujetDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoParseBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DataBaseDefinition;
import tn.com.smartsoft.framework.dao.definition.DbDefinition;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;

public class DefaultDbDefinition implements DbDefinition, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationManager applicationManager;

	public DefaultDbDefinition(ApplicationManager applicationManager) {
		super();
		this.applicationManager = applicationManager;
	}

	public DaoBeanDefinition getDaoBeanDefinition(ComponentId daoBeanId) {
		DaoBeanDefinition daoBeanDefinition = getSujetDefinition(daoBeanId.getSujetId()).getDaoBeanDefinition(daoBeanId.getId());
		if (daoBeanDefinition == null)
			throw new TechnicalException("no DaoBean whith  " + daoBeanId);
		return daoBeanDefinition;
	}

	public EntiteBean getEntiteBean(Class<?> entiteId) {
		return applicationManager.applicationCacheDictionaryManager().getEntiteBean(entiteId.getName());
	}

	public DataBaseDefinition getDataBaseDefinition() {
		return applicationManager.applicationDefinition().getDataBaseDefinition();
	}

	public SujetDefinition getSujetDefinition(SujetBeanId sujetId) {
		ModuleDefinition moduleDefinition = applicationManager.applicationDefinition().getModuleDefinition(sujetId.getModuleId());
		if (moduleDefinition == null)
			throw new TechnicalException("no Module whith  ModuleId" + sujetId.getModuleId());
		SubModuleDefinition subModuleDefinition = moduleDefinition.getSubModuleDefinition(sujetId.getSubModuleId());
		if (subModuleDefinition == null)
			throw new TechnicalException("no SubModule whith  ModuleId" + sujetId.getModuleId() + "  ,SubModuleId :" + sujetId.getSubModuleId());
		SujetDefinition sujetDefinition = subModuleDefinition.getSujetDefinition(sujetId.getSujetId());
		if (sujetDefinition == null)
			throw new TechnicalException("no Sujet whith  " + sujetId);
		return sujetDefinition;
	}

	public DaoParseBeanDefinition getDaoParseBeanDefinition(ComponentId daoParseBeanId) {
		DaoParseBeanDefinition daoParseBeanDefinition = getSujetDefinition(daoParseBeanId.getSujetId()).getDaoParseBeanDefinition(daoParseBeanId.getId());
		if (daoParseBeanDefinition == null)
			throw new TechnicalException("no DaoParseBean whith  " + daoParseBeanId);
		return daoParseBeanDefinition;
	}

}
