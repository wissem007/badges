package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class EleveDemandeCartesModel extends GenericEntiteModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> listSaisons;
	private List<DataBusinessBean> listClasses;
	private List<DataBusinessBean> listStatus;

	public List<DataBusinessBean> getListSaisons() {
		return listSaisons;
	}

	public void setListSaisons(List<DataBusinessBean> listSaisons) {
		this.listSaisons = listSaisons;
	}

	public List<DataBusinessBean> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<DataBusinessBean> listClasses) {
		this.listClasses = listClasses;
	}

	public List<DataBusinessBean> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<DataBusinessBean> listStatus) {
		this.listStatus = listStatus;
	}
}
