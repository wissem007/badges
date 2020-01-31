package tn.com.smartsoft.iap.system.presentation.controler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.view.desktop.DefaultUserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuItem;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserBean;
import tn.com.smartsoft.iap.administration.securite.utilisateur.beans.UserPreferenceBean;
import tn.com.smartsoft.iap.system.business.UserBusiness;
import tn.com.smartsoft.iap.system.presentation.model.LoginModel;

public class UserAccesControler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserBusiness userBusiness;

	public void login(ListenerContext context) {
		UserDesktop userDesktop = context.userAction().userDesktop();
		LoginModel loginModel = (LoginModel) context.userAction().getDataObject();
		UserBean userBean = loginModel.getUserBean();
		try {
			userDesktop.userDesktopNavigation().doStartSession(context.webContext(),
					userBusiness.authenticate(userBean));
		} catch (FunctionalException e) {
			userBean.setPasseWord(null);
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}

	public void getDispalyName(ListenerContext context) throws FunctionalException {
		LoginModel loginModel = (LoginModel) context.userAction().getDataObject();
		UserBean user = userBusiness.getDisplayUser(loginModel.getUserBean());
		if (user == null)
			user = new UserBean();
		loginModel.getUserBean().setDisplayName(user.getDisplayName());
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData("displayName", loginModel.getUserBean().getDisplayName());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	public void logout(ListenerContext context) throws FunctionalException {
		UserDesktop userDesktop = context.userAction().userDesktop();
		UserBean user = userDesktop.userContext().getUser();
		Map<String, String> userPrefs = userDesktop.userContext().getUserPreferences();
		Map<String, UserPreferenceBean> preferences = new HashMap<String, UserPreferenceBean>();
		Iterator<String> iterator = userPrefs.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) userPrefs.get(key);
			preferences.put(key, new UserPreferenceBean(key, user.getUserId(), value));
		}
		user.setPreferences(preferences);
		userBusiness.savePreferance(userDesktop.userContext(), user);
		userDesktop.userDesktopNavigation().doEndSession(context.webContext());
	}

	public void changeOrganisme(ListenerContext context) {
		UserDesktop userDesktop = context.userAction().userDesktop();
		LoginModel loginModel = (LoginModel) context.userAction().getDataObject();
		int organismeId = context.webContext().request().requestParameter().getParameterAsInteger("organismeId");
		int societeId = context.webContext().request().requestParameter().getParameterAsInteger("societeId");
		loginModel.getOrganismeId().setOrganismeId(new Long(organismeId));
		loginModel.getOrganismeId().setSocieteId(new Long(societeId));
		userDesktop.userContext().switchUserOrganisme(loginModel.getOrganismeId());
		if (userDesktop.userContext().getCurrentUserOrganisme().getModuleExplorer() == null)
			userDesktop.userContext().getCurrentUserOrganisme().setModuleExplorer(ApplicationConfiguration
					.applicationManager().presentationBeanFactory().loadModuleExplorers(userDesktop));
		((DefaultUserDesktop) userDesktop)
				.setModuleExplorer(userDesktop.userContext().getCurrentUserOrganisme().getModuleExplorer());
		userDesktop.menuBar().setMenuActions((UIMenuItem) null);
		userDesktop.menuBar().setDisplayActionTitle(null);
		context.userDesktop().userDesktopNavigation().goToModule(context.userDesktop().curentUserModule().getId(),
				context.webContext());
	}

	public void changeSociete(ListenerContext context) {
		UserDesktop userDesktop = context.userAction().userDesktop();
		int societeId = context.webContext().request().requestParameter().getParameterAsInteger("societeId");
		userDesktop.userContext().switchUserSociete(new Long(societeId));
		if (userDesktop.userContext().getCurrentUserOrganisme().getModuleExplorer() == null)
			userDesktop.userContext().getCurrentUserOrganisme().setModuleExplorer(ApplicationConfiguration
					.applicationManager().presentationBeanFactory().loadModuleExplorers(userDesktop));
		((DefaultUserDesktop) userDesktop)
				.setModuleExplorer(userDesktop.userContext().getCurrentUserOrganisme().getModuleExplorer());
		context.userDesktop().userDesktopNavigation().goToModule(context.userDesktop().curentUserModule().getId(),
				context.webContext());
	}

	public void setUserBusiness(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}

}
