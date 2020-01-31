package tn.com.smartsoft.framework.configuration.definition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class SubModuleDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subModuleId;
	private String defaultDataSource;
	private Map<Serializable, SujetDefinition> sujetsDef = new HashMap<Serializable, SujetDefinition>();
	private ModuleDefinition parentModuleDefinition;

	public Map<Serializable, SujetDefinition> getSujetsDef() {
		return sujetsDef;
	}

	public void setSujetsDef(Map<Serializable, SujetDefinition> sujetsDef) {
		this.sujetsDef = sujetsDef;
	}

	public void addSujetDefinition(SujetDefinition value) {
		value.setParentSubModuleDef(this);
		sujetsDef.put(value.getId(), value);
	}

	public SujetDefinition getSujetDefinition(String name) {
		SujetDefinition sujetDefinition = (SujetDefinition) sujetsDef.get(name);
		return sujetDefinition;
	}

	public String getSubModuleId() {
		return subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

	public Serializable getId() {
		return getSubModuleId();
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public ModuleDefinition getParentModuleDefinition() {
		return parentModuleDefinition;
	}

	public void setParentModuleDefinition(ModuleDefinition parentModuleDefinition) {
		this.parentModuleDefinition = parentModuleDefinition;
	}

	public String getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}
}
