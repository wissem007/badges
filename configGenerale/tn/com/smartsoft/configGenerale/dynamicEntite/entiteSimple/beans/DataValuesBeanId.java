package tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class DataValuesBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dataValueId;
	private String entiteId;
	private String moduleId;
	private String subModuleId;
	private String sujetId;

	public DataValuesBeanId() {
		super();
	}

	public DataValuesBeanId(DataValuesBean dataValuesBean) {
		super();
		this.setDataValueId(dataValuesBean.getDataValueId());
		this.setEntiteId(dataValuesBean.getEntiteId());
		this.setModuleId(dataValuesBean.getModuleId());
		this.setSubModuleId(dataValuesBean.getSubModuleId());
		this.setSujetId(dataValuesBean.getSujetId());
	}

	public void copyValue(DataValuesBean objectValue) {
		objectValue.setDataValueId(this.getDataValueId());
		objectValue.setEntiteId(this.getEntiteId());
		objectValue.setModuleId(this.getModuleId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setSujetId(this.getSujetId());

	}

	public String getDataValueId() {
		return this.dataValueId;
	}

	public void setDataValueId(String dataValueId) {
		this.dataValueId = dataValueId;
	}

	public String getEntiteId() {
		return this.entiteId;
	}

	public void setEntiteId(String entiteId) {
		this.entiteId = entiteId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSubModuleId() {
		return this.subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

	public String getSujetId() {
		return this.sujetId;
	}

	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getDataValueId() == null ? 0 : this.getDataValueId().hashCode());
		result = 37 * result + (this.getEntiteId() == null ? 0 : this.getEntiteId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		result = 37 * result + (this.getSubModuleId() == null ? 0 : this.getSubModuleId().hashCode());
		result = 37 * result + (this.getSujetId() == null ? 0 : this.getSujetId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DataValuesBeanId))
			return false;
		DataValuesBeanId castOther = (DataValuesBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getDataValueId(), castOther.getDataValueId());
		result = result && ValueUtils.equals(this.getEntiteId(), castOther.getEntiteId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		return result;
	}
}