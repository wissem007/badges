package tn.com.smartsoft.iap.dictionary.graphique.message.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;
import tn.com.smartsoft.iap.dictionary.graphique.message.presentation.model.MessageModel;
import tn.com.smartsoft.iap.dictionary.graphique.messageType.beans.MessageTypeBean;

public class MessageControler extends GenericEntiteControler {

	private static final long serialVersionUID = 1L;
	protected GenericEntiteBusiness messageTypeBusiness;
	protected GenericEntiteBusiness dataEntitesSimplesBusiness;
	private Object String;

	public void setDataEntitesSimplesBusiness(GenericEntiteBusiness dataEntitesSimplesBusiness) {
		this.dataEntitesSimplesBusiness = dataEntitesSimplesBusiness;
	}

	public void setMessageTypeBusiness(GenericEntiteBusiness messageTypeBusiness) {
		this.messageTypeBusiness = messageTypeBusiness;
	}

	public void onInitListMessageType(ListenerContext context) throws FunctionalException {
		MessageModel entiteModel = (MessageModel) context.userAction().getDataObject();
		List listMessagesType = messageTypeBusiness.getByCriteria(new MessageTypeBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListMessagesType(listMessagesType);

		if (context.userAction().getUserActionId().getActionBeanId().getActionId().equals("creer")) {
			if (entiteModel.getDetailBean() == null)
				entiteModel.setDetailBean(new MessageBean());

			MessageBean messageBean = (MessageBean) entiteModel.getDetailBean();
			messageBean.setIsAlert(true);
		} else {
			if (entiteModel.getSearcheBean() == null)
				entiteModel.setSearcheBean(new MessageBean());

			MessageBean messageBean = (MessageBean) entiteModel.getSearcheBean();
			messageBean.setIsAlert(true);
		}

	}

}
