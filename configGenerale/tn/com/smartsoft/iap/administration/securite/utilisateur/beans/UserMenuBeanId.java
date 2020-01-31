package tn.com.smartsoft.iap.administration.securite.utilisateur.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class UserMenuBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long menuId;
	private String moduleId;
	private String userId;

	public UserMenuBeanId() {
		super();
	}

	public UserMenuBeanId(UserMenuBean userMenuBean) {
		super();
		this.setUserId(userMenuBean.getUserId());
		this.setModuleId(userMenuBean.getModuleId());
		this.setMenuId(userMenuBean.getMenuId());
	}

	public void copyValue(UserMenuBean objectValue) {
		objectValue.setUserId(this.getUserId());
		objectValue.setModuleId(this.getModuleId());
		objectValue.setMenuId(this.getMenuId());
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		result = 37 * result + (this.getMenuId() == null ? 0 : this.getMenuId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserMenuBeanId))
			return false;
		UserMenuBeanId castOther = (UserMenuBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getUserId(), castOther.getUserId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		result = result && ValueUtils.equals(this.getMenuId(), castOther.getMenuId());
		return result;
	}
}