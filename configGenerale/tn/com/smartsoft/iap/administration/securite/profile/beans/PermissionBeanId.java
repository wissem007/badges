package tn.com.smartsoft.iap.administration.securite.profile.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class PermissionBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long profileId;
	private String roleId;
	protected String moduleId;

	public PermissionBeanId() {
		super();
	}

	public PermissionBeanId(Long profileId, String moduleId, String roleId) {
		super();
		this.profileId = profileId;
		this.moduleId = moduleId;
		this.roleId = roleId;
	}

	public PermissionBeanId(PermissionBean permissionBean) {
		super();
		this.setProfileId(permissionBean.getProfileId());
		this.setRoleId(permissionBean.getRoleId());
		this.setModuleId(permissionBean.getModuleId());
	}

	public void copyValue(PermissionBean objectValue) {
		objectValue.setProfileId(this.getProfileId());
		objectValue.setRoleId(this.getRoleId());
		objectValue.setModuleId(this.getModuleId());

	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public Long getProfileId() {
		return this.profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getProfileId() == null ? 0 : this.getProfileId().hashCode());
		result = 37 * result + (this.getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PermissionBeanId))
			return false;
		PermissionBeanId castOther = (PermissionBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getProfileId(), castOther.getProfileId());
		result = result && ValueUtils.equals(this.getRoleId(), castOther.getRoleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}