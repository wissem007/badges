package tn.com.smartsoft.framework.presentation.view.comman;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;

public class Style implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, String> attributes;
	private String styleClass;
	private Font font;

	public Style() {
		super();
		this.attributes = new Hashtable<String, String>();
	}

	public void parse(String style) {
		if (StringUtils.isEmpty(style))
			return;
		String[] ats = style.split(";");
		for (int i = 0; i < ats.length; i++) {
			String v = ats[i];
			if (v.indexOf(":") > 0) {
				String[] at = v.split(":");
				setAttribute(at[0].trim(), at[1].trim());
			}
		}
	}

	public Style setAttribute(String attribute, String value) {
		this.attributes.put(attribute, value);
		return this;
	}

	public void removeAttribute(String attribute) {
		if (attributes == null)
			this.attributes = new Hashtable<String, String>();
		this.attributes.remove(attribute);
	}

	public Style setBackColor(Color color) {
		return setAttribute("background-color", color.toHtml());
	}

	public Style setBorderColor(Color color) {
		return setAttribute("border-color", color.toHtml());
	}

	public Style setBorderStyle(String value) {
		return setAttribute("border-style", value);
	}

	public Style setBorderWidth(String value) {
		return setAttribute("border-width:", value);
	}

	public Style setStyleClass(String styleClass) {
		this.styleClass = styleClass;
		return this;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public Font font() {
		if (font == null)
			font = new Font();
		return font;
	}

	public Style setFont(Font font) {
		this.font = font;
		return this;
	}

	public Style setHeight(String value) {
		return setAttribute("height", value);
	}

	public Style setWidth(String value) {
		return setAttribute("width", value);
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public boolean isEmpty() {
		if (attributes.isEmpty() && StringHelper.isEmpty(styleClass) && font().isEmpty())
			return true;
		return false;
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		Map<String, String> attributes = getAttributes();
		Font font = font();
		for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
			String attribute = (String) iterator.next();
			String value = (String) attributes.get(attribute);
			sb.append(attribute).append(":").append(value).append(";");
		}
		if (font != null)
			sb.append(font.toHtml());
		return sb.toString();
	}
}
