package tn.com.smartsoft.framework.configuration.impl;

import java.io.Serializable;
import java.util.Iterator;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.CacheFactory;
import tn.com.smartsoft.commons.xml.XmlParserManager;
import tn.com.smartsoft.commons.xml.XmlParserManagerFactory;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.beans.definition.BeansDefinition;
import tn.com.smartsoft.framework.beans.factory.BeanFactory;
import tn.com.smartsoft.framework.beans.factory.BeanFactoryImpl;
import tn.com.smartsoft.framework.business.factory.BusinessBeanFactory;
import tn.com.smartsoft.framework.business.factory.BusinessBeanFactoryImpl;
import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.IApplicationCacheDictionary;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;
import tn.com.smartsoft.framework.dao.factory.DaoFactory;
import tn.com.smartsoft.framework.dao.factory.DaoFactoryImpl;
import tn.com.smartsoft.framework.dao.utils.DbSessionUtils;
import tn.com.smartsoft.framework.presentation.factory.DefaultPresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.formater.FormaterManger;
import tn.com.smartsoft.framework.security.PermissionFactory;
import tn.com.smartsoft.iap.system.business.IApplicationDictionnaireLoader;
import tn.com.smartsoft.iap.system.cache.ApplicationCacheDictionary;

public class DefaultApplicationManager implements ApplicationManager,Serializable{
	
	public static final String			SETTING_XML					= "tn/com/smartsoft/ressources/setting.xml";
	/**
	 * 
	 */
	private static final long			serialVersionUID			= 1L;
	private static final String			SYS_MODULE					= "system";
	private static final int			CACHE_TYPE					= CacheFactory.OBJECT_CACHE_TYPE;
	private static final Object			CACHE_PARAMS				= IApplicationCacheDictionary.CACHE_NAMES;
	private static final String			SYS_LOADER_DICTIONNAIRE		= "applicationDictionnaireLoader";
	private static final ComponentId	SYS_LOADER_DICTIONNAIRE_ID	= new ComponentIdImpl(SYS_MODULE, SYS_MODULE, SYS_MODULE, SYS_LOADER_DICTIONNAIRE);
	private ApplicationDefinition		applicationDefinition;
	private IApplicationCacheDictionary	applicationDictionary;
	private XmlParserManager			xmlParserManager;
	private BeanFactory					beanProxy;
	private DaoFactory					daoProxy;
	private BusinessBeanFactory			businessBeanProxy;
	private PresentationBeanFactory		guiBeanProxy;
	private BeanFactory					applicationBeanProxy;
	private BeanFactory					webBeanProxy;
	private FormaterManger				formaterManger;
	
	public DefaultApplicationManager(String file) throws FunctionalException {
		super();
		this.xmlParserManager = XmlParserManagerFactory.getXmlParserManager();
		this.applicationDefinition = (ApplicationDefinition) xmlParserManager.getUniqueResultat(SETTING_XML, ApplicationDefinition.class);
		this.applicationDictionary = new ApplicationCacheDictionary(CacheFactory.createCache(CACHE_TYPE, CACHE_PARAMS));
		initProxy();
		loadApplicationDictionnaire();
	}
	private void loadApplicationDictionnaire() throws FunctionalException {
		IApplicationDictionnaireLoader applicationDictionnaireLoader = (IApplicationDictionnaireLoader) businessBeanProxy.createBusinessBean(SYS_LOADER_DICTIONNAIRE_ID);
		applicationDictionnaireLoader.loadApplicationCacheDictionary((ApplicationCacheDictionary) applicationDictionary);
		DbSessionUtils.commitTransactionAndCloseAll();
	}
	protected void initProxy() {
		try {
			Iterator<?> it = applicationDefinition.getDataBaseDefinition().getDataSourceDefinitions().keySet().iterator();
			while (it.hasNext()) {
				DataSourceDefinition hibernateConfiguration = applicationDefinition.getDataBaseDefinition().getDataSourceDefinition((String) it.next());
				hibernateConfiguration.buildSessionFactory();
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		this.beanProxy = new BeanFactoryImpl(new DefaultBeansDefinition(applicationDefinition));
		this.daoProxy = new DaoFactoryImpl(new DefaultDbDefinition(this));
		this.businessBeanProxy = new BusinessBeanFactoryImpl(new DefaultBusinessBeansDefinition(applicationDefinition), this.beanProxy, this.daoProxy);
		this.guiBeanProxy = new DefaultPresentationBeanFactory(new DefaultPresantationDefinition(applicationDefinition, applicationCacheDictionaryManager()), this.beanProxy,
				this.businessBeanProxy);
		applicationBeanProxy = new BeanFactoryImpl(new BeansDefinition(){
			
			public BeanDefinition getBeanDefinition(ComponentId beanId) {
				BeanDefinition beanDefinition = applicationDefinition.getBeanDefinition(beanId.getId());
				if (beanDefinition == null) throw new TechnicalException("no Bean whith  " + beanId);
				return beanDefinition;
			}
		});
		webBeanProxy = new BeanFactoryImpl(new BeansDefinition(){
			
			public BeanDefinition getBeanDefinition(ComponentId beanId) {
				BeanDefinition beanDefinition = applicationDefinition.getWebDefinition().getBeanDefinition(beanId.getId());
				if (beanDefinition == null) throw new TechnicalException("no Bean whith  " + beanId);
				return beanDefinition;
			}
		});
		try {
			formaterManger = (FormaterManger) webBeanProxy.createBean(new ComponentIdImpl(null, null, null, "formaterManger"));
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public void createXsSchema(Class<?> clazz, String file) throws ParseException {
		XmlParserManagerFactory.createXsSchema(clazz, file);
	}
	public DbSession getAdminDataSource() throws DaoFunctionalException {
		return this.daoProxy.createDbSession(applicationDefinition.getAdminDataSource());
	}
	public XmlParserManager xmlParserManager() {
		return xmlParserManager;
	}
	public BeanFactory beanFactory() {
		return beanProxy;
	}
	public DaoFactory daoFactory() {
		return daoProxy;
	}
	public BusinessBeanFactory businessBeanFactory() {
		return businessBeanProxy;
	}
	public PresentationBeanFactory presentationBeanFactory() {
		return guiBeanProxy;
	}
	public ApplicationDefinition applicationDefinition() {
		return applicationDefinition;
	}
	public ApplicationCacheDictionaryManager applicationCacheDictionaryManager() {
		return new ApplicationCacheDictionaryManager(applicationDictionary);
	}
	public Object createConfigBean(String beanId) throws DaoFunctionalException {
		return applicationBeanProxy.createBean(new ComponentIdImpl(null, null, null, beanId));
	}
	public Object createWebConfigBean(String beanId) throws DaoFunctionalException {
		return webBeanProxy.createBean(new ComponentIdImpl(null, null, null, beanId));
	}
	public FormaterManger formaterManger() {
		return formaterManger;
	}
	public PermissionFactory permissionFactory() {
		return new PermissionFactory();
	}
}
