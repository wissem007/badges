package tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;

public class SujetBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private String libelle;
	private Long rang;
	private String help;

	private SubModuleBean parentSubModule;

	private ActionBean actionBean;

	private List<DataBusinessBean> listActions;

	public SujetBean() {
		super();
	}

	public Serializable getId() {
		return new SujetBeanId(this);
	}

	public void setId(SujetBeanId id) {
		id.copyValue(this);
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

	public Long getRang() {
		return this.rang;
	}

	public SubModuleBean getParentSubModule() {
		return parentSubModule;
	}

	public void setParentSubModule(SubModuleBean parentSubModule) {
		this.parentSubModule = parentSubModule;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public List<DataBusinessBean> getListActions() {
		if (listActions == null || listActions.size() == 0) {
			listActions = new ArrayList<DataBusinessBean>();
			listActions.add(new ActionBean());
		}
		return listActions;
	}

	public void setListActions(List<DataBusinessBean> listActions) {
		this.listActions = listActions;
	}

	public ActionBean getActionBean() {
		return actionBean;
	}

	public void setActionBean(ActionBean actionBean) {
		this.actionBean = actionBean;
	}

}