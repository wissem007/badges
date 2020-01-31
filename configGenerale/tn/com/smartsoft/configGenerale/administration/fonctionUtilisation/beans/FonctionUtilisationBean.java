package tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class FonctionUtilisationBean extends DataBusinessBean{
	
	private static final long				serialVersionUID	= 1L;
	private Long							fonctionUtilisationId;
	private String							libelle;
	private List<FonctionUtilProfilBean>	profils				= new ArrayList<FonctionUtilProfilBean>();
	
	public FonctionUtilisationBean() {
		super();
	}
	public FonctionUtilProfilBean getFonctionUtilProfil(int index) {
		return (FonctionUtilProfilBean) profils.get(index);
	}
	public Serializable getId() {
		return getFonctionUtilisationId();
	}
	public void setId(Long id) {
		setFonctionUtilisationId(id);
	}
	public Long getFonctionUtilisationId() {
		return this.fonctionUtilisationId;
	}
	public void setFonctionUtilisationId(Long fonctionUtilisationId) {
		this.fonctionUtilisationId = fonctionUtilisationId;
	}
	public String getLibelle() {
		return this.libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public List<FonctionUtilProfilBean> getProfils() {
		return this.profils;
	}
	public void setProfils(List<FonctionUtilProfilBean> profils) {
		this.profils = profils;
	}
}