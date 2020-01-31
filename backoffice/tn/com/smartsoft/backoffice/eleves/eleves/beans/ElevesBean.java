package tn.com.smartsoft.backoffice.eleves.eleves.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ElevesBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Date dateNaisance;
	private String nom;
	private String prenom;
	private String codePermanent;
	private EleveSaisonsBean eleveSaison;
	private List<DataBusinessBean> historys = new ArrayList<DataBusinessBean>();
	private Long eleveClasseId;
	private Long eleveSaisonId;

	public ElevesBean() {
		super();
	}

	public Serializable getId() {
		return this.getCodePermanent();
	}

	public void setId(String id) {
		this.setCodePermanent(id);
	}

	public Date getDateNaisance() {
		return this.dateNaisance;
	}

	public void setDateNaisance(Date dateNaisance) {
		this.dateNaisance = dateNaisance;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCodePermanent() {
		return this.codePermanent;
	}

	public void setCodePermanent(String codePermanent) {
		this.codePermanent = codePermanent;
	}

	public EleveSaisonsBean getEleveSaison() {
		return eleveSaison;
	}

	public void setEleveSaison(EleveSaisonsBean eleveSaison) {
		this.eleveSaison = eleveSaison;
	}

	public List<DataBusinessBean> getHistorys() {
		return historys;
	}

	public void setHistorys(List<DataBusinessBean> historys) {
		this.historys = historys;
	}

	public Long getEleveClasseId() {
		return eleveClasseId;
	}

	public void setEleveClasseId(Long eleveClasseId) {
		this.eleveClasseId = eleveClasseId;
	}

	public Long getEleveSaisonId() {
		return eleveSaisonId;
	}

	public void setEleveSaisonId(Long eleveSaisonId) {
		this.eleveSaisonId = eleveSaisonId;
	}
}