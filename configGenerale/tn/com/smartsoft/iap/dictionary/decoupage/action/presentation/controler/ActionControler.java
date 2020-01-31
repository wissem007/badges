package tn.com.smartsoft.iap.dictionary.decoupage.action.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.presentation.model.AactionModel;
import tn.com.smartsoft.iap.dictionary.decoupage.actionType.beans.ActionTypeBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.business.GroupedRoleBusiness;

public class ActionControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness subModuleBusiness;
	protected GenericEntiteBusiness sujetBusiness;
	protected GenericEntiteBusiness actionTemplateBusiness;
	protected GenericEntiteBusiness actionTypeBusiness;
	protected GroupedRoleBusiness groupedRoleBusiness;

	public void setActionTemplateBusiness(GenericEntiteBusiness actionTemplateBusiness) {
		this.actionTemplateBusiness = actionTemplateBusiness;
	}

	public void setActionTypeBusiness(GenericEntiteBusiness actionTypeBusiness) {
		this.actionTypeBusiness = actionTypeBusiness;
	}

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setSubModuleBusiness(GenericEntiteBusiness subModuleBusiness) {
		this.subModuleBusiness = subModuleBusiness;
	}

	public void setSujetBusiness(GenericEntiteBusiness sujetBusiness) {
		this.sujetBusiness = sujetBusiness;
	}

	public void setGroupedRoleBusiness(GroupedRoleBusiness groupedRoleBusiness) {
		this.groupedRoleBusiness = groupedRoleBusiness;
	}

	public void onInitListAction(ListenerContext context) throws FunctionalException {
		AactionModel entiteModel = (AactionModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);

		List listActionTemplate = actionTemplateBusiness.getByCriteria(new ActionTemplateBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListActionTemplate(listActionTemplate);

		List listActionType = actionTypeBusiness.getByCriteria(new ActionTypeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListType(listActionType);

	}

	public void getSubModule(ListenerContext context) throws FunctionalException {
		AactionModel entiteModel = (AactionModel) context.userAction().getDataObject();
		SubModuleBean subModuleBean = new SubModuleBean();
		ActionRoleBean actionRoleBean = new ActionRoleBean();
		String modId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			modId = ((ActionBean) entiteModel.getDetailBean()).getModuleId();
		} else {
			modId = ((ActionBean) entiteModel.getSearcheBean()).getModuleId();
		}
		subModuleBean.setModuleId(modId);
		actionRoleBean.setModuleId(modId);
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		List listActionRoles = groupedRoleBusiness.getActionRoles(actionRoleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());

		entiteModel.setListSubModule(listSubModule);
		entiteModel.setListActionRoles(listActionRoles);
		JsonItemResponseModel.reponseEmpty(context);
	}

	public void getSujet(ListenerContext context) throws FunctionalException {
		AactionModel entiteModel = (AactionModel) context.userAction().getDataObject();
		SujetBean sujetBean = new SujetBean();
		String moduleId;
		String smodId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			moduleId = ((ActionBean) entiteModel.getDetailBean()).getModuleId();
			smodId = ((ActionBean) entiteModel.getDetailBean()).getSubModuleId();
		} else {
			moduleId = ((ActionBean) entiteModel.getSearcheBean()).getModuleId();
			smodId = ((ActionBean) entiteModel.getSearcheBean()).getSubModuleId();
		}
		sujetBean.setModuleId(moduleId);
		sujetBean.setSubModuleId(smodId);
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);

	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		AactionModel entiteModel = (AactionModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		ActionBean rowBean = (ActionBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (ActionBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);

		SubModuleBean subModuleBean = new SubModuleBean();
		subModuleBean.setModuleId(rowBean.getModuleId());
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);

		SujetBean sujetBean = new SujetBean();
		sujetBean.setSubModuleId(rowBean.getSubModuleId());
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);
		
		ActionRoleBean actionRoleBean = new ActionRoleBean();
		actionRoleBean.setModuleId(rowBean.getModuleId());
		List listActionRoles = groupedRoleBusiness.getActionRoles(actionRoleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListActionRoles(listActionRoles);

		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

}
