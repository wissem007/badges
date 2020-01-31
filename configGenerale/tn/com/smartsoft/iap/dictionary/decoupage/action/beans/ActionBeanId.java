package tn.com.smartsoft.iap.dictionary.decoupage.action.beans;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class ActionBeanId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	
	public ActionBeanId() {
		super();
	}
	
	public ActionBeanId(String actionId, String sujetId, String subModuleId, String moduleId) {
		super();
		this.actionId = actionId;
		this.sujetId = sujetId;
		this.subModuleId = subModuleId;
		this.moduleId = moduleId;
	}
	
	public ActionBeanId(ActionBean actionBean) {
		super();
		this.setActionId(actionBean.getActionId());
		this.setSujetId(actionBean.getSujetId());
		this.setSubModuleId(actionBean.getSubModuleId());
		this.setModuleId(actionBean.getModuleId());
	}
	
	public void copyValue(ActionBean objectValue) {
		objectValue.setActionId(this.getActionId());
		objectValue.setSujetId(this.getSujetId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());
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
	
	public boolean is(String actionId) {
		return StringUtils.equalsIgnoreCase(this.getActionId(), actionId);
	}
	
	public int hashCode() {
		int result = 17;
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
		if (!(other instanceof ActionBeanId))
			return false;
		ActionBeanId castOther = (ActionBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getActionId(), castOther.getActionId());
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
	
	public String toString() {
		return "ActionBeanId [actionId=" + actionId + ", sujetId=" + sujetId + ", subModuleId=" + subModuleId + ", moduleId=" + moduleId + "]";
	}
}