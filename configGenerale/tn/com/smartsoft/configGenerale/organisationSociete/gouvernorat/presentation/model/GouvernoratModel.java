package tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class GouvernoratModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;
	private List listPays;

	public List getListPays() {
		return listPays;
	}

	public void setListPays(List listPays) {
		this.listPays = listPays;
	}

}
