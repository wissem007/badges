package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;

public class XsElement extends XsTypedNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maxOccurs;
	private String minOccurs;
	private XsComplexType complexType;

	public XsElement(String name, String typeName) {
		super(name);
		setTypeName(typeName);
	}

	public XsElement(String name, String typeName, String minOccurs, String maxOccurs) {
		this(name, typeName);
		this.maxOccurs = maxOccurs;
		this.minOccurs = minOccurs;
	}

	public XsElement(String name, String minOccurs, String maxOccurs) {
		this(name, null);
		this.maxOccurs = maxOccurs;
		this.minOccurs = minOccurs;
	}

	public XsComplexType getComplexType() {
		return complexType;
	}

	public void setComplexType(XsComplexType complexType) {
		complexType.setSchema(schema);
		this.complexType = complexType;
	}

	public XsElement() {
		super();
	}

	public String getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(String maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public String getMinOccurs() {
		return minOccurs;
	}

	public void setMinOccurs(String minOccurs) {
		this.minOccurs = minOccurs;
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode, boolean schmaElement) {
		XmlNode xmlNode = new XmlNode("xsd:element", parentXmlNode);
		xmlNode.addAttribute("name", getName());
		xmlNode.addAttribute("type", typeName);
		if (!schmaElement) {
			xmlNode.addAttribute("minOccurs", getMinOccurs());
			xmlNode.addAttribute("maxOccurs", getMaxOccurs() == null ? "unbounded" : getMaxOccurs());
		}
		if (complexType == null)
			xmlNode.addStarNodeAndClose(buffer);
		else {
			xmlNode.addStarNode(buffer);
			complexType.addToXsd(buffer, xmlNode);
			xmlNode.addEndNode(buffer);
		}
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		addToXsd(buffer, parentXmlNode, false);
	}
}
