package tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans;

import java.io.Serializable;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class OrganismeBean extends DataBusinessBean{
	
	private static final long	serialVersionUID	= 1L;
	private Long				organismeId;
	private Long				societeId;
	private String				libelle;
	private String				abreviation;
	private SocieteBean			parentSociete;
	private Integer				hashOrganismeId;
	private String				siteWeb;
	private String				email;
	private String				adresse;
	private Long				localiteId;
	private String				telephone1;
	private String				telephone2;
	private String				fax;
	private LocaliteBean		localite;
	private Boolean				isAll				= Boolean.FALSE;	;
	
	public OrganismeBean(Long organismeId, Long societeId, String libelle) {
		super();
		this.organismeId = organismeId;
		this.societeId = societeId;
		this.libelle = libelle;
		setParentSociete(new SocieteBean(societeId, libelle));
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Long getLocaliteId() {
		return localiteId;
	}
	public void setLocaliteId(Long localiteId) {
		this.localiteId = localiteId;
	}
	public String getTelephone1() {
		return telephone1;
	}
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
	public String getTelephone2() {
		return telephone2;
	}
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public LocaliteBean getLocalite() {
		return localite;
	}
	public void setLocalite(LocaliteBean localite) {
		this.localite = localite;
	}
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public OrganismeBean() {
		super();
	}
	public OrganismeBean(Long organismeId, Long societeId) {
		super();
		this.organismeId = organismeId;
		this.societeId = societeId;
	}
	public OrganismeBean(Long societeId) {
		super();
		this.societeId = societeId;
	}
	public Serializable getId() {
		return new OrganismeBeanId(this);
	}
	public void setId(OrganismeBeanId id) {
		id.copyValue(this);
	}
	public Integer getHashOrganismeId() {
		if (this.hashOrganismeId == null) this.hashOrganismeId = new OrganismeBeanId(this).hashCode();
		return this.hashOrganismeId;
	}
	public void setHashOrganismeId(Integer hashOrganismeId) {
		this.hashOrganismeId = hashOrganismeId;
	}
	public Long getOrganismeId() {
		return this.organismeId;
	}
	public void setOrganismeId(Long organismeId) {
		this.organismeId = organismeId;
	}
	public Long getSocieteId() {
		return this.societeId;
	}
	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}
	public String getLibelle() {
		return this.libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getAbreviation() {
		return this.abreviation;
	}
	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}
	public String getDispalyLabele() {
		if (isOrganismeSociete()) return getParentSociete().getLibelle();
		else return getLibelle();
	}
	public boolean isOrganismeSociete() {
		return getOrganismeId().intValue() == 0;
	}
	public SocieteBean getParentSociete() {
		return this.parentSociete;
	}
	public void setParentSociete(SocieteBean parentSociete) {
		this.parentSociete = parentSociete;
	}
	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	public Boolean getIsAll() {
		return isAll;
	}
}