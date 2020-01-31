package tn.com.smartsoft.iap.administration.securite.utilisateur.presentation.controler;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.beans.FonctionUtilisationBean;
import tn.com.smartsoft.configGenerale.administration.fonctionUtilisation.business.FonctionUtilisationBusiness;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.business.OrganismeProprietairesBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.gouvernorat.beans.GovernoratBean;
import tn.com.smartsoft.configGenerale.organisationSociete.localite.beans.LocaliteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanModel;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.tags.handler.GenericTagHandler;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtSuggestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridHeaderColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtHeaderRowGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.utils.UITagCreateUtils;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.business.UserBusiness;
import tn.com.smartsoft.iap.administration.securite.utilisateur.presentation.model.UserModel;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.business.ModuleBusiness;

public class UserControler extends GenericEntiteControler{
	
	private static final long					serialVersionUID	= 1L;
	protected GenericEntiteBusiness				paysBusiness;
	protected GenericEntiteBusiness				localiteBusiness;
	protected GenericEntiteBusiness				gouvernoratBusiness;
	protected GenericEntiteBusiness				societeBusiness;
	protected OrganismeProprietairesBusiness	organismeBusiness;
	protected GenericEntiteBusiness				profileBusiness;
	private ModuleBusiness						moduleBusiness;
	protected FonctionUtilisationBusiness		fonctionUtilisationBusiness;
	
	public void setFonctionUtilisationBusiness(FonctionUtilisationBusiness fonctionUtilisationBusiness) {
		this.fonctionUtilisationBusiness = fonctionUtilisationBusiness;
	}
	public void setModuleBusiness(ModuleBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}
	public void setEntiteBusiness(GenericEntiteBusiness entiteBusiness) {
		this.entiteBusiness = entiteBusiness;
	}
	public void setOrganismeBusiness(OrganismeProprietairesBusiness organismeBusiness) {
		this.organismeBusiness = organismeBusiness;
	}
	public void setProfileBusiness(GenericEntiteBusiness profileBusiness) {
		this.profileBusiness = profileBusiness;
	}
	public void setSocieteBusiness(GenericEntiteBusiness societeBusiness) {
		this.societeBusiness = societeBusiness;
	}
	public void setPaysBusiness(GenericEntiteBusiness paysBusiness) {
		this.paysBusiness = paysBusiness;
	}
	public void setLocaliteBusiness(GenericEntiteBusiness localiteBusiness) {
		this.localiteBusiness = localiteBusiness;
	}
	public void setGouvernoratBusiness(GenericEntiteBusiness gouvernoratBusiness) {
		this.gouvernoratBusiness = gouvernoratBusiness;
	}
	public void doIndexBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		((UserBean) rowBean).setConfPasseWord(((UserBean) rowBean).getPasseWord());
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
		((UserBean) rowBean).setConfPasseWord(((UserBean) rowBean).getPasseWord());
		entiteModel.setDetailBean(rowBean);
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
	@SuppressWarnings("rawtypes")
	public void onInitListPays(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		List listPays = paysBusiness.getByCriteria(new PayBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		entiteModel.setListPays(listPays);
		FonctionUtilisationBean fonctionUtilisationBean = new FonctionUtilisationBean();
		List<DataBusinessBean> listFonctionUtil = fonctionUtilisationBusiness.getByCriteria(fonctionUtilisationBean, context.userAction().getUserActionId().getActionBeanId(), context
				.userDesktop().userContext());
		entiteModel.setListFonctionUtil(listFonctionUtil);
		if (!StringUtils.equalsIgnoreCase(action, "creer") && !StringUtils.equalsIgnoreCase(action, "autoriser")) {
			UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
			UIExtComponent field = (UIExtComponent) window.findChild("confPasseWord");
			field.setRendred(false);
			field = (UIExtComponent) window.findChild("passeWord");
			field.setRendred(false);
		}
	}
	@SuppressWarnings("rawtypes")
	public void onInitList(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		OrganismeProprietairesBean organismeBean = new OrganismeProprietairesBean();
		List listOrganisme = organismeBusiness.getByCriteria(organismeBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListOrganisme(listOrganisme);
		List listProfile = profileBusiness.getByCriteria(new ProfileBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListProfile(listProfile);
		if (entiteModel.getDetailBean() == null) {
			entiteModel.setDetailBean(new UserBean());
		}
		if (entiteModel.getSearcheBean() == null) {
			UserBean userBean = new UserBean();
			userBean.setActive(true);
			entiteModel.setSearcheBean(userBean);
		}
	}
	public void onInitListAutoriser(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		UserBean userBean = new UserBean();
		userBean.setActive(true);
		List<?> listUser = ((UserBusiness) entiteBusiness).getByCriteriaNoDetail(userBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListUser(listUser);
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel("profileModules");
		UIWindow uiWindow = context.userAction().getWindow(controlerParams.getDetailWindowId());
		UIExtGrid grid = (UIExtGrid) uiWindow.findChild("detail");
		UIExtHeaderRowGrid headerRowGrid = grid.getHeaderRow(grid.getHeaderRowIds().get(grid.headerRowSize() - 1));
		UIExtGridHeaderColumn gridHeaderColumn = headerRowGrid.getColumn(headerRowGrid.getColumnIds().get(1));
		UIExtStore store = grid.getStore();
		ModuleBean module = new ModuleBean();
		module.setActivateAndTypeOne(Boolean.TRUE);
		List<DataBusinessBean> listModule = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		BeanComparator.sort(new String[] { "rang" }, SorterType.ASC, listModule);
		gridHeaderColumn.setColspan(0);
		for (int j = 0; j < listModule.size(); j++) {
			ModuleBean moduleBean = (ModuleBean) listModule.get(j);
			beanModel.addPropertyModel(moduleBean.getModuleId(), "numerickey", Long.class, moduleBean.getLibelle());
			String profBeanId = moduleBean.getModuleId() + "Ref";
			beanModel.addChildModel(profBeanId, ProfileBean.class, "c");
			String idprofileIdStore = moduleBean.getModuleId() + "Index";
			String profileLibelleIndexStore = profBeanId + "LibelleIndex";
			store.addTag(UITagCreateUtils.createStoreField(idprofileIdStore, moduleBean.getModuleId()));
			store.addTag(UITagCreateUtils.createStoreField(profileLibelleIndexStore, profBeanId + ".libelle"));
			GenericTagHandler tagGridColunm = new GenericTagHandler("ext-grid-column", new String[] { "data-index", "width", "data-index-display", "renderer" }, new Object[] {
					idprofileIdStore, "150", profileLibelleIndexStore, "setBackGroundColor" });
			tagGridColunm.addChildren(new GenericTagHandler("ext-field-combo", new String[] { "display-field", "store-id" }, new Object[] { "profileLibelleIndex", "storeListProfile" }));
			grid.addTag(tagGridColunm);
			gridHeaderColumn.setColspan(gridHeaderColumn.getColspan() + 1);
		}
		beanModel.addPropertyModel("listActive", "chainecharacteur", String.class, "");
	}
	public void onRendeListWindowAuto(ListenerContext context) {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		UIWindow uiWindow = context.userAction().getWindow(controlerParams.getDetailWindowId());
		UIExtGrid grid = (UIExtGrid) uiWindow.findChild("detail");
		grid.setHidden(entiteModel.getProfileModules() == null || entiteModel.getProfileModules().size() == 0);
		grid.getStore().setAutoLoad(!(entiteModel.getProfileModules() == null || entiteModel.getProfileModules().size() == 0));
	}
	public void doFilter(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		UserBean userBean = (UserBean) entiteModel.getSearcheBean();
		String action = context.userAction().getUserActionId().getActionBeanId().getActionId();
		if (action != null && action.equals("activer")) {
			userBean.setActive(false);
		}
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		try {
			List<DataBusinessBean> list = ((UserBusiness) entiteBusiness).getByCriteriaNoDetail(entiteModel.getSearcheBean(), context.userAction().getActionBeanId(), context.userDesktop()
					.userContext());
			entiteModel.setListBean(list);
			jsonItemResponseModel.addData("empty", list.isEmpty());
			if (list.isEmpty()) { throw new FunctionalException(controlerParams.getMessageEmptyRow()); }
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		}
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
	@SuppressWarnings("rawtypes")
	public void onInitListeGouvernorat(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		GovernoratBean governoratBean = new GovernoratBean();
		if (entiteModel.getDetailBean() != null) {
			governoratBean.setPaysId(((UserBean) entiteModel.getDetailBean()).getPaysId());
		} else {
			governoratBean.setPaysId(((UserBean) entiteModel.getSearcheBean()).getPaysId());
		}
		List listGouvernorat = gouvernoratBusiness.getByCriteria(governoratBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListGouvernorat(listGouvernorat);
		UIExtComboBoxField comb = (UIExtComboBoxField) context.source().getWindow().findChild("governorat");
		comb.getStore().fireActionEvent(context.webContext(), ClientEvent.ON_INIT);
	}
	@SuppressWarnings("rawtypes")
	public void onInitListeLocalite(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		GovernoratBean governoratBean = new GovernoratBean();
		if (entiteModel.getDetailBean() != null) {
			governoratBean.setGovernoratId(((UserBean) entiteModel.getDetailBean()).getGovernoratId());
		} else {
			governoratBean.setGovernoratId(((UserBean) entiteModel.getSearcheBean()).getGovernoratId());
		}
		List listGouvernorat = gouvernoratBusiness.getByCriteria(governoratBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListGouvernorat(listGouvernorat);
		LocaliteBean localiteBean = new LocaliteBean();
		if (entiteModel.getDetailBean() != null) {
			localiteBean.setGovernoratId(((UserBean) entiteModel.getDetailBean()).getGovernoratId());
		} else {
			localiteBean.setGovernoratId(((UserBean) entiteModel.getSearcheBean()).getGovernoratId());
		}
		List listLocalite = localiteBusiness.getByCriteria(localiteBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListLocalite(listLocalite);
		UIExtSuggestField comb = (UIExtSuggestField) context.source().getWindow().findChild("localite");
		comb.getStore().fireActionEvent(context.webContext(), ClientEvent.ON_INIT);
	}
	public void doValiderDesactivate(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		UserBean userBean = (UserBean) entiteModel.getDetailBean();
		userBean.setActive(false);
		doValiderUpdate(context);
	}
	public void doValiderActivate(ListenerContext context) throws FunctionalException {
		UserModel entiteModel = (UserModel) context.userAction().getDataObject();
		UserBean userBean = (UserBean) entiteModel.getDetailBean();
		userBean.setActive(true);
		doValiderUpdate(context);
	}
	public void doValiderAutoriser(ListenerContext context) {
		try {
			UserModel entiteModel = (UserModel) context.userAction().getDataObject();
			UserBean bean = (UserBean) entiteModel.getDetailBean();
			UserBean userBean = (UserBean) ListUtils.findByProperty(entiteModel.getListUser(), "userId", bean.getUserId());
			((UserBusiness) entiteBusiness).autoriser(userBean, context.userDesktop().userContext());
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
}
