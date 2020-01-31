package tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class FonctionUtilisationModel extends GenericEntiteModel {

	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> listOrganisme;
	private List<DataBusinessBean> listProfils;
	private List<DataBusinessBean> modules;
	

	public void setListOrganisme(List<DataBusinessBean> listOrganisme) {
		this.listOrganisme = listOrganisme;
	}

	public List<DataBusinessBean> getListOrganisme() {
		return listOrganisme;
	}

	public void setModules(List<DataBusinessBean> modules) {
		this.modules = modules;
	}

	public List<DataBusinessBean> getModules() {
		return modules;
	}

	public List<DataBusinessBean> getListProfils() {
		return listProfils;
	}

	public void setListProfils(List<DataBusinessBean> listProfils) {
		this.listProfils = listProfils;
	}
}
