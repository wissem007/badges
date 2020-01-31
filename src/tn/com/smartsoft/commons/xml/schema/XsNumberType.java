package tn.com.smartsoft.commons.xml.schema;

import java.io.StringWriter;

import tn.com.smartsoft.commons.xml.utils.XMLUtils;

public class XsNumberType extends XsExtSimpleType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer fractionDigits;
	protected Double maxExclusive;// strictement
	protected Double maxInclusive;
	protected Double minExclusive;
	protected Double minInclusive;
	protected Integer totalDigits;
	protected String comment;

	public XsNumberType() {
		super();
		this.restriction = "xsd:double";
	}

	public XsNumberType(String name, String restriction) {
		super(name, restriction);
	}

	public XsNumberType(String name) {
		super(name, "xsd:double");
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getFractionDigits() {
		return fractionDigits;
	}

	public void setFractionDigits(Integer fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public Double getMaxExclusive() {
		return maxExclusive;
	}

	public void setMaxExclusive(Double maxExclusive) {
		this.maxExclusive = maxExclusive;
	}

	public Double getMaxInclusive() {
		return maxInclusive;
	}

	public void setMaxInclusive(Double maxInclusive) {
		this.maxInclusive = maxInclusive;
	}

	public Double getMinExclusive() {
		return minExclusive;
	}

	public void setMinExclusive(Double minExclusive) {
		this.minExclusive = minExclusive;
	}

	public Double getMinInclusive() {
		return minInclusive;
	}

	public void setMinInclusive(Double minInclusive) {
		this.minInclusive = minInclusive;
	}

	public Integer getTotalDigits() {
		return totalDigits;
	}

	public void setTotalDigits(Integer totalDigits) {
		this.totalDigits = totalDigits;
	}

	public void addToXsd(StringWriter buffer, XmlNode parentXmlNode) {
		XmlNode xmlNode = new XmlNode("xsd:simpleType", parentXmlNode);
		xmlNode.addAttribute("name", getName());
		xmlNode.addStarNode(buffer);
		XmlNode restNode = xmlNode.createElement("xsd:restriction").addAttribute("base", restriction);
		restNode.addStarNode(buffer);
		XMLUtils.addElement(restNode, "xsd:fractionDigits", fractionDigits, buffer);
		XMLUtils.addElement(restNode, "xsd:maxExclusive", maxExclusive, buffer);
		XMLUtils.addElement(restNode, "xsd:maxInclusive", maxInclusive, buffer);
		XMLUtils.addElement(restNode, "xsd:minExclusive", minExclusive, buffer);
		XMLUtils.addElement(restNode, "xsd:minInclusive", minInclusive, buffer);
		XMLUtils.addElement(restNode, "xsd:totalDigits", totalDigits, buffer);
		restNode.addEndNode(buffer);
		xmlNode.addEndNode(buffer);
	}

}
