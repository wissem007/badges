package tn.com.smartsoft.framework.beans.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.beans.definition.ComponentIdDefinition;
import tn.com.smartsoft.framework.beans.definition.GenericPropertyDefinition;
import tn.com.smartsoft.framework.beans.definition.ListEntryDefinition;
import tn.com.smartsoft.framework.beans.definition.ListPropertyDefinition;
import tn.com.smartsoft.framework.beans.definition.MapEntryDefinition;
import tn.com.smartsoft.framework.beans.definition.MapPropertyDefinition;
import tn.com.smartsoft.framework.beans.definition.PropertyDefinition;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;

public class BeanFactoryUtils {

	public static Object createBean(BeanFactory beanProxy, ComponentId beanId, BeanDefinition beanDefinition, CreateInstanceBeanFactory createInstanceFactory)
			throws DaoFunctionalException {
		Object bean;
		try {
			bean = beanDefinition.getClassValue().newInstance();
			List<GenericPropertyDefinition> propertyDefs = beanDefinition.getPropertyDefinitions();
			for (int i = 0; i < propertyDefs.size(); i++) {
				GenericPropertyDefinition propertyDef = (GenericPropertyDefinition) propertyDefs.get(i);
				if (propertyDef instanceof PropertyDefinition) {
					setFieldProperty(beanProxy, beanId, bean, (PropertyDefinition) propertyDef);
				} else if (propertyDef instanceof MapPropertyDefinition) {
					setPropertyMap(beanProxy, beanId, bean, (MapPropertyDefinition) propertyDef);
				} else if (propertyDef instanceof ListPropertyDefinition) {
					setPropertyList(beanProxy, beanId, bean, (ListPropertyDefinition) propertyDef);
				} else if (propertyDef instanceof ComponentIdDefinition) {
					setComponentId(beanProxy, beanId, bean, (ComponentIdDefinition) propertyDef);
				}
			}
			bean = createInstanceFactory.create(bean);
			return bean;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	private static void setComponentId(BeanFactory beanProxy, ComponentId beanId, Object bean, ComponentIdDefinition propertyDef) {
		setProperty(bean, propertyDef.getName(), new ComponentIdImpl(beanId, propertyDef.getId()));
	}

	public static void setFieldProperty(BeanFactory beanProxy, ComponentId beanId, Object bean, PropertyDefinition propertyDef) throws DaoFunctionalException {
		Object value = null;
		if (StringUtils.isNotBlank(propertyDef.getValue()))
			value = propertyDef.getValue();
		if (StringUtils.isNotBlank(propertyDef.getRef()))
			value = beanProxy.createBean(new ComponentIdImpl(beanId.getSujetId(), propertyDef.getRef()));
		setProperty(bean, propertyDef.getName(), value);

	}

	@SuppressWarnings("unchecked")
	public static void setPropertyMap(BeanFactory beanProxy, ComponentId beanId, Object bean, MapPropertyDefinition propertyDef) throws DaoFunctionalException {
		List<MapEntryDefinition> listEntry = propertyDef.getEntrys();
		Map<Object, Object> mapValue = new HashMap<Object, Object>();
		boolean add = false;
		Object map = getProperty(bean, propertyDef.getName());
		if (map != null && map instanceof Map) {
			mapValue = (Map) map;
			add = true;
		}
		String type = StringUtils.isBlank(propertyDef.getType()) ? String.class.getName() : propertyDef.getType();
		String keyType = StringUtils.isBlank(propertyDef.getKeyType()) ? String.class.getName() : propertyDef.getKeyType();
		for (int i = 0; i < listEntry.size(); i++) {
			MapEntryDefinition entry = (MapEntryDefinition) listEntry.get(i);
			Object value = null;
			Object keyValue = null;
			try {
				keyValue = ConvertUtils.convert(entry.getKey(), Class.forName(keyType));
			} catch (Exception e1) {
				throw new TechnicalException(e1);
			}
			if (StringUtils.isNotBlank(entry.getValue())) {
				try {
					String entryType = StringUtils.isBlank(entry.getType()) ? type : entry.getType();
					value = ConvertUtils.convert(entry.getValue(), Class.forName(entryType));
				} catch (ClassNotFoundException e) {
					throw new TechnicalException(e);
				}
			} else if (StringUtils.isNotBlank(entry.getRef())) {
				value = beanProxy.createBean(new ComponentIdImpl(beanId.getSujetId(), entry.getRef()));

			}
			mapValue.put(keyValue, value);
		}
		if (!add)
			setProperty(bean, propertyDef.getName(), mapValue);
	}

	@SuppressWarnings("unchecked")
	public static void setPropertyList(BeanFactory beanProxy, ComponentId beanId, Object bean, ListPropertyDefinition propertyDef) throws DaoFunctionalException {
		List<ListEntryDefinition> listEntry = propertyDef.getEntrys();
		List<Object> listValue = new ArrayList<Object>();
		boolean add = false;
		Object list = getProperty(bean, propertyDef.getName());
		if (list != null && (list instanceof List)) {
			listValue = (List) list;
			add = true;
		}
		String type = StringUtils.isBlank(propertyDef.getType()) ? String.class.getName() : propertyDef.getType();
		for (int i = 0; i < listEntry.size(); i++) {
			ListEntryDefinition entry = (ListEntryDefinition) listEntry.get(i);
			Object value = null;
			if (StringUtils.isNotBlank(entry.getValue())) {
				try {
					String entryType = StringUtils.isBlank(entry.getType()) ? type : entry.getType();
					value = ConvertUtils.convert(entry.getValue(), Class.forName(entryType));
				} catch (ClassNotFoundException e) {
					throw new TechnicalException(e);
				}
			} else if (StringUtils.isNotBlank(entry.getRef())) {
				value = beanProxy.createBean(new ComponentIdImpl(beanId.getSujetId(), entry.getRef()));
			}
			listValue.add(value);
		}
		if (!add)
			setProperty(bean, propertyDef.getName(), listValue);

	}

	public static void setProperty(Object bean, String name, Object value) {
		if (PropertyUtils.isWriteable(bean, name)) {
			try {
				BeanUtils.setProperty(bean, name, value);
			} catch (Exception e) {
				throw new TechnicalException(e);
			}
		}
	}

	public static Object getProperty(Object bean, String name) {
		if (PropertyUtils.isReadable(bean, name)) {
			try {
				return PropertyUtils.getProperty(bean, name);
			} catch (Exception e) {
				throw new TechnicalException(e);
			}
		}
		return null;
	}
}
