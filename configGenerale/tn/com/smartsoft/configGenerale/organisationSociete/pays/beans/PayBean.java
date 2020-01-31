package tn.com.smartsoft.configGenerale.organisationSociete.pays.beans;

import java.io.Serializable;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class PayBean extends DataBusinessBean{
	
	private static final long	serialVersionUID	= 1L;
	private Long				paysId;
	private String				libelle;
	private String				libelleArabe;
	private String				nationalite;
	private Long				deviseId;
	private Long				cleIban;
	private Long				longeurCleIban;
	private Long				indicatifTel;
	private String				codeIso;
	private Long				codeBarreMax;
	private Long				codeBarreMin;
	private DeviseBean			devise;
	
	public PayBean(Long paysId, String libelle) {
		super();
		this.paysId = paysId;
		this.libelle = libelle;
	}
	public Long getCodeBarreMax() {
		return codeBarreMax;
	}
	public void setCodeBarreMax(Long codeBarreMax) {
		this.codeBarreMax = codeBarreMax;
	}
	public Long getCodeBarreMin() {
		return codeBarreMin;
	}
	public void setCodeBarreMin(Long codeBarreMin) {
		this.codeBarreMin = codeBarreMin;
	}
	public Long getLongeurCleIban() {
		return longeurCleIban;
	}
	public void setLongeurCleIban(Long longeurCleIban) {
		this.longeurCleIban = longeurCleIban;
	}
	public PayBean(Long paysId) {
		this.paysId = paysId;
	}
	public PayBean() {
		super();
	}
	public Serializable getId() {
		return this.getPaysId();
	}
	public void setId(Long id) {
		this.setPaysId(id);
	}
	public Long getPaysId() {
		return this.paysId;
	}
	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}
	public String getLibelle() {
		return this.libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getLibelleArabe() {
		return this.libelleArabe;
	}
	public void setLibelleArabe(String libelleArabe) {
		this.libelleArabe = libelleArabe;
	}
	public String getNationalite() {
		return this.nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	public Long getDeviseId() {
		return this.deviseId;
	}
	public void setDeviseId(Long deviseId) {
		this.deviseId = deviseId;
	}
	public DeviseBean getDevise() {
		return this.devise;
	}
	public void setDevise(DeviseBean devise) {
		this.devise = devise;
	}
	public Long getCleIban() {
		return cleIban;
	}
	public void setCleIban(Long cleIban) {
		this.cleIban = cleIban;
	}
	public Long getIndicatifTel() {
		return indicatifTel;
	}
	public void setIndicatifTel(Long indicatifTel) {
		this.indicatifTel = indicatifTel;
	}
	public String getCodeIso() {
		return codeIso;
	}
	public void setCodeIso(String codeIso) {
		this.codeIso = codeIso;
	}
}