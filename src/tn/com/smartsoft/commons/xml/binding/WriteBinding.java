package tn.com.smartsoft.commons.xml.binding;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.utils.XmlNodeValue;

public class WriteBinding {
	public static String writeNode(String xmlClass, XmlParserManagerImp xmlParserManager) throws IOException {
		XmlClassBinding xmlClassBinding = xmlParserManager.getClassBinding(xmlClass);
		XmlNodeValue nodeValue = new XmlNodeValue(xmlClassBinding.getNode());
		writeObjectNode(nodeValue, xmlClass, xmlParserManager);
		StringWriter w = new StringWriter();
		nodeValue.createNode(w);
		return w.toString();
	}

	public static void writeOneRefObject(XmlNodeValue nodeValue, XmlParserManagerImp xmlBinding, XmlOneRefBinding xmlOneRefBinding) {
		try {
			writeObjectNode(nodeValue, xmlOneRefBinding.getClassRef(), xmlBinding);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static void writeManyRefObject(XmlNodeValue nodeValue, XmlParserManagerImp xmlBinding, XmlManyRefBinding xmlManyRefBinding) {
		try {
			XmlNodeValue childNodeValueMany = nodeValue;
			if (StringUtils.isNotBlank(xmlManyRefBinding.getManyNode())) {
				childNodeValueMany = new XmlNodeValue(xmlManyRefBinding.getManyNode());
				nodeValue.addChildNode(childNodeValueMany);
			}
			if (xmlManyRefBinding.getClassRef().equals(xmlManyRefBinding.getXmlClassBinding().getName())) {
				nodeValue.addChildNode(new XmlNodeValue(nodeValue.getName()));
			} else
				for (int i = 0; i < 2; i++) {
					writeObjectNode(childNodeValueMany, xmlManyRefBinding.getClassRef(), xmlBinding);
				}

		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static void writeObjectNode(XmlNodeValue parentNodeValue, String fclass, XmlParserManagerImp xmlBinding) {
		try {
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(fclass);
			XmlNodeValue nodeValue = parentNodeValue;
			String node = xmlClassBinding.getNode();
			if (!node.equalsIgnoreCase(parentNodeValue.getName())) {
				nodeValue = new XmlNodeValue(xmlClassBinding.getNode());
				parentNodeValue.addChildNode(nodeValue);
			}
			if (StringUtils.isNotBlank(xmlClassBinding.getPropertyBody())) {
				parentNodeValue.setTextBody("body");
			}
			writeAllProperty(nodeValue, xmlBinding, xmlClassBinding);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	private static void writeAllProperty(XmlNodeValue nodeValue, XmlParserManagerImp xmlBinding, XmlClassBinding xmlClassBinding) {
		for (int i = 0; i < xmlClassBinding.getPropertyBindings().size(); i++) {
			writeProperty(nodeValue, xmlClassBinding.getPropertyBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getMappedPropertyNodeBindings().size(); i++) {
			writeMappedPropertyNode(nodeValue, (XmlMappedPropertyNodeBinding) xmlClassBinding.getMappedPropertyNodeBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getListPropertyNodeBindings().size(); i++) {
			writeListProperty(nodeValue, (XmlListPropertyNodeBinding) xmlClassBinding.getListPropertyNodeBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getOneRefBindings().size(); i++) {
			writeOneRefObject(nodeValue, xmlBinding, (XmlOneRefBinding) xmlClassBinding.getOneRefBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getManyRefBindings().size(); i++) {
			writeManyRefObject(nodeValue, xmlBinding, (XmlManyRefBinding) xmlClassBinding.getManyRefBindings().get(i));
		}
		if (StringUtils.isNotBlank(xmlClassBinding.getExtendsClass()) && xmlBinding.getClassBinding(xmlClassBinding.getExtendsClass()) != null) {
			writeAllProperty(nodeValue, xmlBinding, xmlBinding.getClassBinding(xmlClassBinding.getExtendsClass()));
		}
	}

	public static void writeListProperty(XmlNodeValue nodeValue, XmlListPropertyNodeBinding propertyDef) {
		try {
			String attributeValue = StringUtils.isNotBlank(propertyDef.getAttributeValue()) ? propertyDef.getAttributeValue() : "value";
			String childNode = propertyDef.getNode();
			XmlNodeValue childNodeValueMany = nodeValue;
			XmlNodeValue childNodeValue;
			if (propertyDef.getNode().indexOf("/") > 0) {
				childNodeValueMany = new XmlNodeValue(childNode.substring(0, childNode.indexOf("/")));
				childNode = propertyDef.getNode().substring(propertyDef.getNode().indexOf("/") + 1, propertyDef.getNode().length());
				nodeValue.addChildNode(childNodeValueMany);
			}
			for (int i = 0; i < 2; i++) {
				childNodeValue = new XmlNodeValue(childNode);
				Object value = "--------";
				if (value != null)
					childNodeValue.setAttribute(attributeValue, value.toString());
				else
					childNodeValue.setAttribute(attributeValue, "");
				childNodeValueMany.addChildNode(childNodeValue);
			}
		} catch (Exception e1) {
			throw new TechnicalException(e1);
		}
	}

	public static void writeMappedPropertyNode(XmlNodeValue nodeValue, XmlMappedPropertyNodeBinding propertyDef) {
		try {
			final String attributeKey = StringUtils.isNotBlank(propertyDef.getAttributeKey()) ? propertyDef.getAttributeKey() : "name";
			final String attributeValue = StringUtils.isNotBlank(propertyDef.getAttribute()) ? propertyDef.getAttribute() : "value";
			String childNode = propertyDef.getNode();
			XmlNodeValue childNodeValueMany = nodeValue;
			XmlNodeValue childNodeValue;
			if (propertyDef.getNode().indexOf("/") > 0) {
				childNodeValueMany = new XmlNodeValue(childNode.substring(0, childNode.indexOf("/")));
				childNode = propertyDef.getNode().substring(propertyDef.getNode().indexOf("/") + 1, propertyDef.getNode().length());
				nodeValue.addChildNode(childNodeValueMany);
			}
			if (StringUtils.isNotBlank(propertyDef.getName())) {
				for (int i = 0; i < 2; i++) {
					String keyValue = "-----";
					Object value = "--------";
					childNodeValue = new XmlNodeValue(childNode);
					childNodeValue.setAttribute(attributeKey, keyValue);
					if (value != null)
						childNodeValue.setAttribute(attributeValue, value.toString());
					else
						childNodeValue.setAttribute(attributeValue, "");
					childNodeValueMany.addChildNode(childNodeValue);
				}

			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static void writeProperty(XmlNodeValue nodeValue, Object propertyDef) {
		try {
			String value = null;
			if (propertyDef instanceof XmlPropertyBinding) {
				XmlPropertyBinding propertyBinding = (XmlPropertyBinding) propertyDef;
				String att = !propertyBinding.isAttributeExiste() ? propertyBinding.getName() : propertyBinding.getAttribute();
				value = "-------";
				nodeValue.setAttribute(att, value);
			} else {
				final XmlNodePropertyBinding nodePropertyBinding = (XmlNodePropertyBinding) propertyDef;
				String attNode = StringUtils.isNotBlank(nodePropertyBinding.getNode()) ? nodePropertyBinding.getNode() : nodePropertyBinding.getName();
				XmlNodeValue chilNodeValue = new XmlNodeValue(attNode);
				value = "---";
				if (!nodePropertyBinding.isAttributeExiste())
					chilNodeValue.setTextBody(value);
				else {
					chilNodeValue.setAttribute(nodePropertyBinding.getAttribute(), value);
				}
				nodeValue.addChildNode(chilNodeValue);
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
