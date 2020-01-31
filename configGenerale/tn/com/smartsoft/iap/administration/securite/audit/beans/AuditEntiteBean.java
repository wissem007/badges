package tn.com.smartsoft.iap.administration.securite.audit.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.actionType.beans.ActionTypeBean;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings({ "rawtypes" })
public class AuditEntiteBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long auditEntiteId;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String entiteId;
	private Long auditActionId;
	private Long actionTypeId;
	private String objectIdValue;
	private String parentEntiteId;
	private Long parentAuditEntiteId;
	private EntiteBean entite;
	private ActionTypeBean actionType;
	private List detailAuditEntite = new ArrayList();
	private List auditPropertys = new ArrayList();

	public AuditEntiteBean() {
		super();
	}

	public AuditEntiteBean getAuditEntite(int index) {
		return (AuditEntiteBean) detailAuditEntite.get(index);
	}

	public AuditPropertyBean getAuditProperty(int index) {
		return (AuditPropertyBean) auditPropertys.get(index);
	}

	public Serializable getId() {
		return new AuditEntiteBeanId(this);
	}

	public void setId(AuditEntiteBeanId id) {
		id.copyValue(this);
	}

	public Long getAuditEntiteId() {
		return this.auditEntiteId;
	}

	public void setAuditEntiteId(Long auditEntiteId) {
		this.auditEntiteId = auditEntiteId;
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

	public String getEntiteId() {
		return this.entiteId;
	}

	public void setEntiteId(String entiteId) {
		this.entiteId = entiteId;
	}

	public Long getAuditActionId() {
		return this.auditActionId;
	}

	public void setAuditActionId(Long auditActionId) {
		this.auditActionId = auditActionId;
	}

	public Long getActionTypeId() {
		return this.actionTypeId;
	}

	public void setActionTypeId(Long actionTypeId) {
		this.actionTypeId = actionTypeId;
	}

	public String getObjectIdValue() {
		return this.objectIdValue;
	}

	public void setObjectIdValue(String objectIdValue) {
		this.objectIdValue = objectIdValue;
	}

	public String getParentEntiteId() {
		return this.parentEntiteId;
	}

	public void setParentEntiteId(String parentEntiteId) {
		this.parentEntiteId = parentEntiteId;
	}

	public Long getParentAuditEntiteId() {
		return this.parentAuditEntiteId;
	}

	public void setParentAuditEntiteId(Long parentAuditEntiteId) {
		this.parentAuditEntiteId = parentAuditEntiteId;
	}

	public EntiteBean getEntite() {
		return this.entite;
	}

	public void setEntite(EntiteBean entite) {
		this.entite = entite;
	}

	public ActionTypeBean getActionType() {
		return this.actionType;
	}

	public void setActionType(ActionTypeBean actionType) {
		this.actionType = actionType;
	}

	public List getDetailAuditEntite() {
		return this.detailAuditEntite;
	}

	public void setDetailAuditEntite(List detailAuditEntite) {
		this.detailAuditEntite = detailAuditEntite;
	}

	public List getAuditPropertys() {
		return this.auditPropertys;
	}

	public void setAuditPropertys(List auditPropertys) {
		this.auditPropertys = auditPropertys;
	}
}