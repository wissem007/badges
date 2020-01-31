package tn.com.smartsoft.iap.dictionary.securite.role.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class RoleBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	protected String roleId;
	protected String moduleId;
	protected String libelle;
	protected Long rang;
	protected String help;
	protected ModuleBean parentModule;

	protected Long depId;
	private String natureDepRole;
	private String natureRole;
	private Long permission;

	public RoleBean() {
		super();
	}

	public Serializable getId() {
		return new RoleBeanId(this);
	}

	public void setId(RoleBeanId id) {
		id.copyValue(this);
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

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getRang() {
		return this.rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public ModuleBean getParentModule() {
		return this.parentModule;
	}

	public void setParentModule(ModuleBean parentModule) {
		this.parentModule = parentModule;
	}

	public Long getPermission() {
		return permission;
	}

	public void setPermission(Long permission) {
		this.permission = permission;
	}

	public String getTypeRole() {
		return "Role";
	}

	public String getNatureRole() {
		return natureRole;
	}

	public void setTypeRole(String typeRole) {

	}

	public void setNatureRole(String natureRole) {
		this.natureRole = natureRole;
	}

	public String getNatureDepRole() {
		return natureDepRole;
	}

	public void setNatureDepRole(String natureDepRole) {
		this.natureDepRole = natureDepRole;
	}

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

}