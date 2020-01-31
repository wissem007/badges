package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import tn.com.smartsoft.commons.utils.ListHandler;
import tn.com.smartsoft.commons.utils.NamedObject;
import tn.com.smartsoft.commons.xml.utils.XMLUtils;

public class XsSchema extends NamedObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ListHandler<XsElement> elements = new ListHandler<XsElement>();
	private ListHandler<XsType> types = new ListHandler<XsType>();
	protected String comment;

	public XsSchema(String name) {
		super(name);
	}

	public XsSchema getSchema() {
		return this;
	}

	public void addType(XsType xsdType) {
		xsdType.setSchema(this);
		types.add(xsdType);
	}

	public boolean containsType(String name) {
		return types.contains(name);
	}

	public XsType getType(String name) {
		if (DataTypeMapper.isXsdType(name))
			return new XsSimpleType(name);
		return types.get(name);
	}

	public List<XsType> getTypes() {
		return types.getAll();
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isSimpleType(String type) {
		boolean isSimple = DataTypeMapper.isXsdType(type);
		if (!isSimple && containsType(type)) {
			isSimple = getType(type) instanceof XsExtSimpleType;
		}
		return isSimple;
	}

	public String toString() {
		StringWriter buffer = new StringWriter();
		addToXsd(buffer);
		return buffer.toString();
	}

	public void addToXsd(StringWriter buffer) {
		XmlNode xmlNode = new XmlNode("xsd:schema", (XmlNode) null);
		xmlNode.addAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		xmlNode.addStarNode(buffer);
		XMLUtils.addAnnotation(buffer, null, getComment(), xmlNode);
		for (Iterator<XsType> it = types.iterator(); it.hasNext();) {
			it.next().addToXsd(buffer, xmlNode);
		}
		for (Iterator<XsElement> it = getElements().iterator(); it.hasNext();) {
			it.next().addToXsd(buffer, xmlNode, true);
		}
		xmlNode.addEndNode(buffer);
	}
}
