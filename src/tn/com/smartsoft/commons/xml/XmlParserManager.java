package tn.com.smartsoft.commons.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.xml.parser.XmlElement;

public interface XmlParserManager {

	public abstract List<?> getListResultat(InputStream inputFile, Class<?> resultClass);

	public abstract Map<?, ?> getMapResultat(InputStream inputFile, Class<?> resultClass, String propertyKey);

	public abstract Object getUniqueResultat(InputStream inputFile, Class<?> resultClass) throws TechnicalException;

	public abstract Object getUniqueResultat(XmlElement rootElement, Class<?> resultClass) throws TechnicalException;

	public abstract List<?> getListResultat(String file, Class<?> resultClass);

	public abstract Map<?, ?> getMapResultat(String file, Class<?> resultClass, String propertyKey);

	public abstract Object getUniqueResultat(String file, Class<?> resultClass) throws TechnicalException;

	public abstract void addBindingRessource(String file) throws TechnicalException;

	public void wirtBeanToXml(Writer writer, Object bean) throws IOException;

	public void wirtListToXml(Writer writer, List<?> listBean, String rootNodeName) throws IOException;

	public void wirtMapToXml(Writer writer, Map<?, ?> listBean, String rootNodeName) throws IOException;
}