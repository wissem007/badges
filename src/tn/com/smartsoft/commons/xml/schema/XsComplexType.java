package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import tn.com.smartsoft.commons.utils.ListHandler;
import tn.com.smartsoft.commons.xml.utils.XMLUtils;

public class XsComplexType extends XsType {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String extension;
	protected String comment;
	protected ListHandler<XsAttribute> attributes = new ListHandler<XsAttribute>();
	protected ListHandler<XsElement> elements = new ListHandler<XsElement>();

	public XsComplexType(String name) {
		super(name);
	}

	public XsComplexType() {
		super();
	}

	public List<XsElement> getElements() {
		return elements.getAll();
	}

	public XsElement getElement(String name) {
		return elements.get(name);
	}

	public void addElement(XsElement element) {
		element.setSchema(getSchema());
		elements.add(element);
	}

	public boolean containsElement(String name) {
		return elements.contains(name);
	}

	public boolean containsAttribute(String name) {
		return attributes.contains(name);
	}

	public ListHandler<XsAttribute> getAttributes() {
		return attributes;
	}

	public XsAttribute getAttribute(String name) {
		return attributes.get(name);
	}

	public void addAttribute(XsAttribute attribute) {
		attribute.setSchema(getSchema());
		attributes.add(attribute);
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getComment() {
		return comment;
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode("xsd:complexType", parentXmlNode);
		xmlNode.addAttribute("name", name);
		xmlNode.addStarNode(buffer);
		XMLUtils.addAnnotation(buffer, null, comment, xmlNode);
		XmlNode extensionNode = null;
		if (extension != null) {
			extensionNode = xmlNode.createElement("xsd:extension").addAttribute("base", extension);
			extensionNode.addStarNode(buffer);
		}
		if (elements != null && !elements.isEmpty()) {
			XmlNode sequenceNode = xmlNode.createElement("xsd:sequence");
			sequenceNode.addStarNode(buffer);
			for (Iterator<XsElement> it = elements.iterator(); it.hasNext();) {
				it.next().addToXsd(buffer, sequenceNode);
			}
			sequenceNode.addEndNode(buffer);
		}
		if (attributes != null)
			for (Iterator<XsAttribute> it = attributes.iterator(); it.hasNext();) {
				it.next().addToXsd(buffer, xmlNode);
			}
		if (extension != null) {
			extensionNode.addEndNode(buffer);
		}
		xmlNode.addEndNode(buffer);

	}

}
