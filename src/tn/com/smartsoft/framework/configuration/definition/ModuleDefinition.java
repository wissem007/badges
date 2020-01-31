package tn.com.smartsoft.framework.configuration.definition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;

public class ModuleDefinition implements IDefinition,ParserDefinition{

	/**
	 * 
	 */
	private static final long						serialVersionUID	= 1L;
	private String									moduleId;																;
	private String									defaultDataDource;
	private Map<Serializable, SubModuleDefinition>	subModulesDef		= new HashMap<Serializable, SubModuleDefinition>();
	private ApplicationDefinition					applicationDefinition;
	private Map<String, String>						variableSystem		= new HashMap<String, String>();
	private ComponentId								defaultUserActionId;

	public ApplicationDefinition getApplicationDefinition() {
		return applicationDefinition;
	}
	public void setApplicationDefinition(ApplicationDefinition applicationDefinition) {
		this.applicationDefinition = applicationDefinition;
	}
	public void addMapping(String dataSourceName, String location) {
		applicationDefinition.addMapping(dataSourceName, location);
	}
	public void addDataSourceDefinition(DataSourceDefinition dataSourceDefinition) {
		applicationDefinition.addDataSourceDefinition(dataSourceDefinition);
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public void addSubModuleDefinition(SubModuleDefinition value) {
		value.setParentModuleDefinition(this);
		subModulesDef.put(value.getId(), value);
	}
	public SubModuleDefinition getSubModuleDefinition(String id) {
		return (SubModuleDefinition) subModulesDef.get(id);
	}
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public String getDefaultDataDource() {
		return defaultDataDource;
	}
	public void setDefaultDataDource(String defaultDataDource) {
		this.defaultDataDource = defaultDataDource;
	}
	public Map<Serializable, SubModuleDefinition> getSubModulesDef() {
		return subModulesDef;
	}
	public void setSubModulesDef(Map<Serializable, SubModuleDefinition> subModulesDef) {
		this.subModulesDef = subModulesDef;
	}
	public void addVariableSystem(String name, String value) {
		variableSystem.put(name, value);
	}
	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this);
	}
	public String getVariableSystem(String name) {
		return (String) variableSystem.get(name);
	}
	public ComponentId getDefaultUserActionId() {
		return defaultUserActionId;
	}
	public void setDefaultUserActionId(ComponentId defaultUserActionId) {
		this.defaultUserActionId = defaultUserActionId;
	}
}
