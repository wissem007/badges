package tn.com.smartsoft.iap.administration.securite.profile.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class PermissionProfileBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String roleId;
	protected String moduleId;
	
	public PermissionProfileBeanId() {
		super();
	}
	
	public PermissionProfileBeanId(String moduleId, String roleId) {
		super();
		this.moduleId = moduleId;
		this.roleId = roleId;
	}
	
	public String getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public String getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
		if (!(other instanceof PermissionProfileBeanId))
			return false;
		PermissionProfileBeanId castOther = (PermissionProfileBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getRoleId(), castOther.getRoleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}