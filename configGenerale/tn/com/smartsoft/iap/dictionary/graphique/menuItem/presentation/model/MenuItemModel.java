package tn.com.smartsoft.iap.dictionary.graphique.menuItem.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class MenuItemModel extends GenericEntiteModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List listModules;
	private List listParents;
	private List listSubModules;
	private List listSujets;
	private List listActions;

	public List getListSubModules() {
		return listSubModules;
	}

	public void setListSubModules(List listSubModules) {
		this.listSubModules = listSubModules;
	}

	public List getListSujets() {
		return listSujets;
	}

	public void setListSujets(List listSujets) {
		this.listSujets = listSujets;
	}

	public List getListActions() {
		return listActions;
	}

	public void setListActions(List listActions) {
		this.listActions = listActions;
	}

	public List getListParents() {
		return listParents;
	}

	public void setListParents(List listParents) {
		this.listParents = listParents;
	}

	public List getListModules() {
		return listModules;
	}

	public void setListModules(List listModules) {
		this.listModules = listModules;
	}

}