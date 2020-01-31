package tn.com.smartsoft.iap.dictionary.securite.groupedRole.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class GroupedRoleModel extends GenericEntiteModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> listModules;
	private List<DataBusinessBean> listRoles;
	private List<DataBusinessBean> listNatureRoles;

	public List<DataBusinessBean> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<DataBusinessBean> listRoles) {
		this.listRoles = listRoles;
	}

	public List<DataBusinessBean> getListModules() {
		return listModules;
	}

	public void setListModules(List<DataBusinessBean> listModules) {
		this.listModules = listModules;
	}

	public List<DataBusinessBean> getListNatureRoles() {
		return listNatureRoles;
	}

	public void setListNatureRoles(List<DataBusinessBean> listNatureRoles) {
		this.listNatureRoles = listNatureRoles;
	}

}
