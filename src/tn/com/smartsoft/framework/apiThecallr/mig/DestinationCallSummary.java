package tn.com.smartsoft.framework.apiThecallr.mig;

import java.io.Serializable;

import tn.com.smartsoft.commons.data.DateBorne;

public class DestinationCallSummary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String libelle;
	private Long duration;
	private Long count;
	private Double prixAchat;
	private Double prixAchatApi;
	private Double prixVente;
	private Double prixUnitairVente;
	private Double prixUnitairAchat;
	private Double prixUnitairAchatApi;
	private DateBorne dateBorne;
	private Long idPriod;
	
	public DestinationCallSummary(Long id, String libelle, Long duration, Long count, Double prixAchat, Double prixAchatApi, Double prixVente, Double prixUnitairVente, Double prixUnitairAchat,
			Double prixUnitairAchatApi, DateBorne dateBorne) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.duration = duration;
		this.count = count;
		this.prixAchat = prixAchat;
		this.prixAchatApi = prixAchatApi;
		this.prixVente = prixVente;
		this.prixUnitairVente = prixUnitairVente;
		this.prixUnitairAchat = prixUnitairAchat;
		this.prixUnitairAchatApi = prixUnitairAchatApi;
		this.dateBorne = dateBorne;
	}
	
	public DestinationCallSummary(Long id, String libelle, DateBorne dateBorne) {
		this(id, libelle, 0L, 0L, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, dateBorne);
	}
	
	public DateBorne getDateBorne() {
		return dateBorne;
	}
	
	public void setDateBorne(DateBorne dateBorne) {
		this.dateBorne = dateBorne;
	}
	
	public DestinationCallSummary() {
		super();
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
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
	
	public Double getPrixAchat() {
		return prixAchat;
	}
	
	public void setPrixAchat(Double prixAchat) {
		this.prixAchat = prixAchat;
	}
	
	public Double getPrixAchatApi() {
		return prixAchatApi;
	}
	
	public void setPrixAchatApi(Double prixAchatApi) {
		this.prixAchatApi = prixAchatApi;
	}
	
	public Double getPrixVente() {
		return prixVente;
	}
	
	public void setPrixVente(Double prixVente) {
		this.prixVente = prixVente;
	}
	
	public Double getPrixUnitairVente() {
		return prixUnitairVente;
	}
	
	public void setPrixUnitairVente(Double prixUnitairVente) {
		this.prixUnitairVente = prixUnitairVente;
	}
	
	public Double getPrixUnitairAchat() {
		return prixUnitairAchat;
	}
	
	public void setPrixUnitairAchat(Double prixUnitairAchat) {
		this.prixUnitairAchat = prixUnitairAchat;
	}
	
	public Double getPrixUnitairAchatApi() {
		return prixUnitairAchatApi;
	}
	
	public void setPrixUnitairAchatApi(Double prixUnitairAchatApi) {
		this.prixUnitairAchatApi = prixUnitairAchatApi;
	}
	
	public Long getIdPriod() {
		return idPriod;
	}
	
	public void setIdPriod(Long idPriod) {
		this.idPriod = idPriod;
	}
	
	public String toString() {
		return "[libelle=" + libelle + ", duration=" + duration + ", count=" + count + ", prixAchat=" + prixAchat + ", prixAchatApi=" + prixAchatApi + ", prixVente=" + prixVente
				+ ", prixUnitairVente=" + prixUnitairVente + ", prixUnitairAchat=" + prixUnitairAchat + ", prixUnitairAchatApi=" + prixUnitairAchatApi + "]\n";
	}
	
}
