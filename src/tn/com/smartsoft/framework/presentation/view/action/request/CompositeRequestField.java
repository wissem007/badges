package tn.com.smartsoft.framework.presentation.view.action.request;

import tn.com.smartsoft.framework.presentation.UIObject;

public interface CompositeRequestField extends UIObject {
	public UIObject getRequestField(String childFieldId);
}
