package tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans;

import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class GroupedRoleBean extends RoleBean {
	private static final long serialVersionUID = 1L;
	private String prentRoleId;
	private GroupedRoleBean parentRole;

	public GroupedRoleBean() {
		super();
		setNatureRole("G");
	}

	public String getPrentRoleId() {
		return this.prentRoleId;
	}

	public void setPrentRoleId(String prentRoleId) {
		this.prentRoleId = prentRoleId;
	}

	public GroupedRoleBean getParentRole() {
		return this.parentRole;
	}

	public void setParentRole(GroupedRoleBean parentRole) {
		this.parentRole = parentRole;
	}

	public String getTypeRole() {
		return "Groupement des Roles";
	}

}