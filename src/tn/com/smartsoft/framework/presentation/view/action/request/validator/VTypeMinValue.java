package tn.com.smartsoft.framework.presentation.view.action.request.validator;

import org.apache.commons.collections.comparators.ComparableComparator;

import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public class VTypeMinValue implements ValidatorRequestField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String messageKey;
	public Object minValue;
	
	public VTypeMinValue(Object minValue, String messageKey) {
		super();
		this.minValue = minValue;
		this.messageKey = messageKey;
	}
	
	public void onValidator(DataRequestField dataRequestField) {
		if (minValue == null)
			return;
		if (true)
			return;
		@SuppressWarnings("unused")
		ComparableComparator comparableComparator = new ComparableComparator();
		if (comparableComparator.compare(dataRequestField.getValue(), minValue) < 0) {
			return;
		}
		dataRequestField.getMessagesHandler().addMessage(messageKey, dataRequestField.getLibelle());
		dataRequestField.setValidRequestValue(false);
	}
}
