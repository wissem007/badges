package tn.com.smartsoft.configGenerale.devises.devise.presentation.controler;

import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControlerParams;

public class DeviseControlerParams extends GenericEntiteControlerParams{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String messageExistance;

	public String getMessageExistance() {
		return messageExistance;
	}

	public void setMessageExistance(String messageExistance) {
		this.messageExistance = messageExistance;
	}


}
