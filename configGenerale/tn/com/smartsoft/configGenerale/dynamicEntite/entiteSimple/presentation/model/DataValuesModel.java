package tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class DataValuesModel extends GenericEntiteModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DataBusinessBean> listModule;
	private List<DataBusinessBean> listEntite;
	private List<DataBusinessBean> listOrganisme;
	private List<DataBusinessBean> listSociete;

	public List<DataBusinessBean> getListModule() {
		return listModule;
	}

	public void setListModule(List<DataBusinessBean> listModule) {
		this.listModule = listModule;
	}

	public void setListEntite(List<DataBusinessBean> listEntite) {
		this.listEntite = listEntite;
	}

	public List<DataBusinessBean> getListEntite() {
		return listEntite;
	}

	public void setListOrganisme(List<DataBusinessBean> listOrganisme) {
		this.listOrganisme = listOrganisme;
	}

	public List<DataBusinessBean> getListOrganisme() {
		return listOrganisme;
	}

	public void setListSociete(List<DataBusinessBean> listSociete) {
		this.listSociete = listSociete;
	}

	public List<DataBusinessBean> getListSociete() {
		return listSociete;
	}

}
