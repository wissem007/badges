package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.utils.XMLUtils;

public class XsAttribute extends XsTypedNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String defauld;
	private Class<?> javaType;
	private String property;
	private String fixed;
	private Boolean required = Boolean.FALSE;
	protected String comment;
	protected XsSchema schema;
	private String typeName;

	public XsAttribute() {
		super();
	}

	public XsAttribute(String name, Class<?> javaType, Boolean required, String defauld) {
		super();
		this.name = name;
		this.javaType = javaType;
		this.required = required;
		this.defauld = defauld;
	}

	public XsAttribute(String name, Class<?> javaType) {
		super();
		this.name = name;
		this.javaType = javaType;
	}

	public XsSchema getSchema() {
		return schema;
	}

	public void setSchema(XsSchema schema) {
		this.schema = schema;
	}

	public String getFixed() {
		return fixed;
	}

	public void setFixed(String fixed) {
		this.fixed = fixed;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getProperty() {
		if (!StringUtils.isBlank(name) && StringUtils.isBlank(property))
			return DataTypeMapper.attributeToJavaName(name);
		return property;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getName() {
		if (StringUtils.isBlank(name) && !StringUtils.isBlank(property))
			return DataTypeMapper.propertyToAttributeName(property);
		return name;
	}

	public Class<?> getJavaType() {
		if (!StringUtils.isBlank(typeName) && javaType == null)
			return DataTypeMapper.xsdToJavaType(typeName);
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public String getTypeName() {
		if (StringUtils.isBlank(typeName) && javaType != null)
			return DataTypeMapper.javaToXsdType(javaType);
		return typeName;
	}

	public String getDefauld() {
		return defauld;
	}

	public void setDefauld(String defauld) {
		this.defauld = defauld;
	}

	public String toString() {
		StringWriter buffer = new StringWriter();
		addToXsd(buffer, null);
		return buffer.toString();
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode("xsd:attribute", parentXmlNode);
		xmlNode.addAttribute("name", getName());
		xmlNode.addAttribute("type", getTypeName());
		xmlNode.addAttribute("default", getDefauld());
		xmlNode.addAttribute("use", getRequired() ? "required" : "optional");
		xmlNode.addAttribute("fixed", getFixed());
		xmlNode.addStarNode(buffer);
		XMLUtils.addAnnotation(buffer, javaType, null, xmlNode);
		xmlNode.addEndNode(buffer);
	}

	public void addToCsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode("attribute", parentXmlNode);
		xmlNode.addAttribute("name", getName());
		xmlNode.addAttribute("property", getProperty());
		xmlNode.addAttribute("java-type", DataTypeMapper.classToString(getJavaType()));
		xmlNode.addAttribute("required", getRequired());
		xmlNode.addAttribute("fixed", getFixed());
		if (!DataTypeMapper.isXsdType(getTypeName()))
			xmlNode.addAttribute("type-name", getTypeName());
		xmlNode.addAttribute("default", getDefauld());
		xmlNode.addStarNodeAndClose(buffer);
	}

}
