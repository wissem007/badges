package tn.com.smartsoft.iap.dictionary.graphique.vue.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.presentation.model.VueModel;

public class VueControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness subModuleBusiness;
	protected GenericEntiteBusiness sujetBusiness;
	protected GenericEntiteBusiness actionTemplateBusiness;

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setSubModuleBusiness(GenericEntiteBusiness subModuleBusiness) {
		this.subModuleBusiness = subModuleBusiness;
	}

	public void setSujetBusiness(GenericEntiteBusiness sujetBusiness) {
		this.sujetBusiness = sujetBusiness;
	}

	public void setActionTemplateBusiness(GenericEntiteBusiness actionTemplateBusiness) {
		this.actionTemplateBusiness = actionTemplateBusiness;
	}

	public void onInitListVue(ListenerContext context) throws FunctionalException {
		VueModel vueModel = (VueModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		vueModel.setListModule(listModule);

		List listActionTemplate = actionTemplateBusiness.getByCriteria(new ActionTemplateBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		vueModel.setListActionTemplate(listActionTemplate);

	}

	public void getSubModule(ListenerContext context) throws FunctionalException {
		VueModel entiteModel = (VueModel) context.userAction().getDataObject();
		SubModuleBean subModuleBean = new SubModuleBean();
		String modId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			modId = ((ViewBean) entiteModel.getDetailBean()).getModuleId();
		} else {
			modId = ((ViewBean) entiteModel.getSearcheBean()).getModuleId();
		}
		subModuleBean.setModuleId(modId);
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);
		JsonItemResponseModel.reponseEmpty(context); 
	}

	public void getSujet(ListenerContext context) throws FunctionalException {
		VueModel entiteModel = (VueModel) context.userAction().getDataObject();
		SujetBean sujetBean = new SujetBean();
		String moduleId;
		String smodId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			moduleId = ((ViewBean) entiteModel.getDetailBean()).getModuleId();
			smodId = ((ViewBean) entiteModel.getDetailBean()).getSubModuleId();
		} else {
			moduleId = ((ViewBean) entiteModel.getSearcheBean()).getModuleId();
			smodId = ((ViewBean) entiteModel.getSearcheBean()).getSubModuleId();
		}
		sujetBean.setModuleId(moduleId);
		sujetBean.setSubModuleId(smodId);
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);
		JsonItemResponseModel.reponseEmpty(context); 
	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		VueModel entiteModel = (VueModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		ViewBean rowBean = (ViewBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (ViewBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);

		SubModuleBean subModuleBean = new SubModuleBean();
		subModuleBean.setModuleId(rowBean.getModuleId());
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);

		SujetBean sujetBean = new SujetBean();
		sujetBean.setSubModuleId(rowBean.getSubModuleId());
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);

		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

}
