package tn.com.smartsoft.framework.configuration;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.xml.XmlParserManagerFactory;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.configuration.definition.BeansDefinition;
import tn.com.smartsoft.framework.configuration.definition.ModuleDefinition;
import tn.com.smartsoft.framework.configuration.impl.DefaultApplicationManager;
import tn.com.smartsoft.framework.dao.definition.DataBaseDefinition;
import tn.com.smartsoft.framework.presentation.definition.GroupScriptsDefinition;
import tn.com.smartsoft.framework.presentation.definition.ImagesDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.UITags;

public class ApplicationConfiguration {
	static ApplicationManager applicationManager;
	
	static Logger logger = Logger.getLogger(ApplicationConfiguration.class);
	static final String FILE_APPLICATION_CONFIG = "tn/com/smartsoft/ressources/setting.xml";
	static Boolean isStart = Boolean.FALSE;
	static Exception exceptionStartUp;
	
	public static void startUp() {
		try {
			synchronized (isStart) {
				if (!isStart.booleanValue()) {
					logger.info("initialized------> start");
					applicationManager = new DefaultApplicationManager(FILE_APPLICATION_CONFIG);
					isStart = Boolean.TRUE;
					logger.info("initialized------> end");
				}
			}
		} catch (Exception e) {
			isStart = Boolean.FALSE;
			if (e instanceof FunctionalException) {
				if (e.getCause() != null && e.getCause() instanceof org.hibernate.exception.GenericJDBCException)
					exceptionStartUp = new Exception(((org.hibernate.exception.GenericJDBCException) e.getCause()).getSQLException());
			} else
				exceptionStartUp = e;
		}
		logger.info("Total Memory  :" + getTotalMemory());
		logger.info("Free Memory  :" + getFreeMemory());
		System.gc();
		logger.info("Total Memory  :" + getTotalMemory());
		logger.info("Free Memory  :" + getFreeMemory());
	}
	
	public static void main(String[] args) throws ParseException {
		System.setProperty("catalina.base", "C:");
		String xsdPath = "C:/logs/xsd/";
		XmlParserManagerFactory.createXsSchema(BeansDefinition.class, xsdPath + "beans.xsd");
		XmlParserManagerFactory.createXsSchema(ModuleDefinition.class, xsdPath + "modules.xsd");
		XmlParserManagerFactory.createXsSchema(DataBaseDefinition.class, xsdPath + "dataBase.xsd");
		XmlParserManagerFactory.createXsSchema(GroupScriptsDefinition.class, xsdPath + "scripts.xsd");
		XmlParserManagerFactory.createXsSchema(ApplicationDefinition.class, xsdPath + "setting.xsd");
		XmlParserManagerFactory.createXsSchema(ImagesDefinition.class, xsdPath + "webImages.xsd");
		XmlParserManagerFactory.createXsSchema(WebDefinition.class, xsdPath + "webSetting.xsd");
		XmlParserManagerFactory.createXsSchema(UITags.class, xsdPath + "webTags.xsd");
		
	}
	
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory() / (1024 * 1024);
	}
	
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory() / (1024 * 1024);
	}
	
	public static double getPercentOfUsedMemory() {
		long total = getTotalMemory();
		long used = total - getFreeMemory();
		return total > 0 ? ((double) used) / total : 1d;
	}
	
	public static String getUsageString() {
		long total = getTotalMemory();
		long used = total - getFreeMemory();
		return used + "M of " + total + "M";
	}
	
	public static Exception getExceptionStartUp() {
		return exceptionStartUp;
	}
	
	public static boolean isStartUp() {
		return isStart.booleanValue();
	}
	
	public static ApplicationManager applicationManager() {
		return applicationManager;
	}
	
}
