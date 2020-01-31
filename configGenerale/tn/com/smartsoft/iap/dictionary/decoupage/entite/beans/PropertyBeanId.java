package tn.com.smartsoft.iap.dictionary.decoupage.entite.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class PropertyBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String propertyName;
	private String entiteId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;

	public PropertyBeanId() {
		super();
	}

	public PropertyBeanId(PropertyBean propertyBean) {
		super();
		this.setPropertyName(propertyBean.getPropertyName());
		this.setEntiteId(propertyBean.getEntiteId());
		this.setSujetId(propertyBean.getSujetId());
		this.setSubModuleId(propertyBean.getSubModuleId());
		this.setModuleId(propertyBean.getModuleId());
	}

	public void copyValue(PropertyBean objectValue) {
		objectValue.setPropertyName(this.getPropertyName());
		objectValue.setEntiteId(this.getEntiteId());
		objectValue.setSujetId(this.getSujetId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());

	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getEntiteId() {
		return this.entiteId;
	}

	public void setEntiteId(String entiteId) {
		this.entiteId = entiteId;
	}

	public String getSujetId() {
		return this.sujetId;
	}

	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}

	public String getSubModuleId() {
		return this.subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getPropertyName() == null ? 0 : this.getPropertyName().hashCode());
		result = 37 * result + (this.getEntiteId() == null ? 0 : this.getEntiteId().hashCode());
		result = 37 * result + (this.getSujetId() == null ? 0 : this.getSujetId().hashCode());
		result = 37 * result + (this.getSubModuleId() == null ? 0 : this.getSubModuleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PropertyBeanId))
			return false;
		PropertyBeanId castOther = (PropertyBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getPropertyName(), castOther.getPropertyName());
		result = result && ValueUtils.equals(this.getEntiteId(), castOther.getEntiteId());
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}