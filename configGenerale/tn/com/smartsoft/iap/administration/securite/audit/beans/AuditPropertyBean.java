package tn.com.smartsoft.iap.administration.securite.audit.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import java.io.Serializable;

public class AuditPropertyBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String entiteId;
	private Long auditEntiteId;
	private Long auditActionId;
	private String propertyName;
	private String oldValue;
	private String newValue;
	private PropertyBean property;

	public AuditPropertyBean() {
		super();
	}

	public Serializable getId() {
		return new AuditPropertyBeanId(this);
	}

	public void setId(AuditPropertyBeanId id) {
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

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public PropertyBean getProperty() {
		return this.property;
	}

	public void setProperty(PropertyBean property) {
		this.property = property;
	}
}