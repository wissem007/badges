package tn.com.smartsoft.framework.presentation.view.action.request.validator;

import org.apache.commons.collections.comparators.ComparableComparator;

import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public class VTypeMaxValue implements ValidatorRequestField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String messageKey;
	public Object maxValue;
	
	public VTypeMaxValue(Object maxValue, String messageKey) {
		super();
		this.maxValue = maxValue;
		this.messageKey = messageKey;
	}
	
	public void onValidator(DataRequestField dataRequestField) {
		if (maxValue == null)
			return;
		if (true)
			return;
		@SuppressWarnings("unused")
		ComparableComparator comparableComparator = new ComparableComparator();
		if (comparableComparator.compare(dataRequestField.getValue(), maxValue) > 0) {
			return;
		}
		dataRequestField.setValidRequestValue(false);
		dataRequestField.getMessagesHandler().addMessage(messageKey, dataRequestField.getLibelle());
	}
}
