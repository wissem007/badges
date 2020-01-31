package tn.com.smartsoft.configGenerale.autresParametrage.etatCivils.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class EtatCivilsBean extends DataBusinessBean {
	public EtatCivilsBean(Long etatCivilId, String libelle) {
		super();
		this.etatCivilId = etatCivilId;
		this.libelle = libelle;
	}

	private static final long serialVersionUID = 1L;
	private Long etatCivilId;
	private String libelle;

	public EtatCivilsBean() {
		super();
	}

	public Serializable getId() {
		return this.getEtatCivilId();
	}

	public Long getEtatCivilId() {
		return this.etatCivilId;
	}

	public void setEtatCivilId(Long etatCivilId) {
		this.etatCivilId = etatCivilId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}