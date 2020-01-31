package tn.com.smartsoft.commons.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class BeanObjectUtils {
	
	public static Map<String, PropertyDescriptor> getPropertyDescriptors(Class<?> modelClass) {
		try {
			PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(modelClass);
			Map<String, PropertyDescriptor> propertyDescriptorsMap = new HashMap<String, PropertyDescriptor>();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				propertyDescriptorsMap.put(propertyDescriptors[i].getName(), propertyDescriptors[i]);
			}
			return propertyDescriptorsMap;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	
	public static void setPropertyValue(Object bean, String property, Object value) {
		try {
			PropertyUtils.setProperty(bean, property, value);
		} catch (Exception e) {
			throw new TechnicalException("can not setter property '" + property + "' into bean:'" + bean.getClass() + "' and value :'" + value + "'");
		}
	}
	
	public static Object getPropertyValue(Object bean, String property) {
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (Exception e) {
			throw new TechnicalException("can not getter property '" + property + "' whith bean:'" + bean);
		}
	}
	
	public static boolean isReadableProperty(Object bean, String property) {
		return (PropertyUtils.isReadable(bean, property));
	}
	
	public static boolean isWriteableProperty(Object bean, String property) {
		return (PropertyUtils.isWriteable(bean, property));
	}
	
	public static Object cloneBean(Object bean) {
		try {
			return BeanUtils.cloneBean(bean);
		} catch (Exception e) {
			throw new TechnicalException("can not clone Bean :" + bean);
		}
	}
	
	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
		}
	}
	
	public static void populateJson(Object dest, Map<String, Object> orig) {
		try {
			PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(dest.getClass());
			PropertyUtilsBean pu = new PropertyUtilsBean();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
				String name = propertyDescriptor.getName();
				Object value = orig.get(name);
				if (value != null) {
					if (value instanceof String) {
						String nvalue = (String) value;
						if (propertyDescriptor.getPropertyType().equals(String.class)) {
							pu.setProperty(dest, name, nvalue);
						} else if (propertyDescriptor.getPropertyType().equals(Double.class)) {
							pu.setProperty(dest, name, Double.valueOf(nvalue));
						} else if (propertyDescriptor.getPropertyType().equals(Long.class)) {
							pu.setProperty(dest, name, Long.valueOf(nvalue));
						} else if (propertyDescriptor.getPropertyType().equals(java.util.Date.class)) {
							Date asDateUtil = null;
							if (!"0000-00-00 00:00:00".equals(nvalue))
								asDateUtil = DateUtils.getAsDateUtil(nvalue, "yyyy-MM-dd HH:mm:ss");
							pu.setProperty(dest, name, asDateUtil);
						}
					} else {
						if (propertyDescriptor.getPropertyType().equals(Double.class)) {
							value = Double.valueOf(value.toString());
						}
						pu.setProperty(dest, name, value);
					}
				}
			}
		} catch (Exception e) {
			throw new TechnicalException("can not populate Bean :" + dest + " cause" + e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<? extends Object> toListJson(InstancietClass dest, ArrayList<?> orig) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < orig.size(); i++) {
			Map<String, Object> cap = (Map<String, Object>) orig.get(i);
			Object OutboundCountrie = dest.instanceObject();
			populateJson(OutboundCountrie, cap);
			list.add(OutboundCountrie);
		}
		return list;
	}
	
	public static List<? extends Object> toListJson(final Class<?> dest, ArrayList<?> orig) {
		return toListJson(new InstancietClass() {
			public Object instanceObject() {
				try {
					return dest.newInstance();
				} catch (Exception e) {
					throw new TechnicalException(e);
				}
			}
		}, orig);
		
	}
}
