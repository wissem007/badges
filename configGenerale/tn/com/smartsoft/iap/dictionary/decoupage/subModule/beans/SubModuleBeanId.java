package tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class SubModuleBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subModuleId;
	private String moduleId;

	public SubModuleBeanId() {
		super();
	}

	public SubModuleBeanId(SubModuleBean subModuleBean) {
		super();
		this.setSubModuleId(subModuleBean.getSubModuleId());
		this.setModuleId(subModuleBean.getModuleId());
	}

	public void copyValue(SubModuleBean objectValue) {
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());

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
		result = 37 * result + (this.getSubModuleId() == null ? 0 : this.getSubModuleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SubModuleBeanId))
			return false;
		SubModuleBeanId castOther = (SubModuleBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}