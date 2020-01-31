package tn.com.smartsoft.iap.system.business;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary;

public interface IApplicationDictionnaireLoader {

	public abstract void loadApplicationCacheDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadMessageDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadActionTemplateDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadRoleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadToolActionDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void laodDeviseDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadUserTypeDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadLibelleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadActionDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadEntiteDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadSubModuleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadModuleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadApplicationDictionary(ApplicationCacheDictionary applicationDictionary);

	public abstract void loadViewDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

	public abstract void loadMenuDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException;

}