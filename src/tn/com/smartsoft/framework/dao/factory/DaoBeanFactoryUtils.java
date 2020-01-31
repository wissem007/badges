package tn.com.smartsoft.framework.dao.factory;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.aop.GenericPointcut;
import tn.com.smartsoft.commons.aop.InvocationAspect;
import tn.com.smartsoft.commons.aop.SimpleMethodMatcher;
import tn.com.smartsoft.commons.aop.proxy.ProxyFactory;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.definition.DaoBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoQueryMethodDefinition;
import tn.com.smartsoft.framework.dao.definition.DbDefinition;

public class DaoBeanFactoryUtils {
	
	public static Object createDaoBean(DbDefinition dbDefinition, DaoFactory daoFactory, ComponentId daoBeanId) throws DaoFunctionalException {
		ProxyFactory proxyFactory = new ProxyFactory();
		DaoBeanDefinition daoBeanDefinition = dbDefinition.getDaoBeanDefinition(daoBeanId);
		Map<String, DaoQueryMethodDefinition> methodDefinitions = daoBeanDefinition.getMethodDefinitions();
		for (Iterator<String> iterator = methodDefinitions.keySet().iterator(); iterator.hasNext();) {
			String methodName = (String) iterator.next();
			DaoQueryMethodDefinition methodDefinition = (DaoQueryMethodDefinition) methodDefinitions.get(methodName);
			DaoQueryMethodInvoker daoMethodInvoker;
			DaoParseBean daoParseBean = null;
			String daoParseBeanRef = ((DaoQueryMethodDefinition) methodDefinition).getDaoParseBeanRef();
			if (StringUtils.isNotBlank(daoParseBeanRef)) {
				daoParseBean = daoFactory.createDaoParseBean(new ComponentIdImpl(daoBeanId.getSujetId(), daoParseBeanRef));
			}
			daoMethodInvoker = new DaoQueryMethodInvoker(methodDefinition, daoParseBean);
			InvocationAspect aspecte = new InvocationAspect(daoMethodInvoker, new GenericPointcut(new SimpleMethodMatcher(new String[] { methodName })));
			proxyFactory.addInvocationAspect(aspecte);
		}
		proxyFactory.setInterfaces(new Class[] { daoBeanDefinition.getClassInterface() });
		return proxyFactory.getProxy();
	}
}
