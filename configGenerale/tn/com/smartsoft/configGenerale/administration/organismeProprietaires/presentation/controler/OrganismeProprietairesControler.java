package tn.com.smartsoft.configGenerale.administration.organismeProprietaires.presentation.controler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.beans.OrganismeProprietairesBean;
import tn.com.smartsoft.configGenerale.administration.organismeProprietaires.presentation.model.OrganismeProprietairesModel;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.business.OrganismeBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.business.SocieteBusiness;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.tags.handler.GenericTagHandler;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridHeaderColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtHeaderRowGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.utils.UITagCreateUtils;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.business.ModuleBusiness;

public class OrganismeProprietairesControler extends GenericEntiteControler{

	private static final long	serialVersionUID	= 1L;
	protected SocieteBusiness	societeBusiness;
	protected OrganismeBusiness	organismeBusiness;
	private ModuleBusiness		moduleBusiness;

	public void setSocieteBusiness(SocieteBusiness societeBusiness) {
		this.societeBusiness = societeBusiness;
	}
	public void setOrganismeBusiness(OrganismeBusiness organismeBusiness) {
		this.organismeBusiness = organismeBusiness;
	}
	public void setModuleBusiness(ModuleBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		SocieteBean societe = new SocieteBean();
		societe.setIsAll(Boolean.TRUE);
		List<DataBusinessBean> listSociete = societeBusiness.getByCriteria(societe, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		ModuleBean module = new ModuleBean();
		module.setActivateAndTypeOne(Boolean.TRUE);
		List<DataBusinessBean> modules = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListSociete(listSociete);
		entiteModel.setModules(modules);
	}
	public void onRendeDetailWindow(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeProprietairesBean detailBean = new OrganismeProprietairesBean();
		detailBean.setModules(entiteModel.getModules());
		entiteModel.setDetailBean(detailBean);
	}
	public void getOrganismeFilter(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeProprietairesBean bean = (OrganismeProprietairesBean) entiteModel.getSearcheBean();
		getOrganisme(context, bean);
	}
	public void getOrganisme(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeProprietairesBean bean = (OrganismeProprietairesBean) entiteModel.getDetailBean();
		getOrganisme(context, bean);
	}
	public void getOrganisme(ListenerContext context, OrganismeProprietairesBean bean) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeBean organismeBean = new OrganismeBean();
		organismeBean.setSocieteId(bean.getSocieteId());
		organismeBean.setIsAll(Boolean.TRUE);
		List<DataBusinessBean> listOrganisme = organismeBusiness.getByCriteria(organismeBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListOrganisme(listOrganisme);
	}
	public void onInitListAuto(ListenerContext context) throws FunctionalException {
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel("listBean");
		UIWindow uiWindow = context.userAction().getWindow(controlerParams.getListWindowId());
		UIExtGrid grid = (UIExtGrid) uiWindow.findChild("listBean");
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
			beanModel.addPropertyModel(moduleBean.getModuleId(), "boolean", Boolean.class, moduleBean.getLibelle());
			String idprofileIdStore = moduleBean.getModuleId() + "Index";
			store.addTag(UITagCreateUtils.createStoreField(idprofileIdStore, moduleBean.getModuleId()));
			GenericTagHandler tagGridColunm = new GenericTagHandler("ext-grid-column", new String[] { "data-index", "width" }, new Object[] { idprofileIdStore, "150" });
			GenericTagHandler createFieldCheckbox = new GenericTagHandler("ext-field-checkbox", "checked-value", "unchecked-value", "hideable", "true", "false", "true");
			tagGridColunm.addChildren(createFieldCheckbox);
			grid.addTag(tagGridColunm);
			gridHeaderColumn.setColspan(gridHeaderColumn.getColspan() + 1);
		}
		BeanComparator.sort(new String[] { "rang" }, SorterType.ASC, listModule);
		onInitListByOragnisme(context, new OrganismeBean());
		onInitList(context);
	}
	public void onInitListByOragnisme(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeProprietairesBean bean = (OrganismeProprietairesBean) entiteModel.getDetailBean();
		onInitListByOragnisme(context, bean.getOrganisme());
	}
	public void doFilterOrganisme(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeProprietairesBean bean = (OrganismeProprietairesBean) entiteModel.getSearcheBean();
		onInitListByOragnisme(context, new OrganismeBean(bean.getOrganismeId(), bean.getSocieteId()));
	}
	public void onInitListByOragnisme(ListenerContext context, OrganismeBean organismeSearchBean) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		ActionBeanId actionId = context.userAction().getUserActionId().getActionBeanId();
		UserContext userContext = context.userDesktop().userContext();
		List<DataBusinessBean> listOrganisme = entiteBusiness.getByCriteria(organismeSearchBean, actionId, userContext);
		for (int i = 0; i < listOrganisme.size(); i++) {
			OrganismeProprietairesBean organismeProprietaires = (OrganismeProprietairesBean) listOrganisme.get(i);
			organismeProprietaires.getOrganisme().getParentOrganisme();
			for (Iterator<String> iterator = organismeProprietaires.getAutModules().iterator(); iterator.hasNext();) {
				organismeProprietaires.setDynamicPropertyValue(iterator.next(), true);
			}
		}
		entiteModel.setListBean(listOrganisme);
	}
	
	public void doValiderUpdate(ListenerContext context) throws FunctionalException {
		try { 
			OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
			entiteBusiness.update(entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteBusiness.getDaoSession().clear();
			DataBusinessBean rowBean = entiteBusiness.getById(entiteModel.getDetailBean(), context.userAction().getActionBeanId(), context.userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), rowBean);
			entiteModel.setDetailBean(null);
			onInitListByOragnisme(context, new OrganismeBean());
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		OrganismeProprietairesBean rowBean = (OrganismeProprietairesBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (OrganismeProprietairesBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		onInitListDetails(rowBean, entiteModel.getModules());
		entiteModel.setDetailBean(rowBean);
		getOrganisme(context);
		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
	public void doIndexBean(ListenerContext context) throws FunctionalException {
		OrganismeProprietairesModel entiteModel = (OrganismeProprietairesModel) context.userAction().getDataObject();
		OrganismeProprietairesBean rowBean = (OrganismeProprietairesBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (OrganismeProprietairesBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		onInitListDetails(rowBean, entiteModel.getModules());
		entiteModel.setDetailBean(rowBean);
		getOrganisme(context);
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData("data", context.userAction().currentWindow().getFieldValues());
		jsonItemResponseModel.addData("index", entiteModel.getBeanIndex());
		jsonItemResponseModel.addData("size", entiteModel.getListBean().size());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}
	public void onInitListDetails(OrganismeProprietairesBean rowBean, List<DataBusinessBean> listModuleAll) throws FunctionalException {
		List<DataBusinessBean> listModule = new ArrayList<DataBusinessBean>();
		for (int i = 0; i < listModuleAll.size(); i++) {
			ModuleBean cloneBean = (ModuleBean) BeanObjectUtils.cloneBean(listModuleAll.get(i));
			if (rowBean.getAutModules().contains(cloneBean.getModuleId())) {
				cloneBean.setEtatBusiness(true);
			}
			listModule.add(cloneBean);
		}
		rowBean.setModules(listModule);
	}
}
