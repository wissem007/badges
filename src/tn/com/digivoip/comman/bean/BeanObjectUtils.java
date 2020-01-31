package tn.com.digivoip.comman.bean;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import tn.com.digivoip.framework.exception.TechnicalException;

public class BeanObjectUtils{

	public static void setPropertyValue(Object bean, String property, Object value) {
		try {
			PropertyUtils.setProperty(bean, property, value);
		} catch (Exception e) {
			throw new TechnicalException("can not setter property '" + property + "' into bean:'" + bean + "' and value :'" + value + "'");
		}
	}
	public static Class<?> getPropertyType(Object bean, String property) {
		try {
			return PropertyUtils.getPropertyType(bean, property);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public static Object getPropertyValue(Object bean, String property) {
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (Exception e) {
			throw new TechnicalException("can not getter property '" + property + "' whith bean:'" + bean);
		}
	}
	public static void copyProperties(Object orig, Object dest) {
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public static Object cloneBean(Object bean) {
		try {
			return BeanUtils.cloneBean(bean);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public static boolean isReadableProperty(Object bean, String property) {
		return (PropertyUtils.isReadable(bean, property));
	}
	public static boolean isWriteableProperty(Object bean, String property) {
		return (PropertyUtils.isWriteable(bean, property));
	}
}
