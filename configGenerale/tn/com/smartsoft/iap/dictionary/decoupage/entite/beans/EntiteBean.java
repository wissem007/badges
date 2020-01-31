package tn.com.smartsoft.iap.dictionary.decoupage.entite.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.presentation.model.EntiteModel;
import tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans.SequenceBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.decoupage.typeEntites.beans.TypeEntitesBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;

public class EntiteBean extends DataBusinessBean {

	private static final long serialVersionUID = 1L;
	private String entiteId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private Boolean isPersistant = true;
	private Long rang;
	private String displayKeyFormat;
	private String help;
	private String javaClass;
	private List javaClassList;
	private String dbTable;
	private Boolean isAuditable = true;
	private SujetBean parentSujet;
	private Long typeEntiteId;
	private List typeEntiteList;
	private Long niveauApplicatifId;
	private List niveauApplicatifList;
	private SequenceBean sequence;
	private UserTypeBean userType;
	private TypeEntitesBean typeEntite;

	public PropertyBean getProperty(String propertyName) {
		return (PropertyBean) propertys.get(propertyName);
	}

	private Map<String, PropertyBean> propertys = new HashMap<String, PropertyBean>();

	private List listProprietes = new ArrayList();

	public EntiteBean() {
		super();
	}

	public List getListProprietes() {
		return listProprietes;
	}

	public void setListProprietes(List listProprietes) {
		this.listProprietes = listProprietes;
	}

	public SequenceBean getSequence() {
		return sequence;
	}

	public void setSequence(SequenceBean sequence) {
		this.sequence = sequence;
	}

	public UserTypeBean getUserType() {
		return userType;
	}

	public void setUserType(UserTypeBean userType) {
		this.userType = userType;
	}

	public Long getNiveauApplicatifId() {
		return niveauApplicatifId;
	}

	public void setNiveauApplicatifId(Long niveauApplicatifId) {
		this.niveauApplicatifId = niveauApplicatifId;
	}

	public Boolean getIsPersistant() {
		return isPersistant;
	}

	public void setIsPersistant(Boolean isPersistant) {
		this.isPersistant = isPersistant;
	}

	public Boolean getIsAuditable() {
		return isAuditable;
	}

	public void setIsAuditable(Boolean isAuditable) {
		this.isAuditable = isAuditable;
	}

	public SujetBean getParentSujet() {
		return parentSujet;
	}

	public void setParentSujet(SujetBean parentSujet) {
		this.parentSujet = parentSujet;
	}

	public Serializable getId() {
		return new EntiteBeanId(this);
	}

	public void setId(EntiteBeanId id) {
		id.copyValue(this);
	}

	public String getEntiteId() {
		return this.entiteId;
	}

	public void setEntiteId(String entiteId) {
		this.entiteId = entiteId;
	}

	public String getSujetId() {
		return this.sujetId;
	}

	public void setSujetId(String sujetId) {
		this.sujetId = sujetId;
	}

	public String getSubModuleId() {
		return this.subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Boolean isAuditable() {
		return this.isAuditable;
	}

	public void setAuditable(Boolean isAuditable) {
		this.isAuditable = isAuditable;
	}

	public Boolean isPersistant() {
		return this.isPersistant;
	}

	public void setPersistant(Boolean isPersistant) {
		this.isPersistant = isPersistant;
	}

	public Long getRang() {
		return this.rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public String getDisplayKeyFormat() {
		return this.displayKeyFormat;
	}

	public void setDisplayKeyFormat(String displayKeyFormat) {
		this.displayKeyFormat = displayKeyFormat;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getJavaClass() {
		return this.javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public String getDbTable() {
		return this.dbTable;
	}

	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}

	public Map<String, PropertyBean> getPropertys() {
		return this.propertys;
	}

	public void setPropertys(Map<String, PropertyBean> propertys) {
		this.propertys = propertys;
	}

	public void setTypeEntiteId(Long typeEntiteId) {
		this.typeEntiteId = typeEntiteId;
	}

	public Long getTypeEntiteId() {
		return typeEntiteId;
	}

	public void setTypeEntite(TypeEntitesBean typeEntite) {
		this.typeEntite = typeEntite;
	}

	public TypeEntitesBean getTypeEntite() {
		return typeEntite;
	}

	public Integer getHashId() {
		return ((EntiteBeanId) this.getId()).hashCode();
	}

	public void setHashId(Integer hashId) {
	}

	public String getDispalayLabel() {
		return getLibelle() + "->" + getParentSujet().getLibelle() + "->" + getParentSujet().getParentSubModule().getLibelle() + "->"
				+ getParentSujet().getParentSubModule().getParentModule().getLibelle();
	}

	public void setDispalayLabel(String hashId) {
	}

	public void initListProperties(List<DataBusinessBean> listRefEntites) {

		for (int i = 0; i < this.getListProprietes().size(); i++) {
			PropertyBean propertyBean = (PropertyBean) this.getListProprietes().get(i);
			if (propertyBean != null && propertyBean.getHashRefEntiteId() != null && propertyBean.getHashRefEntiteId().intValue() > 0) {
				EntiteBean entiteRefBean = (EntiteBean) ListUtils.findByProperty(listRefEntites, "hashId", propertyBean.getHashRefEntiteId());
				if (propertyBean != null) {
					propertyBean.setRefModuleId(entiteRefBean.getModuleId());
					propertyBean.setRefSubModuleId(entiteRefBean.getSubModuleId());
					propertyBean.setRefSujetId(entiteRefBean.getSujetId());
					propertyBean.setRefEntiteId(entiteRefBean.getEntiteId());
				}
			}
		}

	}

	public void initListProperties() {

		for (int i = 0; i < this.getListProprietes().size(); i++) {
			PropertyBean propertyBean = (PropertyBean) this.getListProprietes().get(i);
			if (propertyBean != null)
				propertyBean.initHashRefEntiteId();

		}

	}

	public void setJavaClassList(List javaClassList) {
		this.javaClassList = javaClassList;
	}

	public List getJavaClassList() {
		return javaClassList;
	}

	public void setTypeEntiteList(List typeEntiteList) {
		this.typeEntiteList = typeEntiteList;
	}

	public List getTypeEntiteList() {
		return typeEntiteList;
	}

	public void setNiveauApplicatifList(List niveauApplicatifList) {
		this.niveauApplicatifList = niveauApplicatifList;
	}

	public List getNiveauApplicatifList() {
		return niveauApplicatifList;
	}

}