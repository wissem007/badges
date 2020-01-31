package tn.com.smartsoft.iap.administration.securite.audit.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class AuditPropertyBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String entiteId;
	private Long auditEntiteId;
	private Long auditActionId;
	private String propertyName;

	public AuditPropertyBeanId() {
		super();
	}

	public AuditPropertyBeanId(AuditPropertyBean auditPropertyBean) {
		super();
		this.setActionId(auditPropertyBean.getActionId());
		this.setSujetId(auditPropertyBean.getSujetId());
		this.setSubModuleId(auditPropertyBean.getSubModuleId());
		this.setModuleId(auditPropertyBean.getModuleId());
		this.setEntiteId(auditPropertyBean.getEntiteId());
		this.setAuditEntiteId(auditPropertyBean.getAuditEntiteId());
		this.setAuditActionId(auditPropertyBean.getAuditActionId());
		this.setPropertyName(auditPropertyBean.getPropertyName());
	}

	public void copyValue(AuditPropertyBean objectValue) {
		objectValue.setActionId(this.getActionId());
		objectValue.setSujetId(this.getSujetId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());
		objectValue.setEntiteId(this.getEntiteId());
		objectValue.setAuditEntiteId(this.getAuditEntiteId());
		objectValue.setAuditActionId(this.getAuditActionId());
		objectValue.setPropertyName(this.getPropertyName());

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

	public Long getAuditEntiteId() {
		return this.auditEntiteId;
	}

	public void setAuditEntiteId(Long auditEntiteId) {
		this.auditEntiteId = auditEntiteId;
	}

	public Long getAuditActionId() {
		return this.auditActionId;
	}

	public void setAuditActionId(Long auditActionId) {
		this.auditActionId = auditActionId;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getActionId() == null ? 0 : this.getActionId().hashCode());
		result = 37 * result + (this.getSujetId() == null ? 0 : this.getSujetId().hashCode());
		result = 37 * result + (this.getSubModuleId() == null ? 0 : this.getSubModuleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		result = 37 * result + (this.getEntiteId() == null ? 0 : this.getEntiteId().hashCode());
		result = 37 * result + (this.getAuditEntiteId() == null ? 0 : this.getAuditEntiteId().hashCode());
		result = 37 * result + (this.getAuditActionId() == null ? 0 : this.getAuditActionId().hashCode());
		result = 37 * result + (this.getPropertyName() == null ? 0 : this.getPropertyName().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AuditPropertyBeanId))
			return false;
		AuditPropertyBeanId castOther = (AuditPropertyBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getActionId(), castOther.getActionId());
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		result = result && ValueUtils.equals(this.getEntiteId(), castOther.getEntiteId());
		result = result && ValueUtils.equals(this.getAuditEntiteId(), castOther.getAuditEntiteId());
		result = result && ValueUtils.equals(this.getAuditActionId(), castOther.getAuditActionId());
		result = result && ValueUtils.equals(this.getPropertyName(), castOther.getPropertyName());
		return result;
	}
}