package tn.com.smartsoft.iap.administration.securite.utilisateur.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;

public class UserMenuBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long menuId;
	private String moduleId;
	private String userId;
	private String libelle;
	private MenuActionBean menuAction;

	public UserMenuBean() {
		super();
	}

	public Serializable getId() {
		return new UserMenuBeanId(this);
	}

	public void setId(UserMenuBeanId id) {
		id.copyValue(this);
	}

	public MenuActionBean getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(MenuActionBean menuAction) {
		this.menuAction = menuAction;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}