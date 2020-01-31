package tn.com.smartsoft.configGenerale.autresParametrage.jourSemaine.beans;

import java.io.Serializable;
import java.util.Date;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class JourSemaineBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long jourId;
	private Long jourCode;
	private Long rang;
	private String libelle;
	private String abreviation;

	private Date dateDebut;
	private Date dateFin;

	public JourSemaineBean() {
		super();
	}

	public JourSemaineBean(Long jourId, String libelle, Date dateDebut, Date dateFin) {
		this(jourId, libelle);
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	public JourSemaineBean(Long jourId, String libelle) {
		super();
		this.jourId = jourId;
		this.libelle = libelle;
	}

	public Serializable getId() {
		return this.getJourId();
	}

	public Long getJourId() {
		return jourId;
	}

	public void setJourId(Long jourId) {
		this.jourId = jourId;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Long getJourCode() {
		return jourCode;
	}

	public void setJourCode(Long jourCode) {
		this.jourCode = jourCode;
	}

	public Long getRang() {
		return rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getAbreviation() {
		return abreviation;
	}

	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}

}