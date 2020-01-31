package tn.com.smartsoft.framework.presentation.view.desktop;

import org.apache.commons.beanutils.MethodUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.dao.utils.DbSessionUtils;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.WebContext;

public class DesktopControleur implements UIObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDesktop userDesktop;

	public DesktopControleur(UserDesktop userDesktop) {
		super();
		this.userDesktop = userDesktop;
	}

	private void fireActionExecuteEvent(WebContext context, RequestEventInfo requestEventInfo) {
		if (!userDesktop.accesApplication().isLogin() && userDesktop.curentUserAction() == null) {
			userDesktop.accesApplication().fireActionEvent(context);
		} else {
			DesktopElement desktopElement = desktopElement(requestEventInfo.desktopPartType());
			if (desktopElement == null)
				userDesktop.accesApplication().fireActionEvent(context);
			else
				desktopElement.fireActionEvent(context);
		}

	}

	private void fireActionViewEvent(WebContext context) {
		if (!userDesktop.accesApplication().isLogin() && userDesktop.curentUserAction() == null)
			userDesktop.accesApplication().fireActionEvent(context);
		context.responseWindow();
	}

	public void fireActionEvent(WebContext context) {
		RequestEventInfo requestEventInfo = context.request().requestEventInfo();
		try {
			if (DesktopPartType.isView(requestEventInfo.desktopPartType())) {
				fireActionViewEvent(context);
			} else {
				fireActionExecuteEvent(context, requestEventInfo);
			}
		} finally {
			clearUserEventRessource(context);
		}
	}

	private void clearUserEventRessource(WebContext context) {
		try {
			DbSessionUtils.rollbackTransactionAndCloseAll();
		} catch (Exception e) {
			DbSessionUtils.clearResource();
		}
	}

	public DesktopElement desktopElement(String name) {
		DesktopPartType desktopPartType = DesktopPartType.parse(name);
		if (desktopPartType == null)
			throw new TechnicalException("Invalid DesktopPartType: " + desktopPartType);
		try {
			return (DesktopElement) MethodUtils.invokeMethod(userDesktop, desktopPartType.getMethode(), new Object[] {}, new Class[] {});
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
