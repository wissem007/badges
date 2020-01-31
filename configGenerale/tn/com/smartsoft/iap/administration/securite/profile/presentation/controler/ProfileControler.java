package tn.com.smartsoft.iap.administration.securite.profile.presentation.controler;

import java.util.List;
import java.util.Map;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;
import tn.com.smartsoft.iap.administration.securite.profile.business.ProfileBusiness;
import tn.com.smartsoft.iap.administration.securite.profile.presentation.model.ProfileModel;
import tn.com.smartsoft.iap.administration.securite.profile.presentation.utils.PermessionUtils;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.business.GroupedRoleBusiness;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class ProfileControler extends GenericEntiteControler{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private GroupedRoleBusiness		groupedRoleBusiness;
	protected GenericEntiteBusiness	moduleBusiness;

	public void setModuleBusiness(GenericEntiteBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}
	public void setGroupedRoleBusiness(GroupedRoleBusiness groupedRoleBusiness) {
		this.groupedRoleBusiness = groupedRoleBusiness;
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		ProfileModel entiteModel = (ProfileModel) context.userAction().getDataObject();
		ModuleBean module = new ModuleBean();
		module.setActivateAndType(Boolean.TRUE);
		List<DataBusinessBean> listModule = moduleBusiness.getByCriteria(module, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListModule(listModule);
	}
	public void onInitByProfil(ListenerContext context) throws FunctionalException {
		ProfileModel entiteModel = (ProfileModel) context.userAction().getDataObject();
		ProfileBean detailBean = (ProfileBean) entiteModel.getDetailBean();
		PermissionBean searcheBean = new PermissionBean();
		searcheBean.setProfileId(detailBean.getProfileId());
		searcheBean.setModuleId(detailBean.getModuleId());
		ActionBeanId actionId = context.userAction().getUserActionId().getActionBeanId();
		UserContext userContext = context.userDesktop().userContext();
		Map<?, ?> listPermissons = ((ProfileBusiness) entiteBusiness).getPermissions(searcheBean, actionId, userContext);
		RoleBean role = new RoleBean();
		role.setModuleId(detailBean.getModuleId());
		Map<?, ?> listRoles = groupedRoleBusiness.getByCriteriaMap(role, actionId, userContext);
		List<DataBusinessBean> listPermissonsGen = PermessionUtils.createAutorisationRoles(listPermissons, listRoles, searcheBean.getProfileId());
		entiteModel.setListPermissons(listPermissonsGen);
	}
	public void doIndexBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		if (context.userAction().getActionBeanId().getActionId().equals("autoriser")) {
			onInitByProfil(context);
		}
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
		entiteModel.setDetailBean(rowBean);
		if (context.userAction().getActionBeanId().getActionId().equals("autoriser")) {
			onInitByProfil(context);
		}
		UIWindow window = context.userAction().createWindowIfNecessary(context.webContext(), controlerParams.getDetailWindowId());
		UIExtButton extButton = (UIExtButton) window.findChild("valider");
		UIEvent event = extButton.getEvent("click");
		if (context.userAction().getActionBeanId().getActionId().equalsIgnoreCase("supprimer")) {
			event.setConfirmMsg(true);
		}
		context.userAction().goToWindow(controlerParams.getDetailWindowId(), context.webContext());
	}
	public void doValiderPermission(ListenerContext context) {
		try {
			ProfileModel entiteModel = (ProfileModel) context.userAction().getDataObject();
			((ProfileBusiness) entiteBusiness).createPermission(entiteModel.getDetailBean(), entiteModel.getListPermissons(), context.userDesktop().userContext());
			entiteModel.setDetailBean(null);
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageCreateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
}
