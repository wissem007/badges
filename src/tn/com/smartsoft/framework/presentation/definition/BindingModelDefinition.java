package tn.com.smartsoft.framework.presentation.definition;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class BindingModelDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	protected Class<?> type;
	protected String mode;
	protected Map<String, BindingModelDefinition> bindingModels = new HashMap<String, BindingModelDefinition>();
	protected Map<String, BindingPropertyModelDefinition> bindingPropertyModels = new HashMap<String, BindingPropertyModelDefinition>();
	
	public BindingModelDefinition() {
		super();
	}
	
	public BindingModelDefinition(String name, Class<?> type, String mode) {
		super();
		this.name = name;
		this.type = type;
		this.mode = mode;
	}
	
	public void addBindingPropertyModel(BindingPropertyModelDefinition value) {
		bindingPropertyModels.put(value.getName(), value);
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public void setType(Class<?> type) {
		this.type = type;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void copyTo(BindingModelDefinition bindingModelDefinition) {
		bindingModelDefinition.setName(name);
		if (type != null)
			bindingModelDefinition.setType(type);
		if (StringUtils.isNotBlank(mode))
			bindingModelDefinition.setMode(mode);
		Iterator<String> it = bindingModels.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			BindingModelDefinition bindingModelChild = new BindingModelDefinition();
			bindingModels.get(key).copyTo(bindingModelChild);
			bindingModelDefinition.addBindingModel(bindingModelChild);
		}
		it = bindingPropertyModels.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			BindingPropertyModelDefinition bindingModelChild = new BindingPropertyModelDefinition();
			bindingPropertyModels.get(key).copyTo(bindingModelChild);
			bindingModelDefinition.addBindingPropertyModel(bindingModelChild);
		}
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
