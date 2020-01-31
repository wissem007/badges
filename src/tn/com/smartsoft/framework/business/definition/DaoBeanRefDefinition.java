package tn.com.smartsoft.framework.business.definition;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DaoBeanRefDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String ref;
	private String sujet;
	private String subModule;
	private String module;

	public DaoBeanRefDefinition() {
		super();

	}

	public DaoBeanRefDefinition(String name, String ref) {
		super();
		this.name = name;
		this.ref = ref;
	}

	public String getName() {
		return name;
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

}
