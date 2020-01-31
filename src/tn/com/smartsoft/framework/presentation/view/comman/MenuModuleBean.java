package tn.com.smartsoft.framework.presentation.view.comman;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.framework.beans.IdentifiableBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;

public class MenuModuleBean implements IdentifiableBean, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MenuItemBean> menus = new ArrayList<MenuItemBean>();
	private String moduleId;

	public MenuModuleBean(String moduleId) {
		super();
		this.moduleId = moduleId;
	}

	public Serializable getId() {
		return moduleId;
	}

	public List<MenuItemBean> getMenus() {
		return menus;
	}
}