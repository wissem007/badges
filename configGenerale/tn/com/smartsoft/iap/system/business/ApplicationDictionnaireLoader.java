package tn.com.smartsoft.iap.system.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.log.TimerNote;
import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.framework.beans.IdentifiableBean;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.presentation.view.comman.MenuModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import tn.com.smartsoft.iap.dictionary.graphique.menu.beans.MenuBean;
import tn.com.smartsoft.iap.dictionary.graphique.menu.beans.MenuBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.menuAction.beans.MenuActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.menuItem.beans.MenuItemBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewLibelleBean;
import tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary;
import tn.com.smartsoft.iap.system.dao.ApplicationDictionnaireDao;

public class ApplicationDictionnaireLoader implements Serializable,IApplicationDictionnaireLoader{

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	protected DbSession						daoSession;
	protected ApplicationDictionnaireDao	applicationDictionnaireDao;
	protected DaoParseBean					entiteParser;
	protected DaoParseBean					menuParser;
	protected DaoParseBean					actionToolbarParser;
	protected DaoParseBean					viewParser;

	public void setViewParser(DaoParseBean viewParser) {
		this.viewParser = viewParser;
	}
	public void setActionToolbarParser(DaoParseBean actionToolbarParser) {
		this.actionToolbarParser = actionToolbarParser;
	}
	public void setEntiteParser(DaoParseBean entiteParser) {
		this.entiteParser = entiteParser;
	}
	public void setMenuParser(DaoParseBean menuParser) {
		this.menuParser = menuParser;
	}
	public void setDaoSession(DbSession daoSession) {
		this.daoSession = daoSession;
	}
	public void setApplicationDictionnaireDao(ApplicationDictionnaireDao applicationDictionnaireDao) {
		this.applicationDictionnaireDao = applicationDictionnaireDao;
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadApplicationCacheDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadApplicationCacheDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		TimerNote timerNote = TimerNote.createTimerNote();
		timerNote.startTime();
		loadApplicationDictionary(applicationDictionary);
		loadModuleDictionary(applicationDictionary);
		loadSubModuleDictionary(applicationDictionary);
		loadEntiteDictionary(applicationDictionary);
		loadActionDictionary(applicationDictionary);
		loadLibelleDictionary(applicationDictionary);
		loadUserTypeDictionary(applicationDictionary);
		laodDeviseDictionary(applicationDictionary);
		loadToolActionDictionary(applicationDictionary);
		loadRoleDictionary(applicationDictionary);
		loadActionTemplateDictionary(applicationDictionary);
		loadMessageDictionary(applicationDictionary);
		loadMenuDictionary(applicationDictionary);
		loadViewDictionary(applicationDictionary);
		timerNote.endTime();
		timerNote.dispalyExecuteTime();
		daoSession.close();
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadMessageDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadMessageDictionsary() throws DaoFunctionalException {
		
		
	}
	public void loadMessageDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getMessageDictionaryCache(), applicationDictionnaireDao.getMessageDictionary(daoSession), actionToolbarParser);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadActionTemplateDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadActionTemplateDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getActionTemplateDictionary(), applicationDictionnaireDao.getActionTemplateDictionary(daoSession), actionToolbarParser);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadRoleDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadRoleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getRoleDictionary(), applicationDictionnaireDao.getRoleDictionary(daoSession), actionToolbarParser);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadToolActionDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadToolActionDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getToolActionDictionary(), applicationDictionnaireDao.getToolActionDictionary(daoSession), actionToolbarParser);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# laodDeviseDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void laodDeviseDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getDeviseDictionaryCache(), applicationDictionnaireDao.getDeviseDictionary(daoSession), null);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadUserTypeDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadUserTypeDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getUserTypeDictionary(), applicationDictionnaireDao.getUserTypeDictionary(daoSession), null);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadLibelleDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadLibelleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getLibelleDictionaryCache(), applicationDictionnaireDao.getLibelleDictionary(daoSession), null);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadActionDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadActionDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getActionDictionaryCache(), applicationDictionnaireDao.getActionDictionary(daoSession), null);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadEntiteDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadEntiteDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadEntite(applicationDictionary.getEntiteDictionaryCache(), applicationDictionary.getEntiteDictionaryCacheByClass());
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadSubModuleDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadSubModuleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getSubModuleDictionaryCache(), applicationDictionnaireDao.getSubModuleDictionary(daoSession), null);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadModuleDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadModuleDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		loadObjectCache(applicationDictionary.getModuleDictionaryCache(), applicationDictionnaireDao.getModuleDictionary(daoSession), null);
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadApplicationDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	public void loadApplicationDictionary(ApplicationCacheDictionary applicationDictionary) {
		applicationDictionary.setApplicationDictionary(applicationDictionnaireDao.getApplicationDictionary(daoSession));
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadViewDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	@SuppressWarnings("rawtypes")
	public void loadViewDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		List list = applicationDictionnaireDao.getViewDictionary(daoSession);
		for (int i = 0; i < list.size(); i++) {
			ViewBean value = (ViewBean) viewParser.parse(list.get(i));
			applicationDictionary.getViewDictionaryCache().put(value);
		}
		list = applicationDictionnaireDao.getViewLibelleDictionary(daoSession);
		for (int i = 0; i < list.size(); i++) {
			ViewLibelleBean viewLibelle = (ViewLibelleBean) list.get(i);
			ViewBeanId viewBeanId = new ViewBeanId();
			viewBeanId.setViewId(viewLibelle.getViewId());
			viewBeanId.setSujetId(viewLibelle.getSujetId());
			viewBeanId.setSubModuleId(viewLibelle.getSubModuleId());
			viewBeanId.setModuleId(viewLibelle.getModuleId());
			ViewBean value = (ViewBean) applicationDictionary.getViewDictionaryCache().get(viewBeanId);
			if (value.getLibelles() == null) value.setLibelles(new HashMap<String, ViewLibelleBean>());
			value.getLibelles().put(viewLibelle.getViewLibellesId(), viewLibelle);
		}
		list = applicationDictionnaireDao.getViewActionDictionary(daoSession);
		for (int i = 0; i < list.size(); i++) {
			ViewActionBean viewActionBean = (ViewActionBean) list.get(i);
			ViewBeanId viewBeanId = new ViewBeanId();
			viewBeanId.setViewId(viewActionBean.getViewId());
			viewBeanId.setSujetId(viewActionBean.getSujetId());
			viewBeanId.setSubModuleId(viewActionBean.getSubModuleId());
			viewBeanId.setModuleId(viewActionBean.getModuleId());
			ViewBean value = (ViewBean) applicationDictionary.getViewDictionaryCache().get(viewBeanId);
			if (value.getActions() == null) value.setActions(new HashMap<String, ViewActionBean>());
			value.getActions().put(viewActionBean.getViewActionId(), viewActionBean);
		}
	}
	@SuppressWarnings("rawtypes")
	protected void loadEntite(ICache entiteCache, ICache entiteDictionaryCacheByClass) throws DaoFunctionalException {
		List list = applicationDictionnaireDao.getEntiteDictionary(daoSession);
		List listProperty = applicationDictionnaireDao.getPropertyDictionary(daoSession);
		for (int i = 0; i < list.size(); i++) {
			EntiteBean value = (EntiteBean) entiteParser.parse(list.get(i));
			entiteCache.put(value);
			entiteDictionaryCacheByClass.put(value.getJavaClass(), value);
		}
		for (int i = 0; i < listProperty.size(); i++) {
			PropertyBean property = (PropertyBean) listProperty.get(i);
			if (property.getSequence() != null) {
				property.getSequence().getSequenceId();
				property.getSequence().getLengthNumber();
				property.getSequence().getValueGroupExpression();
			}
			EntiteBean value = (EntiteBean) entiteCache.get(new EntiteBeanId(property.getEntiteId(), property.getSujetId(), property.getSubModuleId(), property.getModuleId()));
			if (value.getPropertys() == null) value.setPropertys(new HashMap<String, PropertyBean>());
			value.getPropertys().put(property.getPropertyName(), property);
		}
	}
	/* (non-Javadoc)
	 * @see tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader# loadMenuDictionary (tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary) */
	@SuppressWarnings("rawtypes")
	public void loadMenuDictionary(ApplicationCacheDictionary applicationDictionary) throws DaoFunctionalException {
		List list = applicationDictionnaireDao.getListMenus(daoSession);
		Map<?, ?> menus = menuParser.parseMap(list);
		Map<String, MenuModuleBean> menuModuleMap = new HashMap<String, MenuModuleBean>();
		for (Iterator<?> iterator = menus.keySet().iterator(); iterator.hasNext();) {
			MenuBean value = (MenuBean) menus.get(iterator.next());
			if (value instanceof MenuItemBean) {
				MenuItemBean menuItemBean = (MenuItemBean) value;
				if (menuItemBean.getParentMenuId() == null) {
					MenuModuleBean menuModule = menuModuleMap.get(menuItemBean.getModuleId());
					if (menuModule == null) {
						menuModule = new MenuModuleBean(value.getModuleId());
						menuModuleMap.put(value.getModuleId(), menuModule);
					}
					menuModule.getMenus().add(menuItemBean);
				} else {
					MenuItemBean parentMenuItemBean = (MenuItemBean) menus.get(new MenuBeanId(menuItemBean.getParentMenuId(), menuItemBean.getModuleId()));
					if (parentMenuItemBean.getMenus() == null) parentMenuItemBean.setMenus(new ArrayList<MenuItemBean>());
					parentMenuItemBean.getMenus().add(menuItemBean);
				}
			} else if (value instanceof MenuActionBean) {
				MenuActionBean menuActionBean = (MenuActionBean) value;
				if (menuActionBean.getParentMenuId() != null) {
					MenuItemBean parentMenuItemBean = (MenuItemBean) menus.get(new MenuBeanId(menuActionBean.getParentMenuId(), menuActionBean.getModuleId()));
					if (parentMenuItemBean.getMenuActions() == null) parentMenuItemBean.setMenuActions(new ArrayList<MenuActionBean>());
					parentMenuItemBean.getMenuActions().add(menuActionBean);
				}
			}
		}
		Iterator<String> iterator = menuModuleMap.keySet().iterator();
		while (iterator.hasNext()) {
			MenuModuleBean menuModule = menuModuleMap.get(iterator.next());
			applicationDictionary.getMenuDictionaryCache().put(menuModule);
		}
	}
	protected void loadObjectCache(ICache cache, List<?> list, DaoParseBean parserObject) throws DaoFunctionalException {
		for (int i = 0; i < list.size(); i++) {
			IdentifiableBean value = (IdentifiableBean) list.get(i);
			if (parserObject != null) value = (IdentifiableBean) parserObject.parse(value);
			cache.put(value);
		}
	}
}
