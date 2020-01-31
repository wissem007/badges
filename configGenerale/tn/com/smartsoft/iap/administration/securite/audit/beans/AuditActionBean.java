package tn.com.smartsoft.iap.administration.securite.audit.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import java.util.Date;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class AuditActionBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long auditActionId;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String userId;
	private Long organismeId;
	private Date dateAudit;
	private String auditValue;
	private Long societeId;
	private ActionBean action;
	private OrganismeBean oragnisme;
	private UserBean user;
	private List<Object> auditEntites = new ArrayList<Object>();

	public AuditActionBean() {
		super();
	}

	public AuditEntiteBean getAuditEntite(int index) {
		return (AuditEntiteBean) auditEntites.get(index);
	}

	public Serializable getId() {
		return new AuditActionBeanId(this);
	}

	public void setId(AuditActionBeanId id) {
		id.copyValue(this);
	}

	public Long getAuditActionId() {
		return this.auditActionId;
	}

	public void setAuditActionId(Long auditActionId) {
		this.auditActionId = auditActionId;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOrganismeId() {
		return this.organismeId;
	}

	public void setOrganismeId(Long organismeId) {
		this.organismeId = organismeId;
	}

	public Date getDateAudit() {
		return this.dateAudit;
	}

	public void setDateAudit(Date dateAudit) {
		this.dateAudit = dateAudit;
	}

	public String getAuditValue() {
		return this.auditValue;
	}

	public void setAuditValue(String auditValue) {
		this.auditValue = auditValue;
	}

	public Long getSocieteId() {
		return this.societeId;
	}

	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}

	public ActionBean getAction() {
		return this.action;
	}

	public void setAction(ActionBean action) {
		this.action = action;
	}

	public OrganismeBean getOragnisme() {
		return this.oragnisme;
	}

	public void setOragnisme(OrganismeBean oragnisme) {
		this.oragnisme = oragnisme;
	}

	public UserBean getUser() {
		return this.user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public List<Object> getAuditEntites() {
		return this.auditEntites;
	}

	public void setAuditEntites(List<Object> auditEntites) {
		this.auditEntites = auditEntites;
	}
}