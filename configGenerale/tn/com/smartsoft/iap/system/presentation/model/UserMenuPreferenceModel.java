package tn.com.smartsoft.iap.system.presentation.model;

import java.io.Serializable;
import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;

public class UserMenuPreferenceModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> userMenus;
	private List<DataBusinessBean> listModules;
	private List<DataBusinessBean> listMenusAction;
	private UserMenuBean searcheBean;

	public UserMenuBean getSearcheBean() {
		return searcheBean;
	}

	public void setSearcheBean(UserMenuBean searcheBean) {
		this.searcheBean = searcheBean;
	}

	public List<DataBusinessBean> getListModules() {
		return listModules;
	}

	public void setListModules(List<DataBusinessBean> listModules) {
		this.listModules = listModules;
	}

	public List<DataBusinessBean> getListMenusAction() {
		return listMenusAction;
	}

	public void setListMenusAction(List<DataBusinessBean> listMenusAction) {
		this.listMenusAction = listMenusAction;
	}

	public List<DataBusinessBean> getUserMenus() {
		return userMenus;
	}

	public void setUserMenus(List<DataBusinessBean> userMenus) {
		this.userMenus = userMenus;
	}
}
