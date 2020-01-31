package tn.com.smartsoft.framework.presentation.definition;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class UserActionDefinition implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name = "";
	protected Class<?> modelClass;
	protected String homeWindowId;
	protected Map<String, WindowDefinition> windowBeansDefinition = new HashMap<String, WindowDefinition>();
	protected Map<String, ReportDefinition> reportsDefinition = new HashMap<String, ReportDefinition>();
	protected String controlerBean;
	protected String extendsName;
	protected String onHomeMethode;
	protected UserActionParserDefinition userActionParser = new UserActionParserDefinition();
	protected Map<String, BindingModelDefinition> bindingModels = new HashMap<String, BindingModelDefinition>();
	protected Map<String, BindingPropertyModelDefinition> bindingPropertyModels = new HashMap<String, BindingPropertyModelDefinition>();

	public void addParameter(String name, String value) {
		userActionParser.addParam(name, value);
	}

	public ParserDefinition getUserActionParser() {
		return userActionParser;
	}

	public String getOnHomeMethode() {
		return onHomeMethode;
	}

	public void setOnHomeMethode(String onHomeMethode) {
		this.onHomeMethode = onHomeMethode;
	}

	public String getExtendsName() {
		return extendsName;
	}

	public void setExtendsName(String extendsName) {
		this.extendsName = extendsName;
	}

	public boolean isExtends() {
		return StringUtils.isNotBlank(extendsName);
	}

	public String getControlerBean() {
		return controlerBean;
	}

	public void setControlerBean(String controlerBean) {
		this.controlerBean = controlerBean;
	}

	public UserActionDefinition() {
	}

	public String getHomeWindowId() {
		return homeWindowId;
	}

	public void setHomeWindowId(String homeWindowId) {
		this.homeWindowId = homeWindowId;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBindingPropertyModel(BindingPropertyModelDefinition value) {
		bindingPropertyModels.put(value.getName(), value);
	}

	public void addBindingRefModel(BindingRefModelDefinition value) {
		bindingModels.put(value.getName(), value);
	}

	public Map<String, BindingPropertyModelDefinition> getBindingPropertyModels() {
		return bindingPropertyModels;
	}

	public void addBindingModel(BindingModelDefinition value) {
		bindingModels.put(value.getName(), value);
	}

	public Map<String, BindingModelDefinition> getBindingModels() {
		return bindingModels;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	public void addWindowBeanDefinition(WindowDefinition value) {
		windowBeansDefinition.put(value.getId(), value);
	}

	public void addWindowBeanRefDefinition(WindowRefDefinition value) {
		windowBeansDefinition.put(value.getId(), value);
	}

	public WindowDefinition getWindowBeanDefinition(String id) {
		WindowDefinition windowBeanDefinition = (WindowDefinition) windowBeansDefinition.get(id);
		return windowBeanDefinition;
	}

	public void addReportfinition(ReportDefinition value) {
		reportsDefinition.put(value.getId(), value);
	}

	public ReportDefinition getReportDefinition(String id) {
		ReportDefinition reportDefinition = (ReportDefinition) reportsDefinition.get(id);
		return reportDefinition;
	}

	public boolean isExistReportDefinition(String id) {
		return getReportDefinition(id) != null;
	}

	public boolean isExistWindowDefinition(String id) {
		return getWindowBeanDefinition(id) != null;
	}

	public void copyTo(UserActionDefinition userActionDefinition) {
		userActionDefinition.setName(name);
		if (modelClass != null)
			userActionDefinition.setModelClass(modelClass);
		if (StringUtils.isNotBlank(homeWindowId))
			userActionDefinition.setHomeWindowId(homeWindowId);
		if (StringUtils.isNotBlank(onHomeMethode))
			userActionDefinition.setOnHomeMethode(onHomeMethode);
		if (StringUtils.isNotBlank(controlerBean))
			userActionDefinition.setControlerBean(controlerBean);
		userActionDefinition.bindingModels.putAll(bindingModels);
		userActionDefinition.bindingPropertyModels.putAll(bindingPropertyModels);
		userActionDefinition.windowBeansDefinition.putAll(windowBeansDefinition);
		userActionDefinition.reportsDefinition.putAll(reportsDefinition);
	}

}
