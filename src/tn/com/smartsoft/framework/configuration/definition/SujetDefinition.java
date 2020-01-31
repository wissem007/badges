package tn.com.smartsoft.framework.configuration.definition;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.business.definition.BusinessBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoParseBeanDefinition;
import tn.com.smartsoft.framework.presentation.definition.BindingModelDefinition;
import tn.com.smartsoft.framework.presentation.definition.ControleBeanDefinition;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.definition.WindowDefinition;

public class SujetDefinition implements IDefinition, CompositeBeansDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sujetId;
	private BeansDefinition compositeBeansDefinition = new BeansDefinition();
	private SubModuleDefinition parentSubModuleDef;
	private String defaultDataDource;

	public String getDefaultDataDource() {
		return defaultDataDource;
	}

	public void addBeansDefinition(BeansDefinition beansDefinition) {

	}

	public void setDefaultDataDource(String defaultDataDource) {
		this.defaultDataDource = defaultDataDource;
	}

	public BeanDefinition getBeanDefinition(String name) {
		return compositeBeansDefinition.getBeanDefinition(name);
	}

	public BusinessBeanDefinition getBusinessBeanDefinition(String name) {
		return compositeBeansDefinition.getBusinessBeanDefinition(name);
	}

	public ControleBeanDefinition getControleBeanDefinition(String name) {
		return compositeBeansDefinition.getControleBeanDefinition(name);
	}

	public DaoBeanDefinition getDaoBeanDefinition(String name) {
		return compositeBeansDefinition.getDaoBeanDefinition(name);
	}

	public UserActionDefinition getUserActionDefinition(String name) {
		return compositeBeansDefinition.getUserActionDefinition(name);
	}

	public String getVariableSystem(String name) {
		return compositeBeansDefinition.getVariableSystem(name);
	}

	public DaoParseBeanDefinition getDaoParseBeanDefinition(String name) {
		return compositeBeansDefinition.getDaoParseBeanDefinition(name);
	}

	public BindingModelDefinition getBindingModelDefinition(String name) {
		return compositeBeansDefinition.getBindingModelDefinition(name);
	}

	public WindowDefinition getWindowDefinition(String name) {
		return compositeBeansDefinition.getWindowBeanDefinition(name);
	}

	public String getSujetId() {
		return sujetId;
	}

	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}

	public Serializable getId() {
		return getSujetId();
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public SubModuleDefinition getParentSubModuleDef() {
		return parentSubModuleDef;
	}

	public void setParentSubModuleDef(SubModuleDefinition parentSubModuleDef) {
		this.parentSubModuleDef = parentSubModuleDef;
	}

	public BeansDefinition getCompositeBeansDefinition() {
		return compositeBeansDefinition;
	}

}
