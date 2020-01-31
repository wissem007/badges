package tn.com.smartsoft.commons.xml.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.binding.XmlClassBinding;
import tn.com.smartsoft.commons.xml.binding.XmlListPropertyNodeBinding;
import tn.com.smartsoft.commons.xml.binding.XmlManyRefBinding;
import tn.com.smartsoft.commons.xml.binding.XmlMappedPropertyNodeBinding;
import tn.com.smartsoft.commons.xml.binding.XmlNodePropertyBinding;
import tn.com.smartsoft.commons.xml.binding.XmlOneRefBinding;
import tn.com.smartsoft.commons.xml.binding.XmlPropertyBinding;

public class XmlWriterUtils {
	public static XmlNodeValue transformToXml(Object bean, XmlParserManagerImp xmlBinding) {
		XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(bean.getClass().getName());
		XmlNodeValue nodeValue = new XmlNodeValue(xmlClassBinding.getNode());
		writeObjectNode(nodeValue, bean, xmlBinding);
		return nodeValue;
	}

	public static XmlNodeValue transformToXml(List<?> listBean, String rootNodeName, XmlParserManagerImp xmlBinding) {
		return writeCollectionObjectToXml(listBean, rootNodeName, xmlBinding);
	}

	public static XmlNodeValue transformToXml(Map<?, ?> listBean, String rootNodeName, XmlParserManagerImp xmlBinding) {
		return writeCollectionObjectToXml(listBean.values(), rootNodeName, xmlBinding);
	}

	private static XmlNodeValue writeCollectionObjectToXml(Collection<?> listObject, String rootNodeName, XmlParserManagerImp xmlBinding) {
		XmlNodeValue nodeValue = new XmlNodeValue(rootNodeName);
		for (Iterator<?> iterator = listObject.iterator(); iterator.hasNext();) {
			writeObjectNode(nodeValue, iterator.next(), xmlBinding);
		}
		return nodeValue;
	}

	public static void writeObjectNode(XmlNodeValue parentNodeValue, Object bean, XmlParserManagerImp xmlBinding) {
		try {
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(bean.getClass().getName());
			XmlNodeValue nodeValue = parentNodeValue;
			String node = xmlClassBinding.getNode();
			if (!node.equalsIgnoreCase(parentNodeValue.getName())) {
				nodeValue = new XmlNodeValue(xmlClassBinding.getNode());
				parentNodeValue.addChildNode(nodeValue);
			}
			if (StringUtils.isNotBlank(xmlClassBinding.getPropertyBody()) && PropertyUtils.isReadable(bean, xmlClassBinding.getPropertyBody())) {
				parentNodeValue.setTextBody(BeanUtils.getProperty(bean, xmlClassBinding.getPropertyBody()));
			}
			writeAllProperty(nodeValue, bean, xmlBinding, xmlClassBinding);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	private static void writeAllProperty(XmlNodeValue nodeValue, Object bean, XmlParserManagerImp xmlBinding, XmlClassBinding xmlClassBinding) {

		for (int i = 0; i < xmlClassBinding.getPropertyBindings().size(); i++) {
			writeProperty(nodeValue, bean, xmlClassBinding.getPropertyBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getMappedPropertyNodeBindings().size(); i++) {
			writeMappedPropertyNode(nodeValue, bean, (XmlMappedPropertyNodeBinding) xmlClassBinding.getMappedPropertyNodeBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getListPropertyNodeBindings().size(); i++) {
			writeListProperty(nodeValue, bean, (XmlListPropertyNodeBinding) xmlClassBinding.getListPropertyNodeBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getOneRefBindings().size(); i++) {
			writeOneRefObject(nodeValue, bean, xmlBinding, (XmlOneRefBinding) xmlClassBinding.getOneRefBindings().get(i));
		}
		for (int i = 0; i < xmlClassBinding.getManyRefBindings().size(); i++) {
			writeManyRefObject(nodeValue, bean, xmlBinding, (XmlManyRefBinding) xmlClassBinding.getManyRefBindings().get(i));
		}
		writeDynamicNode(nodeValue, bean, xmlClassBinding);
		if (StringUtils.isNotBlank(xmlClassBinding.getExtendsClass()) && xmlBinding.getClassBinding(xmlClassBinding.getExtendsClass()) != null) {
			writeAllProperty(nodeValue, bean, xmlBinding, xmlBinding.getClassBinding(xmlClassBinding.getExtendsClass()));
		}
	}

	@SuppressWarnings("unchecked")
	public static void writeManyRefObject(XmlNodeValue nodeValue, Object bean, XmlParserManagerImp xmlBinding, XmlManyRefBinding xmlManyRefBinding) {
		try {
			XmlNodeValue childNodeValueMany = nodeValue;
			if (StringUtils.isNotBlank(xmlManyRefBinding.getManyNode())) {
				childNodeValueMany = new XmlNodeValue(xmlManyRefBinding.getManyNode());
				nodeValue.addChildNode(childNodeValueMany);
			}
			Class<?> propertyType = PropertyUtils.getPropertyType(bean, xmlManyRefBinding.getName());
			Collection listObject = null;
			if (propertyType.equals(List.class)) {
				listObject = (Collection) PropertyUtils.getProperty(bean, xmlManyRefBinding.getName());
			} else if (propertyType.equals(Map.class)) {
				Map map = (Map) PropertyUtils.getProperty(bean, xmlManyRefBinding.getName());
				if (map != null) {
					listObject = map.values();
				}
			}
			if (listObject != null) {
				for (Iterator iterator = listObject.iterator(); iterator.hasNext();) {
					writeObjectNode(childNodeValueMany, iterator.next(), xmlBinding);
				}
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static void writeOneRefObject(XmlNodeValue nodeValue, Object bean, XmlParserManagerImp xmlBinding, XmlOneRefBinding xmlOneRefBinding) {
		try {
			Object childBean = PropertyUtils.getProperty(bean, xmlOneRefBinding.getName());
			if (childBean == null)
				return;
			writeObjectNode(nodeValue, bean, xmlBinding);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static void writeMappedPropertyNode(XmlNodeValue nodeValue, Object bean, XmlMappedPropertyNodeBinding propertyDef) {
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
				Class propertyType = PropertyUtils.getPropertyType(bean, propertyDef.getName());
				if (propertyType.equals(Map.class)) {
					Map map = (Map) PropertyUtils.getProperty(bean, propertyDef.getName());
					if (map == null) {
						for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
							String keyValue = (String) iterator.next();
							Object value = iterator.next();
							childNodeValue = new XmlNodeValue(childNode);
							childNodeValue.setAttribute(attributeKey, keyValue);
							if (value != null)
								childNodeValue.setAttribute(attributeValue, value.toString());
							else
								childNodeValue.setAttribute(attributeValue, "");
							childNodeValueMany.addChildNode(childNodeValue);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static void writeListProperty(XmlNodeValue nodeValue, Object bean, XmlListPropertyNodeBinding propertyDef) {
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
			Class propertyType = PropertyUtils.getPropertyType(bean, propertyDef.getName());
			if (propertyType.equals(List.class)) {
				List listValue = (List) PropertyUtils.getProperty(bean, propertyDef.getName());
				if (listValue != null) {
					for (int i = 0; i < listValue.size(); i++) {
						childNodeValue = new XmlNodeValue(childNode);
						Object value = listValue.get(i);
						if (value != null)
							childNodeValue.setAttribute(attributeValue, value.toString());
						else
							childNodeValue.setAttribute(attributeValue, "");
						childNodeValueMany.addChildNode(childNodeValue);
					}
				}
			}
		} catch (Exception e1) {
			throw new TechnicalException(e1);
		}
	}

	public static void writeDynamicNode(XmlNodeValue nodeValue, Object bean, XmlClassBinding xmlClassBinding) {
		try {
			for (int i = 0; i < xmlClassBinding.getDynamicNodes().size(); i++) {
				String name = (String) xmlClassBinding.getDynamicNodes().get(i);
				if (!PropertyUtils.isReadable(bean, name)) {
					return;
				}
				Object value = PropertyUtils.getProperty(bean, name);
				if (value != null && value instanceof XmlNodeValue) {
					nodeValue.addChildNode((XmlNodeValue) value);
				}
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static void writeProperty(XmlNodeValue nodeValue, Object bean, Object propertyDef) {
		try {
			String value = null;
			if (propertyDef instanceof XmlPropertyBinding) {
				XmlPropertyBinding propertyBinding = (XmlPropertyBinding) propertyDef;
				if (!PropertyUtils.isReadable(bean, propertyBinding.getName())) {
					return;
				}
				String att = !propertyBinding.isAttributeExiste() ? propertyBinding.getName() : propertyBinding.getAttribute();
				value = BeanUtils.getProperty(bean, propertyBinding.getName());
				nodeValue.setAttribute(att, value);
			} else {
				final XmlNodePropertyBinding nodePropertyBinding = (XmlNodePropertyBinding) propertyDef;
				if (!PropertyUtils.isReadable(bean, nodePropertyBinding.getName())) {
					return;
				}
				String attNode = StringUtils.isNotBlank(nodePropertyBinding.getNode()) ? nodePropertyBinding.getNode() : nodePropertyBinding.getName();
				XmlNodeValue chilNodeValue = new XmlNodeValue(attNode);
				value = BeanUtils.getProperty(bean, nodePropertyBinding.getName());
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
