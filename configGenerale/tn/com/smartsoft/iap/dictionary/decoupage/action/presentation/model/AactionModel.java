package tn.com.smartsoft.iap.dictionary.decoupage.action.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class AactionModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;

	private List listModule;
	private List listSubModule;
	private List listSujet;

	private List listType;
	private List listActionTemplate;
	private List listActionRoles;

	public List getListType() {
		return listType;
	}

	public void setListType(List listType) {
		this.listType = listType;
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

	public List getListSujet() {
		return listSujet;
	}

	public void setListSujet(List listSujet) {
		this.listSujet = listSujet;
	}

	public List getListActionRoles() {
		return listActionRoles;
	}

	public void setListActionRoles(List listActionRoles) {
		this.listActionRoles = listActionRoles;
	}
	
	

}
