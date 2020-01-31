package tn.com.digivoip.comman.qrCode;

import java.io.Serializable;
import java.util.Date;

public class DataRowValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String saison;
	private int regime;
	private String typeLicence;
	private int competion;
	private String categorieLicence;
	private long numeroLicence;
	private String nom;
	private String prenom;
	private String prenomPere;
	private String prenomMere;
	private String nomMere;
	private String nomPere;
	private Date dateNaissance;
	private String LieuNaissance;
	private String nationalite;
	private String numeroCin;
	private String numeroPasseport;
	private Date dateEnregistrement;
	private Date dateConsultation;
	private String clubOrigine;
	private String clunSaison;
	private String clubN_1;
	private String clubN_2;
	private String clubN_3;
	private String clubN_4;
	private Date dateContrat;
	private Date dateFinContract;
	private String nomMedecin;
	private String prenomMedecin;
	private byte[] signatureDoctorBdata;
	private byte[] signatureIntervenantBdata;
	private byte[] signatureTeamBdata;
	private byte[] vignetteBdata;
	private byte[] cachetMedecinBdata;
	private byte[] photoBdata;
	private String fileName;
	private boolean errors;
	private String qrCode;
	
	public String getSaison() {
		return saison;
	}
	
	public void setSaison(String saison) {
		this.saison = saison;
	}
	
	public int getRegime() {
		return regime;
	}
	
	public void setRegime(int regime) {
		this.regime = regime;
	}
	
	public String getTypeLicence() {
		return typeLicence;
	}
	
	public void setTypeLicence(String typeLicence) {
		this.typeLicence = typeLicence;
	}
	
	public int getCompetion() {
		return competion;
	}
	
	public void setCompetion(int competion) {
		this.competion = competion;
	}
	
	public String getCategorieLicence() {
		return categorieLicence;
	}
	
	public void setCategorieLicence(String categorieLicence) {
		this.categorieLicence = categorieLicence;
	}
	
	public long getNumeroLicence() {
		return numeroLicence;
	}
	
	public void setNumeroLicence(long numeroLicence) {
		this.numeroLicence = numeroLicence;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getPrenomPere() {
		return prenomPere;
	}
	
	public void setPrenomPere(String prenomPere) {
		this.prenomPere = prenomPere;
	}
	
	public String getPrenomMere() {
		return prenomMere;
	}
	
	public void setPrenomMere(String prenomMere) {
		this.prenomMere = prenomMere;
	}
	
	public String getNomMere() {
		return nomMere;
	}
	
	public void setNomMere(String nomMere) {
		this.nomMere = nomMere;
	}
	
	public String getNomPere() {
		return nomPere;
	}
	
	public void setNomPere(String nomPere) {
		this.nomPere = nomPere;
	}
	
	public Date getDateNaissance() {
		return dateNaissance;
	}
	
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public String getLieuNaissance() {
		return LieuNaissance;
	}
	
	public void setLieuNaissance(String lieuNaissance) {
		LieuNaissance = lieuNaissance;
	}
	
	public String getNationalite() {
		return nationalite;
	}
	
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	
	public String getNumeroCin() {
		return numeroCin;
	}
	
	public void setNumeroCin(String numeroCin) {
		this.numeroCin = numeroCin;
	}
	
	public String getNumeroPasseport() {
		return numeroPasseport;
	}
	
	public void setNumeroPasseport(String numeroPasseport) {
		this.numeroPasseport = numeroPasseport;
	}
	
	public Date getDateConsultation() {
		return dateConsultation;
	}
	
	public void setDateConsultation(Date dateConsultation) {
		this.dateConsultation = dateConsultation;
	}
	
	public String getClubOrigine() {
		return clubOrigine;
	}
	
	public void setClubOrigine(String clubOrigine) {
		this.clubOrigine = clubOrigine;
	}
	
	public String getClunSaison() {
		return clunSaison;
	}
	
	public void setClunSaison(String clunSaison) {
		this.clunSaison = clunSaison;
	}
	
	public String getClubN_1() {
		return clubN_1;
	}
	
	public void setClubN_1(String clubN_1) {
		this.clubN_1 = clubN_1;
	}
	
	public String getClubN_2() {
		return clubN_2;
	}
	
	public void setClubN_2(String clubN_2) {
		this.clubN_2 = clubN_2;
	}
	
	public String getClubN_3() {
		return clubN_3;
	}
	
	public void setClubN_3(String clubN_3) {
		this.clubN_3 = clubN_3;
	}
	
	public String getClubN_4() {
		return clubN_4;
	}
	
	public void setClubN_4(String clubN_4) {
		this.clubN_4 = clubN_4;
	}
	
	public Date getDateContrat() {
		return dateContrat;
	}
	
	public void setDateContrat(Date dateContrat) {
		this.dateContrat = dateContrat;
	}
	
	public Date getDateFinContract() {
		return dateFinContract;
	}
	
	public void setDateFinContract(Date dateFinContract) {
		this.dateFinContract = dateFinContract;
	}
	
	public String getNomMedecin() {
		return nomMedecin;
	}
	
	public void setNomMedecin(String nomMedecin) {
		this.nomMedecin = nomMedecin;
	}
	
	public String getPrenomMedecin() {
		return prenomMedecin;
	}
	
	public void setPrenomMedecin(String prenomMedecin) {
		this.prenomMedecin = prenomMedecin;
	}
	
	public byte[] getSignatureDoctorBdata() {
		return signatureDoctorBdata;
	}
	
	public void setSignatureDoctorBdata(byte[] signatureDoctorBdata) {
		this.signatureDoctorBdata = signatureDoctorBdata;
	}
	
	public byte[] getSignatureIntervenantBdata() {
		return signatureIntervenantBdata;
	}
	
	public void setSignatureIntervenantBdata(byte[] signatureIntervenantBdata) {
		this.signatureIntervenantBdata = signatureIntervenantBdata;
	}
	
	public byte[] getSignatureTeamBdata() {
		return signatureTeamBdata;
	}
	
	public void setSignatureTeamBdata(byte[] signatureTeamBdata) {
		this.signatureTeamBdata = signatureTeamBdata;
	}
	
	public byte[] getVignetteBdata() {
		return vignetteBdata;
	}
	
	public void setVignetteBdata(byte[] vignetteBdata) {
		this.vignetteBdata = vignetteBdata;
	}
	
	public byte[] getPhotoBdata() {
		return photoBdata;
	}
	
	public void setPhotoBdata(byte[] photoBdata) {
		this.photoBdata = photoBdata;
	}
	
	public boolean isErrors() {
		return errors;
	}
	
	public void setErrors(boolean errors) {
		this.errors = errors;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getQrCode() {
		return qrCode;
	}
	
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}
	
	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}
	
	public byte[] getCachetMedecinBdata() {
		return cachetMedecinBdata;
	}

	public void setCachetMedecinBdata(byte[] cachetMedecinBdata) {
		this.cachetMedecinBdata = cachetMedecinBdata;
	}

	public String toString() {
		return "DataRowValue [saison=" + saison + ", regime=" + regime + ", typeLicence=" + typeLicence + ", competion=" + competion + ", categorieLicence=" + categorieLicence + ", numeroLicence="
				+ numeroLicence + ", nom=" + nom + ", prenom=" + prenom + ", prenomPere=" + prenomPere + ", prenomMere=" + prenomMere + ", nomMere=" + nomMere + ", nomPere=" + nomPere
				+ ", dateNaissance=" + dateNaissance + ", LieuNaissance=" + LieuNaissance + ", nationalite=" + nationalite + ", numeroCin=" + numeroCin + ", numeroPasseport=" + numeroPasseport
				+ ", dateConsultation=" + dateConsultation + ", clubOrigine=" + clubOrigine + ", clunSaison=" + clunSaison + ", clubN_1=" + clubN_1 + ", clubN_2=" + clubN_2 + ", clubN_3=" + clubN_3
				+ ", clubN_4=" + clubN_4 + ", dateContrat=" + dateContrat + ", dateFinContract=" + dateFinContract + ", nomMedecin=" + nomMedecin + ", prenomMedecin=" + prenomMedecin + ", fileName="
				+ fileName + ", errors=" + errors + "]";
	}
	
}
