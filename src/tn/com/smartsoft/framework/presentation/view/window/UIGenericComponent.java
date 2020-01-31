package tn.com.smartsoft.framework.presentation.view.window;

import java.util.List;
import java.util.Map;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutExpression;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIDefaultEventHandler;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIAttributeHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultAttributeHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIGenericComponent extends UIComponent implements UIAttributeHandler,UIEventHandler{
	
	/**
	 * 
	 */
	private static final long									serialVersionUID	= 1L;
	private UIDefaultAttributeHandler							attributeHandler	= new UIDefaultAttributeHandler();
	private UIDefaultEventHandler								defaultEventHandler	= new UIDefaultEventHandler(this);
	private UIDefaultComponentHandler<UIExtAttrebutComponent>	attrebutComponent	= new UIDefaultComponentHandler<UIExtAttrebutComponent>(this);
	
	public void addAttrebutComponent(String contenu) {
		attrebutComponent.addItem(contenu);
	}
	public void addAttrebutComponent(UIExtAttrebutComponent component) {
		if (BeanObjectUtils.isWriteableProperty(this, component.getExtAttrebut()) && component instanceof UIExtAttrebutExpression) {
			BeanObjectUtils.setPropertyValue(this, component.getExtAttrebut(), ((UIExtAttrebutExpression) component).getExpression());
		}
		else attrebutComponent.addItem(component);
	}
	public UIExtAttrebutComponent findAttrebutComponent(String id) {
		return attrebutComponent.findItem(id);
	}
	public UIExtAttrebutComponent getAttrebutComponent(String id) {
		return attrebutComponent.getItem(id);
	}
	public List<UIExtAttrebutComponent> getAttrebutComponents() {
		return attrebutComponent.getItems();
	}
	public boolean hasAttrebutComponent(String id) {
		return attrebutComponent.hasItem(id);
	}
	public List<String> attrebutComponentIds() {
		return attrebutComponent.itemIds();
	}
	public int attrebutComponentSize() {
		return attrebutComponent.itemSize();
	}
	public void removeAttrebutComponent(int start, int end) {
		attrebutComponent.removeItem(start, end);
	}
	public void removeAttrebutComponent(int start) {
		attrebutComponent.removeItem(start);
	}
	public void removeAttrebutComponent(String id) {
		attrebutComponent.removeItem(id);
	}
	public void removeAttrebutComponent(String[] ids) {
		attrebutComponent.removeItem(ids);
	}
	public void fireActionEvent(WebContext context, ClientEvent eventName) {
		defaultEventHandler.fireActionEvent(context, eventName);
	}
	public void fireActionEvent(WebContext context, String eventName) {
		defaultEventHandler.fireActionEvent(context, eventName);
	}
	public void fireActionEvent(WebContext context) {
		defaultEventHandler.fireActionEvent(context);
	}
	public UIEvent getEvent(String event) {
		return defaultEventHandler.getEvent(event);
	}
	public void addEvent(UIEvent event) {
		defaultEventHandler.addEvent(event);
	}
	public void removeEvent(String event) {
		defaultEventHandler.removeEvent(event);
	}
	public String[] getEventNames() {
		return defaultEventHandler.getEventNames();
	}
	public String[] geAttributesName() {
		return attributeHandler.geAttributesName();
	}
	public String getAttribut(String name) {
		return attributeHandler.getAttribut(name);
	}
	public void setAttribut(Map<String, String> attributes) {
		attributeHandler.setAttribut(attributes);
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
}
