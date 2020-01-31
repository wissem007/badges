package tn.com.smartsoft.iap.dictionary.graphique.userType.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.beans.UserTypeBean;
import tn.com.smartsoft.iap.dictionary.graphique.userType.presentation.model.UserTypeModel;

public class UserTypeControler extends GenericEntiteControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness messageBusiness;

	public void setMessageBusiness(GenericEntiteBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}

	public void onInitListMessage(ListenerContext context) throws FunctionalException {
		UserTypeModel entiteModel = (UserTypeModel) context.userAction().getDataObject();
		List listMessage = messageBusiness.getByCriteria(new MessageBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListMessage(listMessage);

		if (context.userAction().getUserActionId().getActionBeanId().getActionId().equals("creer")) {
			if (entiteModel.getDetailBean() == null)
				entiteModel.setDetailBean(new UserTypeBean());

			UserTypeBean userTypeBean = (UserTypeBean) entiteModel.getDetailBean();
			userTypeBean.setIsLtr(true);
			userTypeBean.setIsComparable(true);
		} else {
			if (entiteModel.getSearcheBean() == null)
				entiteModel.setSearcheBean(new UserTypeBean());

			UserTypeBean userTypeBean = (UserTypeBean) entiteModel.getSearcheBean();
			userTypeBean.setIsLtr(true);
			userTypeBean.setIsComparable(true);
		}
	}

}
