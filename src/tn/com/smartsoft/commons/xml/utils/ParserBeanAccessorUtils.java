package tn.com.smartsoft.commons.xml.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

import tn.com.smartsoft.commons.utils.ClassUtilities;
import tn.com.smartsoft.commons.xml.exception.ParseException;

public class ParserBeanAccessorUtils {
	public static Object convertValue(String value, Class<?> type) throws ParseException {
		try {
			return ConvertUtils.convert(value, type);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public static Object convertValue(String value, String type) throws ParseException {
		try {
			return convertValue(value, Class.forName(type));
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public static Object newInstance(String javaType) throws ParseException {
		try {
			Class<?> resultClass = ClassUtilities.loadClass(javaType);
			return newInstance(resultClass);
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public static Object newInstance(Class<?> javaType) throws ParseException {
		try {
			return javaType.newInstance();
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

	public static Class<?> classForName(String type) throws ParseException {
		try {
			return Class.forName(type);
		} catch (Exception e) {
			throw new ParseException("invalid class type :" + type, e);
		}
	}

	public static Object invokeMethod(Object bean, String methode, Object[] values, Class<?>[] parameterTypes) throws ParseException {
		try {
			return MethodUtils.invokeMethod(bean, methode, values, parameterTypes);
		} catch (Exception e) {
			throw new ParseException("error invocation Method '" + methode + "' from bean:'" + bean.getClass().getName(), e);
		}
	}

	public static Object invokeMethod(Object bean, String methode, Object value, Class<?> type) throws ParseException {
		try {
			return invokeMethod(bean, methode, new Object[] { value }, new Class<?>[] { type });
		} catch (Exception e) {
			throw new ParseException("error invocation Method '" + methode + "' from bean:'" + bean.getClass().getName(), e);

		}
	}

	public static Object invokeMethod(Object bean, String methode, Object value0, Class<?> type0, Object value1, Class<?> type1) throws ParseException {
		try {
			return invokeMethod(bean, methode, new Object[] { value0, value1 }, new Class<?>[] { type0, type1 });
		} catch (Exception e) {
			throw new ParseException("error invocation Method '" + methode + "' from bean:'" + bean.getClass().getName(), e);

		}
	}

	public static Object invokeMethod(Object bean, String methode, Object[] values) throws ParseException {
		try {
			return MethodUtils.invokeMethod(bean, methode, values);
		} catch (Exception e) {
			throw new ParseException("error invocation Method '" + methode + "' from bean:'" + bean.getClass().getName(), e);
		}
	}

	public static Object invokeMethod(Object bean, String methode, Object value) throws ParseException {
		try {
			return invokeMethod(bean, methode, new Object[] { value });
		} catch (Exception e) {
			throw new ParseException("error invocation Method '" + methode + "' from bean:'" + bean.getClass().getName(), e);

		}
	}

	public static Object invokeMethod(Object bean, String methode, Object value0, Object value1) throws ParseException {
		try {
			return invokeMethod(bean, methode, new Object[] { value0, value1 });
		} catch (Exception e) {
			throw new ParseException("error invocation Method '" + methode + "' from bean:'" + bean.getClass().getName(), e);

		}
	}

	public static void setPropertyValue(Object bean, String property, Object value) throws ParseException {
		try {
			BeanUtils.setProperty(bean, property, value);
		} catch (Exception e) {
			throw new ParseException("can not setter property '" + property + "' into bean:'" + bean + "' and value :'" + value + "'", e);
		}
	}

	public static Object getPropertyValue(Object bean, String property) throws ParseException {
		try {
			return BeanUtils.getProperty(bean, property);
		} catch (Exception e) {
			throw new ParseException("can not getter property '" + property + "' whith bean:'" + bean, e);
		}
	}

	public static Class<?> getPropertyType(Object bean, String property) throws ParseException {
		try {
			return PropertyUtils.getPropertyType(bean, property);
		} catch (Exception e) {
			throw new ParseException("can not getter property '" + property + "' whith bean:'" + bean, e);
		}
	}

	public static boolean isReadableProperty(Object bean, String property) {
		return (PropertyUtils.isReadable(bean, property));
	}

	public static boolean isWriteableProperty(Object bean, String property) {
		return (PropertyUtils.isWriteable(bean, property));
	}
}
