package tn.com.smartsoft.framework.presentation.view.tags;

import java.io.StringWriter;

import tn.com.smartsoft.commons.xml.XmlParserManagerImp;
import tn.com.smartsoft.commons.xml.schema.XsSchema;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIDefauldParserContext;
import tn.com.smartsoft.framework.presentation.view.tags.parser.UIParserParameter;

public class UITestValidateFileTag {
	private static final String XML_TAGS_XML = "tn/com/smartsoft/ressources/web-setting.xml";
	private static final String XML_BINDING_SYSTEM_XML = "tn/com/smartsoft/ressources/xml-binding-system.xml";

	static {
		System.setProperty("catalina.base", "C:/Program Files/Apache Software Foundation/Tomcat 5.5");

	}

	public static void main(String[] args) throws Exception {
		XmlParserManagerImp xmlParserManager = new XmlParserManagerImp();
		xmlParserManager.loadBindingRessource(XML_BINDING_SYSTEM_XML);
		WebDefinition webDefinition = (WebDefinition) xmlParserManager.getUniqueResultat(XML_TAGS_XML, WebDefinition.class);
		XsSchema xsSchema = new XsSchema("");
		UIParserContext context = new UIDefauldParserContext(webDefinition, new UIParserParameter(null, null));
		webDefinition.getTag("window").addXsd(xsSchema, null, context);
		StringWriter buffer = new StringWriter();
		xsSchema.addToXsd(buffer);
		System.out.println(buffer);
	}
}
