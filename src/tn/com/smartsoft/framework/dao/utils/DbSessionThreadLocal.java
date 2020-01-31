package tn.com.smartsoft.framework.dao.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.dao.DbSession;

public abstract class DbSessionThreadLocal {

	private static final Logger logger = Logger.getLogger(DbSessionThreadLocal.class);

	private static final ThreadLocal resources = new ThreadLocal();

	public static Map getResourceMap() {
		Map map = (Map) resources.get();
		Map map2 = map;
		return (map2 != null ? Collections.unmodifiableMap(map2) : Collections.EMPTY_MAP);
	}

	public static boolean hasResource(Object key) {
		Map map = (Map) resources.get();
		return ((map != null) && map.containsKey(key));
	}

	public static DbSession getResource(Object key) {
		Map map = (Map) resources.get();
		if (map == null)
			return null;
		Object value = map.get(key);
		if (value != null)
			logger.debug("Retrieved value [" + value + "] for key [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]");
		return (DbSession) value;
	}

	public static void addResource(Object key, DbSession value) throws IllegalStateException {
		Map map = (Map) resources.get();
		if (map == null) {
			map = new HashMap();
			resources.set(map);
		}
		if (map.containsKey(key))
			throw new TechnicalException("Already value [" + map.get(key) + "] for key [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]");
		map.put(key, value);
		logger.debug("Bound value [" + value + "] for key [" + key + "] to thread [" + Thread.currentThread().getName() + "]");
	}

	public static void clearResource() {
		Map map = (Map) resources.get();
		resources.set(null);
	}

	public static DbSession removeResource(Object key) {
		Map map = (Map) resources.get();
		if ((map == null) || !map.containsKey(key))
			return null;
		Object value = map.remove(key);
		if (map.isEmpty())
			resources.set(null);
		logger.debug("Removed value [" + value + "] for key [" + key + "] from thread [" + Thread.currentThread().getName() + "]");
		return (DbSession) value;
	}

}
