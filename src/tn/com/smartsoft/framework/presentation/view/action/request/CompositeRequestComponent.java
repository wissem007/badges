package tn.com.smartsoft.framework.presentation.view.action.request;

import java.util.Hashtable;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public class CompositeRequestComponent implements CompositeRequestField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, UIObject> fields = new Hashtable<String, UIObject>();
	private UIComponent parentComponent;
	
	public CompositeRequestComponent(UIComponent parentComponent) {
		super();
		this.parentComponent = parentComponent;
	}
	
	public void addRequestField(String childFieldId, UIObject component) {
		if (component == null)
			return;
		if (component instanceof UIComponent) {
			((UIComponent) component).setParent(parentComponent);
		}
		fields.put(childFieldId, component);
		return;
	}
	
	public UIObject getRequestField(String childFieldId) {
		return fields.get(childFieldId);
	}
	
}
