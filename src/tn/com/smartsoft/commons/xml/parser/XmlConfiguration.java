package tn.com.smartsoft.commons.xml.parser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.VFS;

import tn.com.smartsoft.commons.exceptions.ConfigurationException;
import tn.com.smartsoft.commons.utils.ClassUtilities;
import tn.com.smartsoft.commons.xml.utils.ParserDefinition;

public class XmlConfiguration extends XmlElement {

	// private static final Log log = LogFactory.getLog(XMLConfiguration.class);
	private XmlDomElement configuration;

	public XmlConfiguration(String name) {

		throw new UnsupportedOperationException("Cannot create XMLConfiguration");
	}

	public XmlConfiguration(File file) throws ConfigurationException {
		this.configuration = new XmlDomElement();
		load(file);
	}

	public XmlConfiguration(FileObject file) throws ConfigurationException {
		this.configuration = new XmlDomElement();
		load(file);
	}

	public XmlConfiguration(URL url) throws ConfigurationException {
		this.configuration = new XmlDomElement();
		load(url);
	}

	public XmlConfiguration(InputStream in) throws ConfigurationException {
		this.configuration = new XmlDomElement();
		load(in);
	}

	public XmlConfiguration(Reader reader) throws ConfigurationException {
		this.configuration = new XmlDomElement();
		load(reader);
	}

	public String getName() {
		return configuration.getName();
	}

	public XmlElement getParent() {
		return configuration.getParent();
	}

	public XmlElement getChild(String name) {
		return configuration.getChild(name);
	}

	public String getChildValue(String name) {
		return configuration.getChildValue(name);
	}

	public String getChildValue(String name, String defaultValue) {
		return configuration.getChildValue(name, defaultValue);
	}

	public List<XmlElement> getChildren() {
		return configuration.getChildren();
	}

	public List<XmlElement> getChildren(String name) {
		return configuration.getChildren(name);
	}

	public List<String> getAttributeNames() {
		return configuration.getAttributeNames();
	}

	public String getAttribute(String name) {
		return configuration.getAttribute(name);
	}

	public String getAttribute(String name, String defaultValue) {
		return configuration.getAttribute(name, defaultValue);
	}

	public String getValue() {
		return configuration.getValue();
	}

	public XmlElement setParserDefinition(ParserDefinition parserDefinition) {
		return configuration.setParserDefinition(parserDefinition);
	}

	public XmlElement setRessourceLocation(String ressourceLocation) {
		return configuration.setRessourceLocation(ressourceLocation);
	}

	public void load(String filename) throws ConfigurationException {
		if (filename == null) {
			throw new ConfigurationException("Filename cannot be null");
		}
		try {
			FileSystemManager fsManager = VFS.getManager();
			FileObject file = fsManager.resolveFile(filename);
			if (file.exists()) {
				load(file);
			} else {
				InputStream in = ClassUtilities.getResourceAsStream(filename);
				if (in != null) {
					try {
						load(in);
					} catch (Exception e) {
						IOUtils.closeQuietly(in);
					}
				} else {
					throw new ConfigurationException("Configuration " + filename + " not found");
				}
			}
		} catch (FileSystemException e) {
			throw new ConfigurationException(e);
		}
	}

	public void load(File file) throws ConfigurationException {
		if (file == null) {
			throw new ConfigurationException("File cannot be null");
		}
		try {
			FileSystemManager fsManager = VFS.getManager();
			load(fsManager.toFileObject(file));
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	public void load(FileObject file) throws ConfigurationException {
		if (file == null) {
			throw new ConfigurationException("File cannot be null");
		}
		InputStream in = null;
		try {
			FileContent content = file.getContent();
			in = content.getInputStream();
			load(in);
		} catch (Exception e) {
			throw new ConfigurationException(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public void load(URL url) throws ConfigurationException {
		if (url == null) {
			throw new ConfigurationException("URL cannot be null");
		}
		InputStream in = null;
		try {
			in = url.openStream();
			load(in);
		} catch (Exception e) {
			throw new ConfigurationException(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public void load(InputStream in) throws ConfigurationException {
		if (in == null) {
			throw new ConfigurationException("InputStream cannot be null");
		}
		try {
			configuration.load(in);
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	public void load(Reader reader) throws ConfigurationException {
		if (reader == null) {
			throw new ConfigurationException("Reader cannot be null");
		}
		try {
			configuration.load(reader);
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	public String getRessourceLocation() {
		return configuration.getRessourceLocation();
	}

}
