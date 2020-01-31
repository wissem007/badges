package tn.com.smartsoft.framework.presentation.view.window.handler;

import java.util.Hashtable;
import java.util.Map;

import tn.com.smartsoft.framework.presentation.UIObject;

public class UIDefaultAttributeHandler implements UIAttributeHandler, UIObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> attributes;
	
	public String[] geAttributesName() {
		if (attributes == null) {
			return new String[0];
		} else {
			String attributeNames[] = new String[attributes.size()];
			return (String[]) attributes.keySet().toArray(attributeNames);
		}
	}
	
	public String getAttribut(String name) {
		if (attributes == null)
			return null;
		else
			return (String) attributes.get(name);
	}
	
	public void setAttribut(String name, String value) {
		if (attributes == null)
			attributes = new Hashtable<String, String>();
		if (value == null)
			attributes.remove(name);
		else
			attributes.put(name, value);
	}
	
	public void setAttribut(Map<String, String> attributes) {
		if (attributes == null)
			return;
		attributes.putAll(attributes);
	}
	
	public void setAttribut(String name, Object value) {
		if (value != null)
			setAttribut(name, value.toString());
	}
	
	public void setAttribut(String name, boolean value) {
		setAttribut(name, (Object) Boolean.valueOf(value));
	}
	
	public void setAttribut(String name, int value) {
		setAttribut(name, (Object) Integer.valueOf(value));
	}
	
}
