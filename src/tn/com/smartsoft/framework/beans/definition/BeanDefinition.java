package tn.com.smartsoft.framework.beans.definition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class BeanDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected Class<Object> classValue;
	protected List<GenericPropertyDefinition> propertyDefinitions = new ArrayList<GenericPropertyDefinition>();

	public BeanDefinition() {
		super();
	}

	public BeanDefinition(String name, Class<Object> classValue) {
		super();
		this.name = name;
		this.classValue = classValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<Object> getClassValue() {
		return classValue;
	}

	public void setClassValue(Class<Object> classValue) {
		this.classValue = classValue;
	}

	public void addPropertyDefinition(GenericPropertyDefinition value) {
		propertyDefinitions.add(value);
	}

	public void addComponentIdDefinition(PropertyDefinition value) {
		propertyDefinitions.add(value);
	}

	public List<GenericPropertyDefinition> getPropertyDefinitions() {
		return propertyDefinitions;
	}

	public Serializable getId() {
		return name;
	}

	public void copyTo(BeanDefinition beanDefinition) {
		if (classValue != null)
			beanDefinition.setClassValue(classValue);
		beanDefinition.setName(name);
		beanDefinition.propertyDefinitions.addAll(propertyDefinitions);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}