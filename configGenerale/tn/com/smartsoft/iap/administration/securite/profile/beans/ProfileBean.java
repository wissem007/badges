package tn.com.smartsoft.iap.administration.securite.profile.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class ProfileBean extends DataBusinessBean{

	private static final long			serialVersionUID	= 1L;
	private Long						profileId;
	private String						libelle;
	private String						help;
	private String						moduleId;
	private ModuleBean					module;
	private Map<String, PermissionBean>	permissions			= new HashMap<String, PermissionBean>();

	public ProfileBean() {
		super();
	}
	public PermissionBean getPermission(String roleId) {
		return (PermissionBean) permissions.get(roleId);
	}
	public Serializable getId() {
		return new ProfileBeanId(this);
	}
	public void setId(ProfileBeanId id) {
		id.copyValue(this);
	}
	public Long getProfileId() {
		return this.profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public String getLibelle() {
		return this.libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getHelp() {
		return this.help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public PermissionBean getPermissions(String roleId) {
		return this.permissions.get(roleId);
	}
	public boolean containsPermissions(String roleId) {
		return this.permissions.containsKey(roleId);
	}
	public Map<String, PermissionBean> getPermissions() {
		return this.permissions;
	}
	public void setPermissions(Map<String, PermissionBean> permissions) {
		this.permissions = permissions;
	}
	public void setModule(ModuleBean module) {
		this.module = module;
	}
	public ModuleBean getModule() {
		return module;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleId() {
		return moduleId;
	}
}