package tn.com.smartsoft.configGenerale.devises.devise.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class DeviseBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long deviseId;
	private String libelle;
	private String symboleMonetaire;
	private Long nombreDecimales;
	private Long arrondissementMode;
	private Long arrondissementMontant;
	private Long isoNumeric;
	private String sousUnite;
	private String isoAlpha;

	public DeviseBean() {
		super();
	}

	public Serializable getId() {
		return this.getDeviseId();
	}

	public void setId(Long id) {
		this.setDeviseId(id);
	}

	public Long getDeviseId() {
		return this.deviseId;
	}

	public void setDeviseId(Long deviseId) {
		this.deviseId = deviseId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getSymboleMonetaire() {
		return this.symboleMonetaire;
	}

	public void setSymboleMonetaire(String symboleMonetaire) {
		this.symboleMonetaire = symboleMonetaire;
	}

	public Long getNombreDecimales() {
		return this.nombreDecimales;
	}

	public void setNombreDecimales(Long nombreDecimales) {
		this.nombreDecimales = nombreDecimales;
	}

	public Long getArrondissementMode() {
		return this.arrondissementMode;
	}

	public void setArrondissementMode(Long arrondissementMode) {
		this.arrondissementMode = arrondissementMode;
	}

	public Long getArrondissementMontant() {
		return this.arrondissementMontant;
	}

	public void setArrondissementMontant(Long arrondissementMontant) {
		this.arrondissementMontant = arrondissementMontant;
	}

	public Long getIsoNumeric() {
		return isoNumeric;
	}

	public void setIsoNumeric(Long isoNumeric) {
		this.isoNumeric = isoNumeric;
	}

	public String getSousUnite() {
		return sousUnite;
	}

	public void setSousUnite(String sousUnite) {
		this.sousUnite = sousUnite;
	}

	public String getIsoAlpha() {
		return isoAlpha;
	}

	public void setIsoAlpha(String isoAlpha) {
		this.isoAlpha = isoAlpha;
	}
	
}