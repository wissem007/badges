package tn.com.digivoip.framework.presentation.contoler;

import java.io.Serializable;
import java.util.List;
import tn.com.digivoip.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.report.ReportModel;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.response.xl.XlModel;
import tn.com.smartsoft.framework.presentation.view.response.xl.XlSheetContent;
import tn.com.smartsoft.framework.presentation.view.response.xl.XlTableContent;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;

public class GenericEntiteControler implements Serializable {
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	protected GenericEntiteControlerParams	controlerParams;
	protected GenericEntiteBusiness			entiteBusiness;
	
	public void setControlerParams(GenericEntiteControlerParams controlerParams) {
		this.controlerParams = controlerParams;
	}
	
	public void setEntiteBusiness(GenericEntiteBusiness entiteBusiness) {
		this.entiteBusiness = entiteBusiness;
	}
	
	public void onRendeListWindow(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.userAction().getWindow(controlerParams.getListWindowId()).findChild(controlerParams.getDisplayGridId());
		grid.setHidden(entiteModel.getListBean() == null || entiteModel.getListBean().size() == 0);
		grid.getStore().setAutoLoad(!(entiteModel.getListBean() == null || entiteModel.getListBean().size() == 0));
	}
	
	public void doFilter(ListenerContext context) throws FunctionalException {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			List<DataBusinessBean> list = entiteBusiness.getByCriteria(entiteModel.getSearcheBean(), context.userAction().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListBean(list);
			jsonItemResponseModel.addData("empty", list.isEmpty());
			if (list.isEmpty())
				throw new FunctionalException(controlerParams.getMessageEmptyRow());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		}
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
	
	public void doIndexBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.getListBean().set(entiteModel.getBeanIndex(), rowBean);
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
		entiteModel.getListBean().set(entiteModel.getBeanIndex(), rowBean);
		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
	
	public void doFirstBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		entiteModel.setBeanIndex(0);
		doIndexBean(context);
	}
	
	public void doPreviousBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		entiteModel.setBeanIndex(entiteModel.getBeanIndex() - 1);
		doIndexBean(context);
	}
	
	public void doNextBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		entiteModel.setBeanIndex(entiteModel.getBeanIndex() + 1);
		doIndexBean(context);
	}
	
	public void doLastBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		entiteModel.setBeanIndex(entiteModel.getListBean().size() - 1);
		doIndexBean(context);
	}
	
	public void doRetourList(ListenerContext context) {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		entiteModel.setDetailBean(null);
		context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
	}
	
	public void doHomePage(ListenerContext context) {
		context.userDesktop().userDesktopNavigation().goToDefaultUserAction(context.webContext());
	}
	
	public void doExporterPdf(ListenerContext context) {
		UIComponent component = (UIComponent) context.source();
		UIExtGrid grid = (UIExtGrid) component.getWindow().findChild(controlerParams.getDisplayGridId());
		context.webContext().response(ReportModel.PDF, grid.createExportPdfModel(context.webContext()));
	}
	
	public void doExporterXl(ListenerContext context) {
		UIComponent component = (UIComponent) context.source();
		UIExtGrid grid = (UIExtGrid) component.getWindow().findChild(controlerParams.getDisplayGridId());
		context.webContext().response(ReportModel.EXCEL, grid.createExportPdfModel(context.webContext()));
	}
	
	public void doExporterPXl(ListenerContext context) {
		UIComponent component = (UIComponent) context.source();
		UIExtGrid grid = (UIExtGrid) component.getWindow().findChild(controlerParams.getDisplayGridId());
		String libelle = (String) grid.getWindow().evalExpression(controlerParams.getListLibelleId());
		XlModel xlModel = new XlModel(libelle, context.userAction());
		XlSheetContent sheetContent = new XlSheetContent(libelle, 0);
		sheetContent.addContent(new XlTableContent(grid, 0));
		xlModel.addSheet(sheetContent);
		context.webContext().response("pxls", xlModel);
	}
	
	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			entiteBusiness.create(entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	
	public void doValiderUpdate(ListenerContext context) throws FunctionalException {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			entiteBusiness.update(entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteBusiness.getDaoSession().clear();
			DataBusinessBean rowBean = entiteBusiness.getById(entiteModel.getDetailBean(), context.userAction().getActionBeanId(), context.userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), rowBean);
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	
	public void doValiderDelete(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			entiteBusiness.delete(entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.getListBean().remove(entiteModel.getBeanIndex());
			entiteModel.setDetailBean(null);
			entiteModel.setBeanIndex(0);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageDeleteValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	
	public void doRefreshDetail(ListenerContext context) {
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
}
