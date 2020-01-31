package tn.com.smartsoft.backoffice.referentiel.eleveClasseNiveaux.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.io.Serializable;

public class EleveClasseNiveauxBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String nom;
	private Long eleveClasseNiveauId;

	public EleveClasseNiveauxBean() {
		super();
	}

	public Serializable getId() {
		return this.getEleveClasseNiveauId();
	}

	public void setId(Long id) {
		this.setEleveClasseNiveauId(id);
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getEleveClasseNiveauId() {
		return this.eleveClasseNiveauId;
	}

	public void setEleveClasseNiveauId(Long eleveClasseNiveauId) {
		this.eleveClasseNiveauId = eleveClasseNiveauId;
	}
}