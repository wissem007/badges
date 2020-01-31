package tn.com.smartsoft.iap.dictionary.securite.role.beans;

import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.EntiteRoleBean;

public class ItemRoleBean extends RoleBean {
	private static final long serialVersionUID = 1L;
	private String prentRoleId;
	private String dependenceRoleId;
	private EntiteRoleBean parentRole;
	private ItemRoleBean depandenceRole;
	
	public ItemRoleBean() {
		super();
	}
	
	public String getPrentRoleId() {
		return this.prentRoleId;
	}
	
	public void setPrentRoleId(String prentRoleId) {
		this.prentRoleId = prentRoleId;
	}
	
	public String getDependenceRoleId() {
		return this.dependenceRoleId;
	}
	
	public void setDependenceRoleId(String dependenceRoleId) {
		this.dependenceRoleId = dependenceRoleId;
	}
	
	public EntiteRoleBean getParentRole() {
		return this.parentRole;
	}
	
	public void setParentRole(EntiteRoleBean parentRole) {
		this.parentRole = parentRole;
	}
	
	public ItemRoleBean getDepandenceRole() {
		return this.depandenceRole;
	}
	
	public void setDepandenceRole(ItemRoleBean depandenceRole) {
		this.depandenceRole = depandenceRole;
	}
}