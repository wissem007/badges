package tn.com.smartsoft.backoffice.eleves.eleveDemandeCarte.beans;

import tn.com.digivoip.comman.barcode.BarCodeUtility;
import tn.com.digivoip.comman.qrCode.QRCodeEncoder;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

import java.util.Date;

import tn.com.smartsoft.backoffice.eleves.eleves.beans.EleveSaisonsBean;

import java.io.Serializable;

public class EleveDemandeCartesBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Date dateDemande;
	private Date dateImpresion;
	private Long statuDemandesId;
	private byte[] photoBdata;
	private String photoCtype;
	private Long photoSdata;
	private String codePermanent;
	private Long eleveDemandeCarteId;
	private Long eleveSaisonId;
	private EleveSaisonsBean eleveSaison;
	private StatuDemandes statuDemande;
	private Long eleveClasseId;
	private FileBean photo;
	private Boolean checked = Boolean.TRUE;
	private String telResponsable1;
	private String telResponsable2;

	public EleveDemandeCartesBean() {
		super();
	}

	public Serializable getId() {
		return new EleveDemandeCartesBeanId(this);
	}

	public void setId(EleveDemandeCartesBeanId id) {
		id.copyValue(this);
	}

	public String getTelResponsable1() {
		return telResponsable1;
	}

	public void setTelResponsable1(String telResponsable1) {
		this.telResponsable1 = telResponsable1;
	}

	public String getTelResponsable2() {
		return telResponsable2;
	}

	public void setTelResponsable2(String telResponsable2) {
		this.telResponsable2 = telResponsable2;
	}

	public Date getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	public Date getDateImpresion() {
		return this.dateImpresion;
	}

	public void setDateImpresion(Date dateImpresion) {
		this.dateImpresion = dateImpresion;
	}

	public Long getStatuDemandesId() {
		return statuDemandesId;
	}

	public void setStatuDemandesId(Long statuDemandesId) {
		this.statuDemandesId = statuDemandesId;
	}

	public StatuDemandes getStatuDemande() {
		return statuDemande;
	}

	public void setStatuDemande(StatuDemandes statuDemande) {
		this.statuDemande = statuDemande;
	}

	public byte[] getPhotoBdata() {
		return this.photoBdata;
	}

	public void setPhotoBdata(byte[] photoBdata) {
		this.photoBdata = photoBdata;
	}

	public String getPhotoCtype() {
		return this.photoCtype;
	}

	public void setPhotoCtype(String photoCtype) {
		this.photoCtype = photoCtype;
	}

	public Long getPhotoSdata() {
		return this.photoSdata;
	}

	public void setPhotoSdata(Long photoSdata) {
		this.photoSdata = photoSdata;
	}

	public String getCodePermanent() {
		return this.codePermanent;
	}

	public void setCodePermanent(String codePermanent) {
		this.codePermanent = codePermanent;
	}

	public Long getEleveDemandeCarteId() {
		return this.eleveDemandeCarteId;
	}

	public void setEleveDemandeCarteId(Long eleveDemandeCarteId) {
		this.eleveDemandeCarteId = eleveDemandeCarteId;
	}

	public Long getEleveSaisonId() {
		return this.eleveSaisonId;
	}

	public void setEleveSaisonId(Long eleveSaisonId) {
		this.eleveSaisonId = eleveSaisonId;
	}

	public EleveSaisonsBean getEleveSaison() {
		return this.eleveSaison;
	}

	public void setEleveSaison(EleveSaisonsBean eleveSaison) {
		this.eleveSaison = eleveSaison;
	}

	public Long getEleveClasseId() {
		return eleveClasseId;
	}

	public void setEleveClasseId(Long eleveClasseId) {
		this.eleveClasseId = eleveClasseId;
	}

	public FileBean getPhoto() {
		this.photo = FileBean.createGetFile(this, "photo");
		return photo;
	}

	public void setPhoto(FileBean vignette) {
		this.photo = FileBean.createSetFile(this, vignette, "photo");
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public FileBean getBarcode() {
		try {
			return BarCodeUtility.encodeToFile(codePermanent);
		} catch (Exception e) {
			return null;
		}
	}

	public void setBarcode(FileBean vignette) {
	}

	public FileBean getQrcode() {
		try {
			return QRCodeEncoder.encodeToFile(120, 120, codePermanent);
		} catch (Exception e) {
			return null;
		}
	}

	public void setQrcode(FileBean vignette) {
	}

}