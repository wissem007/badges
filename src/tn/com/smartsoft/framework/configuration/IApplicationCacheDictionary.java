package tn.com.smartsoft.framework.configuration;

import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.iap.dictionary.decoupage.application.beans.ApplicationBean;

public interface IApplicationCacheDictionary {
	public static final String MESSAGE_DICTIONARY_CACHE = "messageDictionaryCache";
	public static final String ACTION_TEMPLATE_DICTIONARY = "actionTemplateDictionary";
	public static final String MENU_DICTIONARY_CACHE = "menuDictionaryCache";
	public static final String LIBELLE_DICTIONARY_CACHE = "libelleDictionaryCache";
	public static final String USER_TYPE_DICTIONARY = "userTypeDictionary";
	public static final String ACTION_DICTIONARY_CACHE = "actionDictionaryCache";
	public static final String SUB_MODULE_DICTIONARY_CACHE = "subModuleDictionaryCache";
	public static final String MODULE_DICTIONARY_CACHE = "moduleDictionaryCache";
	public static final String DEVISE_DICTIONARY_CACHE = "deviseDictionaryCache";
	public static final String ENTITE_DICTIONARY_CACHE = "entiteDictionaryCache";
	public static final String TOOL_ACTION_DICTIONARY = "toolActionDictionary";
	public static final String VIEW_DICTIONARY_CACHE = "viewDictionaryCache";
	public static final String ENTITE_DICTIONARY_CACHE_BY_CLASS = "entiteDictionaryCacheByClass";
	public static final String ROLE_DICTIONARY = "roleDictionary";
	public static final String[] CACHE_NAMES = new String[] { MESSAGE_DICTIONARY_CACHE, ACTION_TEMPLATE_DICTIONARY, MENU_DICTIONARY_CACHE, LIBELLE_DICTIONARY_CACHE,
			USER_TYPE_DICTIONARY, ACTION_DICTIONARY_CACHE, SUB_MODULE_DICTIONARY_CACHE, MODULE_DICTIONARY_CACHE, DEVISE_DICTIONARY_CACHE, ENTITE_DICTIONARY_CACHE,
			TOOL_ACTION_DICTIONARY, VIEW_DICTIONARY_CACHE, ENTITE_DICTIONARY_CACHE_BY_CLASS, ROLE_DICTIONARY };

	public abstract ICache getRoleDictionary();

	public abstract ICache getViewDictionaryCache();

	public abstract ICache getToolActionDictionary();

	public abstract ICache getEntiteDictionaryCache();

	public abstract ICache getDeviseDictionaryCache();

	public abstract ICache getModuleDictionaryCache();

	public abstract ICache getSubModuleDictionaryCache();

	public abstract ApplicationBean getApplicationDictionary();

	public abstract ICache getActionDictionaryCache();

	public abstract ICache getUserTypeDictionary();

	public abstract ICache getLibelleDictionaryCache();

	public abstract ICache getMenuDictionaryCache();

	public abstract ICache getActionTemplateDictionary();

	public ICache getEntiteDictionaryCacheByClass();

	public ICache getMessageDictionaryCache();

}