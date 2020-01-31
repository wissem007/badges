package tn.com.smartsoft.commons.xml.utils;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.utils.HtmlEncodeUtil;

public class XmlNodeValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> attributes;
	private String name;
	private boolean asData;
	private List<XmlNodeValue> childs;
	private String textBody;
	private String space = "";

	public XmlNodeValue(String name, boolean asData) {
		super();
		this.name = name;
		this.asData = asData;
		this.attributes = new HashMap<String, String>();
		this.childs = new ArrayList<XmlNodeValue>();
	}

	public XmlNodeValue(String name) {
		this(name, false);

	}

	public void addChildNode(XmlNodeValue node) {
		childs.add(node);
	}

	public void setAttributes(final String name, final String value, boolean evenIfNull) {
		if (value == null && !evenIfNull) {
			return;
		}
		this.attributes.put(name, value);
	}

	public void clearAttribute() {
		this.attributes.clear();
	}

	public Set<String> getAttributeKeySet() {
		return this.attributes.keySet();
	}

	public void removeAttribute(final String name) {
		this.attributes.remove(name);
	}

	public void setAttribute(final String name, final String value) {
		setAttributes(name, value, false);
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes.putAll(attributes);
	}

	public String getAttributeValue(final String name) {
		return (String) this.attributes.get(name);
	}

	public String getName() {
		return name;
	}

	public boolean isAsData() {
		return asData;
	}

	public void createStarNode(Writer writer) throws IOException {
		if (!space.equals(""))
			writer.append("\n");
		writer.append(space).append("<").append(name);
		for (Iterator<String> iter = getAttributeKeySet().iterator(); iter.hasNext();) {
			String attr = (String) iter.next();
			writer.append(" ").append(attr).append("=\"").append(getAttributeValue(attr)).append("\"");
		}
		if (childs.size() == 0 && StringUtils.isBlank(textBody))
			writer.append("/>");
		else {
			writer.append(">");
			if (isAsData() && StringUtils.isNotBlank(textBody) && childs.size() == 0) {
				writer.append(space).append("<![CDATA[");
			}
		}
	}

	public void createChildsNode(Writer writer) throws IOException {
		for (int i = 0; i < childs.size(); i++) {
			((XmlNodeValue) childs.get(i)).space = space + "   ";
			((XmlNodeValue) childs.get(i)).createNode(writer);
		}
	}

	public void createEndNode(Writer writer) throws IOException {
		if (childs.size() == 0 && StringUtils.isBlank(textBody))
			return;
		if (isAsData() && StringUtils.isNotBlank(textBody) && childs.size() == 0) {
			writer.append("]]>");
		}
		writer.append("\n");
		writer.append(space).append("</").append(name).append(">");
	}

	public void createNode(Writer writer) throws IOException {
		createStarNode(writer);
		if (StringUtils.isNotBlank(textBody) && childs.size() == 0) {
			writer.append(HtmlEncodeUtil.encodeHtml(textBody));
		} else
			createChildsNode(writer);
		createEndNode(writer);
	}

	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

}