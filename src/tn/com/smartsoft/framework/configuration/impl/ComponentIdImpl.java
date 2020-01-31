package tn.com.smartsoft.framework.configuration.impl;

import java.io.Serializable;

import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBeanId;

public class ComponentIdImpl implements Serializable, ComponentId {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SujetBeanId sujetId;
	private String id;

	public ComponentIdImpl() {
		super();
	}

	public ComponentIdImpl(ComponentId componentBeanId, String id) {
		this(componentBeanId.getSujetId(), id);
	}

	public ComponentIdImpl(String module, String subModule, String sujet, String id) {
		this(getSujetBeanId(sujet, subModule, module), id);
	}

	public ComponentIdImpl(ActionBeanId actionBean) {
		this(getSujetBeanId(actionBean.getSujetId(), actionBean.getSubModuleId(), actionBean.getModuleId()), actionBean.getActionId());
	}

	public ComponentIdImpl(SujetBeanId sujetId, String id) {
		super();
		this.sujetId = sujetId;
		this.id = id;
	}

	public void setModule(String module) {
		getSujetId().setModuleId(module);
	}

	public void setSubModule(String subModule) {
		getSujetId().setSubModuleId(subModule);
	}

	public void setSujet(String sujet) {
		getSujetId().setSujetId(sujet);
	}

	public void setId(String id) {
		this.id = id;
	}

	public SujetBeanId getSujetId() {
		if (sujetId == null)
			sujetId = new SujetBeanId();
		return sujetId;
	}

	public String getId() {
		return id;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sujetId == null) ? 0 : sujetId.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComponentIdImpl other = (ComponentIdImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sujetId == null) {
			if (other.sujetId != null)
				return false;
		} else if (!sujetId.equals(other.sujetId))
			return false;
		return true;
	}

	public ViewBeanId getViewBeanId() {
		ViewBeanId viewBeanId = new ViewBeanId();
		viewBeanId.setModuleId(sujetId.getModuleId());
		viewBeanId.setSubModuleId(sujetId.getSubModuleId());
		viewBeanId.setSujetId(sujetId.getSujetId());
		viewBeanId.setViewId(getId());
		return viewBeanId;
	}

	public EntiteBeanId getEntiteBeanId() {
		EntiteBeanId entiteBeanId = new EntiteBeanId();
		entiteBeanId.setModuleId(sujetId.getModuleId());
		entiteBeanId.setSubModuleId(sujetId.getSubModuleId());
		entiteBeanId.setSujetId(sujetId.getSujetId());
		entiteBeanId.setEntiteId(getId());
		return entiteBeanId;
	}

	public ActionBeanId getActionBeanId() {
		ActionBeanId actionBeanId = new ActionBeanId();
		actionBeanId.setModuleId(sujetId.getModuleId());
		actionBeanId.setSubModuleId(sujetId.getSubModuleId());
		actionBeanId.setSujetId(sujetId.getSujetId());
		actionBeanId.setActionId(getId());
		return actionBeanId;
	}

	public static SujetBeanId getSujetBeanId(String sujetId, String subModuleId, String moduleId) {
		SujetBeanId sujetBeanId = new SujetBeanId();
		sujetBeanId.setSujetId(sujetId);
		sujetBeanId.setSubModuleId(subModuleId);
		sujetBeanId.setModuleId(moduleId);
		return sujetBeanId;
	}

	public String toString() {
		return getSujetId().toString() + " ,id :" + id;
	}

}
