package tn.com.smartsoft.backoffice.referentiel.eleveClasses.beans;

import tn.com.smartsoft.backoffice.referentiel.eleveClasseNiveaux.beans.EleveClasseNiveauxBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

import java.io.Serializable;

public class EleveClassesBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String nom;
	private Long eleveClasseId;
	private EleveClasseNiveauxBean eleveClasseNiveau;
	private Long eleveClasseNiveauId;

	public EleveClassesBean() {
		super();
	}

	public Serializable getId() {
		return this.getEleveClasseId();
	}

	public void setId(Long id) {
		this.setEleveClasseId(id);
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getEleveClasseId() {
		return this.eleveClasseId;
	}

	public void setEleveClasseId(Long eleveClasseId) {
		this.eleveClasseId = eleveClasseId;
	}

	public Long getEleveClasseNiveauId() {
		return eleveClasseNiveauId;
	}

	public void setEleveClasseNiveauId(Long eleveClasseNiveauId) {
		this.eleveClasseNiveauId = eleveClasseNiveauId;
	}

	public EleveClasseNiveauxBean getEleveClasseNiveau() {
		return eleveClasseNiveau;
	}

	public void setEleveClasseNiveau(EleveClasseNiveauxBean eleveClasseNiveau) {
		this.eleveClasseNiveau = eleveClasseNiveau;
	}
}