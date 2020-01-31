package tn.com.smartsoft.backoffice.referentiel.saison.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class SaisonsBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Boolean opened;
	private String nom;
	private Long saisonId;

	public SaisonsBean() {
		super();
	}

	public Serializable getId() {
		return this.getSaisonId();
	}

	public void setId(Long id) {
		this.setSaisonId(id);
	}

	public Boolean isOpened() {
		return this.opened;
	}

	public void setOpened(Boolean opened) {
		this.opened = opened;
	}

	public Boolean getOpened() {
		return opened;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getSaisonId() {
		return this.saisonId;
	}

	public void setSaisonId(Long saisonId) {
		this.saisonId = saisonId;
	}
}