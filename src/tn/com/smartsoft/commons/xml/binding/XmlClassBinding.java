package tn.com.smartsoft.commons.xml.binding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.xml.XmlParserManager;
import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlLoadContext;
import tn.com.smartsoft.commons.xml.schema.XsComplexType;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.commons.xml.utils.ParserBeanAccessorUtils;

public class XmlClassBinding extends XmlBindingElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String node;
	private String packageValue;
	private String extendsClass;
	private List<XmlOneRefBinding> oneRefBindings = new ArrayList<XmlOneRefBinding>();
	private List<XmlManyRefBinding> manyRefBindings = new ArrayList<XmlManyRefBinding>();
	private List<Serializable> propertyBindings = new ArrayList<Serializable>();
	private List<XmlMappedPropertyNodeBinding> mappedPropertyNodeBindings = new ArrayList<XmlMappedPropertyNodeBinding>();
	private List<XmlMappedPropertyNodeBinding> systemePropertieNodeBindings = new ArrayList<XmlMappedPropertyNodeBinding>();
	private List<XmlIncludeBinding> includeBindings = new ArrayList<XmlIncludeBinding>();
	private List<XmlListPropertyNodeBinding> listPropertyNodeBindings = new ArrayList<XmlListPropertyNodeBinding>();
	private boolean loaddedAfterProperty = true;
	private String propertyBody;
	private List<String> dynamicNodes = new ArrayList<String>();
	protected static Logger logger = Logger.getLogger(XmlParserManager.class);

	public String getPropertyBody() {
		return propertyBody;
	}

	public void setPropertyBody(String propertyBody) {
		this.propertyBody = propertyBody;
	}

	public boolean isLoaddedAfterProperty() {
		return loaddedAfterProperty;
	}

	public void setLoaddedAfterProperty(boolean loaddedAfterProperty) {
		this.loaddedAfterProperty = loaddedAfterProperty;
	}

	public void addOneRefBinding(XmlOneRefBinding value) {
		value.setXmlClassBinding(this);
		oneRefBindings.add(value);
	}

	public void addDynamicNode(String value) {
		dynamicNodes.add(value);
	}

	public List<String> getDynamicNodes() {
		return dynamicNodes;
	}

	public void addManyRefBinding(XmlManyRefBinding value) {
		value.setXmlClassBinding(this);
		manyRefBindings.add(value);
	}

	public void addNodePropertyBinding(XmlNodePropertyBinding value) {
		value.setXmlClassBinding(this);
		propertyBindings.add(value);
	}

	public void addPropertyBinding(XmlPropertyBinding value) {
		value.setXmlClassBinding(this);
		propertyBindings.add(value);
	}

	public void addMappedPropertyNodeBinding(XmlMappedPropertyNodeBinding value) {
		value.setXmlClassBinding(this);
		mappedPropertyNodeBindings.add(value);
	}

	public void addListPropertyNodeBinding(XmlListPropertyNodeBinding value) {
		value.setXmlClassBinding(this);
		listPropertyNodeBindings.add(value);
	}

	public void addSystemePropertyNodeBinding(XmlMappedPropertyNodeBinding value) {
		value.setXmlClassBinding(this);
		systemePropertieNodeBindings.add(value);
	}

	public void addIncludeBinding(XmlIncludeBinding value) {
		value.setXmlClassBinding(this);
		includeBindings.add(value);
	}

	public List<XmlMappedPropertyNodeBinding> getMappedPropertyNodeBindings() {
		return mappedPropertyNodeBindings;
	}

	public List<XmlMappedPropertyNodeBinding> getSystemPropertieNodeBindings() {
		return systemePropertieNodeBindings;
	}

	public List<XmlIncludeBinding> getIncludeBindings() {
		return includeBindings;
	}

	public List<XmlOneRefBinding> getOneRefBindings() {
		return oneRefBindings;
	}

	public List<XmlManyRefBinding> getManyRefBindings() {
		return manyRefBindings;
	}

	public List<Serializable> getPropertyBindings() {
		return propertyBindings;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public List<XmlListPropertyNodeBinding> getListPropertyNodeBindings() {
		return listPropertyNodeBindings;
	}

	public String getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}

	public Object newInstance() throws ParseException {
		return ParserBeanAccessorUtils.newInstance(getName());
	}

	public XsComplexType addXsd(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManageraddType) throws ParseException {
		try {
			XsComplexType childXsComplexType = new XsComplexType(getName());
			xsSchema.addType(childXsComplexType);
			Object result = newInstance();
			addXsdInType(xsSchema, childXsComplexType, result, xmlParserManageraddType);
			return childXsComplexType;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void addXsdInType(XsSchema xsSchema, XsComplexType xsComplexType, Object parentBean, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			Object result = parentBean;
			for (int i = 0; i < this.getSystemPropertieNodeBindings().size(); i++) {
				((XmlMappedPropertyNodeBinding) this.getSystemPropertieNodeBindings().get(i)).addXsd(xsSchema, xsComplexType, result, xmlParserManager);
			}
			for (int i = 0; i < this.getPropertyBindings().size(); i++) {
				Object propertyDef = this.getPropertyBindings().get(i);
				if (propertyDef instanceof XmlPropertyBinding) {
					XmlPropertyBinding propertyBinding = (XmlPropertyBinding) propertyDef;
					propertyBinding.addXsd(xsSchema, xsComplexType, result);
				} else {
					XmlNodePropertyBinding nodePropertyBinding = (XmlNodePropertyBinding) propertyDef;
					nodePropertyBinding.addXsd(xsSchema, xsComplexType, result, xmlParserManager);
				}
			}
			for (int i = 0; i < this.getMappedPropertyNodeBindings().size(); i++) {
				((XmlMappedPropertyNodeBinding) this.getMappedPropertyNodeBindings().get(i)).addXsd(xsSchema, xsComplexType, result, xmlParserManager);
			}
			for (int i = 0; i < this.getListPropertyNodeBindings().size(); i++) {
				((XmlListPropertyNodeBinding) this.getListPropertyNodeBindings().get(i)).addXsd(xsSchema, xsComplexType, result, xmlParserManager);
			}
			for (int i = 0; i < this.getOneRefBindings().size(); i++) {
				((XmlOneRefBinding) this.getOneRefBindings().get(i)).addXsd(xsSchema, xsComplexType, result, xmlParserManager);
			}
			for (int i = 0; i < this.getManyRefBindings().size(); i++) {
				((XmlManyRefBinding) this.getManyRefBindings().get(i)).addXsd(xsSchema, xsComplexType, result, xmlParserManager);
			}
			for (int i = 0; i < this.getIncludeBindings().size(); i++) {
				((XmlIncludeBinding) this.getIncludeBindings().get(i)).addXsd(xsSchema, xsComplexType, result, xmlParserManager);
			}
			if (StringUtils.isNotBlank(this.getExtendsClass()) && xmlParserManager.getClassBinding(this.getExtendsClass()) != null) {
				(xmlParserManager.getClassBinding(this.getExtendsClass())).addXsdInType(xsSchema, xsComplexType, parentBean, xmlParserManager);
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public Object read(XmlElement parentXmlElement, XmlLoadContext xmlLoadContext) throws ParseException {
		try {
			String node = this.getNode();
			XmlElement childeElement = parentXmlElement;
			if (!this.getNode().equalsIgnoreCase(parentXmlElement.getName()))
				childeElement = parentXmlElement.getChild(node);
			if (childeElement == null)
				return null;
			logger.info("Mapping class :" + this.getName() + " -> " + childeElement.getName());
			Object result = newInstance();
			this.read(childeElement, result, xmlLoadContext);
			return result;

		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void read(String file, Object result, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			XmlElement rootElement = xmlParserManager.loadXmlRessource(file);
			XmlLoadContext xmlLoadFile = new XmlLoadContext(0, null, xmlParserManager);
			String node = this.getNode();
			XmlElement childeElement = rootElement;
			if (!this.getNode().equalsIgnoreCase(rootElement.getName()))
				childeElement = rootElement.getChild(node);
			if (childeElement == null)
				return;
			this.read(rootElement, result, xmlLoadFile);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public void read(XmlElement parentXmlElement, Object parentBean, XmlLoadContext xmlLoadContext) throws ParseException {
		if (parentXmlElement == null)
			return;
		logger.info("Mapping class :" + this.getName() + " -> " + parentXmlElement.getName());
		for (int i = 0; i < this.getSystemPropertieNodeBindings().size(); i++) {
			((XmlMappedPropertyNodeBinding) this.getSystemPropertieNodeBindings().get(i)).readSystemPropertie(parentXmlElement, parentBean, xmlLoadContext);
		}
		for (int i = 0; i < this.getPropertyBindings().size(); i++) {
			Object propertyDef = this.getPropertyBindings().get(i);
			if (propertyDef instanceof XmlPropertyBinding) {
				XmlPropertyBinding propertyBinding = (XmlPropertyBinding) propertyDef;
				propertyBinding.read(parentXmlElement, parentBean, xmlLoadContext);
			} else {
				XmlNodePropertyBinding nodePropertyBinding = (XmlNodePropertyBinding) propertyDef;
				nodePropertyBinding.read(parentXmlElement, parentBean, xmlLoadContext);
			}
		}
		for (int i = 0; i < this.getMappedPropertyNodeBindings().size(); i++) {
			((XmlMappedPropertyNodeBinding) this.getMappedPropertyNodeBindings().get(i)).read(parentXmlElement, parentBean, xmlLoadContext);
		}
		for (int i = 0; i < this.getListPropertyNodeBindings().size(); i++) {
			((XmlListPropertyNodeBinding) this.getListPropertyNodeBindings().get(i)).read(parentXmlElement, parentBean, xmlLoadContext);
		}
		for (int i = 0; i < this.getOneRefBindings().size(); i++) {
			((XmlOneRefBinding) this.getOneRefBindings().get(i)).read(parentXmlElement, parentBean, xmlLoadContext);
		}
		for (int i = 0; i < this.getManyRefBindings().size(); i++) {
			((XmlManyRefBinding) this.getManyRefBindings().get(i)).read(parentXmlElement, parentBean, xmlLoadContext);
		}
		for (int i = 0; i < this.getIncludeBindings().size(); i++) {
			((XmlIncludeBinding) this.getIncludeBindings().get(i)).read(parentXmlElement, parentBean, xmlLoadContext);
		}
		if (StringUtils.isNotBlank(this.getExtendsClass()) && xmlLoadContext.xmlParserManager().getClassBinding(this.getExtendsClass()) != null) {
			(xmlLoadContext.xmlParserManager().getClassBinding(this.getExtendsClass())).read(parentXmlElement, parentBean, xmlLoadContext);
		}
	}
}
