package tn.com.smartsoft.commons.xml.binding;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlOneRefBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String classRef;
	private String setterParentName;
	private boolean required;
	private String node;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSetterParentName() {
		return setterParentName;
	}

	public void setSetterParentName(String setterParentName) {
		this.setterParentName = setterParentName;
	}

	public String getClassRef() {
		return getClassRef(classRef);
	}

	public void setClassRef(String classRef) {
		this.classRef = classRef;
	}

	public boolean isRequired() {
		return required;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			XmlClassBinding xmlClassBindingRef = xmlParserManager.getClassBinding(this.getClassRef());
			String nodeOne = xmlClassBindingRef.getNode();
			if (StringUtils.isNotBlank(this.getNode()))
				nodeOne = this.getNode();
			XsComplexType childXsComplexType;
			if (!xsSchema.containsType(xmlClassBindingRef.getName()))
				childXsComplexType = xmlClassBindingRef.addXsd(xsSchema, xsComplexType, parentBean, xmlParserManager);
			else
				childXsComplexType = (XsComplexType) xsSchema.getType(xmlClassBindingRef.getName());
			xsComplexType.addElement(new XsElement(nodeOne, childXsComplexType.getName(), "0", "1"));
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		try {
			XmlClassBinding xmlClassBindingRef = xmlLoadContext.xmlParserManager().getClassBinding(this.getClassRef());
			String nodeOne = xmlClassBindingRef.getNode();
			if (StringUtils.isNotBlank(this.getNode()))
				nodeOne = this.getNode();
			XmlElement childElement = parentXmlElement.getChild(nodeOne);
			if (childElement == null)
				return;
			Object childValue = xmlClassBindingRef.newInstance();
			if (StringUtils.isNotBlank(this.getSetterParentName())) {
				ParserBeanAccessorUtils.setPropertyValue(childValue, this.getSetterParentName(), parentBean);
			}
			xmlClassBindingRef.read(childElement, childValue, xmlLoadContext);
			ParserBeanAccessorUtils.setPropertyValue(parentBean, this.getName(), childValue);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

}
