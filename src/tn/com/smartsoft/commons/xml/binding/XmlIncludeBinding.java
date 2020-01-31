package tn.com.smartsoft.commons.xml.binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.parser.XmlParserUtils;
import tn.com.smartsoft.commons.xml.schema.XsAttribute;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlIncludeBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String addedMethode;
	private String classRef;
	private String setterParentName;
	private String propertyKey;
	private String node;
	private List<XmlPropertyBinding> propertyBindings = new ArrayList<XmlPropertyBinding>();
	private String includeAttribute;

	public String getIncludeAttribute() {
		return includeAttribute;
	}

	public void setIncludeAttribute(String includeAttribute) {
		this.includeAttribute = includeAttribute;
	}

	public void addPropertyBinding(XmlPropertyBinding value) {
		value.setXmlClassBinding(getXmlClassBinding());
		propertyBindings.add(value);
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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public List<XmlPropertyBinding> getPropertyBindings() {
		return propertyBindings;
	}

	public void setPropertyBindings(List<XmlPropertyBinding> propertyBindings) {
		this.propertyBindings = propertyBindings;
	}

	public String getAddedMethode() {
		return addedMethode;
	}

	public void setAddedMethode(String addedMethode) {
		this.addedMethode = addedMethode;
	}

	public void addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManager) throws ParseException {
		String childNode = this.getNode();
		Object childValue = ParserBeanAccessorUtils.newInstance(this.getClassRef());
		if (this.getNode().indexOf("/") > 0) {
			String mayChildNode = this.getNode().substring(0, this.getNode().indexOf("/"));
			childNode = this.getNode().substring(this.getNode().indexOf("/") + 1, this.getNode().length());
			XsElement mayattElement = new XsElement(mayChildNode, "0", "1");
			xsComplexType.addElement(mayattElement);
			XsComplexType attmayXsComplexType = new XsComplexType();
			mayattElement.setComplexType(attmayXsComplexType);
			XsElement attElement = new XsElement(childNode, "0", null);
			attmayXsComplexType.addElement(attElement);
			XsComplexType attXsComplexType = new XsComplexType();
			attXsComplexType.addAttribute(new XsAttribute(getIncludeAttribute(), String.class));
			attElement.setComplexType(attXsComplexType);
			for (int j = 0; j < this.getPropertyBindings().size(); j++) {
				XmlPropertyBinding propertyBinding = (XmlPropertyBinding) this.getPropertyBindings().get(j);
				propertyBinding.addXsd(xsSchema, attXsComplexType, childValue);
			}
		} else {
			XsElement attElement = new XsElement(childNode, "0", null);
			xsComplexType.addElement(attElement);
			XsComplexType attXsComplexType = new XsComplexType();
			attXsComplexType.addAttribute(new XsAttribute(getIncludeAttribute(), String.class));
			attElement.setComplexType(attXsComplexType);
			for (int j = 0; j < this.getPropertyBindings().size(); j++) {
				XmlPropertyBinding propertyBinding = (XmlPropertyBinding) this.getPropertyBindings().get(j);
				propertyBinding.addXsd(xsSchema, attXsComplexType, childValue);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		XmlElement childElements = parentXmlElement;
		String childNode = this.getNode();
		if (this.getNode().indexOf("/") > 0) {
			childElements = parentXmlElement.getChild(this.getNode().substring(0, this.getNode().indexOf("/")));
			if (childElements == null)
				return;
			childNode = this.getNode().substring(this.getNode().indexOf("/") + 1, this.getNode().length());
		}
		List childs = childElements.getChildren(childNode);
		for (int i = 0; i < childs.size(); i++) {
			XmlElement childElement = (XmlElement) childs.get(i);
			Object childValue = ParserBeanAccessorUtils.newInstance(this.getClassRef());
			if (StringUtils.isNotBlank(this.getSetterParentName())) {
				ParserBeanAccessorUtils.setPropertyValue(childValue, this.getSetterParentName(), parentBean);
			}
			String file = xmlLoadContext.parse(childElement.getAttribute(this.getIncludeAttribute()));
			XmlParserUtils.loadClassFromFile(childValue, file, xmlLoadContext.xmlParserManager());
			for (int j = 0; j < this.getPropertyBindings().size(); j++) {
				XmlPropertyBinding propertyBinding = (XmlPropertyBinding) this.getPropertyBindings().get(j);
				final String att = !propertyBinding.isAttributeExiste() ? propertyBinding.getName() : propertyBinding.getAttribute();
				String value = childElement.getAttribute(att);
				if (StringUtils.isBlank(value))
					value = propertyBinding.getDefaultValue();
				if (StringUtils.isBlank(value) && propertyBinding.isRequired()) {
					throw new ParseException("this property :" + att + " is  Required from Node" + this.getNode());
				}
				if (StringUtils.isNotBlank(value)) {
					ParserBeanAccessorUtils.setPropertyValue(childValue, propertyBinding.getName(), xmlLoadContext.parse(value));
				}
			}
			if (StringUtils.isNotBlank(this.getName())) {
				Class<?> propertyType = ParserBeanAccessorUtils.getPropertyType(parentBean, this.getName());
				if (propertyType.equals(List.class)) {
					List list = (List) ParserBeanAccessorUtils.getPropertyValue(parentBean, this.getName());
					if (list == null) {
						list = new ArrayList();
						ParserBeanAccessorUtils.setPropertyValue(parentBean, this.getName(), list);
					}
					list.add(childValue);
				} else if (propertyType.equals(Map.class)) {
					Map map = (Map) ParserBeanAccessorUtils.getPropertyValue(parentBean, this.getName());
					if (map == null) {
						map = new HashMap();
						ParserBeanAccessorUtils.setPropertyValue(parentBean, this.getName(), map);
					}
					map.put(ParserBeanAccessorUtils.getPropertyValue(childValue, this.getPropertyKey()), childValue);
				} else {
					ParserBeanAccessorUtils.setPropertyValue(parentBean, this.getName(), childValue);
				}
			} else {
				ParserBeanAccessorUtils.invokeMethod(parentBean, this.getAddedMethode(), new Object[] { childValue });
			}
		}
	}
}
