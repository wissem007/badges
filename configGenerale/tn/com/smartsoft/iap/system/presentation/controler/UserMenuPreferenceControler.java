package tn.com.smartsoft.iap.system.presentation.controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.context.impl.UserContextBeanImpl;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserMenuBean;
import tn.com.smartsoft.iap.system.business.UserMenuPreferenceBusiness;
import tn.com.smartsoft.iap.system.presentation.model.UserMenuPreferenceModel;

public class UserMenuPreferenceControler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserMenuPreferenceBusiness userMenuPreferenceBusiness;

	public void setUserMenuPreferenceBusiness(UserMenuPreferenceBusiness userMenuPreferenceBusiness) {
		this.userMenuPreferenceBusiness = userMenuPreferenceBusiness;
	}

	public void onRendeListWindow(ListenerContext context) {

	}

	public void onInitMenuPreferenceView(ListenerContext context) {
		UserMenuPreferenceModel userMenuPreferenceModel = (UserMenuPreferenceModel) context.userAction().getDataObject();
		UserDesktop userDesktop = context.userAction().userDesktop();
		Collection<UIUserModule> userModules = userDesktop.moduleExplorer().getUserModules().values();
		List<DataBusinessBean> listModules = new ArrayList<DataBusinessBean>();
		for (Iterator<UIUserModule> iterator = userModules.iterator(); iterator.hasNext();) {
			UIUserModule uiUserModule = (UIUserModule) iterator.next();
			listModules.add(uiUserModule.getModuleBean());
		}
		userMenuPreferenceModel.setListModules(listModules);
	}

	public void doFilter(ListenerContext context) throws FunctionalException {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		try {
			UserDesktop userDesktop = context.userAction().userDesktop();
			UserMenuPreferenceModel userMenuPreferenceModel = (UserMenuPreferenceModel) context.userAction().getDataObject();
			UserMenuBean userMenuBean = userMenuPreferenceModel.getSearcheBean();
			userMenuBean.setUserId(userDesktop.userContext().userId());
			List<DataBusinessBean> dataList = userMenuPreferenceBusiness.getByCriteria(userMenuBean, context.userAction().getActionBeanId(), userDesktop.userContext());
			userMenuPreferenceModel.setUserMenus(dataList);
			List<DataBusinessBean> listMenusAction = userMenuPreferenceBusiness.getMenuAction(userMenuBean, context.userAction().getActionBeanId(), userDesktop.userContext());
			userMenuPreferenceModel.setListMenusAction(listMenusAction);
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		}
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	public void doValider(ListenerContext context) {
		try {
			UserDesktop userDesktop = context.userAction().userDesktop();
			UserMenuPreferenceModel userMenuPreferenceModel = (UserMenuPreferenceModel) context.userAction().getDataObject();
			userMenuPreferenceModel.getSearcheBean().setUserId(userDesktop.userContext().userId());
			List<DataBusinessBean> userMenus = userMenuPreferenceModel.getUserMenus();
			List<DataBusinessBean> userMenusClear = new ArrayList<DataBusinessBean>();
			List<Long> keys = new ArrayList<Long>();
			for (int i = 0; i < userMenus.size(); i++) {
				UserMenuBean userMenuBean = (UserMenuBean) userMenus.get(i);
				if (userMenuBean.getMenuId() != null && userMenuBean.getMenuId().intValue() != 0 && !keys.contains(userMenuBean.getMenuId())) {
					userMenuBean.setUserId(userMenuPreferenceModel.getSearcheBean().getUserId());
					userMenuBean.setModuleId(userMenuPreferenceModel.getSearcheBean().getModuleId());
					userMenusClear.add(userMenuBean);
					keys.add(userMenuBean.getMenuId());
				}
			}
			userMenuPreferenceBusiness.upadteUserMenu(userMenuPreferenceModel.getUserMenus(), userMenuPreferenceModel.getSearcheBean(), context.userAction().getActionBeanId(),
					userDesktop.userContext());
			userMenuPreferenceModel.setUserMenus(userMenusClear);
			List<UserMenuBean> list = ((UserContextBeanImpl) userDesktop.userContext()).getUserMenu(userMenuPreferenceModel.getSearcheBean().getModuleId());
			if (list != null)
				list.clear();
			for (int i = 0; i < userMenusClear.size(); i++) {
				((UserContextBeanImpl) userDesktop.userContext()).addUserMenu((UserMenuBean) userMenusClear.get(i));
			}
			context.userDesktop().messagesHandler().addMessage("0120008");
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
		}
		context.userAction().goToCurrentWindow(context.webContext());
	}

	public void doHomePage(ListenerContext context) {
		context.userDesktop().userDesktopNavigation().goToDefaultUserAction(context.webContext());
	}
}
