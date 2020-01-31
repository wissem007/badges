package tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class EntiteRoleBean extends RoleBean {
	private static final long serialVersionUID = 1L;
	private String prentRoleId;
	private GroupedRoleBean parentRole;

	private List<ItemRoleBean> itemRoles = new ArrayList<ItemRoleBean>();

	private List<ItemRoleBean> itemActionRoles = new ArrayList<ItemRoleBean>();
	private List<ItemRoleBean> itemFieldRoles = new ArrayList<ItemRoleBean>();

	public EntiteRoleBean() {
		super();
		setNatureRole("E");
	}

	public ItemRoleBean getItemRole(int index) {
		return (ItemRoleBean) itemRoles.get(index);
	}

	public String getPrentRoleId() {
		return this.prentRoleId;
	}

	public void setPrentRoleId(String prentRoleId) {
		this.prentRoleId = prentRoleId;
	}

	public String getTypeRole() {
		return "Entite";
	}

	public GroupedRoleBean getParentRole() {
		return this.parentRole;
	}

	public void setParentRole(GroupedRoleBean parentRole) {
		this.parentRole = parentRole;
	}

	public List<ItemRoleBean> getItemRoles() {
		return this.itemRoles;
	}

	public void setItemRoles(List<ItemRoleBean> itemRoles) {
		this.itemRoles = itemRoles;
	}

	public List<ItemRoleBean> getItemFieldRoles() {
		return itemFieldRoles;
	}

	public void setItemFieldRoles(List<ItemRoleBean> itemFieldRoles) {
		this.itemFieldRoles = itemFieldRoles;
	}

	public List<ItemRoleBean> getItemActionRoles() {
		return itemActionRoles;
	}

	public void setItemActionRoles(List<ItemRoleBean> itemActionRoles) {
		this.itemActionRoles = itemActionRoles;
	}

}