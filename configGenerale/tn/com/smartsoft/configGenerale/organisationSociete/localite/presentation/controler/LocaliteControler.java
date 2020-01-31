package tn.com.smartsoft.configGenerale.organisationSociete.localite.presentation.controler;

import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.NumberUtils;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.business.LocaliteBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.presentation.model.LocaliteModel;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
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

public class LocaliteControler extends GenericEntiteControler {
	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness gouvernoratBusiness;
	protected GenericEntiteBusiness paysBusiness;

	public void setPaysBusiness(GenericEntiteBusiness paysBusiness) {
		this.paysBusiness = paysBusiness;
	}

	public void setGouvernoratBusiness(GenericEntiteBusiness gouvernoratBusiness) {
		this.gouvernoratBusiness = gouvernoratBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		LocaliteModel entiteModel = (LocaliteModel) context.userAction().getDataObject();
		List listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListPays(listPays);
	}

	public void onInitListFilter(ListenerContext context) throws FunctionalException {
		LocaliteModel entiteModel = (LocaliteModel) context.userAction().getDataObject();
		List listGouvernorat = gouvernoratBusiness.getByCriteria(new GovernoratBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListGouvernorat(listGouvernorat);
		List listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListPays(listPays);

	}

	public void onInitListGouvernorat(ListenerContext context) throws FunctionalException {
		LocaliteModel entiteModel = (LocaliteModel) context.userAction().getDataObject();
		if(((LocaliteBean) entiteModel.getDetailBean()).getGovernorat()!=null){
			GovernoratBean governoratBean = new GovernoratBean();
			governoratBean.setPaysId(((LocaliteBean) entiteModel.getDetailBean()).getGovernorat().getPaysId());
			List listGouvernorat = gouvernoratBusiness.getByCriteria(governoratBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setListGouvernorat(listGouvernorat);
		}
	}

	public void doIndexBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		onInitListGouvernorat(context);
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
		onInitListGouvernorat(context);
		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			LocaliteBusiness localiteBusiness = (LocaliteBusiness) entiteBusiness;
			LocaliteBean loc= new LocaliteBean();
			LocaliteBean locOld=	(LocaliteBean)entiteModel.getDetailBean();
			loc.setLibelle(locOld.getLibelle());
			loc.setGovernoratId(locOld.getGovernoratId());
			List listLocalite =localiteBusiness.getByCriteria(loc, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if(!listLocalite.isEmpty()){
				context.userDesktop().messagesHandler().addMessage(((LocaliteControlerParams) controlerParams).getMessageExistance(),new String[]{"Localite"});
				context.userAction().goToCurrentWindow(context.webContext());
				return;
			}			
			
			
			localiteBusiness.create((LocaliteBean) entiteModel.getDetailBean(), context.userDesktop().userContext());
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
			LocaliteBusiness localiteBusiness=(LocaliteBusiness)entiteBusiness;
			
			LocaliteBean loc= new LocaliteBean();
			LocaliteBean locOld=	(LocaliteBean)entiteModel.getDetailBean();
			loc.setLibelle(locOld.getLibelle());
			loc.setGovernoratId(locOld.getGovernoratId());
			List listLocalite =localiteBusiness.getByCriteria(loc, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if(!listLocalite.isEmpty()){
				switch(listLocalite.size()){
				case 1:
					loc=(LocaliteBean)listLocalite.get(0);
					if(NumberUtils.isEgale(loc.getLocaliteId(),locOld.getLocaliteId()))
							break;					
				default:			
					context.userDesktop().messagesHandler().addMessage(((LocaliteControlerParams) controlerParams).getMessageExistance(),new String[]{"Localite"});
					context.userAction().goToCurrentWindow(context.webContext());
				return;
				}
			}
			
			localiteBusiness.update((LocaliteBean)entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), entiteModel.getDetailBean());

			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		} 
	}
	public void onRenderPage(ListenerContext context) throws FunctionalException {

		LocaliteModel entiteModel = (LocaliteModel) context.userAction().getDataObject();

		if (entiteModel.getDetailBean() == null)
			entiteModel.setDetailBean(new LocaliteBean());
		
	
		GovernoratBean gov=new GovernoratBean();
		gov.setPays(context.getPays());
		gov.setPaysId(context.getPaysId());

		((LocaliteBean) entiteModel.getDetailBean()).setGovernorat(gov);
	
	}
	
}
