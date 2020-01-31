package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;

public abstract class XsExtSimpleType extends XsSimpleType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String comment;
	protected String restriction;

	public XsExtSimpleType() {
		super(true);
	}

	public XsExtSimpleType(String name) {
		super(true, name);
	}

	public XsExtSimpleType(String name, String restriction) {
		super(true, name);
		this.restriction = restriction;
	}

	public String getRestriction() {
		return restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode("xsd:simpleType", parentXmlNode);
		xmlNode.addAttribute("name", getName());
		xmlNode.addStarNode(buffer);
		XmlNode restNode = xmlNode.createElement("xsd:restriction").addAttribute("base", restriction);
		restNode.addStarNode(buffer);
		restNode.addEndNode(buffer);
		xmlNode.addEndNode(buffer);
	}
}
