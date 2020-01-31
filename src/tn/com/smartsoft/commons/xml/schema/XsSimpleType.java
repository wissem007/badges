package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;

public class XsSimpleType extends XsType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isExtendXsd;

	public XsSimpleType(String name) {
		this(false, name);
	}

	public XsSimpleType(boolean isExtendXsd) {
		super();
		this.isExtendXsd = isExtendXsd;
	}

	public XsSimpleType(boolean isExtendXsd, String name) {
		super(name);
		this.isExtendXsd = isExtendXsd;
	}

	public boolean isXsd() {
		return !isExtendXsd;
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode(getName(), parentXmlNode);
		xmlNode.addStarNodeAndClose(buffer);
	}
}
