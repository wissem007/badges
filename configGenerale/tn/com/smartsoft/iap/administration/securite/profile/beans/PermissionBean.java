package tn.com.smartsoft.iap.administration.securite.profile.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;

public class PermissionBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long profileId;
	private String roleId;
	protected String moduleId;
	private Long permission;
	private ItemRoleBean role;
	
	public PermissionBean() {
		super();
	}
	
	public PermissionBean(Long profileId, String roleId, String moduleId, Long permission) {
		super();
		this.profileId = profileId;
		this.roleId = roleId;
		this.moduleId = moduleId;
		this.permission = permission;
	}
	
	public Serializable getId() {
		return new PermissionBeanId(this);
	}
	
	public String getModuleId() {
		return moduleId;
	}
	
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public void setId(PermissionBeanId id) {
		id.copyValue(this);
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
	
	public Long getPermission() {
		return this.permission;
	}
	
	public void setPermission(Long permission) {
		this.permission = permission;
	}
	
	public ItemRoleBean getRole() {
		return this.role;
	}
	
	public void setRole(ItemRoleBean role) {
		this.role = role;
	}
}