package tn.com.smartsoft.iap.administration.securite.audit.beans;

import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class AuditActionBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long auditActionId;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;

	public AuditActionBeanId() {
		super();
	}

	public AuditActionBeanId(AuditActionBean auditActionBean) {
		super();
		this.setAuditActionId(auditActionBean.getAuditActionId());
		this.setActionId(auditActionBean.getActionId());
		this.setSujetId(auditActionBean.getSujetId());
		this.setSubModuleId(auditActionBean.getSubModuleId());
		this.setModuleId(auditActionBean.getModuleId());
	}

	public void copyValue(AuditActionBean objectValue) {
		objectValue.setAuditActionId(this.getAuditActionId());
		objectValue.setActionId(this.getActionId());
		objectValue.setSujetId(this.getSujetId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());

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

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getAuditActionId() == null ? 0 : this.getAuditActionId().hashCode());
		result = 37 * result + (this.getActionId() == null ? 0 : this.getActionId().hashCode());
		result = 37 * result + (this.getSujetId() == null ? 0 : this.getSujetId().hashCode());
		result = 37 * result + (this.getSubModuleId() == null ? 0 : this.getSubModuleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AuditActionBeanId))
			return false;
		AuditActionBeanId castOther = (AuditActionBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getAuditActionId(), castOther.getAuditActionId());
		result = result && ValueUtils.equals(this.getActionId(), castOther.getActionId());
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}