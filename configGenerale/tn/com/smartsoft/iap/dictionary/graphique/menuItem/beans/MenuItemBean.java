package tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans;

import tn.com.smartsoft.iap.dictionary.graphique.menu.beans.MenuBean;
import java.util.List;
import java.util.ArrayList;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;

public class MenuItemBean extends MenuBean {
	private static final long serialVersionUID = 1L;
	private Long parentMenuId;
	private List<MenuItemBean> menus = new ArrayList<MenuItemBean>();
	private List<MenuActionBean> menuActions = new ArrayList<MenuActionBean>();

	public MenuItemBean() {
		super();
	}

	public MenuItemBean getMenuItem(int index) {
		return (MenuItemBean) menus.get(index);
	}

	public MenuActionBean getMenuAction(int index) {
		return (MenuActionBean) menuActions.get(index);
	}

	public Long getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public List<MenuItemBean> getMenus() {
		return this.menus;
	}

	public void setMenus(List<MenuItemBean> menus) {
		this.menus = menus;
	}

	public List<MenuActionBean> getMenuActions() {
		return this.menuActions;
	}

	public void setMenuActions(List<MenuActionBean> menuActions) {
		this.menuActions = menuActions;
	}
}