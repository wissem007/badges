package tn.com.smartsoft.iap.system.cache;

import java.io.Serializable;

import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.commons.utils.cache.ICacheManager;
import tn.com.smartsoft.framework.configuration.IApplicationCacheDictionary;
import tn.com.smartsoft.iap.dictionary.decoupage.application.beans.ApplicationBean;

public class ApplicationCacheDictionary implements Serializable, IApplicationCacheDictionary {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ICacheManager manager;
	private ApplicationBean applicationDictionary;

	public ApplicationCacheDictionary(ICacheManager manager) {
		super();
		this.manager = manager;
	}

	public void setApplicationDictionary(ApplicationBean applicationDictionary) {
		this.applicationDictionary = applicationDictionary;
	}

	public ICache getRoleDictionary() {
		return manager.getCache(ROLE_DICTIONARY);
	}

	public ICache getEntiteDictionaryCacheByClass() {
		return manager.getCache(ENTITE_DICTIONARY_CACHE_BY_CLASS);
	}

	public ICache getViewDictionaryCache() {
		return manager.getCache(VIEW_DICTIONARY_CACHE);
	}

	public ICache getToolActionDictionary() {
		return manager.getCache(TOOL_ACTION_DICTIONARY);
	}

	public ICache getEntiteDictionaryCache() {
		return manager.getCache(ENTITE_DICTIONARY_CACHE);
	}

	public ICache getDeviseDictionaryCache() {
		return manager.getCache(DEVISE_DICTIONARY_CACHE);
	}

	public ICache getModuleDictionaryCache() {
		return manager.getCache(MODULE_DICTIONARY_CACHE);
	}

	public ICache getSubModuleDictionaryCache() {
		return manager.getCache(SUB_MODULE_DICTIONARY_CACHE);
	}

	public ApplicationBean getApplicationDictionary() {
		return applicationDictionary;
	}

	public ICache getActionDictionaryCache() {
		return manager.getCache(ACTION_DICTIONARY_CACHE);
	}

	public ICache getUserTypeDictionary() {
		return manager.getCache(USER_TYPE_DICTIONARY);
	}

	public ICache getLibelleDictionaryCache() {
		return manager.getCache(LIBELLE_DICTIONARY_CACHE);
	}

	public ICache getMenuDictionaryCache() {
		return manager.getCache(MENU_DICTIONARY_CACHE);
	}

	public ICache getActionTemplateDictionary() {
		return manager.getCache(ACTION_TEMPLATE_DICTIONARY);
	}

	public ICache getMessageDictionaryCache() {
		return manager.getCache(MESSAGE_DICTIONARY_CACHE);
	}
}
