package tn.com.smartsoft.framework.business.definition;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;

public class BusinessBeanRefDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String ref;
	private String sujet;
	private String subModule;
	private String module;

	public BusinessBeanRefDefinition() {
		super();

	}

	public String getName() {
		return name;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public ComponentId getComponentRefId(ComponentId parentComponentId) {
		SujetBeanId sujetBeanId = new SujetBeanId(parentComponentId.getSujetId(), getModule(), getSubModule(), getSujet());
		ComponentId componentBeanRefId = new ComponentIdImpl(sujetBeanId, getRef());
		return componentBeanRefId;
	}
}
