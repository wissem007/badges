package tn.com.smartsoft.framework.presentation.view.tags.delegate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.tags.TagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.UIAttributeTag;
import tn.com.smartsoft.framework.presentation.view.tags.UIChildTag;
import tn.com.smartsoft.framework.presentation.view.tags.UIParameterTag;
import tn.com.smartsoft.framework.presentation.view.tags.UIParserContext;
import tn.com.smartsoft.framework.presentation.view.tags.UIPropertiesTag;
import tn.com.smartsoft.framework.presentation.view.tags.UIPropertyTag;
import tn.com.smartsoft.framework.presentation.view.tags.UITag;
import tn.com.smartsoft.framework.presentation.view.tags.handler.XmlTagHandler;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIDefauldParserContext;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ParserBeanAccessorUtils;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ResourceParserUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlTextBox;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIAttributeHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;

public class UITagDelegator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UITag uiTagDelegate;
	private UIDefauldParserContext parserContext;

	public UITagDelegator(UITag uiTagDelegate, UIDefauldParserContext parserContext) {
		super();
		this.uiTagDelegate = uiTagDelegate;
		this.parserContext = parserContext;
	}

	public UITagDelegator getExtendsTag() {
		if (StringUtils.isNotBlank(uiTagDelegate.getExtendsTag()))
			return new UITagDelegator(parserContext.tagManager().getTag(uiTagDelegate.getExtendsTag()), parserContext);
		else
			return null;
	}

	public UIPropertiesTag getPropertiesTag(String name) {
		if (uiTagDelegate.getPropertiess().get(name) != null)
			return (UIPropertiesTag) uiTagDelegate.getPropertiess().get(name);
		UITagDelegator extendsTag = getExtendsTag();
		if (extendsTag != null)
			return extendsTag.getPropertiesTag(name);
		return null;
	}

	public UIPropertyTag getPropertyTag(String name) {
		if (uiTagDelegate.getPropertys().get(name) != null)
			return (UIPropertyTag) uiTagDelegate.getPropertys().get(name);
		UITagDelegator extendsTag = getExtendsTag();
		if (extendsTag != null)
			return extendsTag.getPropertyTag(name);
		return null;
	}

	public UIChildTag getChildTag(String name) {
		if (uiTagDelegate.getChildTag(name) != null)
			return uiTagDelegate.getChildTag(name);
		UITagDelegator extendsTag = getExtendsTag();
		if (extendsTag != null)
			return extendsTag.getChildTag(name);
		return null;
	}

	public UIParameterTag getParameterTag(String name) {
		if (uiTagDelegate.getParameters().get(name) != null)
			return (UIParameterTag) uiTagDelegate.getParameters().get(name);
		UITagDelegator extendsTag = getExtendsTag();
		if (extendsTag != null)
			return extendsTag.getParameterTag(name);
		return null;
	}

	public UIAttributeTag getAttributeTag(String name) {
		if (uiTagDelegate.getAttributes().get(name) != null)
			return (UIAttributeTag) uiTagDelegate.getAttributes().get(name);
		UITagDelegator extendsTag = getExtendsTag();
		if (extendsTag != null)
			return extendsTag.getAttributeTag(name);
		return null;

	}

	public UIChildTag getUIChildTag(TagHandler tagHandler, Object component) {
		UIChildTag childTag = getChildTag(tagHandler.getName());
		if (childTag != null)
			return childTag;
		UITag childTagValue = parserContext.getTagComponent(tagHandler.getName());
		if (ClassUtils.isAssignable(childTagValue.getTagClass(), UIEvent.class) && component instanceof UIEventHandler) {
			return new UIChildTag(tagHandler.getName(), "addEvent", UIEvent.class, tagHandler.getName());
		} else if (ClassUtils.isAssignable(childTagValue.getTagClass(), UIComponent.class) && component instanceof UIComponentHandler) {
			return new UIChildTag(tagHandler.getName(), "addItem", UIComponent.class, tagHandler.getName());
		}
		return null;
	}

	public UIObject parse(UIObject component, TagHandler tagHandler) throws UIParseException {
		parseAttributes(tagHandler, component);
		parseChilds(tagHandler, component);
		return component;
	}

	public UIObject parse(TagHandler tagHandler) throws UIParseException {
		UIObject component = create(tagHandler);
		return parse(component, tagHandler);
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, UIParserContext context) throws UIParseException {
		try {
			UIObject parentBean = (UIObject) ParserBeanAccessorUtils.newInstance(uiTagDelegate.getTagClass());
			XsComplexType childXsComplexType = new XsComplexType(uiTagDelegate.getTag());
			xsSchema.addType(childXsComplexType);
			XsElement attElement = new XsElement(uiTagDelegate.getTag(), childXsComplexType.getName(), "0");
			if (xsComplexType != null)
				xsComplexType.addElement(attElement);
			else
				xsSchema.addElement(attElement);
			Collection<UIAttributeTag> attributes = uiTagDelegate.getAttributes().values();
			Collection<UIChildTag> childTags = uiTagDelegate.getChildTags().values();
			Collection<UIPropertyTag> propertys = uiTagDelegate.getPropertys().values();
			Collection<UIPropertiesTag> propertiess = uiTagDelegate.getPropertiess().values();
			for (Iterator<UIAttributeTag> iterator = attributes.iterator(); iterator.hasNext();) {
				UIAttributeTag attributeTag = (UIAttributeTag) iterator.next();
				attributeTag.addXsd(xsSchema, childXsComplexType, parentBean);
			}

			for (Iterator<UIPropertyTag> iterator = propertys.iterator(); iterator.hasNext();) {
				UIPropertyTag attributeTag = (UIPropertyTag) iterator.next();
				attributeTag.addXsd(xsSchema, childXsComplexType, parentBean);
			}
			for (Iterator<UIPropertiesTag> iterator = propertiess.iterator(); iterator.hasNext();) {
				UIPropertiesTag attributeTag = (UIPropertiesTag) iterator.next();
				attributeTag.addXsd(xsSchema, childXsComplexType, parentBean);
			}
			for (Iterator<UIChildTag> iterator = childTags.iterator(); iterator.hasNext();) {
				UIChildTag attributeTag = (UIChildTag) iterator.next();
				
				attributeTag.addXsd(xsSchema, childXsComplexType, context, parentBean);
			}
		} catch (Exception e) {
			throw new UIParseException(e);
		}
	}

	public UIObject create(TagHandler tagHandler) throws UIParseException {
		UIObject component = (UIObject) ParserBeanAccessorUtils.newInstance(uiTagDelegate.getTagClass());
		if (component instanceof UIComponent) {
			((UIComponent) component).setTagName(uiTagDelegate.getTag());
		}
		return component;
	}

	public void parseChilds(TagHandler tagHandler, Object component) throws UIParseException {
		for (int i = 0; i < tagHandler.childrenSize(); i++) {
			TagHandler tagHandlerChild = (TagHandler) tagHandler.getChildren(i);
			tagHandlerChild.setParserParameter(parserContext.systemParserParameter());
			if (tagHandlerChild.getName().equalsIgnoreCase("include")) {
				TagHandler tagHandlerInclude = new XmlTagHandler(ResourceParserUtils.getXmlRootElement(tagHandlerChild.getAttributeValue("path"), tagHandler
						.getRessourceLocation()));
				parseChilds(tagHandlerInclude, component);
			} else {
				UIPropertyTag propertyTag = this.getPropertyTag(tagHandlerChild.getName());
				if (propertyTag != null) {
					propertyTag.parse(component, tagHandlerChild);
				} else {
					UIPropertiesTag propertiesTag = this.getPropertiesTag(tagHandlerChild.getName());
					if (propertiesTag != null) {
						propertiesTag.parse(component, tagHandlerChild);
					} else {
						UIChildTag childTag = getUIChildTag(tagHandlerChild, component);
						if (childTag == null)
							return;
						childTag.parseChildTag(component, parserContext, tagHandlerChild);
					}
				}
			}
		}
	}

	public void parseAttributes(TagHandler tagHandler, Object component) throws UIParseException {
		try {
			List<String> attributeNames = tagHandler.getAttributeNames();
			for (int i = 0; i < attributeNames.size(); i++) {
				String name = (String) attributeNames.get(i);
				String value = tagHandler.getAttributeValue(name);
				UIAttributeTag attributeTag = this.getAttributeTag(name);
				UIParameterTag parameterTag = null;
				if (attributeTag == null)
					parameterTag = this.getParameterTag(name);
				if (attributeTag != null) {
					attributeTag.parse(component, tagHandler);
				} else if (parameterTag != null) {
					parameterTag.parse(component, tagHandler);
				} else if (PropertyUtils.isWriteable(component, name)) {
					ParserBeanAccessorUtils.setPropertyValue(component, name, value);
				} else if (component instanceof UIAttributeHandler) {
					UIAttributeHandler uiAttributeHandler = (UIAttributeHandler) component;
					uiAttributeHandler.setAttribut(name, value);
				}
			}
			if (component instanceof UIHtmlContainerComponent) {
				String attributeValue = tagHandler.getValue();
				if (StringUtils.isNotBlank(attributeValue)) {
					UIHtmlTextBox htmlTextBox = new UIHtmlTextBox(attributeValue);
					parserContext.updateIdValue(htmlTextBox);
					((UIHtmlContainerComponent) component).addItem(htmlTextBox);
				}
			}
			if (StringUtils.isNotBlank(uiTagDelegate.getContentProperty())) {
				String attributeValue = tagHandler.getValue();
				ParserBeanAccessorUtils.setPropertyValue(component, uiTagDelegate.getContentProperty(), attributeValue);
			}
			parserContext.updateIdValue(component);
			if (parserContext.isAttachedToWindow(component)) {
				parserContext.attachedToWindow(((UIComponent) component));
			}
		} catch (Exception e) {
			throw new UIParseException(e);
		}
	}
}
