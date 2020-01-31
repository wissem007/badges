package tn.com.smartsoft.framework.dao.definition;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DaoBeanDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Class<?> classInterface;
	private Map<String, DaoQueryMethodDefinition> methodDefinitions = new HashMap<String, DaoQueryMethodDefinition>();

	public void addMethodBeanDefinition(DaoQueryMethodDefinition queryMethod) {
		methodDefinitions.put(queryMethod.getName(), queryMethod);
	}

	public Map<String, DaoQueryMethodDefinition> getMethodDefinitions() {
		return methodDefinitions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getClassInterface() {
		return classInterface;
	}

	public void setClassInterface(Class<?> classInterface) {
		this.classInterface = classInterface;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
