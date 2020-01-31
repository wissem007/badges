package tn.com.smartsoft.configGenerale.organisationSociete.societe.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

@SuppressWarnings("rawtypes")
public class SocieteModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;
	private List listPays;
	private List listLocalite;
	private List listGouvernorat;
	private List listDevise;
	private List listActivite;
	private List listPersonnalite;
	private List listSite;
	private List listSousActivite;
	private List listSecteurActivites;
	private List<DataBusinessBean> listConventions;

	public List getListSecteurActivites() {
		return listSecteurActivites;
	}

	public void setListSecteurActivites(List listSecteurActivites) {
		this.listSecteurActivites = listSecteurActivites;
	}

	public List getListSite() {
		return listSite;
	}

	public void setListSite(List listSite) {
		this.listSite = listSite;
	}

	public List getListSousActivite() {
		return listSousActivite;
	}

	public void setListSousActivite(List listSousActivite) {
		this.listSousActivite = listSousActivite;
	}

	public List getListPersonnalite() {
		return listPersonnalite;
	}

	public void setListPersonnalite(List listPersonnalite) {
		this.listPersonnalite = listPersonnalite;
	}

	public List getListDevise() {
		return listDevise;
	}

	public void setListDevise(List listDevise) {
		this.listDevise = listDevise;
	}

	public List getListActivite() {
		return listActivite;
	}

	public void setListActivite(List listActivite) {
		this.listActivite = listActivite;
	}

	public List getListPays() {
		return listPays;
	}

	public List getListLocalite() {
		return listLocalite;
	}

	public void setListLocalite(List listLocalite) {
		this.listLocalite = listLocalite;
	}

	public List getListGouvernorat() {
		return listGouvernorat;
	}

	public void setListGouvernorat(List listGouvernorat) {
		this.listGouvernorat = listGouvernorat;
	}

	public void setListPays(List listPays) {
		this.listPays = listPays;
	}

	public List<DataBusinessBean> getListConventions() {
		return listConventions;
	}

	public void setListConventions(List<DataBusinessBean> listConventions) {
		this.listConventions = listConventions;
	}

}
