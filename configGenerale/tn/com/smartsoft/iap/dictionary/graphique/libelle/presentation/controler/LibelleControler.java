package tn.com.smartsoft.iap.dictionary.graphique.libelle.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.libelle.beans.LibelleBean;
import tn.com.smartsoft.iap.dictionary.graphique.libelle.presentation.model.LibelleModel;

public class LibelleControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness subModuleBusiness;
	protected GenericEntiteBusiness sujetBusiness;

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setSubModuleBusiness(GenericEntiteBusiness subModuleBusiness) {
		this.subModuleBusiness = subModuleBusiness;
	}

	public void setSujetBusiness(GenericEntiteBusiness sujetBusiness) {
		this.sujetBusiness = sujetBusiness;
	}

	public void onInitListLibelle(ListenerContext context) throws FunctionalException {
		LibelleModel entiteModel = (LibelleModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);

	}

	public void getSubModule(ListenerContext context) throws FunctionalException {
		LibelleModel entiteModel = (LibelleModel) context.userAction().getDataObject();
		SubModuleBean subModuleBean = new SubModuleBean();
		String modId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			modId = ((LibelleBean) entiteModel.getDetailBean()).getModuleId();
		} else {
			modId = ((LibelleBean) entiteModel.getSearcheBean()).getModuleId();
		}
		subModuleBean.setModuleId(modId);
		List listSubModule = subModuleBusiness.getByCriteria(subModuleBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSubModule(listSubModule);

	}

	public void getSujet(ListenerContext context) throws FunctionalException {
		LibelleModel entiteModel = (LibelleModel) context.userAction().getDataObject();
		SujetBean sujetBean = new SujetBean();
		String smodId;
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("creer")) {
			smodId = ((LibelleBean) entiteModel.getDetailBean()).getSubModuleId();
		} else {
			smodId = ((LibelleBean) entiteModel.getSearcheBean()).getSubModuleId();
		}
		sujetBean.setSubModuleId(smodId);
		List listSujet = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSujet(listSujet);

	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		LibelleModel entiteModel = (LibelleModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		LibelleBean rowBean = (LibelleBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (LibelleBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
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
