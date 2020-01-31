package tn.com.smartsoft.commons.xml.utils;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tn.com.smartsoft.commons.web.utils.HtmlEncodeUtil;

public class NodeValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> attributes;
	private String name;
	private boolean asData;
	private boolean body;

	public NodeValue(String name, boolean asData, boolean body) {
		super();
		this.name = name;
		this.asData = asData;
		this.body = body;
		this.attributes = new HashMap<String, String>();
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

	public boolean isBody() {
		return body;
	}

	public void createStarNode(StringWriter xml) {
		xml.append("<").append(name);
		for (Iterator<String> iter = getAttributeKeySet().iterator(); iter.hasNext();) {
			String attr = (String) iter.next();
			xml.append(" ").append(attr).append("=\"").append(getAttributeValue(attr)).append("\"");
		}
		if (!body)
			xml.append("/>");
		else {
			xml.append(">");
			if (isAsData()) {
				xml.append("<![CDATA[");
			}
		}
	}

	public void createEndNode(StringWriter xml) {
		if (isAsData()) {
			xml.append("]]>");
		}
		xml.append("</" + name + ">");
	}

	public void createNode(StringWriter xml, String value, boolean escapeHTML) {
		createStarNode(xml);
		if (escapeHTML)
			xml.append(HtmlEncodeUtil.encodeHtml(value != null ? value.toString() : ""));
		else
			xml.append(value != null ? value.toString() : "");
		createEndNode(xml);
	}

}
