package tn.com.smartsoft.framework.presentation.definition;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class BindingPropertyModelDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	protected Class<?> type;
	protected String mode;
	protected String libelleExp;
	protected String userType;
	String refEntiteId = null;
	String refSujetId = null;
	String refSubModuleId = null;
	String refModuleId = null;

	public BindingPropertyModelDefinition() {
		super();
	}

	public BindingPropertyModelDefinition(String name, Class<?> type, String mode, String libelleExp, String userType) {
		super();
		this.name = name;
		this.type = type;
		this.mode = mode;
		this.libelleExp = libelleExp;
		this.userType = userType;
	}

	public String getRefEntiteId() {
		return refEntiteId;
	}

	public void setRefEntiteId(String refEntiteId) {
		this.refEntiteId = refEntiteId;
	}

	public String getRefSujetId() {
		return refSujetId;
	}

	public void setRefSujetId(String refSujetId) {
		this.refSujetId = refSujetId;
	}

	public String getRefSubModuleId() {
		return refSubModuleId;
	}

	public void setRefSubModuleId(String refSubModuleId) {
		this.refSubModuleId = refSubModuleId;
	}

	public String getRefModuleId() {
		return refModuleId;
	}

	public void setRefModuleId(String refModuleId) {
		this.refModuleId = refModuleId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLibelleExp() {
		return libelleExp;
	}

	public void setLibelleExp(String libelleExp) {
		this.libelleExp = libelleExp;
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

	public void copyTo(BindingPropertyModelDefinition bindingModelDefinition) {
		bindingModelDefinition.setName(name);
		if (type != null)
			bindingModelDefinition.setType(type);
		if (StringUtils.isNotBlank(mode))
			bindingModelDefinition.setMode(mode);
		if (StringUtils.isNotBlank(libelleExp))
			bindingModelDefinition.setLibelleExp(libelleExp);
		if (StringUtils.isNotBlank(userType))
			bindingModelDefinition.setUserType(userType);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
