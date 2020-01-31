package tn.com.smartsoft.configGenerale.dynamicEntite.paramEntiteSimple.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.presentation.controler.EntiteControler;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.presentation.model.EntiteModel;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.niveauApplicatif.beans.NiveauApplicatifBean;

public class EntitesControler extends EntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness niveauApplicatifBusiness;

	public void setNiveauApplicatifBusiness(GenericEntiteBusiness niveauApplicatifBusiness) {
		this.niveauApplicatifBusiness = niveauApplicatifBusiness;
	}

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
		ModuleBean module = new ModuleBean();
		module.setActivateAndType(Boolean.TRUE);

		List listModule = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		List listNiveauApplicatif = niveauApplicatifBusiness.getByCriteria(new NiveauApplicatifBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());

		entiteModel.setListNiveauApplicatif(listNiveauApplicatif);
		entiteModel.setListModule(listModule);

	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		EntiteModel entiteModel = (EntiteModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		EntiteBean rowBean = (EntiteBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		entiteModel.setDetailBean(rowBean);

		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
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
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
}
