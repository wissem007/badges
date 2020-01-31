package tn.com.smartsoft.backoffice.eleves.eleves.beans;

import java.io.Serializable;

import tn.com.digivoip.comman.barcode.BarCodeUtility;
import tn.com.digivoip.comman.qrCode.QRCodeEncoder;
import tn.com.smartsoft.backoffice.referentiel.eleveClasses.beans.EleveClassesBean;
import tn.com.smartsoft.backoffice.referentiel.saison.beans.SaisonsBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class EleveSaisonsBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String nomResponsable1;
	private String nomResponsable2;
	private String prenomResponsable1;
	private String prenomResponsable2;
	private Boolean serviceCantine;
	private Boolean serviceGarde;
	private String email;
	private Boolean servicePanier;
	private String telResponsable1;
	private String telResponsable2;
	private String codePermanent;
	private Long eleveClasseId;
	private Long eleveSaisonId;

	private byte[] photoBdata;
	private String photoCtype;
	private Long photoSdata;
	private ElevesBean eleve;
	private EleveClassesBean classe;
	private SaisonsBean saison;
	private FileBean photo;

	public EleveSaisonsBean() {
		super();
	}

	public Serializable getId() {
		return new EleveSaisonsBeanId(this);
	}

	public void setId(EleveSaisonsBeanId id) {
		id.copyValue(this);
	}

	public String getNomResponsable1() {
		return this.nomResponsable1;
	}

	public void setNomResponsable1(String nomResponsable1) {
		this.nomResponsable1 = nomResponsable1;
	}

	public String getNomResponsable2() {
		return this.nomResponsable2;
	}

	public void setNomResponsable2(String nomResponsable2) {
		this.nomResponsable2 = nomResponsable2;
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

	public String getPrenomResponsable1() {
		return this.prenomResponsable1;
	}

	public void setPrenomResponsable1(String prenomResponsable1) {
		this.prenomResponsable1 = prenomResponsable1;
	}

	public String getPrenomResponsable2() {
		return this.prenomResponsable2;
	}

	public void setPrenomResponsable2(String prenomResponsable2) {
		this.prenomResponsable2 = prenomResponsable2;
	}

	public Boolean isServiceCantine() {
		return this.serviceCantine;
	}

	public void setServiceCantine(Boolean serviceCantine) {
		this.serviceCantine = serviceCantine;
	}

	public Boolean isServiceGarde() {
		return this.serviceGarde;
	}

	public void setServiceGarde(Boolean serviceGarde) {
		this.serviceGarde = serviceGarde;
	}

	public String getTelResponsable1() {
		return this.telResponsable1;
	}

	public void setTelResponsable1(String telResponsable1) {
		this.telResponsable1 = telResponsable1;
	}

	public String getTelResponsable2() {
		return this.telResponsable2;
	}

	public void setTelResponsable2(String telResponsable2) {
		this.telResponsable2 = telResponsable2;
	}

	public String getCodePermanent() {
		return this.codePermanent;
	}

	public void setDispaly(String d) {
	}

	public String getDispaly() {
		String dispaly = "";
		if (getServiceCantine() != null && getServiceCantine())
			dispaly = "Cantine";
		if (getServiceGarde() != null && getServiceGarde())
			dispaly = dispaly + (!dispaly.equals("") ? " & " : "") + "Garde";
		return dispaly;
	}

	public void setCodePermanent(String codePermanent) {
		this.codePermanent = codePermanent;
	}

	public Long getEleveClasseId() {
		return this.eleveClasseId;
	}

	public void setEleveClasseId(Long eleveClasseId) {
		this.eleveClasseId = eleveClasseId;
	}

	public Long getEleveSaisonId() {
		return this.eleveSaisonId;
	}

	public void setEleveSaisonId(Long eleveSaisonId) {
		this.eleveSaisonId = eleveSaisonId;
	}

	public EleveClassesBean getClasse() {
		return this.classe;
	}

	public void setClasse(EleveClassesBean classe) {
		this.classe = classe;
	}

	public SaisonsBean getSaison() {
		return saison;
	}

	public void setSaison(SaisonsBean saison) {
		this.saison = saison;
	}

	public Boolean getServiceCantine() {
		return serviceCantine;
	}

	public Boolean getServiceGarde() {
		return serviceGarde;
	}

	public FileBean getPhoto() {
		this.photo = FileBean.createGetFile(this, "photo");
		return photo;
	}

	public void setPhoto(FileBean vignette) {
		this.photo = FileBean.createSetFile(this, vignette, "photo");
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

	public ElevesBean getEleve() {
		return eleve;
	}

	public void setEleve(ElevesBean eleve) {
		this.eleve = eleve;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getServicePanier() {
		return servicePanier;
	}

	public void setServicePanier(Boolean servicePanier) {
		this.servicePanier = servicePanier;
	}
}