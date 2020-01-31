package tn.com.smartsoft.commons.xml.schema;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.NamedObject;

public abstract class XsType extends NamedObject implements Serializable, XsNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected XsSchema schema;

	public XsType() {
		super();
	}

	public XsType(String name) {
		super();
		this.name = name;
	}

	public XsSchema getSchema() {
		return schema;
	}

	public void setSchema(XsSchema schema) {
		this.schema = schema;
	}
}
