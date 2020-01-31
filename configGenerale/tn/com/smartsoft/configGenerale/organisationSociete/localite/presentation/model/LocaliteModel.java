package tn.com.smartsoft.configGenerale.organisationSociete.localite.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class LocaliteModel extends GenericEntiteModel{
	private static final long serialVersionUID = 1L;
	private List listGouvernorat;
	private List listPays;

	public List getListPays() {
		return listPays;
	}

	public void setListPays(List listPays) {
		this.listPays = listPays;
	}


	public List getListGouvernorat() {
		return listGouvernorat;
	}

	public void setListGouvernorat(List listGouvernorat) {
		this.listGouvernorat = listGouvernorat;
	}

}
