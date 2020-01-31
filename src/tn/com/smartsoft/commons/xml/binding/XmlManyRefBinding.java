package tn.com.smartsoft.commons.xml.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlManyRefBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String classRef;
	private String setterParentName;
	private String propertyKey;
	private String manyNode;
	private int min = 0;
	private String addedMethode;

	public String getAddedMethode() {
		return addedMethode;
	}

	public void setAddedMethode(String addedMethode) {
		this.addedMethode = addedMethode;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getSetterParentName() {
		return setterParentName;
	}

	public void setSetterParentName(String setterParentName) {
		this.setterParentName = setterParentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassRef() {
		return getClassRef(classRef);
	}

	public void setClassRef(String classRef) {
		this.classRef = classRef;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getManyNode() {
		return manyNode;
	}

	public void setManyNode(String manyNode) {
		this.manyNode = manyNode;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			XmlClassBinding xmlClassBindingRef = xmlParserManager.getClassBinding(this.getClassRef());
			String nodeOne = xmlClassBindingRef.getNode();
			XsComplexType childXsComplexType;
			if (!xsSchema.containsType(xmlClassBindingRef.getName()))
				childXsComplexType = xmlClassBindingRef.addXsd(xsSchema, xsComplexType, parentBean, xmlParserManager);
			else
				childXsComplexType = (XsComplexType) xsSchema.getType(xmlClassBindingRef.getName());
			if (StringUtils.isNotBlank(this.getManyNode())) {
				XsComplexType mayChildXsComplexType = new XsComplexType();
				XsElement mayElement = new XsElement(this.getManyNode(), "0", "1");
				xsComplexType.addElement(mayElement);
				mayElement.setComplexType(mayChildXsComplexType);
				mayChildXsComplexType.addElement(new XsElement(nodeOne, childXsComplexType.getName(), "0", null));
			} else {
				xsComplexType.addElement(new XsElement(nodeOne, childXsComplexType.getName(), "0", null));
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		try {
			XmlClassBinding xmlClassBindingRef = xmlLoadContext.xmlParserManager().getClassBinding(this.getClassRef());
			XmlElement childElements = parentXmlElement;
			if (parentXmlElement.getChild(this.getManyNode()) != null) {
				childElements = parentXmlElement.getChild(this.getManyNode());
				if (childElements == null)
					return;
			}
			List<XmlElement> childs = childElements.getChildren(xmlClassBindingRef.getNode());
			for (int i = 0; i < childs.size(); i++) {
				XmlElement childElement = (XmlElement) childs.get(i);
				Object childValue = xmlClassBindingRef.newInstance();
				if (StringUtils.isNotBlank(this.getSetterParentName())) {
					ParserBeanAccessorUtils.setPropertyValue(childValue, this.getSetterParentName(), parentBean);
				}
				xmlClassBindingRef.read(childElement, childValue, xmlLoadContext);
				if (StringUtils.isNotBlank(this.getName())) {
					Class<?> propertyType;
					propertyType = ParserBeanAccessorUtils.getPropertyType(parentBean, this.getName());
					if (propertyType.equals(List.class)) {
						List list;
						list = (List) PropertyUtils.getProperty(parentBean, this.getName());
						if (list == null) {
							list = new ArrayList();
							PropertyUtils.setProperty(parentBean, this.getName(), list);
						}
						list.add(childValue);
					} else if (propertyType.equals(Map.class)) {
						Map map = (Map) ParserBeanAccessorUtils.getPropertyValue(parentBean, this.getName());
						if (map == null) {
							map = new HashMap();
							ParserBeanAccessorUtils.setPropertyValue(parentBean, this.getName(), map);
						}
						map.put(ParserBeanAccessorUtils.getPropertyValue(parentBean, this.getPropertyKey()), childValue);
					}
				} else {
					ParserBeanAccessorUtils.invokeMethod(parentBean, this.getAddedMethode(), childValue);
				}
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}

	}

}
