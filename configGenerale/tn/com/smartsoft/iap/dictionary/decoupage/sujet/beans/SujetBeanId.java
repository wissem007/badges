package tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.utils.ValueUtils;

public class SujetBeanId implements Serializable{
	
	private static final long	serialVersionUID	= 1L;
	private String				sujetId;
	private String				subModuleId;
	private String				moduleId;
	
	public SujetBeanId() {
		super();
	}
	public SujetBeanId(SujetBeanId sujetBeanId, String moduleId, String subModuleId, String sujetId) {
		super();
		this.setSujetId(StringUtils.isNotBlank(sujetId) ? sujetId : sujetBeanId.getSujetId());
		this.setSubModuleId(StringUtils.isNotBlank(subModuleId) ? subModuleId : sujetBeanId.getSubModuleId());
		this.setModuleId(StringUtils.isNotBlank(moduleId) ? moduleId : sujetBeanId.getModuleId());
	}
	public SujetBeanId(SujetBean sujetBean) {
		super();
		this.setSujetId(sujetBean.getSujetId());
		this.setSubModuleId(sujetBean.getSubModuleId());
		this.setModuleId(sujetBean.getModuleId());
	}
	public void copyValue(SujetBean objectValue) {
		objectValue.setSujetId(this.getSujetId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());
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
		result = 37 * result + (this.getSujetId() == null ? 0 : this.getSujetId().hashCode());
		result = 37 * result + (this.getSubModuleId() == null ? 0 : this.getSubModuleId().hashCode());
		result = 37 * result + (this.getModuleId() == null ? 0 : this.getModuleId().hashCode());
		return result;
	}
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof SujetBeanId)) return false;
		SujetBeanId castOther = (SujetBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
	public String toString() {
		return "SujetBeanId [sujetId=" + sujetId + ", subModuleId=" + subModuleId + ", moduleId=" + moduleId + "]";
	}
}