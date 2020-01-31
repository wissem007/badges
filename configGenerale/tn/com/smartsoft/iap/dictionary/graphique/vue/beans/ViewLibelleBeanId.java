package tn.com.smartsoft.iap.dictionary.graphique.vue.beans;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.ValueUtils;

public class ViewLibelleBeanId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String viewLibellesId;
	private String viewId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;

	public ViewLibelleBeanId() {
		super();
	}

	public ViewLibelleBeanId(ViewLibelleBean viewLibelleBean) {
		super();
		this.setViewLibellesId(viewLibelleBean.getViewLibellesId());
		this.setViewId(viewLibelleBean.getViewId());
		this.setSujetId(viewLibelleBean.getSujetId());
		this.setSubModuleId(viewLibelleBean.getSubModuleId());
		this.setModuleId(viewLibelleBean.getModuleId());
	}

	public void copyValue(ViewLibelleBean objectValue) {
		objectValue.setViewLibellesId(this.getViewLibellesId());
		objectValue.setViewId(this.getViewId());
		objectValue.setSujetId(this.getSujetId());
		objectValue.setSubModuleId(this.getSubModuleId());
		objectValue.setModuleId(this.getModuleId());

	}

	public String getViewLibellesId() {
		return this.viewLibellesId;
	}

	public void setViewLibellesId(String viewLibellesId) {
		this.viewLibellesId = viewLibellesId;
	}

	public String getViewId() {
		return this.viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
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
		result = 37 * result + (this.getViewLibellesId() == null ? 0 : this.getViewLibellesId().hashCode());
		result = 37 * result + (this.getViewId() == null ? 0 : this.getViewId().hashCode());
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
		if (!(other instanceof ViewLibelleBeanId))
			return false;
		ViewLibelleBeanId castOther = (ViewLibelleBeanId) other;
		boolean result = true;
		result = result && ValueUtils.equals(this.getViewLibellesId(), castOther.getViewLibellesId());
		result = result && ValueUtils.equals(this.getViewId(), castOther.getViewId());
		result = result && ValueUtils.equals(this.getSujetId(), castOther.getSujetId());
		result = result && ValueUtils.equals(this.getSubModuleId(), castOther.getSubModuleId());
		result = result && ValueUtils.equals(this.getModuleId(), castOther.getModuleId());
		return result;
	}
}