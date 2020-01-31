package tn.com.smartsoft.iap.dictionary.decoupage.application.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.decoupage.application.presentation.model.ApplicationModel;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;

public class ApplicationControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness deviseBusiness;
	protected GenericEntiteBusiness paysBusiness;

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setDeviseBusiness(GenericEntiteBusiness deviseBusiness) {
		this.deviseBusiness = deviseBusiness;
	}

	public void setPaysBusiness(GenericEntiteBusiness paysBusiness) {
		this.paysBusiness = paysBusiness;
	}

	public void onInitListApplication(ListenerContext context) throws FunctionalException {
		ApplicationModel entiteModel = (ApplicationModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);

		List listSystemModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSystemModule(listSystemModule);

		List listDevise = deviseBusiness.getByCriteria(new DeviseBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListDevise(listDevise);

		List<DataBusinessBean> listPays = paysBusiness.getByCriteria(new PayBean(), null, context.userDesktop().userContext());
		entiteModel.setListPays(listPays);

	}
}
