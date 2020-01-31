package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.tags.delegate.UITagDelegator;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIDefauldParserContext;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;

public class UITag implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tag;
	private String contentProperty;
	protected Class<?> tagClass;
	protected Map<String, UIAttributeTag> attributes = new HashMap<String, UIAttributeTag>();
	protected Map<String, UIParameterTag> parameters = new HashMap<String, UIParameterTag>();
	protected Map<String, UIChildTag> childTags = new HashMap<String, UIChildTag>();
	protected Map<String, UIPropertyTag> propertys = new HashMap<String, UIPropertyTag>();
	protected Map<String, UIPropertiesTag> propertiess = new HashMap<String, UIPropertiesTag>();
	private String extendsTag;

	public UITag() {
		super();
	}

	public String getExtendsTag() {
		return extendsTag;
	}

	public void setExtendsTag(String extendsTag) {
		this.extendsTag = extendsTag;
	}

	public Class<?> getTagClass() {
		return tagClass;
	}

	public void setTagClass(Class<?> tagClass) {
		this.tagClass = tagClass;
	}

	public void addProperties(UIPropertiesTag properties) {
		propertiess.put(properties.getTag(), properties);
	}

	public void addProperty(UIPropertyTag property) {
		propertys.put(property.getTag(), property);
	}

	public void addAttribute(UIAttributeTag attributeTag) {
		attributes.put(attributeTag.getAttribute(), attributeTag);
	}

	public void addParamter(String property, String value) {
		parameters.put(property, new UIParameterTag(property, value));
	}

	public void addChild(UIChildTag childTag) {
		childTags.put(childTag.getTag(), childTag);
	}

	public Map<String, UIChildTag> getChildTags() {
		return childTags;
	}

	public UIChildTag getChildTag(String tagName) {
		return (UIChildTag) childTags.get(tagName);
	}

	public Map<String, UIParameterTag> getParameters() {
		return parameters;
	}

	public Map<String, UIAttributeTag> getAttributes() {
		return attributes;
	}

	public Map<String, UIPropertyTag> getPropertys() {
		return propertys;
	}

	public Map<String, UIPropertiesTag> getPropertiess() {
		return propertiess;
	}

	public String getContentProperty() {
		return contentProperty;
	}

	public void setContentProperty(String contentProperty) {
		this.contentProperty = contentProperty;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, UIParserContext context) throws UIParseException {
		UITagDelegator uiTagDelegate = new UITagDelegator(this, (UIDefauldParserContext) context);
		uiTagDelegate.addXsd(xsSchema, xsComplexType, context);
	}

	public UIObject parse(UIObject component, UIDefauldParserContext context, TagHandler tagHandler) throws UIParseException {
		UITagDelegator uiTagDelegate = new UITagDelegator(this, context);
		uiTagDelegate.parseAttributes(tagHandler, component);
		uiTagDelegate.parseChilds(tagHandler, component);
		return component;
	}

	public UIObject parse(UIDefauldParserContext context, TagHandler tagHandler) throws UIParseException {
		UITagDelegator uiTagDelegate = new UITagDelegator(this, context);
		UIObject component = uiTagDelegate.create(tagHandler);
		return parse(component, context, tagHandler);
	}

}
