package tn.com.smartsoft.commons.utils;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

import net.sf.jasperreports.engine.export.JRXlsExporter;

public class ConcurrentMapFactory {
	private static final Class mapClass = getMapClass();
	private static final Class hashMapClass = getHashMapClass();

	public static Map createMap() {
		try {
			return (Map) hashMapClass.newInstance();
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	public static boolean isConcurrent(Map map) {
		return mapClass != null && mapClass.isInstance(map);
	}

	private static Class getMapClass() {
		return java.util.concurrent.ConcurrentMap.class;

	}

	private static Class getHashMapClass() {
		return java.util.concurrent.ConcurrentHashMap.class;
	}

}