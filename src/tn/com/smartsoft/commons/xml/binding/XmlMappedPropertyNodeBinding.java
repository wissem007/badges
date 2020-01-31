package tn.com.smartsoft.commons.xml.binding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.schema.XsAttribute;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlMappedPropertyNodeBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String addedMethode;
	private String attributeKey;
	private String attributeValue;
	private String attributeType;
	private String node;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}

	public String getAttribute() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAddedMethode() {
		return addedMethode;
	}

	public void setAddedMethode(String addedMethode) {
		this.addedMethode = addedMethode;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			final String attributeKey = StringUtils.isNotBlank(this.getAttributeKey()) ? this.getAttributeKey() : "name";
			final String attributeValue = StringUtils.isNotBlank(this.getAttribute()) ? this.getAttribute() : "value";
			String childNode = this.getNode();
			if (this.getNode().indexOf("/") > 0) {
				String mayChildNode = this.getNode().substring(0, this.getNode().indexOf("/"));
				childNode = this.getNode().substring(this.getNode().indexOf("/") + 1, this.getNode().length());
				XsElement mayattElement = new XsElement(mayChildNode, "0", "1");
				xsComplexType.addElement(mayattElement);
				XsComplexType attmayXsComplexType = new XsComplexType();
				mayattElement.setComplexType(attmayXsComplexType);
				XsElement attElement = new XsElement(childNode, "0", null);
				attmayXsComplexType.addElement(attElement);
				XsComplexType attXsComplexType = new XsComplexType();
				attElement.setComplexType(attXsComplexType);
				attXsComplexType.addAttribute(new XsAttribute(attributeKey, String.class));
				attXsComplexType.addAttribute(new XsAttribute(attributeValue, String.class));
				if (StringUtils.isNotBlank(attributeType))
					attXsComplexType.addAttribute(new XsAttribute(attributeType, String.class));
			} else {
				XsElement attElement = new XsElement(childNode, "0", null);
				xsComplexType.addElement(attElement);
				XsComplexType attXsComplexType = new XsComplexType();
				attElement.setComplexType(attXsComplexType);
				attXsComplexType.addAttribute(new XsAttribute(attributeKey, String.class));
				attXsComplexType.addAttribute(new XsAttribute(attributeValue, String.class));
				if (StringUtils.isNotBlank(attributeType))
					attXsComplexType.addAttribute(new XsAttribute(attributeType, String.class));
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void readSystemPropertie(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		try {
			final String attributeKey = StringUtils.isNotBlank(this.getAttributeKey()) ? this.getAttributeKey() : "name";
			final String attributeValue = StringUtils.isNotBlank(this.getAttribute()) ? this.getAttribute() : "value";
			XmlElement childElements = parentXmlElement;
			String childNode = this.getNode();
			if (this.getNode().indexOf("/") > 0) {
				childElements = parentXmlElement.getChild(this.getNode().substring(0, this.getNode().indexOf("/")));
				if (childElements == null)
					return;
				childNode = this.getNode().substring(this.getNode().indexOf("/") + 1, this.getNode().length());
			}
			List<XmlElement> childs = childElements.getChildren(childNode);
			for (int i = 0; i < childs.size(); i++) {
				XmlElement childElement = (XmlElement) childs.get(i);
				xmlLoadContext.addSystemField(childElement.getAttribute(attributeKey), xmlLoadContext.parse(childElement.getAttribute(attributeValue)));
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		final String attributeKey = StringUtils.isNotBlank(this.getAttributeKey()) ? this.getAttributeKey() : "name";
		final String attributeValue = StringUtils.isNotBlank(this.getAttribute()) ? this.getAttribute() : "value";
		XmlElement childElements = parentXmlElement;
		String childNode = this.getNode();
		if (this.getNode().indexOf("/") > 0) {
			childElements = parentXmlElement.getChild(this.getNode().substring(0, this.getNode().indexOf("/")));
			if (childElements == null)
				return;
			childNode = this.getNode().substring(this.getNode().indexOf("/") + 1, this.getNode().length());
		}
		List<XmlElement> childs = childElements.getChildren(childNode);
		for (int i = 0; i < childs.size(); i++) {
			XmlElement childElement = (XmlElement) childs.get(i);
			String type = xmlLoadContext.parse(childElement.getAttribute(this.getAttributeType()));
			type = StringUtils.isBlank(type) ? "java.lang.String" : type;
			Object value = xmlLoadContext.parse(childElement.getAttribute(attributeValue));
			Object keyValue = xmlLoadContext.parse(childElement.getAttribute(attributeKey));
			if (StringUtils.isNotBlank((String) value))
				value = ParserBeanAccessorUtils.convertValue((String) value, type);
			if (value == null && StringUtils.isNotBlank(childElement.getValue())) {
				value = ParserBeanAccessorUtils.convertValue(xmlLoadContext.parse(childElement.getValue()), type);
			}
			if (StringUtils.isNotBlank(this.getName())) {
				Class<?> propertyType = ParserBeanAccessorUtils.getPropertyType(parentBean, this.getName());
				if (propertyType.equals(Map.class)) {
					Map mapValue = (Map) ParserBeanAccessorUtils.getPropertyValue(parentBean, this.getName());
					if (mapValue == null) {
						mapValue = new HashMap();
						ParserBeanAccessorUtils.setPropertyValue(parentBean, this.getName(), mapValue);
					}
					mapValue.put(keyValue, value);
				}
			} else {
				if (value != null)
					ParserBeanAccessorUtils.invokeMethod(parentBean, this.getAddedMethode(), keyValue, value);
			}
		}
	}
}
