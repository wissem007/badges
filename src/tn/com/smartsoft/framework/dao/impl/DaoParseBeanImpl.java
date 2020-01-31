package tn.com.smartsoft.framework.dao.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ClassUtils;
import org.hibernate.proxy.HibernateProxyHelper;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.definition.DaoParseBeanDefinition;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DaoParseBeanImpl implements DaoParseBean {
	private DaoParseBeanDefinition daoParseBeanDefinition;
	
	public DaoParseBeanImpl(DaoParseBeanDefinition daoParseBeanDefinition) {
		super();
		this.daoParseBeanDefinition = daoParseBeanDefinition;
	}
	
	public Object parse(Object entity) throws DaoFunctionalException {
		return parse(entity, daoParseBeanDefinition);
	}
	

	public Map parseMap(List entitys) throws DaoFunctionalException {
		return parseMap(entitys, "id");
	}
	
	public Map parseMap(List entitys, String keyPropertyName) throws DaoFunctionalException {
		Map newValue = new HashMap();
		for (int i = 0; i < entitys.size(); i++) {
			Object bean = parse(entitys.get(i));
			newValue.put(getValue(bean, keyPropertyName), bean);
		}
		return newValue;
	}
	
	public List parse(List entitys) throws DaoFunctionalException {
		List newValue = new ArrayList();
		for (int i = 0; i < entitys.size(); i++) {
			newValue.add(parse(entitys.get(i)));
		}
		return newValue;
	}
	
	private Object parse(Object entity, DaoParseBeanDefinition daoParseBeanDefinition) throws DaoFunctionalException {
		if (entity == null)
			return null;
		Class beanClass = HibernateProxyHelper.getClassWithoutInitializingProxy(entity);
		if (daoParseBeanDefinition.isRecursive()) {
			String property = daoParseBeanDefinition.getProperty();
			daoParseBeanDefinition = daoParseBeanDefinition.getParent().getCopy();
			daoParseBeanDefinition.setProperty(property);
		}
		Object newBean = getInstance(beanClass);
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			String propertyName = propertyDescriptors[i].getName();
			if (PropertyUtils.isReadable(newBean, propertyName) && PropertyUtils.isWriteable(newBean, propertyName)) {
				Class type = propertyDescriptors[i].getPropertyType();
				DaoParseBeanDefinition childDaoParseBean = daoParseBeanDefinition.getPropertyBean(propertyName);
				Object value = null;
				if (childDaoParseBean != null) {
					value = getValue(entity, propertyName);
					if (value instanceof Map) {
						value = getValueMap(value, childDaoParseBean);
					} else if (value instanceof List) {
						value = getValueList(value, childDaoParseBean);
					} else {
						value = parse(value, childDaoParseBean);
					}
				} else if (isSimpleProperty(type)) {
					value = getValue(entity, propertyName);
				}
				
				setValue(newBean, propertyName, value);
			}
		}
		return newBean;
	}
	
	private boolean isSimpleProperty(Class<?> type) {
		return !ClassUtils.isAssignable(type, Map.class) && !ClassUtils.isAssignable(type, List.class);
	}
	
	private void setValue(Object newBean, String propertyName, Object value) {
		try {
			PropertyUtils.setProperty(newBean, propertyName, value);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	
	private Object getInstance(Class beanClass) {
		try {
			return beanClass.newInstance();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	
	private Object getValueList(Object value, DaoParseBeanDefinition childDaoParseBean) throws DaoFunctionalException {
		List listValue = (List) value;
		value = new ArrayList();
		for (int j = 0; j < listValue.size(); j++) {
			Object itemValue = listValue.get(j);
			itemValue = parse(itemValue, childDaoParseBean);
			((List) value).add(itemValue);
		}
		return value;
	}
	
	private Object getValueMap(Object value, DaoParseBeanDefinition childDaoParseBean) throws DaoFunctionalException {
		Map mapValue = (Map) value;
		value = new HashMap();
		for (Iterator iterator = mapValue.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			Object itemValue = mapValue.get(key);
			if (itemValue != null && !isSimpleProperty(itemValue.getClass()))
				itemValue = parse(itemValue, childDaoParseBean);
			if (itemValue != null)
				((Map) value).put(key, itemValue);
		}
		return value;
	}
	
	private Object getValue(Object entity, String propertyName) {
		try {
			return PropertyUtils.getProperty(entity, propertyName);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}