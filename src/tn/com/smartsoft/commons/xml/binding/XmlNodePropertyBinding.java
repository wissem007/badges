package tn.com.smartsoft.commons.xml.binding;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.schema.XsAttribute;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlNodePropertyBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String attribute;
	private String node;
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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public boolean isNodeExiste() {
		return StringUtils.isNotBlank(node);
	}

	public boolean isAttributeExiste() {
		return StringUtils.isNotBlank(attribute);
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			final String att = !this.isAttributeExiste() ? "value" : this.getAttribute();
			String attNode = StringUtils.isNotBlank(this.getNode()) ? this.getNode() : this.getName();
			XsElement attElement = new XsElement(attNode, "0", "1");
			xsComplexType.addElement(attElement);
			XsComplexType attXsComplexType = new XsComplexType();
			attElement.setComplexType(attXsComplexType);
			attXsComplexType.addAttribute(new XsAttribute(att, ParserBeanAccessorUtils.getPropertyType(parentBean, this.getName()), required, getDefaultValue()));
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		try {
			final String att = !this.isAttributeExiste() ? "value" : this.getAttribute();
			String attNode = StringUtils.isNotBlank(this.getNode()) ? this.getNode() : this.getName();
			XmlElement childElement = parentXmlElement.getChild(attNode);
			if (childElement == null)
				return;
			String value = childElement.getAttribute(att);
			if (StringUtils.isBlank(value)) {
				value = childElement.getValue();
			}
			if (StringUtils.isBlank(value))
				value = getDefaultValue();
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
