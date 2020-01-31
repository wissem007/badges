package tn.com.digivoip.comman.config;

import java.io.File;
import tn.com.digivoip.framework.xml.XmlElement;

public class OptionsXmlConfig extends DefaultXmlConfig{

	private GuiItem				guiItem;
	private static final String	OPTIONS_PROXY	= "/options/proxy"; //$NON-NLS-1$
	private static final String	OPTIONS_GUI		= "/options/gui";	//$NON-NLS-1$
	private static final String	HTTP_PROXYHOST	= "http.proxyHost"; //$NON-NLS-1$
	private static final String	HTTP_PROXYPORT	= "http.proxyPort"; //$NON-NLS-1$
	private static final String	PROXY_HOST		= "host";			//$NON-NLS-1$
	private static final String	PROXY_PORT		= "port";			//$NON-NLS-1$

	public OptionsXmlConfig(final File file) {
		super(file);
	}
	public boolean load() {
		final boolean result = super.load();
		final XmlElement proxy = getRoot().getElement(OptionsXmlConfig.OPTIONS_PROXY);
		if ((proxy != null) && (System.getProperty(OptionsXmlConfig.HTTP_PROXYHOST) != null)) {
			System.setProperty(OptionsXmlConfig.HTTP_PROXYHOST, proxy.getAttribute(OptionsXmlConfig.PROXY_HOST));
			System.setProperty(OptionsXmlConfig.HTTP_PROXYPORT, proxy.getAttribute(OptionsXmlConfig.PROXY_PORT));
		}
		return result;
	}
	public GuiItem getGuiItem() {
		if (guiItem == null) {
			guiItem = new GuiItem(getRoot().getElement(OptionsXmlConfig.OPTIONS_GUI));
		}
		return guiItem;
	}
}
