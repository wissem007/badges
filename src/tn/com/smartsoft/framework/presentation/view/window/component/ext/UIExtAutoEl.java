package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIAttributeHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultAttributeHandler;

public class UIExtAutoEl implements UIObject, UIAttributeHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected UIDefaultAttributeHandler attributeHandler = new UIDefaultAttributeHandler();
	private String tag;

	public String[] geAttributesName() {
		return attributeHandler.geAttributesName();
	}

	public String getAttribut(String name) {
		return attributeHandler.getAttribut(name);
	}

	public void setAttribut(String name, boolean value) {
		attributeHandler.setAttribut(name, value);
	}

	public void setAttribut(String name, int value) {
		attributeHandler.setAttribut(name, value);
	}

	public void setAttribut(String name, Object value) {
		attributeHandler.setAttribut(name, value);
	}

	public void setAttribut(String name, String value) {
		attributeHandler.setAttribut(name, value);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
