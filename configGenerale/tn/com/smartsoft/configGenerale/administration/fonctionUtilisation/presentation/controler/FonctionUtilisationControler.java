package tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.presentation.controler;

import java.util.ArrayList;
import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilProfilBean;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilisationBean;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.presentation.model.FonctionUtilisationModel;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.business.OrganismeProprietairesBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.business.SocieteBusiness;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;
import tn.com.smartsoft.iap.administration.securite.profile.business.ProfileBusiness;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.business.ModuleBusiness;

public class FonctionUtilisationControler extends GenericEntiteControler{
	
	private static final long					serialVersionUID	= 1L;
	protected SocieteBusiness					societeBusiness;
	protected OrganismeProprietairesBusiness	organismeBusiness;
	private ModuleBusiness						moduleBusiness;
	private ProfileBusiness						profileBusiness;
	
	public void setSocieteBusiness(SocieteBusiness societeBusiness) {
		this.societeBusiness = societeBusiness;
	}
	public void setOrganismeBusiness(OrganismeProprietairesBusiness organismeBusiness) {
		this.organismeBusiness = organismeBusiness;
	}
	public void setModuleBusiness(ModuleBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}
	public void setProfileBusiness(ProfileBusiness profileBusiness) {
		this.profileBusiness = profileBusiness;
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
		ModuleBean module = new ModuleBean();
		module.setActivateAndType(Boolean.TRUE);
		List<DataBusinessBean> modules = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		List<DataBusinessBean> listOrganisme = organismeBusiness.getByCriteria(new OrganismeProprietairesBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
				.userContext());
		entiteModel.setListOrganisme(listOrganisme);
		entiteModel.setModules(modules);
	}
	public void onRendeDetailWindow(ListenerContext context) throws FunctionalException {
		FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
		FonctionUtilisationBean detailBean = new FonctionUtilisationBean();
		detailBean.setProfils(onListModuleProfils(entiteModel.getModules(), null));
		entiteModel.setDetailBean(detailBean);
	}
	public void doFilter(ListenerContext context) throws FunctionalException {
		super.doFilter(context);
	}
	public void doValiderCreate(ListenerContext context) {
		try {
			FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
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
			FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
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
	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		FonctionUtilisationBean rowBean = (FonctionUtilisationBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (FonctionUtilisationBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		rowBean.setProfils(onListModuleProfils(entiteModel.getModules(), rowBean.getProfils()));
		entiteModel.setDetailBean(rowBean);
		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
	public void doIndexBean(ListenerContext context) throws FunctionalException {
		FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
		FonctionUtilisationBean rowBean = (FonctionUtilisationBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (FonctionUtilisationBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		rowBean.setProfils(onListModuleProfils(entiteModel.getModules(), rowBean.getProfils()));
		entiteModel.setDetailBean(rowBean);
		onInitList(context);
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData("data", context.userAction().currentWindow().getFieldValues());
		jsonItemResponseModel.addData("index", entiteModel.getBeanIndex());
		jsonItemResponseModel.addData("size", entiteModel.getListBean().size());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
	public void getProfils(ListenerContext context) throws FunctionalException {
		FonctionUtilisationModel entiteModel = (FonctionUtilisationModel) context.userAction().getDataObject();
		String moduleId = context.requestParameters().getParameter("moduleId");
		UserContext userContext = context.userDesktop().userContext();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setModuleId(moduleId);
		List<DataBusinessBean> listProfile = profileBusiness.getByCriteria(profileBean, context.userAction().getUserActionId().getActionBeanId(), userContext);
		entiteModel.setListProfils(listProfile);
	}
	public List<FonctionUtilProfilBean> onListModuleProfils(List<DataBusinessBean> modules, List<?> listFromDB) {
		List<FonctionUtilProfilBean> profils = new ArrayList<FonctionUtilProfilBean>();
		for (int i = 0; i < modules.size(); i++) {
			ModuleBean moduleBean = (ModuleBean) modules.get(i);
			FonctionUtilProfilBean bean = null;
			if (listFromDB != null && listFromDB.size() > 0) bean = (FonctionUtilProfilBean) ListUtils.findByProperty(listFromDB, "moduleId", moduleBean.getModuleId());
			if (bean == null) {
				bean = new FonctionUtilProfilBean();
				bean.setModuleId(moduleBean.getModuleId());
			}
			bean.setModuleBean(moduleBean);
			profils.add(bean);
		}
		return profils;
	}
}
