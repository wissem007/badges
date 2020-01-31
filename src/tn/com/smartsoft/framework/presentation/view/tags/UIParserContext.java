package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.List;
import java.util.Map;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public interface UIParserContext {

	public abstract String generateId();

	public abstract void attachedToWindow(UIComponent component);

	public abstract void dettachedFromWindow(String id);

	public abstract boolean hasChild(String id);

	public abstract UIComponent findChild(String id);

	public abstract List<UIComponent> getChild(Class<?> type);

	public abstract Map<String, String> getFieldValues();

	public abstract UITag getTagComponent(String tagName);

	public UIObject parse(UIObject component, TagHandler tagHandler, String refTag) throws UIParseException;

	public UIObject parse(TagHandler tagHandler, String refTag) throws UIParseException;

	public abstract UIObject parse(TagHandler tagHandler) throws UIParseException;

	public abstract UIObject parse(UIObject component, TagHandler tagHandler) throws UIParseException;

	public abstract void updateIdValue(Object component) throws UIParseException;

	public abstract boolean isAttachedToWindow(Object component);

}