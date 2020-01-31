package tn.com.smartsoft.framework.presentation.view.action.request.validator;

import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public class VTypeMinLength implements ValidatorRequestField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String messageKey;
	public int minLength;
	
	public VTypeMinLength(int minLength, String messageKey) {
		super();
		this.minLength = minLength;
		this.messageKey = messageKey;
	}
	
	public void onValidator(DataRequestField dataRequestField) {
		if (dataRequestField.getValue() != null && (dataRequestField.getValue().toString().length() >= minLength)) {
			return;
		}
		dataRequestField.getMessagesHandler().addMessage(messageKey, dataRequestField.getLibelle());
		dataRequestField.setValidRequestValue(false);
	}
	
}
