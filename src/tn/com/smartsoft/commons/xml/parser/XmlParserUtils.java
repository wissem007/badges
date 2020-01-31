package tn.com.smartsoft.commons.xml.parser;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.xml.XmlParserManager;
import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.binding.XmlClassBinding;
import tn.com.smartsoft.commons.xml.exception.ParseException;

public class XmlParserUtils {
	protected static Logger logger = Logger.getLogger(XmlParserManager.class);

	@SuppressWarnings("unchecked")
	public static List getListResultat(InputStream in, XmlParserManagerImp xmlBinding, Class<?> resultClass) throws ParseException {
		try {
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
			XmlElement rootElement = new XmlConfiguration(in);
			XmlLoadContext xmlLoadFile = new XmlLoadContext(1, rootElement.getName(), xmlBinding);
			List<XmlElement> childs = rootElement.getChildren(xmlClassBinding.getNode());
			for (int i = 0; i < childs.size(); i++) {
				XmlElement childElement = (XmlElement) childs.get(i);
				xmlLoadFile.addObject(loadXmlClassBinding(childElement, xmlClassBinding, xmlLoadFile));
			}
			return (List) xmlLoadFile.getRoot();
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List getListResultat(String file, XmlParserManagerImp xmlBinding, Class<?> resultClass) throws ParseException {
		try {
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
			XmlElement rootElement = xmlBinding.loadXmlRessource(file);
			XmlLoadContext xmlLoadFile = new XmlLoadContext(1, rootElement.getName(), xmlBinding);
			List childs = rootElement.getChildren(xmlClassBinding.getNode());
			for (int i = 0; i < childs.size(); i++) {
				XmlElement childElement = (XmlElement) childs.get(i);
				xmlLoadFile.addObject(loadXmlClassBinding(childElement, xmlClassBinding, xmlLoadFile));
			}
			return (List) xmlLoadFile.getRoot();
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map getMapResultat(InputStream in, XmlParserManagerImp xmlBinding, Class<?> resultClass, String propertyKey) throws ParseException {
		try {
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
			XmlElement rootElement = new XmlConfiguration(in);
			XmlLoadContext xmlLoadFile = new XmlLoadContext(rootElement.getName(), propertyKey, xmlBinding);
			List childs = rootElement.getChildren(xmlClassBinding.getNode());
			for (int i = 0; i < childs.size(); i++) {
				XmlElement childElement = (XmlElement) childs.get(i);
				xmlLoadFile.addObject(loadXmlClassBinding(childElement, xmlClassBinding, xmlLoadFile));
			}
			return (Map) xmlLoadFile.getRoot();
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map getMapResultat(String file, XmlParserManagerImp xmlBinding, Class<?> resultClass, String propertyKey) throws ParseException {
		try {
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
			XmlElement rootElement = xmlBinding.loadXmlRessource(file);
			XmlLoadContext xmlLoadFile = new XmlLoadContext(rootElement.getName(), propertyKey, xmlBinding);
			List childs = rootElement.getChildren(xmlClassBinding.getNode());
			for (int i = 0; i < childs.size(); i++) {
				XmlElement childElement = (XmlElement) childs.get(i);
				xmlLoadFile.addObject(loadXmlClassBinding(childElement, xmlClassBinding, xmlLoadFile));
			}
			return (Map) xmlLoadFile.getRoot();
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object getUniqueResultat(XmlElement rootElement, XmlParserManagerImp xmlBinding, Class<?> resultClass) throws ParseException {
		try {
			XmlLoadContext xmlLoadFile = new XmlLoadContext(0, null, xmlBinding);
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
			return loadXmlClassBinding(rootElement, xmlClassBinding, xmlLoadFile);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object getUniqueResultat(InputStream in, XmlParserManagerImp xmlBinding, Class<?> resultClass) throws ParseException {
		try {
			XmlElement rootElement = new XmlConfiguration(in);
			XmlLoadContext xmlLoadFile = new XmlLoadContext(0, null, xmlBinding);
			XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
			return loadXmlClassBinding(rootElement, xmlClassBinding, xmlLoadFile);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object getUniqueResultat(String file, XmlParserManagerImp xmlBinding, Class<?> resultClass) throws ParseException {
		XmlElement rootElement = xmlBinding.loadXmlRessource(file);
		XmlLoadContext xmlLoadFile = new XmlLoadContext(0, null, xmlBinding);
		XmlClassBinding xmlClassBinding = xmlBinding.getClassBinding(resultClass.getName());
		return loadXmlClassBinding(rootElement, xmlClassBinding, xmlLoadFile);
	}

	@SuppressWarnings("unchecked")
	public static Object loadXmlClassBinding(XmlElement rootElement, XmlClassBinding xmlClassBinding, final XmlLoadContext xmlLoadFile) throws ParseException {
		return xmlClassBinding.read(rootElement, xmlLoadFile);
	}

	@SuppressWarnings("unchecked")
	public static void loadClassFromFile(Object result, String file, XmlParserManagerImp xmlParserManager) throws ParseException {
		try {
			XmlClassBinding xmlClassBinding = xmlParserManager.getClassBinding(result.getClass().getName());
			System.out.println("filefilefilefilefilefile:"+file);
			xmlClassBinding.read(file, result, xmlParserManager);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
}
