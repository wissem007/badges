package tn.com.smartsoft.backoffice.referentiel.eleveClasses.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class EleveClassesModel extends GenericEntiteModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DataBusinessBean> listClasseNiveau;

	public List<DataBusinessBean> getListClasseNiveau() {
		return listClasseNiveau;
	}

	public void setListClasseNiveau(List<DataBusinessBean> listClasseNiveau) {
		this.listClasseNiveau = listClasseNiveau;
	}
}
