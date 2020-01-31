package tn.com.smartsoft.iap.dictionary.graphique.toolAction.beans;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import java.io.Serializable;

public class ToolActionBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private Long toolActionId;
	private String actionId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private Long rang;
	private ActionBean action;
	private Boolean toolBar;

	public ToolActionBean() {
		super();
	}

	public Serializable getId() {
		return this.getToolActionId();
	}

	public void setId(Long id) {
		this.setToolActionId(id);
	}

	public Long getToolActionId() {
		return this.toolActionId;
	}

	public void setToolActionId(Long toolActionId) {
		this.toolActionId = toolActionId;
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

	public Long getRang() {
		return this.rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}

	public ActionBean getAction() {
		return this.action;
	}

	public void setAction(ActionBean action) {
		this.action = action;
	}


	public Boolean getToolBar() {
		return toolBar;
	}

	public void setToolBar(Boolean toolBar) {
		this.toolBar = toolBar;
	}
}