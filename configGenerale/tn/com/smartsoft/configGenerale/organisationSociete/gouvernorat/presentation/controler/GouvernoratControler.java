package tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.presentation.controler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.NumberUtils;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.business.GouvernoratBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.presentation.model.GouvernoratModel;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.presentation.model.LocaliteModel;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;


public class GouvernoratControler extends GenericEntiteControler {
	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness paysBusiness;
	
	
	public void setPaysBusiness(GenericEntiteBusiness paysBusiness) {
		this.paysBusiness = paysBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		GouvernoratModel entiteModel = (GouvernoratModel) context.userAction().getDataObject();
		List listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListPays(listPays);
	}
	
	
	
	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			GouvernoratBusiness gouvernoratBusiness=(GouvernoratBusiness)entiteBusiness;
		
			List listgouv =gouvernoratBusiness.getByCriteria((GovernoratBean)entiteModel.getDetailBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if(!listgouv.isEmpty()){
				context.userDesktop().messagesHandler().addMessage(((GouvernoratControlerParams) controlerParams).getMessageExistance(),new String[]{"Gouvernorat"});
				context.userAction().goToCurrentWindow(context.webContext());
				return;
			}
			gouvernoratBusiness.create((GovernoratBean)entiteModel.getDetailBean(), context.userDesktop().userContext());
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
			GouvernoratBusiness gouvernoratBusiness=(GouvernoratBusiness)entiteBusiness;
			GovernoratBean gouv=new GovernoratBean();
			
			GovernoratBean gouvold= (GovernoratBean)entiteModel.getDetailBean();
			gouv.setLibelle(gouvold.getLibelle());
			gouv.setPaysId(gouvold.getPaysId());
			List listgouv =gouvernoratBusiness.getByCriteria(gouv, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if(!listgouv.isEmpty()){
				switch(listgouv.size()){
				case 1:
					GovernoratBean gv=(GovernoratBean)listgouv.get(0);
					if(NumberUtils.isEgale(gv.getGovernoratId(),gouvold.getGovernoratId()))
							break;					
				default:			
					
     				context.userDesktop().messagesHandler().addMessage(((GouvernoratControlerParams) controlerParams).getMessageExistance(),new String[]{"Gouvernorat"});
					context.userAction().goToCurrentWindow(context.webContext());
				return;
				}
			}
			
			gouvernoratBusiness.update((GovernoratBean)entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), entiteModel.getDetailBean());

			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		} 
	}
	public void onRenderPage(ListenerContext context) throws FunctionalException {

		GouvernoratModel entiteModel = (GouvernoratModel) context.userAction().getDataObject();

		if (entiteModel.getDetailBean() == null)
			entiteModel.setDetailBean(new GovernoratBean());
		
	
		GovernoratBean gov=new GovernoratBean();
		gov.setPays(context.getPays());
		gov.setPaysId(context.getPaysId());

		((GovernoratBean) entiteModel.getDetailBean()).setPays(context.getPays());
		((GovernoratBean) entiteModel.getDetailBean()).setPaysId(context.getPaysId());
	
	}
	
	
	
}
