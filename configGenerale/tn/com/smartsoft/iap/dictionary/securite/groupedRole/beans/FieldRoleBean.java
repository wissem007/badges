package tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans;

import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;

public class FieldRoleBean extends ItemRoleBean {
	private static final long serialVersionUID = 1L;
	private Boolean isSearcheble;
	private Boolean isReadable;
	private Boolean isInsertable;
	private Boolean isUpdatable;

	public FieldRoleBean() {
		super();
		setNatureRole("F");
	}

	public Boolean getIsSearcheble() {
		return isSearcheble;
	}

	public void setIsSearcheble(Boolean isSearcheble) {
		this.isSearcheble = isSearcheble;
	}

	public Boolean getIsReadable() {
		return isReadable;
	}

	public void setIsReadable(Boolean isReadable) {
		this.isReadable = isReadable;
	}

	public Boolean getIsInsertable() {
		return isInsertable;
	}

	public void setIsInsertable(Boolean isInsertable) {
		this.isInsertable = isInsertable;
	}

	public Boolean getIsUpdatable() {
		return isUpdatable;
	}

	public void setIsUpdatable(Boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}

	public Boolean isSearcheble() {
		return this.isSearcheble;
	}

	public void setSearcheble(Boolean isSearcheble) {
		this.isSearcheble = isSearcheble;
	}

	public Boolean isReadable() {
		return this.isReadable;
	}

	public void setReadable(Boolean isReadable) {
		this.isReadable = isReadable;
	}

	public Boolean isInsertable() {
		return this.isInsertable;
	}

	public void setInsertable(Boolean isInsertable) {
		this.isInsertable = isInsertable;
	}

	public Boolean isUpdatable() {
		return this.isUpdatable;
	}

	public void setUpdatable(Boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}

	public String getTypeRole() {
		return "Champ";
	}
}