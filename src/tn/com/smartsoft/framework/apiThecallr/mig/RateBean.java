package tn.com.smartsoft.framework.apiThecallr.mig;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.NumberUtils;

public class RateBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String libelle;
	private Double prixAchat;
	private Double tauxVente;
	
	public RateBean(Long id, String libelle, Double prixAchat, Double tauxVente) {
		super();
		this.tauxVente = tauxVente;
		this.prixAchat = prixAchat;
		this.id = id;
		this.libelle = libelle;
	}
	
	public Double getPrixVente() {
		return NumberUtils.multiplier(getTauxVente(), getPrixAchat());
	}
	
	public Double getTauxVente() {
		return tauxVente;
	}
	
	public void setTauxVente(Double tauxVente) {
		this.tauxVente = tauxVente;
	}
	
	public Double getPrixAchat() {
		return prixAchat;
	}
	
	public void setPrixAchat(Double prixAchat) {
		this.prixAchat = prixAchat;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
