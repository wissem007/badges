package tn.com.smartsoft.framework.business.factory;

import java.io.Serializable;
import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.factory.BeanFactory;
import tn.com.smartsoft.framework.beans.factory.BeanFactoryUtils;
import tn.com.smartsoft.framework.business.definition.BusinessBeanDefinition;
import tn.com.smartsoft.framework.business.definition.BusinessBeanRefDefinition;
import tn.com.smartsoft.framework.business.definition.BusinessBeansDefinition;
import tn.com.smartsoft.framework.business.definition.DaoBeanRefDefinition;
import tn.com.smartsoft.framework.business.definition.DaoParseBeanRefDefinition;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.factory.DaoFactory;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;

public class BusinessBeanFactoryImpl implements Serializable, BusinessBeanFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BusinessBeansDefinition businessBeansDefinition;
	private BeanFactory beanProxy;
	private DaoFactory daoProxy;

	public BusinessBeanFactoryImpl(BusinessBeansDefinition businessBeansDefinition, BeanFactory beanProxy, DaoFactory daoProxy) {
		super();
		this.businessBeansDefinition = businessBeansDefinition;
		this.beanProxy = beanProxy;
		this.daoProxy = daoProxy;
	}

	public Object createBusinessBean(ComponentId beanId) throws FunctionalException {
		BusinessBeanDefinition businessBeanDefinition = businessBeansDefinition.getBusinessBeanDefinition(beanId);
		Object bussinessBean = BeanFactoryUtils.createBean(beanProxy, beanId, businessBeanDefinition, new BusinessCreateInstanceFactory());
		List<DaoBeanRefDefinition> daoSessions = businessBeanDefinition.getRefDaoSessions();
		for (int i = 0; i < daoSessions.size(); i++) {
			DaoBeanRefDefinition sessionRef = (DaoBeanRefDefinition) daoSessions.get(i);
			DbSession daoSession = daoProxy.createDbSession(sessionRef.getRef());
			BeanFactoryUtils.setProperty(bussinessBean, sessionRef.getName(), daoSession);
		}
		List<DaoBeanRefDefinition> refDaoBeans = businessBeanDefinition.getRefDaoBeans();
		for (int i = 0; i < refDaoBeans.size(); i++) {
			DaoBeanRefDefinition refDaoBean = (DaoBeanRefDefinition) refDaoBeans.get(i);
			SujetBeanId sujetBeanId = new SujetBeanId(beanId.getSujetId(), refDaoBean.getModule(), refDaoBean.getSubModule(), refDaoBean.getSujet());
			Object daoBean = daoProxy.createDaoBean(new ComponentIdImpl(sujetBeanId, refDaoBean.getRef()));
			BeanFactoryUtils.setProperty(bussinessBean, refDaoBean.getName(), daoBean);
		}
		List<DaoParseBeanRefDefinition> refDaoParseBeanRefs = businessBeanDefinition.getRefDaoParseBeanRefs();
		for (int i = 0; i < refDaoParseBeanRefs.size(); i++) {
			DaoParseBeanRefDefinition daoParseBeanRef = (DaoParseBeanRefDefinition) refDaoParseBeanRefs.get(i);
			SujetBeanId sujetBeanId = new SujetBeanId(beanId.getSujetId(), daoParseBeanRef.getModule(), daoParseBeanRef.getSubModule(), daoParseBeanRef.getSujet());
			Object daoBean = daoProxy.createDaoParseBean(new ComponentIdImpl(sujetBeanId, daoParseBeanRef.getRef()));
			BeanFactoryUtils.setProperty(bussinessBean, daoParseBeanRef.getName(), daoBean);
		}
		List<BusinessBeanRefDefinition> refBusinessBeans = businessBeanDefinition.getRefBusinessBeans();
		for (int i = 0; i < refBusinessBeans.size(); i++) {
			BusinessBeanRefDefinition businessBeanRefDefinition = (BusinessBeanRefDefinition) refBusinessBeans.get(i);
			SujetBeanId sujetBeanId = new SujetBeanId(beanId.getSujetId(), businessBeanRefDefinition.getModule(), businessBeanRefDefinition.getSubModule(),
					businessBeanRefDefinition.getSujet());
			Object businessBean = this.createBusinessBean(new ComponentIdImpl(sujetBeanId, businessBeanRefDefinition.getRef()));
			BeanFactoryUtils.setProperty(bussinessBean, businessBeanRefDefinition.getName(), businessBean);
		}
		return bussinessBean;
	}
}
