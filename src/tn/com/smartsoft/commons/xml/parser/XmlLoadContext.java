package tn.com.smartsoft.commons.xml.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;

public class XmlLoadContext implements Serializable, ParserDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object root;
	private Map<String, String> systemFields = new HashMap<String, String>();
	private int type = 0;
	private String propertyKey;
	private String rootNode;
	private XmlParserManagerImp xmlParserManager;

	public XmlLoadContext(String rootNode, String propertyKey, XmlParserManagerImp xmlParserManager) {
		super();
		this.type = 2;
		this.propertyKey = propertyKey;
		this.rootNode = rootNode;
		this.xmlParserManager = xmlParserManager;
	}

	public XmlLoadContext(int type, String rootNode, XmlParserManagerImp xmlParserManager) {
		this.type = type;
		this.xmlParserManager = xmlParserManager;
		this.rootNode = rootNode;
	}

	public XmlParserManagerImp xmlParserManager() {
		return xmlParserManager;
	}

	public String getRootNode(String node) {
		if (StringUtils.isNotBlank(rootNode)) {
			return rootNode + "/" + node;
		}
		return node;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getRoot() {
		return root;
	}

	@SuppressWarnings("unchecked")
	public void addObject(Object value) throws Exception {
		if (type == 0)
			root = value;
		else if (type == 1) {
			if (root == null)
				root = new ArrayList();
			((ArrayList) root).add(value);
		} else {
			if (root == null)
				root = new HashMap();
			((HashMap) root).put(PropertyUtils.getProperty(value, propertyKey), value);
		}
	}

	public String getVariableSystem(String key) {
		return (String) systemFields.get(key);
	}

	public void addSystemField(String key, String value) {
		systemFields.put(key, value);
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this);
	}
}
