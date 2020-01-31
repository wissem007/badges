package tn.com.smartsoft.framework.configuration.definition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.dao.definition.DataBaseDefinition;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;

public class ApplicationDefinition implements IDefinition, ParserDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, ModuleDefinition> modulesDefs = new HashMap<String, ModuleDefinition>();
	private String defaultDataDource;
	private String adminDataSource;
	private DataBaseDefinition dataBaseDefinition = new DataBaseDefinition();
	private WebDefinition webDefinition;
	protected Map<Serializable, BeanDefinition> beansDefinition = new HashMap<Serializable, BeanDefinition>();
	private Map<String, String> variableSystem = new HashMap<String, String>();
	private ComponentId defaultUserActionId;
	private ComponentId loginUserActionId;

	public ApplicationDefinition() {
		super();

	}

	public ComponentId getDefaultUserActionId() {
		return defaultUserActionId;
	}

	public void setDefaultUserActionId(ComponentId defaultUserActionId) {
		this.defaultUserActionId = defaultUserActionId;
	}

	public ComponentId getLoginUserActionId() {
		return loginUserActionId;
	}

	public void setLoginUserActionId(ComponentId loginUserActionId) {
		this.loginUserActionId = loginUserActionId;
	}

	public void addBeanDefinition(BeanDefinition value) {
		beansDefinition.put(value.getId(), value);
	}

	public BeanDefinition getBeanDefinition(String name) {
		return (BeanDefinition) beansDefinition.get(name);
	}

	public Map<String, ModuleDefinition> getModulesDefs() {
		return modulesDefs;
	}

	public void addMapping(String dataSourceName, String location) {
		getDataBaseDefinition().getDataSourceDefinition(dataSourceName).addResource(location);
	}

	public void addDataSourceDefinition(DataSourceDefinition dataSourceDefinition) {
		dataBaseDefinition.addDataSourceDefinition(dataSourceDefinition);
	}

	public void setModulesDefs(Map<String, ModuleDefinition> modulesDefs) {
		this.modulesDefs = modulesDefs;
	}

	public void addModuleDefinition(ModuleDefinition moduleDefinition) {
		moduleDefinition.setApplicationDefinition(this);
		modulesDefs.put(moduleDefinition.getModuleId(), moduleDefinition);
	}

	public ModuleDefinition getModuleDefinition(String moduleName) {
		return (ModuleDefinition) modulesDefs.get(moduleName);
	}

	public String getAdminDataSource() {
		return adminDataSource;
	}

	public void setAdminDataSource(String adminDataSource) {
		this.adminDataSource = adminDataSource;
	}

	public String getDefaultDataDource() {
		return defaultDataDource;
	}

	public void setDefaultDataDource(String defaultDataDource) {
		this.defaultDataDource = defaultDataDource;
	}

	public void setDataBaseDefinition(DataBaseDefinition dataBaseDefinition) {
		this.dataBaseDefinition = dataBaseDefinition;
	}

	public DataBaseDefinition getDataBaseDefinition() {
		return dataBaseDefinition;
	}

	public WebDefinition getWebDefinition() {
		return webDefinition;
	}

	public void setWebDefinition(WebDefinition webDefinition) {
		this.webDefinition = webDefinition;
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

}
