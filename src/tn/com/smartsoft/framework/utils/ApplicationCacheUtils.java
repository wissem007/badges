package tn.com.smartsoft.framework.utils;

import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;

public class ApplicationCacheUtils {
	
	public static WebDefinition webDefinition() {
		return ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition();
	}
	
	public static ApplicationCacheDictionaryManager dictionaryManager() {
		return ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager();
	}
	
	public static ApplicationDefinition applicationDefinition() {
		return ApplicationConfiguration.applicationManager().applicationDefinition();
	}
	
	public static PresentationBeanFactory presentationBeanFactory() {
		return ApplicationConfiguration.applicationManager().presentationBeanFactory();
	}
	
}
