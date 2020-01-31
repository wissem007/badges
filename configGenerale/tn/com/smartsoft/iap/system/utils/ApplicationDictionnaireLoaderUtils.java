package tn.com.smartsoft.iap.system.utils;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader;
import tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary;

public class ApplicationDictionnaireLoaderUtils {

	private ApplicationCacheDictionary getApplicationDictionnaire() {
		return (ApplicationCacheDictionary) ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getApplicationDictionnaire();
	}

	public void loadApplicationCacheDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadApplicationCacheDictionary(getApplicationDictionnaire());
	}

	public void loadMessageDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadMessageDictionary(getApplicationDictionnaire());
	}

	public void loadActionTemplateDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadActionTemplateDictionary(getApplicationDictionnaire());
	}

	public void loadRoleDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadRoleDictionary(getApplicationDictionnaire());
	}

	public void loadToolActionDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadToolActionDictionary(getApplicationDictionnaire());
	}

	public void laodDeviseDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.laodDeviseDictionary(getApplicationDictionnaire());
	}

	public void loadUserTypeDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadUserTypeDictionary(getApplicationDictionnaire());
	}

	public void loadLibelleDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadLibelleDictionary(getApplicationDictionnaire());
	}

	public void loadActionDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadActionDictionary(getApplicationDictionnaire());
	}

	public void loadEntiteDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadEntiteDictionary(getApplicationDictionnaire());
	}

	public void loadSubModuleDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadSubModuleDictionary(getApplicationDictionnaire());
	}

	public void loadModuleDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadModuleDictionary(getApplicationDictionnaire());
	}

	public void loadApplicationDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadApplicationDictionary(getApplicationDictionnaire());
	}

	public void loadViewDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadViewDictionary(getApplicationDictionnaire());
	}

	public void loadMenuDictionary(IApplicationDictionnaireLoader applicationDictionnaireLoader) throws DaoFunctionalException {
		applicationDictionnaireLoader.loadMenuDictionary(getApplicationDictionnaire());
	}

}
