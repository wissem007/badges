package tn.com.smartsoft.iap.dictionary.decoupage.subModule.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.subModule.presentation.model.SubModuleModel;

public class SubModuleControler extends GenericEntiteControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	
	
	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}


	public void onInitListModule(ListenerContext context) throws FunctionalException {
		SubModuleModel entiteModel = (SubModuleModel) context.userAction().getDataObject();
		List listModule = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);
	}


}
