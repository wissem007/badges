package tn.com.smartsoft.framework.presentation.view.tags.utils;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.ConfigurationException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.commons.xml.parser.XmlConfiguration;
import tn.com.smartsoft.commons.xml.parser.XmlElement;

public class ResourceParserUtils {
	private static FileLocator fileLocator = new FileLocator();

	public static XmlElement getXmlRootElement(String path, String parentPath) {
		try {
			String ressourcePath = path;
			if (StringUtils.isNotBlank(parentPath)) {
				if (path.startsWith("./")) {
					int ind = StringUtils.lastIndexOf(parentPath, "/");
					ressourcePath = StringUtils.substring(parentPath, 0, ind) + StringUtils.substring(path, 1, path.length());
				}
			}
			XmlElement xmlTag = new XmlConfiguration(fileLocator.getConfURL(ressourcePath)).setRessourceLocation(ressourcePath);
			return xmlTag;
		} catch (ConfigurationException e) {
			throw new TechnicalException(e);
		}
	}
}
