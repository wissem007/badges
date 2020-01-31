package tn.com.smartsoft.iap.dictionary.graphique.menuItem.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.beans.SubModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.presentation.model.MenuItemModel;

public class MenuItemControler extends GenericEntiteControler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GenericEntiteBusiness moduleBusiness;
	private GenericEntiteBusiness subModuleBusiness;
	private GenericEntiteBusiness sujetBusiness;
	private GenericEntiteBusiness actionBusiness;

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setSubModuleBusiness(GenericEntiteBusiness subModuleBusiness) {
		this.subModuleBusiness = subModuleBusiness;
	}

	public void setSujetBusiness(GenericEntiteBusiness sujetBusiness) {
		this.sujetBusiness = sujetBusiness;
	}

	public void setActionBusiness(GenericEntiteBusiness actionBusiness) {
		this.actionBusiness = actionBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		MenuItemModel entiteModel = (MenuItemModel) context.userAction().getDataObject();
		List listModules = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModules(listModules);
	}

	public void onInitListParentByModule(ListenerContext context) throws FunctionalException {
		MenuItemModel entiteModel = (MenuItemModel) context.userAction().getDataObject();
		// String moduleId =
		// context.requestParameters().getParameter("moduleId");
		String moduleId = ((MenuItemBean) entiteModel.getDetailBean()).getModuleId();
		if (moduleId != null && !moduleId.equalsIgnoreCase("")) {

			MenuItemBean bean = new MenuItemBean();
			bean.setModuleId(moduleId);

			SubModuleBean subModuleBean = new SubModuleBean();
			subModuleBean.setModuleId(moduleId);

			List listParents = entiteBusiness.getByCriteria(bean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			List listSubModules = subModuleBusiness.getByCriteria(bean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());

			entiteModel.setListParents(listParents);
			entiteModel.setListSubModules(listSubModules);
		}
	}

	public void onInitListSujetBySubModule(ListenerContext context) throws FunctionalException {
		MenuItemModel entiteModel = (MenuItemModel) context.userAction().getDataObject();

		String moduleId = context.requestParameters().getParameter("moduleId");
		String subModuleId = context.requestParameters().getParameter("subModuleId");

		if (moduleId != null && !moduleId.equalsIgnoreCase("") && subModuleId != null && !subModuleId.equalsIgnoreCase("")) {

			SujetBean sujetBean = new SujetBean();
			sujetBean.setModuleId(moduleId);
			sujetBean.setSubModuleId(subModuleId);

			List listSujets = sujetBusiness.getByCriteria(sujetBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListSujets(listSujets);
		}
	}

	public void onInitListActionBySujet(ListenerContext context) throws FunctionalException {
		MenuItemModel entiteModel = (MenuItemModel) context.userAction().getDataObject();

		String moduleId = context.requestParameters().getParameter("moduleId");
		String subModuleId = context.requestParameters().getParameter("subModuleId");
		String sujetId = context.requestParameters().getParameter("sujetId");
		if (moduleId != null && !moduleId.equalsIgnoreCase(""))
			if (subModuleId != null && !subModuleId.equalsIgnoreCase(""))
				if (sujetId != null && !sujetId.equalsIgnoreCase("")) {

					ActionBean bean = new ActionBean();
					bean.setModuleId(moduleId);
					bean.setSubModuleId(subModuleId);
					bean.setSujetId(sujetId);

					List listActions = actionBusiness.getByCriteria(bean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());

					entiteModel.setListActions(listActions);
				}
	}

	public void doIndexBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData("data", context.userAction().currentWindow().getFieldValues());
		jsonItemResponseModel.addData("index", entiteModel.getBeanIndex());
		jsonItemResponseModel.addData("size", entiteModel.getListBean().size());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		this.onInitListParentByModule(context);
		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

}
