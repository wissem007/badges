package tn.com.smartsoft.framework.presentation.view.tags.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.tags.TagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.UIHtmlTag;
import tn.com.smartsoft.framework.presentation.view.tags.UIParserContext;
import tn.com.smartsoft.framework.presentation.view.tags.UITag;
import tn.com.smartsoft.framework.presentation.view.tags.UITagManager;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtField;

public class UIDefauldParserContext implements UIObject, UIParserContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UITagManager tagManager;
	private UIParserParameter systemParserParameter;
	private int idCounter;
	private Hashtable<String, UIComponent> childs = new Hashtable<String, UIComponent>();

	public UIDefauldParserContext(UITagManager tagManager, UIParserParameter systemParserParameter) {
		super();
		this.tagManager = tagManager;
		this.systemParserParameter = systemParserParameter;
	}

	public UITagManager tagManager() {
		return tagManager;
	}

	public String generateId() {
		idCounter++;
		return "id" + idCounter;
	}

	public void attachedToWindow(UIComponent component) {
		if (component == null)
			return;
		childs.put(component.getId(), component);
		return;
	}

	public void dettachedFromWindow(String id) {
		childs.remove(id);
	}

	public boolean hasChild(String id) {
		return (childs.containsKey(id));
	}

	public UIComponent findChild(String id) {
		return (UIComponent) childs.get(id);
	}

	public List<UIComponent> getChild(Class<?> type) {
		ArrayList<UIComponent> cmps = new ArrayList<UIComponent>();
		Collection<UIComponent> childsValue = childs.values();
		for (Iterator<UIComponent> iterator = childsValue.iterator(); iterator.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();
			if (type.isAssignableFrom(component.getClass())) {
				cmps.add(component);
			}
		}
		return cmps;
	}

	public Map<String, String> getFieldValues() {
		List<UIComponent> fields = getChild(UIExtField.class);
		Map<String, String> values = new HashMap<String, String>();
		for (int i = 0; i < fields.size(); i++) {
			UIExtField field = (UIExtField) fields.get(i);
			values.put(field.getId(), field.getCustomValue());
		}
		return values;
	}

	public UITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(UITagManager tagManager) {
		this.tagManager = tagManager;
	}

	public UIParserParameter systemParserParameter() {
		return systemParserParameter;
	}

	public UITag getTagComponent(String tagName) {
		UITag uiTag = tagManager().getTag(tagName);
		if (uiTag == null || uiTag.getTagClass() == null) {
			return new UIHtmlTag(tagName);
		} else {
			return uiTag;
		}
	}

	public UIObject parse(TagHandler tagHandler) throws UIParseException {
		UITag uiTag = getTagComponent(tagHandler.getName());
		return uiTag.parse(this, tagHandler);
	}

	public UIObject parse(UIObject component, TagHandler tagHandler) throws UIParseException {
		UITag uiTag = getTagComponent(tagHandler.getName());
		return uiTag.parse(component, this, tagHandler);
	}

	public UIObject parse(TagHandler tagHandler, String refTag) throws UIParseException {
		UITag uiTag = getTagComponent(refTag);
		return uiTag.parse(this, tagHandler);
	}

	public UIObject parse(UIObject component, TagHandler tagHandler, String refTag) throws UIParseException {
		UITag uiTag = getTagComponent(refTag);
		return uiTag.parse(component, this, tagHandler);
	}

	public void updateIdValue(Object component) throws UIParseException {
		try {
			if (component instanceof UIComponent) {
				if (StringUtils.isNotBlank(((UIComponent) component).getId())) {
					return;
				}
				((UIComponent) component).setGeneratedId(true);
				((UIComponent) component).setId(generateId());
			}
		} catch (Exception e) {
			if (e instanceof UIParseException) {
				throw (UIParseException) e;
			}
			throw new UIParseException(e);
		}
	}

	public boolean isAttachedToWindow(Object component) {
		return (component instanceof UIComponent);
	}
}
