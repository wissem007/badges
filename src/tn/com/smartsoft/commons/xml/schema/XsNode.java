package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;

public interface XsNode {
	public abstract void addToXsd(StringWriter buffer, XmlNode parentXmlNode);

	public XsSchema getSchema();

	public void setSchema(XsSchema schema);

	public abstract String toString();
}
