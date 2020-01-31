package tn.com.smartsoft.configGenerale.organisationSociete.societe.beans;

import java.io.Serializable;
import java.math.BigInteger;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class SocieteBean extends DataBusinessBean{
	
	private static final long	serialVersionUID	= 1L;
	private Long				societeId;
	private Long				fileId;
	private String				abreviation;
	private String				libelle;
	private String				libelleArabe;
	private String				adresse;
	private Long				paysId;
	private Long				governoratId;
	private Long				localiteId;
	private String				adresseArabe;
	private String				telephone1;
	private String				telephone2;
	private String				fax;
	private String				matriculeFiscal;
	private String				registreCommerce;
	private Long				deviseId;
	private String				matriculeCnss;
	private Long				activiteId;
	private BigInteger			numeroCcb;
	private String				matriculeCnrps;
	private String				email;
	private String				siteWeb;
	private String				matriculeCavis;
	private Long				conventionId;
	private Long				personnaliteId;
	private Long				siteId;
	private Long				sousActiviteId;
	private Long				nationaliteId;
	private Long				residenceFiscaleId;
	private Boolean				isAll				= Boolean.FALSE;	;
	private PayBean				residenceFiscale;
	private PayBean				nationalite;
	private PayBean				pays;
	private GovernoratBean		governorat;
	private LocaliteBean		localite;
	private DeviseBean			devise;
	private FileBean			file;
	
	public SocieteBean(Long societeId, String libelle) {
		super();
		this.societeId = societeId;
		this.libelle = libelle;
	}
	public Long getResidenceFiscaleId() {
		return residenceFiscaleId;
	}
	public void setResidenceFiscaleId(Long residenceFiscaleId) {
		this.residenceFiscaleId = residenceFiscaleId;
	}
	public PayBean getResidenceFiscale() {
		return residenceFiscale;
	}
	public void setResidenceFiscale(PayBean residenceFiscale) {
		this.residenceFiscale = residenceFiscale;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getSousActiviteId() {
		return sousActiviteId;
	}
	public void setSousActiviteId(Long sousActiviteId) {
		this.sousActiviteId = sousActiviteId;
	}
	public Long getNationaliteId() {
		return nationaliteId;
	}
	public void setNationaliteId(Long nationaliteId) {
		this.nationaliteId = nationaliteId;
	}
	public PayBean getNationalite() {
		return nationalite;
	}
	public void setNationalite(PayBean nationalite) {
		this.nationalite = nationalite;
	}
	public Long getPersonnaliteId() {
		return personnaliteId;
	}
	public void setPersonnaliteId(Long personnaliteId) {
		this.personnaliteId = personnaliteId;
	}
	public GovernoratBean getGovernorat() {
		return governorat;
	}
	public void setGovernorat(GovernoratBean governorat) {
		this.governorat = governorat;
	}
	public SocieteBean() {
		super();
	}
	public Serializable getId() {
		return this.getSocieteId();
	}
	public void setId(Long id) {
		this.setSocieteId(id);
	}
	public Long getSocieteId() {
		return this.societeId;
	}
	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}
	public Long getFileId() {
		return this.fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getAbreviation() {
		return this.abreviation;
	}
	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
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
	public String getAdresse() {
		return this.adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Long getPaysId() {
		return this.paysId;
	}
	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}
	public Long getGovernoratId() {
		return this.governoratId;
	}
	public void setGovernoratId(Long governoratId) {
		this.governoratId = governoratId;
	}
	public Long getLocaliteId() {
		return this.localiteId;
	}
	public void setLocaliteId(Long localiteId) {
		this.localiteId = localiteId;
	}
	public String getAdresseArabe() {
		return this.adresseArabe;
	}
	public void setAdresseArabe(String adresseArabe) {
		this.adresseArabe = adresseArabe;
	}
	public String getTelephone1() {
		return this.telephone1;
	}
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
	public String getTelephone2() {
		return this.telephone2;
	}
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	public String getFax() {
		return this.fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMatriculeFiscal() {
		return this.matriculeFiscal;
	}
	public void setMatriculeFiscal(String matriculeFiscal) {
		this.matriculeFiscal = matriculeFiscal;
	}
	public String getRegistreCommerce() {
		return this.registreCommerce;
	}
	public void setRegistreCommerce(String registreCommerce) {
		this.registreCommerce = registreCommerce;
	}
	public Long getDeviseId() {
		return this.deviseId;
	}
	public void setDeviseId(Long deviseId) {
		this.deviseId = deviseId;
	}
	public String getMatriculeCnss() {
		return this.matriculeCnss;
	}
	public void setMatriculeCnss(String matriculeCnss) {
		this.matriculeCnss = matriculeCnss;
	}
	public Long getActiviteId() {
		return this.activiteId;
	}
	public void setActiviteId(Long activiteId) {
		this.activiteId = activiteId;
	}
	public BigInteger getNumeroCcb() {
		return this.numeroCcb;
	}
	public void setNumeroCcb(BigInteger numeroCcb) {
		this.numeroCcb = numeroCcb;
	}
	public String getMatriculeCnrps() {
		return this.matriculeCnrps;
	}
	public void setMatriculeCnrps(String matriculeCnrps) {
		this.matriculeCnrps = matriculeCnrps;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSiteWeb() {
		return this.siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	public String getMatriculeCavis() {
		return this.matriculeCavis;
	}
	public void setMatriculeCavis(String matriculeCavis) {
		this.matriculeCavis = matriculeCavis;
	}
	public PayBean getPays() {
		return this.pays;
	}
	public void setPays(PayBean pays) {
		this.pays = pays;
	}
	public LocaliteBean getLocalite() {
		return this.localite;
	}
	public void setLocalite(LocaliteBean localite) {
		this.localite = localite;
	}
	public DeviseBean getDevise() {
		return this.devise;
	}
	public void setDevise(DeviseBean devise) {
		this.devise = devise;
	}
	public FileBean getFile() {
		return this.file;
	}
	public void setFile(FileBean file) {
		this.file = file;
	}
	public Long getConventionId() {
		return conventionId;
	}
	public void setConventionId(Long conventionId) {
		this.conventionId = conventionId;
	}
	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	public Boolean getIsAll() {
		return isAll;
	}
}