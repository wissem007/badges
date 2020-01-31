package tn.com.smartsoft.commons.xml.binding;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;

public abstract class XmlBindingElement implements Serializable {
	private XmlClassBinding xmlClassBinding;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public XmlClassBinding getXmlClassBinding() {
		return xmlClassBinding;
	}

	public void setXmlClassBinding(XmlClassBinding xmlClassBinding) {
		this.xmlClassBinding = xmlClassBinding;
	}

	public String getClassRef(String classRef) {
		if (classRef.indexOf(".") < 0)
			return getXmlClassBinding().getPackageValue() + "." + classRef;
		return classRef;
	}

	public abstract void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException;
}
