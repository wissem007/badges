package tn.com.smartsoft.iap.dictionary.graphique.toolAction.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class ToolActionModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;

	private List listModule;
	private List listSubModule;
	private List listSujet;
	private List listAction;


	public List getListAction() {
		return listAction;
	}

	public void setListAction(List listAction) {
		this.listAction = listAction;
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

}
