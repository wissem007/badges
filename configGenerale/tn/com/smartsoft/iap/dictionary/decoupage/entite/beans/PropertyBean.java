package tn.com.smartsoft.iap.dictionary.decoupage.entite.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sequences.beans.SequenceBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;

public class PropertyBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String propertyName;
	private String entiteId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String refEntiteId;
	private String refSujetId;
	private String refSubModuleId;
	private String refModuleId;
	private String sequenceId;
	private String userTypeId;
	private String roleId;
	private String libelle;
	private Long rang;
	private Boolean auditable = true;
	private Boolean encrypted = true;
	private Boolean cherchable = true;
	private Boolean persistant = true;
	private Boolean dynamique = false;
	private String defaultValue;
	private String help;
	private SequenceBean sequence;
	private FieldRoleBean role;
	private UserTypeBean userType;
	private Integer hashRefEntiteId;
	private String libelleEntiteRef;

	public PropertyBean() {
		super();
	}

	public String getLibelleEntiteRef() {
		return libelleEntiteRef;
	}

	public void setLibelleEntiteRef(String libelleEntiteRef) {
		this.libelleEntiteRef = libelleEntiteRef;
	}

	public Serializable getId() {
		return new PropertyBeanId(this);
	}

	public void setId(PropertyBeanId id) {
		id.copyValue(this);
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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

	public String getRefEntiteId() {
		return refEntiteId;
	}

	public EntiteBeanId getRefEntiteBeanId() {
		return new EntiteBeanId(this.getRefEntiteId(), this.getRefSujetId(), this.getRefSubModuleId(), this.getRefModuleId());
	}

	public void setRefEntiteId(String refEntiteId) {
		this.refEntiteId = refEntiteId;
	}

	public String getRefSujetId() {
		return refSujetId;
	}

	public void setRefSujetId(String refSujetId) {
		this.refSujetId = refSujetId;
	}

	public String getRefSubModuleId() {
		return refSubModuleId;
	}

	public void setRefSubModuleId(String refSubModuleId) {
		this.refSubModuleId = refSubModuleId;
	}

	public String getRefModuleId() {
		return refModuleId;
	}

	public void setRefModuleId(String refModuleId) {
		this.refModuleId = refModuleId;
	}

	public String getSequenceId() {
		return this.sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getUserTypeId() {
		return this.userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getRang() {
		return this.rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public Boolean getAuditable() {
		return auditable;
	}

	public void setAuditable(Boolean auditable) {
		this.auditable = auditable;
	}

	public Boolean getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(Boolean encrypted) {
		this.encrypted = encrypted;
	}

	public Boolean getCherchable() {
		return cherchable;
	}

	public void setCherchable(Boolean cherchable) {
		this.cherchable = cherchable;
	}

	public Boolean getPersistant() {
		return persistant;
	}

	public void setPersistant(Boolean persistant) {
		this.persistant = persistant;
	}

	public void setDynamique(Boolean dynamique) {
		this.dynamique = dynamique;
	}

	public Boolean getDynamique() {
		return dynamique;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public SequenceBean getSequence() {
		return this.sequence;
	}

	public void setSequence(SequenceBean sequence) {
		this.sequence = sequence;
	}

	public FieldRoleBean getRole() {
		return this.role;
	}

	public void setRole(FieldRoleBean role) {
		this.role = role;
	}

	public UserTypeBean getUserType() {
		return this.userType;
	}

	public void setUserType(UserTypeBean userType) {
		this.userType = userType;
	}

	public void initHashRefEntiteId() {
		if (this.getRefEntiteId() == null && this.getRefSujetId() == null && this.getRefSubModuleId() == null && this.getRefModuleId() == null)
			this.hashRefEntiteId = null;
		else
			this.hashRefEntiteId = new EntiteBeanId(refEntiteId, refSujetId, refSubModuleId, refModuleId).hashCode();
	}

	public Integer getHashRefEntiteId() {
		return hashRefEntiteId;
	}

	public void setHashRefEntiteId(Integer hashRefEntiteId) {
		this.hashRefEntiteId = hashRefEntiteId;
	}

}