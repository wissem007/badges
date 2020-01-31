package tn.com.smartsoft.iap.administration.securite.utilisateur.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilisationBean;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class UserBean extends DataBusinessBean {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String passeWord;
	private Boolean isActive;
	private Long userType = new Long(2);
	private String userName;
	private String gsm;
	private String mail;
	private String adresse;
	private String confPasseWord;
	private String oldPasseWord;
	private String oldEditPasseWord;
	private Long paysId;
	private Long governoratId;
	private Long localiteId;
	private String displayName;
	private String telephone;
	private String libprf;
	private Integer etatActive = 3;
	private Long collaborateurAgentId;
	private Long collaborateurId;
	private Map<String, UserPreferenceBean> preferences = new HashMap<String, UserPreferenceBean>();
	private List<UserMenuBean> menuPreferences = new ArrayList<UserMenuBean>();
	private GovernoratBean gouvernorat;
	private LocaliteBean localite;
	private PayBean pays;
	private Long fonctionUtilisationId;
	private FonctionUtilisationBean fonctionUtilisation;

	public Integer getEtatActive() {
		return etatActive;
	}

	public void setEtatActive(Integer etatActive) {
		this.etatActive = etatActive;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getConfPasseWord() {
		return confPasseWord;
	}

	public void setConfPasseWord(String confPasseWord) {
		this.confPasseWord = confPasseWord;
	}

	public UserBean() {
		super();
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public UserPreferenceBean getUserPreference(String userPreferenceId) {
		return (UserPreferenceBean) preferences.get(userPreferenceId);
	}

	public List<UserMenuBean> getMenuPreferences() {
		return menuPreferences;
	}

	public void setMenuPreferences(List<UserMenuBean> menuPreferences) {
		this.menuPreferences = menuPreferences;
	}

	public UserMenuBean getMenuPreferenceBean(int index) {
		return (UserMenuBean) menuPreferences.get(index);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Serializable getId() {
		return this.getUserId();
	}

	public void setId(String id) {
		this.setUserId(id);
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasseWord() {
		return this.passeWord;
	}

	public void setPasseWord(String passeWord) {
		this.passeWord = passeWord;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isSysAdmin() {
		return userType.intValue() == 1;
	}

	public Long getPaysId() {
		return paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}

	public Long getGovernoratId() {
		return governoratId;
	}

	public void setGovernoratId(Long governoratId) {
		this.governoratId = governoratId;
	}

	public Long getLocaliteId() {
		return localiteId;
	}

	public void setLocaliteId(Long localiteId) {
		this.localiteId = localiteId;
	}

	public String getOldPasseWord() {
		return oldPasseWord;
	}

	public String getOldEditPasseWord() {
		return oldEditPasseWord;
	}

	public void setOldEditPasseWord(String oldEditPasseWord) {
		this.oldEditPasseWord = oldEditPasseWord;
	}

	public void setOldPasseWord(String oldPasseWord) {
		this.oldPasseWord = oldPasseWord;
	}

	public GovernoratBean getGouvernorat() {
		return gouvernorat;
	}

	public void setGouvernorat(GovernoratBean gouvernorat) {
		this.gouvernorat = gouvernorat;
	}

	public LocaliteBean getLocalite() {
		return localite;
	}

	public void setLocalite(LocaliteBean localite) {
		this.localite = localite;
	}

	public PayBean getPays() {
		return pays;
	}

	public void setPays(PayBean pays) {
		this.pays = pays;
	}

	public Boolean isActive() {
		return this.isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, UserPreferenceBean> getPreferences() {
		return this.preferences;
	}

	public void setPreferences(Map<String, UserPreferenceBean> preferences) {
		this.preferences = preferences;
	}

	public String getGsm() {
		return gsm;
	}

	public void setGsm(String gsm) {
		this.gsm = gsm;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLibprf() {
		return libprf;
	}

	public void setLibprf(String libprf) {
		this.libprf = libprf;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public FonctionUtilisationBean getFonctionUtilisation() {
		return fonctionUtilisation;
	}

	public void setFonctionUtilisation(FonctionUtilisationBean fonctionUtilisation) {
		this.fonctionUtilisation = fonctionUtilisation;
	}

	public Long getFonctionUtilisationId() {
		return fonctionUtilisationId;
	}

	public void setFonctionUtilisationId(Long fonctionUtilisationId) {
		this.fonctionUtilisationId = fonctionUtilisationId;
	}

	public Long getCollaborateurAgentId() {
		return collaborateurAgentId;
	}

	public void setCollaborateurAgentId(Long collaborateurAgentId) {
		this.collaborateurAgentId = collaborateurAgentId;
	}

	public Long getCollaborateurId() {
		return collaborateurId;
	}

	public void setCollaborateurId(Long collaborateurId) {
		this.collaborateurId = collaborateurId;
	}

}