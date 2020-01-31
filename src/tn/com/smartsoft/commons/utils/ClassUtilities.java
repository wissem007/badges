package tn.com.smartsoft.commons.utils;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class ClassUtilities {

	private static Log log = LogFactory.getLog(ClassUtilities.class);

	public static Class getJavaClass(String className) {
		try {
			return loadClass(className, null);
		} catch (ClassNotFoundException e) {
			throw new TechnicalException(e);
		}
	}

	public static Class loadClass(String className) throws ClassNotFoundException {
		return loadClass(className, null);
	}

	public static Class loadClass(String className, Object requestor) throws ClassNotFoundException {
		Class requestorClass = null;
		if (requestor == null) {
			requestorClass = ClassUtilities.class;
		} else {
			requestorClass = requestor.getClass();
		}
		return loadClass(className, requestorClass);
	}

	public static Class loadClass(String className, Class requestor) throws ClassNotFoundException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {

			return cl.loadClass(className);
		} catch (ClassNotFoundException e) {
			log.info("ClassNotFoundException using thread context class loader:" + className);
			cl = requestor.getClass().getClassLoader();
			return cl.loadClass(className);
		} catch (SecurityException e) {
			log.info("SecurityException using thread context class loader :" + className);
			cl = requestor.getClass().getClassLoader();
			return cl.loadClass(className);
		}
	}

	public static Object cloneBean(Object bean) {
		try {
			return BeanUtils.cloneBean(bean);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static URL getResource(String name) {
		return getResource(name, null);
	}

	public static URL getResource(String name, Object requestor) {
		Class requestorClass = null;
		if (requestor == null) {
			requestorClass = ClassUtilities.class;
		} else {
			requestorClass = requestor.getClass();
		}
		return getResource(name, requestorClass);
	}

	public static URL getResource(String name, Class requestor) {
		URL resource = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		resource = cl.getResource(name);
		if (resource == null) {
			cl = requestor.getClass().getClassLoader();
			if (cl == null)
				throw new TechnicalException("ressource not exist " + name);
			resource = cl.getResource(name);
		}
		return resource;
	}

	public static InputStream getResourceAsStream(String name) {
		return getResourceAsStream(name, null);
	}

	public static InputStream getResourceAsStream(String name, Object requestor) {
		Class requestorClass = null;
		if (requestor == null) {
			requestorClass = ClassUtilities.class;
		} else {
			requestorClass = requestor.getClass();
		}
		return getResourceAsStream(name, requestorClass);
	}

	public static InputStream getResourceAsStream(String name, Class requestor) {
		InputStream resourceStream = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		resourceStream = cl.getResourceAsStream(name);
		if (resourceStream == null) {
			cl = requestor.getClass().getClassLoader();
			resourceStream = cl.getResourceAsStream(name);
		}
		return resourceStream;
	}

	public static String[] getAllSimpleProperty(Class startClass) {
		try {
			PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(startClass.newInstance());
			ArrayList<String> fields = new ArrayList<String>();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
				if (!propertyDescriptor.getPropertyType().equals(DataBusinessBean.class)) {
					fields.add(propertyDescriptor.getName());
				}
			}
			String[] includs = new String[fields.size()];
			includs = (String[]) fields.toArray(includs);
			return includs;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static List getAllClassesAndInterfaces(Class startClass) {
		ArrayList classes = new ArrayList();

		addClassesAndInterfaces(startClass, classes);

		return classes;
	}

	public static String getRessourcePath(Class clzzz, String sourcePath) {
		return StringUtils.replace(clzzz.getPackage().getName(), ".", "/") + "/" + sourcePath;
	}

	protected static void addClassesAndInterfaces(Class c, List classes) {
		if (c == null) {
			return;
		}
		Class superClass = c.getSuperclass();
		Class[] interfaces = c.getInterfaces();
		if (superClass != null && !classes.contains(superClass)) {
			classes.add(superClass);
		}
		for (int i = 0; i < interfaces.length; i++) {
			if (interfaces[i] != null && !classes.contains(interfaces[i])) {
				classes.add(interfaces[i]);
			}
		}
		addClassesAndInterfaces(superClass, classes);
		for (int i = 0; i < interfaces.length; i++) {
			addClassesAndInterfaces(interfaces[i], classes);
		}
	}

	public static Object newInstance(String className) {
		try {
			return newInstance(loadClass(className));
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static Object newInstance(Class<?> listener) {
		try {
			return listener.newInstance();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
