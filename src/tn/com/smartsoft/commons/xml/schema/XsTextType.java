package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.xml.utils.XMLUtils;

public class XsTextType extends XsExtSimpleType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer length;
	protected Integer maxLength;
	protected Integer minLength;
	protected String whiteSpace;
	protected String pattern;
	protected String restriction = "xsd:string";
	protected List<String> enumerations = new ArrayList<String>();

	public XsTextType() {
		super();
		this.restriction = "xsd:string";
	}

	public XsTextType(String name, String restriction) {
		super(name, restriction);
	}

	public XsTextType(String name) {
		super(name, "xsd:string");
	}

	public List<String> getEnumerations() {
		return enumerations;
	}

	public String getEnumeration(int index) {
		return enumerations.get(index);
	}

	public void setEnumerations(List<String> enumerations) {
		this.enumerations = enumerations;
	}

	public void addEnumeration(String enumeration) {
		enumerations.add(enumeration);
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public String getWhiteSpace() {
		return whiteSpace;
	}

	public void setWhiteSpace(String whiteSpace) {
		this.whiteSpace = whiteSpace;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode("xsd:simpleType", parentXmlNode);
		xmlNode.addAttribute("name", getName());
		xmlNode.addStarNode(buffer);
		XmlNode restNode = xmlNode.createElement("xsd:restriction").addAttribute("base", restriction);
		restNode.addStarNode(buffer);
		XMLUtils.addElement(restNode, "xsd:length", length, buffer);
		XMLUtils.addElement(restNode, "xsd:maxLength", maxLength, buffer);
		XMLUtils.addElement(restNode, "xsd:minLength", minLength, buffer);
		XMLUtils.addElement(restNode, "xsd:whiteSpace", whiteSpace, buffer);
		XMLUtils.addElement(restNode, "xsd:pattern", pattern, buffer);
		for (int i = 0; i < enumerations.size(); i++) {
			XMLUtils.addElement(restNode, "xsd:enumeration", enumerations.get(i), buffer);
		}
		restNode.addEndNode(buffer);
		xmlNode.addEndNode(buffer);
	}
}
