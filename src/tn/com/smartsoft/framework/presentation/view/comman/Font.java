package tn.com.smartsoft.framework.presentation.view.comman;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;

public class Font implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean bold = false;
	private boolean italic = false;
	private int size = 0;
	private String fontFamily;

	public Font() {
		super();
	}

	public Font setBold(boolean bold) {
		this.bold = bold;
		return this;
	}

	public Font setItalic(boolean italic) {
		this.italic = italic;
		return this;
	}

	public Font setSize(int size) {
		this.size = size;
		return this;
	}

	public Font setSize(String size) {
		if (StringHelper.isEmpty(size) && !StringUtils.isNumeric(size))
			return this;

		this.size = Integer.parseInt(size);
		return this;
	}

	public Font setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		return this;
	}

	public void setFontFamily(String[] fontFamilys) {
		this.fontFamily = "";
		for (int i = 0; i < fontFamilys.length; i++) {
			if (fontFamilys[i].indexOf(" ") > 0)
				this.fontFamily = this.fontFamily + ",'" + fontFamilys[i] + "'";
			else
				this.fontFamily = this.fontFamily + "," + fontFamilys[i];
		}
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		if (size != 0)
			sb.append("font-size :").append(size).append(";");
		if (bold)
			sb.append("font-weight :").append("bold").append(";");
		if (italic)
			sb.append("font :").append("italic ").append(";");
		if (fontFamily != null)
			sb.append("font-family :").append(fontFamily).append(";");
		return sb.toString();
	}

	public boolean isEmpty() {
		if (!bold && !italic && !italic && size == 0 && StringHelper.isEmpty(fontFamily))
			return true;
		return false;
	}
}
