package tn.com.smartsoft.configGenerale.administration.organismeProprietaires.presentation.model;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class OrganismeProprietairesModel extends GenericEntiteModel {

	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> listOrganisme;
	private List<DataBusinessBean> listSociete;
	private List<OrganismeProprietairesBean> organismeModules = new ArrayList<OrganismeProprietairesBean>();
	private List<DataBusinessBean> modules;

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

	public void setOrganismeModules(List<OrganismeProprietairesBean> organismeModules) {
		this.organismeModules = organismeModules;
	}

	public List<OrganismeProprietairesBean> getOrganismeModules() {
		return organismeModules;
	}

	public void setModules(List<DataBusinessBean> modules) {
		this.modules = modules;
	}

	public List<DataBusinessBean> getModules() {
		return modules;
	}

}
