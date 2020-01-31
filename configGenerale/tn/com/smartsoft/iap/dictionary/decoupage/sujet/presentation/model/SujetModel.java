package tn.com.smartsoft.iap.dictionary.decoupage.sujet.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class SujetModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;

	private List listModule;
	private List listSubModule;

	private List listActionType;
	private List listActionTemplate;
	private List listActionRoles;

	public List getListActionType() {
		return listActionType;
	}

	public void setListActionType(List listActionType) {
		this.listActionType = listActionType;
	}

	public List getListActionTemplate() {
		return listActionTemplate;
	}

	public void setListActionTemplate(List listActionTemplate) {
		this.listActionTemplate = listActionTemplate;
	}

	public List getListModule() {
		return listModule;
	}

	public void setListModule(List listModule) {
		this.listModule = listModule;
	}

	public List getListSubModule() {
		return listSubModule;
	}

	public void setListSubModule(List listSubModule) {
		this.listSubModule = listSubModule;
	}

	public List getListActionRoles() {
		return listActionRoles;
	}

	public void setListActionRoles(List listActionRoles) {
		this.listActionRoles = listActionRoles;
	}

}
