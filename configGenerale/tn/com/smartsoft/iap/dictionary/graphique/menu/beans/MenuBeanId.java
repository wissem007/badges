package tn.com.smartsoft.iap.dictionary.graphique.menu.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class MenuBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long menuId;
	private String moduleId;

	public MenuBeanId() {
		super();
	}

	public MenuBeanId(Long menuId, String moduleId) {
		super();
		this.menuId = menuId;
		this.moduleId = moduleId;
	}

	public MenuBeanId(MenuBean menuBean) {
		super();
		this.setMenuId(menuBean.getMenuId());
		this.setModuleId(menuBean.getModuleId());
	}

	public void copyValue(MenuBean objectValue) {
		objectValue.setMenuId(this.getMenuId());
		objectValue.setModuleId(this.getModuleId());

	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getMenuId() == null ? 0 : this.getMenuId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MenuBeanId))
			return false;
		MenuBeanId castOther = (MenuBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getMenuId(), castOther.getMenuId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}

	public String toString() {
		return "MenuBeanId [menuId=" + menuId + ", moduleId=" + moduleId + "]";
	}
}