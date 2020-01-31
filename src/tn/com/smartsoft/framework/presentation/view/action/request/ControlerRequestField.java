package tn.com.smartsoft.framework.presentation.view.action.request;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;

public interface ControlerRequestField extends UIObject {
	public void validateDataType(DataRequestField dataRequestField);
	
	public void validate(DataRequestField dataRequestField);
	
	public void commit(DataRequestField dataRequestField);
	
	public String getLibelle();
	
	public String getId();
}
