package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.StringWriter;

import tn.com.smartsoft.framework.presentation.view.tags.xtpl.utils.XTplUtils;

public class XTplSetValue implements XTplNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void write(StringWriter buffer) {
		buffer.write("#set(");
		buffer.write(name);
		buffer.write("=");
		buffer.write(XTplUtils.formatAttributeValue(value));
		buffer.write(")\n");
	}

}
