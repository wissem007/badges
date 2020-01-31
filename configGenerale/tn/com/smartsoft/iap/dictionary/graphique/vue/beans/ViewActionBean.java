package tn.com.smartsoft.iap.dictionary.graphique.vue.beans;

import java.io.Serializable;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;

public class ViewActionBean extends DataBusinessBean {
	private static final long serialVersionUID = 1L;
	private String viewActionId;
	private String viewId;
	private String sujetId;
	private String subModuleId;
	private String moduleId;
	private Long actionTemplateId;
	private String roleId;
	private String help;
	private ActionRoleBean role;
	private ActionTemplateBean template;

	public ViewActionBean() {
		super();
	}

	public Serializable getId() {
		return new ViewActionBeanId(this);
	}

	public void setId(ViewActionBeanId id) {
		id.copyValue(this);
	}

	public String getViewActionId() {
		return this.viewActionId;
	}

	public void setViewActionId(String viewActionId) {
		this.viewActionId = viewActionId;
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

	public Long getActionTemplateId() {
		return this.actionTemplateId;
	}

	public void setActionTemplateId(Long actionTemplateId) {
		this.actionTemplateId = actionTemplateId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public ActionRoleBean getRole() {
		return this.role;
	}

	public void setRole(ActionRoleBean role) {
		this.role = role;
	}

	public ActionTemplateBean getTemplate() {
		return this.template;
	}

	public void setTemplate(ActionTemplateBean template) {
		this.template = template;
	}
}