package tn.com.smartsoft.iap.dictionary.securite.role.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class RoleBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String roleId;
	private String moduleId;

	public RoleBeanId() {
		super();
	}

	public RoleBeanId(String roleId, String moduleId) {
		super();
		this.roleId = roleId;
		this.moduleId = moduleId;
	}

	public RoleBeanId(RoleBean roleBean) {
		super();
		this.setRoleId(roleBean.getRoleId());
		this.setModuleId(roleBean.getModuleId());
	}

	public void copyValue(RoleBean objectValue) {
		objectValue.setRoleId(this.getRoleId());
		objectValue.setModuleId(this.getModuleId());

	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoleBeanId))
			return false;
		RoleBeanId castOther = (RoleBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getRoleId(), castOther.getRoleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}