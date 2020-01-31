package tn.com.smartsoft.iap.system.presentation.controler;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.system.business.UserBusiness;
import tn.com.smartsoft.iap.system.presentation.model.LoginModel;

public class UpdatePassWordControler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserBusiness userBusiness;

	public void initUpadte(ListenerContext context) {
		UserDesktop userDesktop = context.userAction().userDesktop();
		LoginModel loginModel = (LoginModel) context.userAction().getDataObject();
		userDesktop.userContext().getUser().setOldEditPasseWord(userDesktop.userContext().getUser().getPasseWord());
		loginModel.setUserBean(userDesktop.userContext().getUser());
		context.userAction().goToWindow("detailWindow", context.webContext());
	}

	public void doValider(ListenerContext context) {
		UserDesktop userDesktop = context.userAction().userDesktop();
		LoginModel loginModel = (LoginModel) context.userAction().getDataObject();
		try {
			userBusiness.changePassWord(userDesktop.userContext(), loginModel.getUserBean());
			context.userDesktop().messagesHandler().addMessage("0120008");
			context.userDesktop().userDesktopNavigation().goToDefaultUserAction(context.webContext());
		} catch (FunctionalException e) {
			userDesktop.userContext().getUser().setPasseWord(userDesktop.userContext().getUser().getOldEditPasseWord());
			userDesktop.userContext().getUser().setConfPasseWord("");
			userDesktop.userContext().getUser().setOldPasseWord("");
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}

	public void doHomePage(ListenerContext context) {
		context.userDesktop().userDesktopNavigation().goToDefaultUserAction(context.webContext());
	}

	public void doRefreshDetail(ListenerContext context) {
		context.userAction().goToWindow("detailWindow", context.webContext());
	}

	public void setUserBusiness(UserBusiness userBusiness) {
		this.userBusiness = userBusiness;
	}
}
