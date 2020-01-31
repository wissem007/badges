package tn.com.smartsoft.configGenerale.devises.devise.presentation.controler;

import java.util.List;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.NumberUtils;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.devises.devise.business.DeviseBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class DeviseControler extends GenericEntiteControler{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public void doValiderCreate(ListenerContext context) {
		try {
			GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
			DeviseBusiness deviseBusiness = (DeviseBusiness) entiteBusiness;
			List listDevise = deviseBusiness.getByCriteria((DeviseBean) entiteModel.getDetailBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop()
					.userContext());
			if (!listDevise.isEmpty()) {
				context.userDesktop().messagesHandler().addMessage(((DeviseControlerParams) controlerParams).getMessageExistance(), new String[] { "Devise" });
				context.userAction().goToCurrentWindow(context.webContext());
				return;
			}
			deviseBusiness.create((DeviseBean) entiteModel.getDetailBean(), context.userDesktop().userContext());
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
			DeviseBusiness deviseBusiness = (DeviseBusiness) entiteBusiness;
			DeviseBean dev = new DeviseBean();
			DeviseBean devold = (DeviseBean) entiteModel.getDetailBean();
			dev.setLibelle(devold.getLibelle());
			List listdev = deviseBusiness.getByCriteria(dev, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			if (!listdev.isEmpty()) {
				switch (listdev.size()) {
				case 1:
					DeviseBean dv = (DeviseBean) listdev.get(0);
					if (NumberUtils.isEgale(dv.getDeviseId(), devold.getDeviseId())) break;
				default:
					context.userDesktop().messagesHandler().addMessage(((DeviseControlerParams) controlerParams).getMessageExistance(), new String[] { "Devise" });
					context.userAction().goToCurrentWindow(context.webContext());
					return;
				}
			}
			deviseBusiness.update((DeviseBean) entiteModel.getDetailBean(), context.userDesktop().userContext());
			entiteModel.getListBean().set(entiteModel.getBeanIndex(), entiteModel.getDetailBean());
			context.userDesktop().messagesHandler().addMessage(controlerParams.getMessageUpdateValid());
			context.userAction().goToWindow(controlerParams.getListWindowId(), context.webContext());
		} catch (FunctionalException e) {
			context.userDesktop().messagesHandler().addMessage(e);
			context.userAction().goToCurrentWindow(context.webContext());
		}
	}
}
