package tn.com.smartsoft.configGenerale.organisationSociete.pays.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class PaysModel extends GenericEntiteModel {
	private List listDevise;
	private static final long serialVersionUID = 1L;

	public List getListDevise() {
		return listDevise;
	}

	public void setListDevise(List listDevise) {
		this.listDevise = listDevise;
	}
}
