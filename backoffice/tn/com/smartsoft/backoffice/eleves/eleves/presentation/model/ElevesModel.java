package tn.com.smartsoft.backoffice.eleves.eleves.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class ElevesModel extends GenericEntiteModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> listSaisons;
	private List<DataBusinessBean> listClasses;

	public List<DataBusinessBean> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<DataBusinessBean> listClasses) {
		this.listClasses = listClasses;
	}

	public List<DataBusinessBean> getListSaisons() {
		return listSaisons;
	}

	public void setListSaisons(List<DataBusinessBean> listSaisons) {
		this.listSaisons = listSaisons;
	}
}
