package tn.com.digivoip.comman.config;

import java.io.File;
import java.net.MalformedURLException;
import tn.com.digivoip.framework.xml.XmlIO;

public class DefaultXmlConfig extends XmlIO{

	@SuppressWarnings("deprecation")
	public DefaultXmlConfig(final File file) {
		try {
			setURL(file.toURL());
		} catch (final MalformedURLException mue) {}
	}
	public boolean load() {
		final boolean result = super.load();
		return result;
	}
}
