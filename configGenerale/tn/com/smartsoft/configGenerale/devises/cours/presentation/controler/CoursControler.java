package tn.com.smartsoft.configGenerale.devises.cours.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.configGenerale.devises.cours.beans.CourBean;
import tn.com.smartsoft.configGenerale.devises.cours.business.CourBusiness;
import tn.com.smartsoft.configGenerale.devises.cours.presentation.model.CoursModel;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;

public class CoursControler extends GenericEntiteControler {
	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness deviseBusiness;

	public void setDeviseBusiness(GenericEntiteBusiness deviseBusiness) {
		this.deviseBusiness = deviseBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		CoursModel entiteModel = (CoursModel) context.userAction().getDataObject();
		List listDevise = deviseBusiness.getByCriteria(new DeviseBean(), context.userAction().getUserActionId().getActionBeanId(), context
				.userDesktop().userContext());
		entiteModel.setListDevise(listDevise);

	}
	
	
	public void doValiderCreate(ListenerContext context) {
		try {
			CoursModel entiteModel = (CoursModel) context.userAction().getDataObject();
			CourBean coursBean=(CourBean)entiteModel.getDetailBean();
			
			((CourBusiness)entiteBusiness).create(coursBean, context.userDesktop().userContext());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
}
