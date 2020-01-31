package tn.com.smartsoft.iap.dictionary.graphique.userType.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class UserTypeModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;

	private List listMessage;

	public List getListMessage() {
		return listMessage;
	}

	public void setListMessage(List listMessage) {
		this.listMessage = listMessage;
	}

}
