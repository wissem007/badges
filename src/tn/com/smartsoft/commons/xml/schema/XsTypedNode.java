package tn.com.smartsoft.commons.xml.schema;

import tn.com.smartsoft.commons.utils.NamedObject;

public abstract class XsTypedNode extends NamedObject implements XsNode {
	protected XsSchema schema;
	protected String comment;
	protected String typeName;

	public XsTypedNode() {
		super();
	}

	public XsTypedNode(String name) {
		super(name);
	}

	public String getTypeName() {
		return typeName;
	}

	public String getComment() {
		return comment;
	}

	public XsType getType() {
		return schema.getType(typeName);
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public XsSchema getSchema() {
		return schema;
	}

	public void setSchema(XsSchema schema) {
		this.schema = schema;
	}

}
