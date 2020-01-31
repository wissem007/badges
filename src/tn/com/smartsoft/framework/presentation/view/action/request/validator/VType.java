package tn.com.smartsoft.framework.presentation.view.action.request.validator;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public class VType implements ValidatorRequestField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PropertyModel model;
	protected String messageBlankKey;
	protected String messageTypeKey;
	private boolean allowBlank = true;
	
	public VType(PropertyModel model, boolean allowBlank, String messageTypeKey, String messageBlankKey) {
		super();
		this.allowBlank = allowBlank;
		this.model = model;
		this.messageTypeKey = messageTypeKey;
		this.messageBlankKey = messageBlankKey;
	}
	
	public VType(String name, String userType, Class<?> JavaType, boolean allowBlank, String messageTypeKey, String messageBlankKey) {
		this(new PropertyModel(name, userType, JavaType), allowBlank, messageTypeKey, messageBlankKey);
	}
	
	public void onValidator(DataRequestField dataRequestField) {
		boolean isblank = dataRequestField.getRequestValue() == null || StringUtils.isBlank(dataRequestField.getRequestValue().toString());
		if (isblank && !allowBlank) {
			dataRequestField.setValidRequestValue(false);
			dataRequestField.getMessagesHandler().addMessage(messageBlankKey, dataRequestField.getLibelle());
			return;
		}
		if (isblank) {
			dataRequestField.setValue(null);
			return;
		}
		boolean isValid = model.validate(dataRequestField.getRequestValue());
		if (!isValid) {
			if (StringUtils.isBlank(messageTypeKey))
				messageTypeKey = model.getFormatter().getUserType().getMessagesId();
			dataRequestField.setValidRequestValue(false);
			dataRequestField.getMessagesHandler().addMessage(messageTypeKey, dataRequestField.getLibelle());
			return;
		}
		dataRequestField.setValue(model.getAsObject(dataRequestField.getRequestValue()));
	}
}
