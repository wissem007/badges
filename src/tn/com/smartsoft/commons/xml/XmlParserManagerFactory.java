package tn.com.smartsoft.commons.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import tn.com.smartsoft.commons.xml.binding.XmlClassBinding;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.commons.xml.schema.XsElement;
import tn.com.smartsoft.commons.xml.schema.XsSchema;

public class XmlParserManagerFactory {
	private static XmlParserManager xmlParserManager = null;
	public static final String BINDING_SYSTEM_XML = "tn/com/smartsoft/ressources/xml-binding-system.xml";

	public static XmlParserManager getXmlParserManager() {
		if (xmlParserManager == null) {
			xmlParserManager = new XmlParserManagerImp();
			((XmlParserManagerImp) xmlParserManager).loadBindingRessource(BINDING_SYSTEM_XML);
		}
		return xmlParserManager;
	}

	public static void genrataFile(File file, String srcCode) {
		try {
			File fileDir = new File(file.getParent());
			if (!fileDir.exists())
				fileDir.mkdirs();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStream out = new BufferedOutputStream(fileOutputStream);
			out.write(srcCode.getBytes("UTF-8"));
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void createXsSchema(Class<?> javaClass, String filePath) throws ParseException {
		if (xmlParserManager == null)
			getXmlParserManager();
		XmlClassBinding xmlClassBinding = ((XmlParserManagerImp) xmlParserManager).getClassBinding(javaClass.getName());
		XsSchema xsSchema = new XsSchema("");
		xmlClassBinding.addXsd(xsSchema, null, null, (XmlParserManagerImp) xmlParserManager);
		xsSchema.addElement(new XsElement(xmlClassBinding.getNode(), javaClass.getName()));
		StringWriter buffer = new StringWriter();
		xsSchema.addToXsd(buffer);
		File file = new File(filePath);
		genrataFile(file, buffer.toString());
	}

}
