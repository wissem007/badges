package tn.com.smartsoft.framework.presentation.view.action.request;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public abstract interface ValidatorRequestField extends UIObject {
	
	public abstract void onValidator(DataRequestField dataRequestField);
	
}
