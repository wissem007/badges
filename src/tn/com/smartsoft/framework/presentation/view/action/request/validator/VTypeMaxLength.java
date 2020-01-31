package tn.com.smartsoft.framework.presentation.view.action.request.validator;

import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public class VTypeMaxLength implements ValidatorRequestField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageKey;
	private int maxLength;
	
	public VTypeMaxLength(int maxLength, String messageKey) {
		super();
		this.maxLength = maxLength;
		this.messageKey = messageKey;
	}
	
	public void onValidator(DataRequestField dataRequestField) {
		if (maxLength <= 0)
			return;
		if (dataRequestField.getValue() != null && (dataRequestField.getValue().toString().length() <= maxLength)) {
			return;
		}
		dataRequestField.setValidRequestValue(false);
		dataRequestField.getMessagesHandler().addMessage(messageKey, dataRequestField.getLibelle());
	}
	
}
