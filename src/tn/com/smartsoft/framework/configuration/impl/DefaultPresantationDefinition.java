package tn.com.smartsoft.framework.configuration.impl;

import java.io.Serializable;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SubModuleDefinition;
import tn.com.smartsoft.framework.configuration.definition.SujetDefinition;
import tn.com.smartsoft.framework.presentation.definition.BindingModelDefinition;
import tn.com.smartsoft.framework.presentation.definition.ControleBeanDefinition;
import tn.com.smartsoft.framework.presentation.definition.PresentationDefinition;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.definition.WindowDefinition;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;

public class DefaultPresantationDefinition implements PresentationDefinition, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationDefinition applicationDefinition;
	private ApplicationCacheDictionaryManager applicationCacheDictionaryManager;

	public DefaultPresantationDefinition(ApplicationDefinition applicationDefinition, ApplicationCacheDictionaryManager applicationCacheDictionaryManager) {
		super();
		this.applicationDefinition = applicationDefinition;
		this.applicationCacheDictionaryManager = applicationCacheDictionaryManager;
	}

	public boolean isExistUserAction(ComponentId userActionId) {
		ModuleDefinition moduleDefinition = applicationDefinition.getModuleDefinition(userActionId.getSujetId().getModuleId());
		if (moduleDefinition == null)
			return false;
		SubModuleDefinition subModuleDefinition = moduleDefinition.getSubModuleDefinition(userActionId.getSujetId().getSubModuleId());
		if (subModuleDefinition == null)
			return false;
		SujetDefinition sujetDefinition = subModuleDefinition.getSujetDefinition(userActionId.getSujetId().getSujetId());
		if (sujetDefinition == null)
			return false;
		UserActionDefinition userActionDefinition = sujetDefinition.getUserActionDefinition(userActionId.getId());
		if (userActionDefinition == null)
			return false;
		return true;
	}

	public UserActionDefinition getUserActionDefinition(ComponentId applicationActionId) {
		UserActionDefinition userActionDefinition = getSujetDefinition(applicationActionId.getSujetId()).getUserActionDefinition(applicationActionId.getId());
		if (userActionDefinition == null)
			throw new TechnicalException("no UserAction whith  " + applicationActionId);
		if (userActionDefinition.isExtends()) {
			UserActionDefinition userActionDefinitionIn = new UserActionDefinition();
			UserActionDefinition userActionDefinitionEx = getUserActionDefinition(new ComponentIdImpl(applicationActionId, userActionDefinition.getExtendsName()));
			userActionDefinitionEx.copyTo(userActionDefinitionIn);
			userActionDefinition.copyTo(userActionDefinitionIn);
			userActionDefinition = userActionDefinitionIn;
		}
		return userActionDefinition;
	}

	public ControleBeanDefinition getControleBeanDefinition(ComponentId controleBeanId) {
		ControleBeanDefinition controleBeanDefinition = getSujetDefinition(controleBeanId.getSujetId()).getControleBeanDefinition(controleBeanId.getId());
		if (controleBeanDefinition == null)
			throw new TechnicalException("no ControleBean whith  " + controleBeanId);
		if (controleBeanDefinition.isExtends()) {
			ControleBeanDefinition controleBeanDefinitionIn = new ControleBeanDefinition();
			ControleBeanDefinition controleBeanDefinitionEx = getControleBeanDefinition(new ComponentIdImpl(controleBeanId, controleBeanDefinition.getExtendsName()));
			controleBeanDefinitionEx.copyTo(controleBeanDefinitionIn);
			controleBeanDefinition.copyTo(controleBeanDefinitionIn);
			controleBeanDefinition = controleBeanDefinitionIn;
		}
		return controleBeanDefinition;
	}

	public WebDefinition getWebDefinition() {
		return applicationDefinition.getWebDefinition();
	}

	public SujetDefinition getSujetDefinition(SujetBeanId sujetId) {
		ModuleDefinition moduleDefinition = applicationDefinition.getModuleDefinition(sujetId.getModuleId());
		if (moduleDefinition == null)
			throw new TechnicalException("no  Module whith  ModuleId:" + sujetId.getModuleId());
		SubModuleDefinition subModuleDefinition = moduleDefinition.getSubModuleDefinition(sujetId.getSubModuleId());
		if (subModuleDefinition == null)
			throw new TechnicalException("no  SubModule whith  SubModuleId:" + sujetId.getSubModuleId());
		SujetDefinition sujetDefinition = subModuleDefinition.getSujetDefinition(sujetId.getSujetId());
		if (sujetDefinition == null)
			throw new TechnicalException("no Sujet whith  " + sujetId);
		return sujetDefinition;
	}

	public ApplicationCacheDictionaryManager getApplicationCacheDictionaryManager() {
		return applicationCacheDictionaryManager;
	}

	public void setApplicationCacheDictionaryManager(ApplicationCacheDictionaryManager applicationCacheDictionaryManager) {
		this.applicationCacheDictionaryManager = applicationCacheDictionaryManager;
	}

	public Map<String, ModuleDefinition> getModuleDefinitions() {
		return applicationDefinition.getModulesDefs();
	}

	public ModuleDefinition getModuleDefinition(String moduleId) {
		return applicationDefinition.getModuleDefinition(moduleId);
	}

	public ComponentId getDefaultUserActionId() {
		return applicationDefinition.getDefaultUserActionId();
	}

	public ComponentId getLoginUserActionId() {
		return applicationDefinition.getLoginUserActionId();
	}

	public BindingModelDefinition getBindingModelDefinition(ComponentId bindingModelId) {
		BindingModelDefinition bindingModelDefinition = getSujetDefinition(bindingModelId.getSujetId()).getBindingModelDefinition(bindingModelId.getId());
		if (bindingModelDefinition == null)
			throw new TechnicalException("no BindingModelDefinition whith  " + bindingModelId);
		return bindingModelDefinition;
	}

	public WindowDefinition getWindowDefinition(ComponentId windowDefinitionId) {
		WindowDefinition windowDefinition = getSujetDefinition(windowDefinitionId.getSujetId()).getWindowDefinition(windowDefinitionId.getId());
		if (windowDefinition == null)
			throw new TechnicalException("no windowDefinition whith  " + windowDefinition);
		return windowDefinition;
	}

}
