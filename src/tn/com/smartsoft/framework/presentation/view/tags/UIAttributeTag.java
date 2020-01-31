package tn.com.smartsoft.framework.presentation.view.tags;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.schema.XsAttribute;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParseException;
import tn.com.smartsoft.framework.presentation.view.tags.utils.ParserBeanAccessorUtils;

public class UIAttributeTag implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attribute;
	private String property;
	private String defaultValue;
	private boolean required;
	private String description;
	private String html;

	public UIAttributeTag() {
		super();
	}

	public String getProperty() {
		return property;
	}

	public String getDescription() {
		return description;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean) throws UIParseException {
		try {
			xsComplexType.addAttribute(new XsAttribute(attribute, ParserBeanAccessorUtils.getPropertyType(parentBean, this.getProperty()), required, getDefaultValue()));
		} catch (Exception e) {
			throw new UIParseException(e);
		}
	}

	public void parse(Object component, TagHandler tagHandler) throws UIParseException {
		try {
			String value = tagHandler.getAttributeValue(attribute);
			if (StringUtils.isBlank(value))
				value = this.getDefaultValue();
			if (StringUtils.isNotBlank(value)) {
				ParserBeanAccessorUtils.setPropertyValue(component, this.getProperty(), value);
			}
		} catch (Exception e) {
			throw new UIParseException("Erreur load Property :" + this.getProperty() + " and attribute" + this.getAttribute() + " to component :" + component, e);
		}
	}
}
