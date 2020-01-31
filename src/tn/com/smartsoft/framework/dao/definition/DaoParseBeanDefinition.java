package tn.com.smartsoft.framework.dao.definition;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DaoParseBeanDefinition implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Map<String, DaoParseBeanDefinition> propertyBeans = new HashMap<String, DaoParseBeanDefinition>();
	private String property;
	private Boolean recursive = Boolean.FALSE;
	private DaoParseBeanDefinition parent;

	public DaoParseBeanDefinition getPropertyBean(String property) {
		return (DaoParseBeanDefinition) propertyBeans.get(property);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(Boolean recursive) {
		this.recursive = recursive;
	}

	public DaoParseBeanDefinition getParent() {
		return parent;
	}

	public void setParent(DaoParseBeanDefinition parent) {
		this.parent = parent;
	}

	public DaoParseBeanDefinition getCopy() {
		DaoParseBeanDefinition daoParseBeanDefinition = new DaoParseBeanDefinition();
		daoParseBeanDefinition.id = id;
		daoParseBeanDefinition.propertyBeans = propertyBeans;
		daoParseBeanDefinition.property = property;
		daoParseBeanDefinition.recursive = recursive;
		daoParseBeanDefinition.parent = parent;
		return daoParseBeanDefinition;
	}

	public void addDaoParsePropertyBeanDefinition(DaoParseBeanDefinition daoParseBeanDefinition) {
		daoParseBeanDefinition.setParent(this);
		propertyBeans.put(daoParseBeanDefinition.getProperty(), daoParseBeanDefinition);
	}
}
