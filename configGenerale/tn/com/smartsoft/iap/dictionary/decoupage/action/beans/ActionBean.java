package tn.com.smartsoft.iap.dictionary.decoupage.action.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.actionType.beans.ActionTypeBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;

public class ActionBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String actionId;

	private String sujetId;
	private String subModuleId;
	private String moduleId;

	private Long actionTypeId;
	private Long actionTemplateId;
	private String roleId;
	private String libelle;
	private Boolean isAuditable;
	private String auditValueExpression;
	private String help;

	private ActionTemplateBean actionTemplate;
	private ActionTypeBean type;
	private ActionRoleBean role; 
	private SujetBean parentSujet;

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

	public ActionBean() {
		super();
	}

	public Serializable getId() {
		return new ActionBeanId(this);
	}

	public void setId(ActionBeanId id) {
		id.copyValue(this);
	}

	public String getActionId() {
		return this.actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
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

	public Long getActionTypeId() {
		return this.actionTypeId;
	}

	public void setActionTypeId(Long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public Long getActionTemplateId() {
		return this.actionTemplateId;
	}

	public void setActionTemplateId(Long actionTemplateId) {
		this.actionTemplateId = actionTemplateId;
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

	public Boolean isAuditable() {
		return this.isAuditable;
	}

	public void setAuditable(Boolean isAuditable) {
		this.isAuditable = isAuditable;
	}

	public String getAuditValueExpression() {
		return this.auditValueExpression;
	}

	public void setAuditValueExpression(String auditValueExpression) {
		this.auditValueExpression = auditValueExpression;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public ActionTemplateBean getActionTemplate() {
		return this.actionTemplate;
	}

	public void setActionTemplate(ActionTemplateBean actionTemplate) {
		this.actionTemplate = actionTemplate;
	}

	public ActionTypeBean getType() {
		return this.type;
	}

	public void setType(ActionTypeBean type) {
		this.type = type;
	}

	public ActionRoleBean getRole() {
		return this.role;
	}

	public void setRole(ActionRoleBean role) {
		this.role = role;
	}
}