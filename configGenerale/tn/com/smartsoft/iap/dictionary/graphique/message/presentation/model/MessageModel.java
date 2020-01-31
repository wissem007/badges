package tn.com.smartsoft.iap.dictionary.graphique.message.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class MessageModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;
	private List listMessagesType;

	public List getListMessagesType() {
		return listMessagesType;
	}

	public void setListMessagesType(List listMessagesType) {
		this.listMessagesType = listMessagesType;
	}

}
