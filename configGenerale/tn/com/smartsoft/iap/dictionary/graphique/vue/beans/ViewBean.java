package tn.com.smartsoft.iap.dictionary.graphique.vue.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;

public class ViewBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String viewId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private String help;
	private SujetBean parentSujet;
	private Map<String, ViewLibelleBean> libelles = new HashMap<String, ViewLibelleBean>();
	private Map<String, ViewActionBean> actions = new HashMap<String, ViewActionBean>();

	private List listLibelles = new ArrayList();
	private List listActions = new ArrayList();

	public ViewBean() {
		super();
	}

	public ViewLibelleBean getViewLibelle(String viewLibellesId) {
		return (ViewLibelleBean) libelles.get(viewLibellesId);
	}

	public ViewActionBean getViewAction(String viewActionId) {
		return (ViewActionBean) actions.get(viewActionId);
	}

	public Serializable getId() {
		return new ViewBeanId(this);
	}

	public void setId(ViewBeanId id) {
		id.copyValue(this);
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

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public SujetBean getParentSujet() {
		return this.parentSujet;
	}

	public void setParentSujet(SujetBean parentSujet) {
		this.parentSujet = parentSujet;
	}

	public Map<String, ViewLibelleBean> getLibelles() {
		return this.libelles;
	}

	public void setLibelles(Map<String, ViewLibelleBean> libelles) {
		this.libelles = libelles;
	}

	public Map<String, ViewActionBean> getActions() {
		return this.actions;
	}

	public void setActions(Map<String, ViewActionBean> actions) {
		this.actions = actions;
	}

	public List getListLibelles() {
		return listLibelles;
	}

	public void setListLibelles(List listLibelles) {
		this.listLibelles = listLibelles;
	}

	public List getListActions() {
		return listActions;
	}

	public void setListActions(List listActions) {
		this.listActions = listActions;
	}

}