package tn.com.digivoip.comman.reflect;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tn.com.digivoip.framework.exception.TechnicalException;

public class ClassUtilities{

	private static Log	log	= LogFactory.getLog(ClassUtilities.class);

	@SuppressWarnings("rawtypes")
	public static Class loadClass(String className) throws ClassNotFoundException {
		return ClassUtilities.loadClass(className, null);
	}
	@SuppressWarnings("rawtypes")
	public static Class loadClass(String className, Object requestor) throws ClassNotFoundException {
		Class requestorClass = null;
		if (requestor == null) {
			requestorClass = ClassUtilities.class;
		} else {
			requestorClass = requestor.getClass();
		}
		return ClassUtilities.loadClass(className, requestorClass);
	}
	@SuppressWarnings("rawtypes")
	public static Class loadClass(String className, Class requestor) throws ClassNotFoundException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			return cl.loadClass(className);
		} catch (ClassNotFoundException e) {
			ClassUtilities.log.info("ClassNotFoundException using thread context class loader:" + className);
			cl = requestor.getClass().getClassLoader();
			return cl.loadClass(className);
		} catch (SecurityException e) {
			ClassUtilities.log.info("SecurityException using thread context class loader :" + className);
			cl = requestor.getClass().getClassLoader();
			return cl.loadClass(className);
		}
	}
	public static URL getResource(String name) {
		return ClassUtilities.getResource(name, null);
	}
	@SuppressWarnings("rawtypes")
	public static URL getResource(String name, Object requestor) {
		Class requestorClass = null;
		if (requestor == null) {
			requestorClass = ClassUtilities.class;
		} else {
			requestorClass = requestor.getClass();
		}
		return ClassUtilities.getResource(name, requestorClass);
	}
	@SuppressWarnings("rawtypes")
	public static URL getResource(String name, Class requestor) {
		URL resource = null;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		resource = cl.getResource(name);
		if (resource == null) {
			cl = requestor.getClass().getClassLoader();
			if (cl == null) { throw new TechnicalException("ressource not exist " + name); }
			resource = cl.getResource(name);
		}
		return resource;
	}
	public static InputStream getResourceAsStream(String name) {
		return ClassUtilities.getResourceAsStream(name, null);
	}
	@SuppressWarnings("rawtypes")
	public static InputStream getResourceAsStream(String name, Object requestor) {
		Class requestorClass = null;
		if (requestor == null) {
			requestorClass = ClassUtilities.class;
		} else {
			requestorClass = requestor.getClass();
		}
		return ClassUtilities.getResourceAsStream(name, requestorClass);
	}
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("rawtypes")
	public static List getAllClassesAndInterfaces(Class startClass) {
		ArrayList classes = new ArrayList();
		ClassUtilities.addClassesAndInterfaces(startClass, classes);
		return classes;
	}
	@SuppressWarnings("rawtypes")
	public static String getRessourcePath(Class clzzz, String sourcePath) {
		return StringUtils.replace(clzzz.getPackage().getName(), ".", "/") + "/" + sourcePath;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void addClassesAndInterfaces(Class c, List classes) {
		if (c == null) { return; }
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
		ClassUtilities.addClassesAndInterfaces(superClass, classes);
		for (int i = 0; i < interfaces.length; i++) {
			ClassUtilities.addClassesAndInterfaces(interfaces[i], classes);
		}
	}
	public static Object newInstance(Class<?> listener) {
		try {
			return listener.newInstance();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	public static void sleep(long d) {
		try {
			Thread.sleep(d);
		} catch (InterruptedException e) {}
	}
}
