package tn.com.smartsoft.commons.xml.binding;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.schema.XsAttribute;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlPropertyBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String attribute;
	private boolean required;
	private String defaultValue;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAttributeExiste() {
		return StringUtils.isNotBlank(attribute);
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean) throws ParseException {
		try {
			final String att = !this.isAttributeExiste() ? this.getName() : this.getAttribute();
			xsComplexType.addAttribute(new XsAttribute(att, ParserBeanAccessorUtils.getPropertyType(parentBean, this.getName()), required, getDefaultValue()));
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		try {
			final String att = !this.isAttributeExiste() ? this.getName() : this.getAttribute();
			String value = parentXmlElement.getAttribute(att);
			if (StringUtils.isBlank(value))
				value = this.getDefaultValue();
			if (StringUtils.isBlank(value) && required) {
				throw new TechnicalException("this property :" + name + " is  Required from " + parentBean.getClass().getName());
			}
			if (StringUtils.isNotBlank(value)) {
				ParserBeanAccessorUtils.setPropertyValue(parentBean, name, xmlLoadContext.parse(value));
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
}
