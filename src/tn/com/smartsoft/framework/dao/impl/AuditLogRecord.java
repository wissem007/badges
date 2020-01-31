package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.util.Date;

public class AuditLogRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				entityName;
	private String				message;
	private String				entityAttribute;
	private String				updatedBy;
	private Date				updatedDate;
	private String				newValue;
	private String				oldValue;
	private String				entityId;
	private Object				entity;
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEntityAttribute() {
		return entityAttribute;
	}
	public void setEntityAttribute(String entityAttribute) {
		this.entityAttribute = entityAttribute;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public Object getEntity() {
		return entity;
	}
	public void setEntity(Object entity) {
		this.entity = entity;
	}
	public String toString() {
		return "AuditLogRecord [entityName=" + entityName + ", message=" + message + ", entityAttribute=" + entityAttribute + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", newValue=" + newValue + ", oldValue=" + oldValue
				+ ", entityId=" + entityId + ", entity=" + entity + "]";
	}
}
