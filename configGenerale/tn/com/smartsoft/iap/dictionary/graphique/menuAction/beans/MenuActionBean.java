package tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans;

import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.menu.beans.MenuBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;

public class MenuActionBean extends MenuBean {
	private static final long serialVersionUID = 1L;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private Long parentMenuId;
	private ActionBean action;
	private MenuItemBean parentMenu;

	public MenuActionBean() {
		super();
	}

	public MenuItemBean getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(MenuItemBean parentMenu) {
		this.parentMenu = parentMenu;
	}

	public String getMenuLibelle() {
		if (this.getParentMenu() != null)
			return this.getParentMenu().getLibelle() + "(" + getLibelle() + ")";
		else
			return getLibelle();
	}

	public void setMenuLibelle(String libelle) {

	}

	public String getActionId() {
		return this.actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getSujetId() {
		return this.sujetId;
	}

	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}

	public String getSubModuleId() {
		return this.subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

	public Long getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public ActionBean getAction() {
		return this.action;
	}

	public void setAction(ActionBean action) {
		this.action = action;
	}
}