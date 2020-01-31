package tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans.DataValuesBean;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.presentation.model.DataValuesModel;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.business.OrganismeBusiness;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.beans.SocieteBean;
import tn.com.smartsoft.configGenerale.organisationSociete.societe.business.SocieteBusiness;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.action.exception.CreateWindowException;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;

public class DataValuesControler extends GenericEntiteControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected GenericEntiteBusiness moduleBusiness;
	protected GenericEntiteBusiness entiteDbBusiness;
	protected SocieteBusiness societeBusiness;
	protected OrganismeBusiness organismeBusiness;

	public void setEntiteDbBusiness(GenericEntiteBusiness entiteDbBusiness) {
		this.entiteDbBusiness = entiteDbBusiness;
	}

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}

	public void setSocieteBusiness(SocieteBusiness societeBusiness) {
		this.societeBusiness = societeBusiness;
	}

	public void setOrganismeBusiness(OrganismeBusiness organismeBusiness) {
		this.organismeBusiness = organismeBusiness;
	}

	public void onInitList(ListenerContext context) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		ModuleBean module = new ModuleBean();
		module.setActivateAndType(Boolean.TRUE);
		List<DataBusinessBean> listModule = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		List<DataBusinessBean> listSociete = societeBusiness.getByCriteria(new SocieteBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);
		entiteModel.setListSociete(listSociete);
	}

	public void getEntiteFilter(ListenerContext context) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		DataValuesBean bean = (DataValuesBean) entiteModel.getSearcheBean();
		getEntite(context, bean);

	}

	public void getEntite(ListenerContext context) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		DataValuesBean bean = (DataValuesBean) entiteModel.getDetailBean();
		getEntite(context, bean);

	}

	public void getEntite(ListenerContext context, DataValuesBean bean) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		EntiteBean entiteBean = new EntiteBean();

		entiteBean.setModuleId(bean.getModuleId());
		entiteBean.setSubModuleId("EntiteSimple");
		entiteBean.setSujetId("EntiteSimple");
		entiteBean.setTypeEntiteId(new Long(2));

		List<DataBusinessBean> listEntite = entiteDbBusiness.getByCriteria(entiteBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListEntite(listEntite);

	}

	public void getOrganismeFilter(ListenerContext context) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		DataValuesBean bean = (DataValuesBean) entiteModel.getSearcheBean();
		getOrganisme(context, bean);

	}

	public void getOrganisme(ListenerContext context) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		DataValuesBean bean = (DataValuesBean) entiteModel.getDetailBean();
		getOrganisme(context, bean);

	}

	public void getOrganisme(ListenerContext context, DataValuesBean bean) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		OrganismeBean organismeBean = new OrganismeBean();
		organismeBean.setSocieteId(bean.getSocieteId());
		organismeBean.setIsAll(Boolean.TRUE);
		List<DataBusinessBean> listOrganisme = organismeBusiness.getByCriteria(organismeBean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListOrganisme(listOrganisme);

	}

	public void doIndexBean(ListenerContext context) throws FunctionalException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		DataValuesBean rowBean = (DataValuesBean) entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = (DataValuesBean) entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		getOrganisme(context);
		EntiteBean entiteBean = (EntiteBean) ListUtils.findByProperty(entiteModel.getListEntite(), "entiteId", rowBean.getEntiteId());
		StringBuffer scriptBuffer = new StringBuffer().append("getSelectedGrid('").append(entiteBean.getNiveauApplicatifId()).append("');");

		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData("data", context.userAction().currentWindow().getFieldValues());
		jsonItemResponseModel.addData("index", entiteModel.getBeanIndex());
		jsonItemResponseModel.addData("size", entiteModel.getListBean().size());
		jsonItemResponseModel.setUserScript(scriptBuffer.toString());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		getEntite(context);
		getOrganisme(context);

		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}

	public void onRenderDetailWindow(ListenerContext context) throws CreateWindowException {
		DataValuesModel entiteModel = (DataValuesModel) context.userAction().getDataObject();
		UIWindow window = context.userAction().getWindow(controlerParams.getDetailWindowId());
		if (window == null)
			window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());

		if (entiteModel.getDetailBean() == null)
			entiteModel.setDetailBean(new DataValuesBean());

		DataValuesBean bean = (DataValuesBean) entiteModel.getDetailBean();
		EntiteBean entiteBean = (EntiteBean) ListUtils.findByProperty(entiteModel.getListEntite(), "entiteId", bean.getEntiteId());

		UIExtComboBoxField societeId = (UIExtComboBoxField) window.findChild("societeId");
		UIExtComboBoxField organismeId = (UIExtComboBoxField) window.findChild("organismeId");

		if (entiteBean != null && entiteBean.getNiveauApplicatifId() == 1) {
			societeId.setHidden(true);
			organismeId.setHidden(true);
		} else if (entiteBean != null && entiteBean.getNiveauApplicatifId() == 2) {
			societeId.setHidden(false);
			organismeId.setHidden(true);
		} else if (entiteBean != null && entiteBean.getNiveauApplicatifId() == 3) {
			societeId.setHidden(false);
			organismeId.setHidden(false);
		}
	}
}
