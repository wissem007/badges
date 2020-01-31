package tn.com.smartsoft.commons.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.commons.xml.binding.XmlClassBinding;
import tn.com.smartsoft.commons.xml.parser.XmlConfiguration;
import tn.com.smartsoft.commons.xml.parser.XmlElement;
import tn.com.smartsoft.commons.xml.parser.XmlParserUtils;
import tn.com.smartsoft.commons.xml.utils.XmlNodeValue;
import tn.com.smartsoft.commons.xml.utils.XmlWriterUtils;

public class XmlParserManagerImp implements Serializable, XmlParserManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(XmlParserManager.class);
	private Map<String, XmlClassBinding> classBindings = new HashMap<String, XmlClassBinding>(0);
	private URL rules;
	private String packageValue;
	private FileLocator fileLocator = new FileLocator();

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public XmlParserManagerImp() {
		rules = fileLocator.getConfURL("tn/com/smartsoft/commons/xml/xml-mapping-rules.xml");
	}

	public void addClassBinding(XmlClassBinding value) {
		value.setPackageValue(packageValue);
		if (value.getName().indexOf(".") < 0)
			value.setName(getPackageValue() + "." + value.getName());
		if (StringUtils.isNotBlank(value.getExtendsClass())) {
			if (value.getExtendsClass().indexOf(".") < 0)
				value.setExtendsClass(getPackageValue() + "." + value.getExtendsClass());
		}
		classBindings.put(value.getName(), value);
	}

	public XmlClassBinding getClassBinding(String name) {
		if (classBindings.get(name) == null)
			throw new TechnicalException("no xml definition form class name :" + name);
		return (XmlClassBinding) classBindings.get(name);
	}

	public XmlElement loadXmlRessource(String file) throws TechnicalException {
		try {
			logger.info("Reading mappings from resource :" + file);
			XmlElement resutat = new XmlConfiguration(fileLocator.getConfURL(file));
			return resutat;
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException("error de chargement de fichie :" + file, e);
		}
	}

	@SuppressWarnings("unchecked")
	public List getListResultat(InputStream inputFile, Class resultClass) {
		try {
			return XmlParserUtils.getListResultat(inputFile, this, resultClass);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Map getMapResultat(InputStream inputFile, Class resultClass, String propertyKey) {
		try {
			return XmlParserUtils.getMapResultat(inputFile, this, resultClass, propertyKey);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getUniqueResultat(InputStream inputFile, Class resultClass) throws TechnicalException {
		try {
			return XmlParserUtils.getUniqueResultat(inputFile, this, resultClass);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List getListResultat(String file, Class resultClass) {
		try {
			return XmlParserUtils.getListResultat(file, this, resultClass);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Map getMapResultat(String file, Class resultClass, String propertyKey) {
		try {
			return XmlParserUtils.getMapResultat(file, this, resultClass, propertyKey);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getUniqueResultat(String file, Class resultClass) throws TechnicalException {
		try {
			return XmlParserUtils.getUniqueResultat(file, this, resultClass);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getUniqueResultat(XmlElement rootElement, Class resultClass) throws TechnicalException {
		try {
			return XmlParserUtils.getUniqueResultat(rootElement, this, resultClass);
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

	public void wirtBeanToXml(Writer writer, Object bean) throws IOException {
		XmlNodeValue nodeValue = XmlWriterUtils.transformToXml(bean, this);
		nodeValue.createNode(writer);
	}

	@SuppressWarnings("unchecked")
	public void wirtListToXml(Writer writer, List listBean, String rootNodeName) throws IOException {
		XmlNodeValue nodeValue = XmlWriterUtils.transformToXml(listBean, rootNodeName, this);
		nodeValue.createNode(writer);
	}

	@SuppressWarnings("unchecked")
	public void wirtMapToXml(Writer writer, Map listBean, String rootNodeName) throws IOException {
		XmlNodeValue nodeValue = XmlWriterUtils.transformToXml(listBean, rootNodeName, this);
		nodeValue.createNode(writer);
	}

	public void loadBindingRessource(String file) throws TechnicalException {
		Digester digester = DigesterLoader.createDigester(rules);
		digester.push(XmlParserManagerImp.this);
		digester.addCallMethod("xml-binding-system/binding", "addBindingRessource", 1);
		digester.addCallParam("xml-binding-system/binding", 0, "location");
		try {
			digester.parse(fileLocator.getConfStream(file));
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public void addBindingRessource(String file) throws TechnicalException {
		Digester digester = DigesterLoader.createDigester(rules);
		digester.push(XmlParserManagerImp.this);
		try {
			digester.parse(fileLocator.getConfStream(file));
		} catch (Exception e) {
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			}
			throw new TechnicalException(e);
		}
	}

}
