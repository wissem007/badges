package tn.com.smartsoft.configGenerale.organisationSociete.organisation.presentation.model;

import java.util.List;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

@SuppressWarnings("rawtypes")
public class OrganismeModel extends GenericEntiteModel{

	private List	listParentSociete;
	private List	listPays;
	private List	listGovernorat;
	private List	listLocalite;

	public List getListPays() {
		return listPays;
	}
	public void setListPays(List listPays) {
		this.listPays = listPays;
	}
	public List getListGovernorat() {
		return listGovernorat;
	}
	public void setListGovernorat(List listGovernorat) {
		this.listGovernorat = listGovernorat;
	}
	public List getListLocalite() {
		return listLocalite;
	}
	public void setListLocalite(List listLocalite) {
		this.listLocalite = listLocalite;
	}
	public List getListParentSociete() {
		return listParentSociete;
	}
	public void setListParentSociete(List listParentSociete) {
		this.listParentSociete = listParentSociete;
	}

	private static final long	serialVersionUID	= 1L;
}
