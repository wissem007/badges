package tn.com.smartsoft.iap.dictionary.graphique.toolAction.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.toolAction.beans.ToolActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.toolAction.presentation.model.ToolActionModel;

public class ToolActionControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness subModuleBusiness;
	protected GenericEntiteBusiness sujetBusiness;
	protected GenericEntiteBusiness actionBusiness;

	public void setActionBusiness(GenericEntiteBusiness actionBusiness) {
		this.actionBusiness = actionBusiness;
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

	public void onInitListToolAction(ListenerContext context) throws FunctionalException {
		ToolActionModel entiteModel = (ToolActionModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);

	}

	public void getSubModule(ListenerContext context) throws FunctionalException {
		ToolActionModel entiteModel = (ToolActionModel) context.userAction().getDataObject();
		SubModuleBean subModuleBean = new SubModuleBean();
		String modId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			modId = ((ToolActionBean) entiteModel.getDetailBean()).getModuleId();
		} else {
			modId = ((ToolActionBean) entiteModel.getSearcheBean()).getModuleId();
		}
		subModuleBean.setModuleId(modId);
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);
		JsonItemResponseModel.reponseEmpty(context); 

	}

	public void getSujet(ListenerContext context) throws FunctionalException {
		ToolActionModel entiteModel = (ToolActionModel) context.userAction().getDataObject();
		SujetBean sujetBean = new SujetBean();
		String moduleId;
		String smodId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			moduleId = ((ToolActionBean) entiteModel.getDetailBean()).getModuleId();
			smodId = ((ToolActionBean) entiteModel.getDetailBean()).getSubModuleId();
		} else {
			moduleId = ((ToolActionBean) entiteModel.getSearcheBean()).getModuleId();
			smodId = ((ToolActionBean) entiteModel.getSearcheBean()).getSubModuleId();
		}
		sujetBean.setModuleId(moduleId);
		sujetBean.setSubModuleId(smodId);
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);
		JsonItemResponseModel.reponseEmpty(context); 

	}

	public void getAction(ListenerContext context) throws FunctionalException {
		ToolActionModel entiteModel = (ToolActionModel) context.userAction().getDataObject();
		ActionBean actionBean = new ActionBean();
		String moduleId;
		String subModuleId;
		String sujetId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			moduleId = ((ToolActionBean) entiteModel.getDetailBean()).getModuleId();
			subModuleId = ((ToolActionBean) entiteModel.getDetailBean()).getSubModuleId();
			sujetId = ((ToolActionBean) entiteModel.getDetailBean()).getSujetId();
		} else {
			moduleId = ((ToolActionBean) entiteModel.getSearcheBean()).getModuleId();
			subModuleId = ((ToolActionBean) entiteModel.getSearcheBean()).getSubModuleId();
			sujetId = ((ToolActionBean) entiteModel.getSearcheBean()).getSujetId();
		}

		actionBean.setModuleId(moduleId);
		actionBean.setSubModuleId(subModuleId);
		actionBean.setSujetId(sujetId);
		List listAction = actionBusiness.getByCriteria(actionBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListAction(listAction);
		JsonItemResponseModel.reponseEmpty(context); 

	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		ToolActionModel entiteModel = (ToolActionModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		ToolActionBean rowBean = (ToolActionBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (ToolActionBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);

		getSubModule(context);
		getSujet(context);
		getAction(context);

		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

}
