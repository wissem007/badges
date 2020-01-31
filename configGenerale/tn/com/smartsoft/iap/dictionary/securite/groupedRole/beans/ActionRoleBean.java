package tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans;

import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;

public class ActionRoleBean extends ItemRoleBean {
	private static final long serialVersionUID = 1L;

	public ActionRoleBean() {
		super();
	}

	public String getTypeRole() {
		return "Action";
	}
	public String getNatureRole() {
		return "A";
	}
	public void setTypeRole(String typeRole) {

	}

}