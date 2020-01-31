package tn.com.smartsoft.commons.xml.schema;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.xml.utils.XMLUtils;

public class XmlNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> attributes;
	private List<String> values;
	private String name;
	private String space = " ";

	public XmlNode(String name, String space) {
		super();
		this.name = name;
		this.space = space;
		this.attributes = new ArrayList<String>();
		this.values = new ArrayList<String>();
	}

	public XmlNode(String name) {
		this(name, "");
	}

	public XmlNode(String name, XmlNode parentNode) {
		this(name, parentNode != null ? parentNode.space + " " : " ");
	}

	public XmlNode createElement(String name) {
		return new XmlNode(name, this);
	}

	public XmlNode addAttribute(String name, Object value) {
		if (value == null) {
			return this;
		}
		this.attributes.add(name);
		this.values.add(value.toString());
		return this;
	}

	public String getName() {
		return name;
	}

	public void addStarNode(StringWriter xml, boolean isClose, boolean isReturn) {
		xml.append(space).append("<").append(name);
		for (int i = 0; i < attributes.size(); i++) {
			xml.append(" ").append(attributes.get(i)).append("=\"").append(XMLUtils.escapeAttributeValue(this.values.get(i))).append("\"");
		}
		if (isClose)
			xml.append("/>");
		else
			xml.append(">");
		if (isReturn)
			xml.append("\n");
	}

	public void addStarNode(StringWriter xml, boolean isReturn) {
		addStarNode(xml, false, isReturn);
	}

	public void addStarNodeNotReturn(StringWriter xml) {
		addStarNode(xml, false, false);
	}

	public void addStarNode(StringWriter xml) {
		addStarNode(xml, false, true);
	}

	public void addStarNodeAndClose(StringWriter xml) {
		addStarNode(xml, true, true);
	}

	public void addCDATAContent(StringWriter xml, String content) {
		xml.append(space).append("<![CDATA[");
		xml.append(XMLUtils.escapeCDATAContent(content));
		xml.append("]]>\n");
	}

	public void addCDATAContentNotReturn(StringWriter xml, String content) {
		xml.append(space).append("<![CDATA[");
		xml.append(XMLUtils.escapeCDATAContent(content));
		xml.append("]]>");
	}

	public void addBodyContent(StringWriter xml, String content) {
		xml.append(space).append(XMLUtils.escapeBodyValue(content));
	}

	public void addEndNode(StringWriter xml) {
		xml.append(space).append("</" + name + ">\n");
	}
}
