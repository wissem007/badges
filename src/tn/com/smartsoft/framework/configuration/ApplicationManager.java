package tn.com.smartsoft.framework.configuration;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.xml.XmlParserManager;
import tn.com.smartsoft.commons.xml.exception.ParseException;
import tn.com.smartsoft.framework.beans.factory.BeanFactory;
import tn.com.smartsoft.framework.business.factory.BusinessBeanFactory;
import tn.com.smartsoft.framework.configuration.definition.ApplicationDefinition;
import tn.com.smartsoft.framework.dao.factory.DaoFactory;
import tn.com.smartsoft.framework.presentation.factory.PresentationBeanFactory;
import tn.com.smartsoft.framework.presentation.formater.FormaterManger;
import tn.com.smartsoft.framework.security.PermissionFactory;

public interface ApplicationManager {

	public ApplicationCacheDictionaryManager applicationCacheDictionaryManager();

	public XmlParserManager xmlParserManager();

	public BeanFactory beanFactory();

	public DaoFactory daoFactory();

	public BusinessBeanFactory businessBeanFactory();

	public PresentationBeanFactory presentationBeanFactory();

	public ApplicationDefinition applicationDefinition();

	public Object createConfigBean(String beanId) throws DaoFunctionalException;

	public Object createWebConfigBean(String beanId) throws DaoFunctionalException;

	public FormaterManger formaterManger();

	public PermissionFactory permissionFactory();

	public void createXsSchema(Class<?> clazz, String file) throws ParseException;

}
