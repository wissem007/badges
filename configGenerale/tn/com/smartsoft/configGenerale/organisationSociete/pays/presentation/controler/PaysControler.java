package tn.com.smartsoft.configGenerale.organisationSociete.pays.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.NumberUtils;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.business.GouvernoratBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.presentation.controler.GouvernoratControlerParams;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.business.PaysBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.presentation.model.PaysModel;

public class PaysControler extends GenericEntiteControler {
	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness deviseBusiness;
	
	public void setDeviseBusiness(GenericEntiteBusiness deviseBusiness) {
		this.deviseBusiness = deviseBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		PaysModel entiteModel = (PaysModel) context.userAction().getDataObject();
		List listDevise = deviseBusiness.getByCriteria(new DeviseBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListDevise(listDevise);

	}
	
	
	
	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			PaysBusiness paysBusiness = (PaysBusiness) entiteBusiness;
			PayBean paysOld =(PayBean)entiteModel.getDetailBean();
			PayBean pays = new PayBean();
			
			pays.setLibelle(paysOld.getLibelle());
			List listPays =paysBusiness.getByCriteria(pays, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if(!listPays.isEmpty()){	
     			context.userDesktop().messagesHandler().addMessage(((PaysControlerParams) controlerParams).getMessageExistance(),new String[]{"Pays"});
				context.userAction().goToCurrentWindow(context.webContext());
				return;
	
			}
			
			paysBusiness.create((PayBean)entiteModel.getDetailBean(), context.userDesktop().userContext());
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
			PaysBusiness paysBusiness=(PaysBusiness)entiteBusiness;
			PayBean pays=new PayBean();
			
			PayBean paysOld= (PayBean)entiteModel.getDetailBean();
			pays.setLibelle(paysOld.getLibelle());
			
			List listPays =paysBusiness.getByCriteria(pays, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if(!listPays.isEmpty()){
				switch(listPays.size()){
				case 1:
					PayBean pay=(PayBean)listPays.get(0);
					if(NumberUtils.isEgale(pay.getPaysId(),paysOld.getPaysId()))
							break;					
				default:			
     				context.userDesktop().messagesHandler().addMessage(((PaysControlerParams) controlerParams).getMessageExistance(),new String[]{"Pays"});
					context.userAction().goToCurrentWindow(context.webContext());
				return;
				}
			}
			
			paysBusiness.update((PayBean)entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), entiteModel.getDetailBean());

			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		} 
	}


	

}
