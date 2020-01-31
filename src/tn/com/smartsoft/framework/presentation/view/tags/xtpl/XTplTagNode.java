package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class XTplTagNode extends XTplBodyNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, String> attributes = new HashMap<String, String>();

	public XTplTagNode() {
		super();
	}

	public XTplTagNode(String name) {
		super();
		this.name = name;
	}

	private String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAttributeNames() {
		return new ArrayList<String>(attributes.keySet());
	}

	public void addAttributeValue(String name, Object value) {
		if (value != null)
			attributes.put(name, value.toString());
		else
			attributes.put(name, null);
	}

	public String getAttributeValue(String name) {
		return attributes.get(name);
	}

	public Set<String> getAttributeKeySet() {
		return this.attributes.keySet();
	}

	public void writeStarNode(StringWriter xml) {
		xml.append("<").append(name);
		for (Iterator<String> iter = getAttributeKeySet().iterator(); iter.hasNext();) {
			String attr = (String) iter.next();
			String attributeValue = getAttributeValue(attr);
			attributeValue = StringUtils.replace(attributeValue, "$", "${xxx}");
			attributeValue = StringUtils.replace(attributeValue, "#{", "${");
			xml.append(" ").append(attr).append("=\"").append(attributeValue).append("\"");
		}
		xml.append(">\n");
		if (StringUtils.isNotBlank(getContenent())) {
			xml.append("<![CDATA[");
			xml.append(getContenent());
			xml.append("]]>\n");
		}
	}

	public void writeEndNode(StringWriter xml) {
		xml.append("</" + name + ">\n");
	}

	public void write(StringWriter buffer) {
		writeStarNode(buffer);
		writeChildren(buffer);
		writeEndNode(buffer);
	}
}
