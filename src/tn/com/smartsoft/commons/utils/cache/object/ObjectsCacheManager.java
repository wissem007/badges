package tn.com.smartsoft.commons.utils.cache.object;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.commons.utils.cache.ICacheManager;

public class ObjectsCacheManager implements ICacheManager {
	private ConcurrentMap<String, ObjectsCache> objectsCaches = new ConcurrentHashMap<String, ObjectsCache>();

	public ObjectsCacheManager() {
		super();
	}

	public ObjectsCacheManager(String[] names) {
		this.addCaches(names);
	}

	public ICache getCache(String name) {
		return objectsCaches.get(name);
	}

	public void addCache(String name, ObjectsCache objectsCache) {
		objectsCaches.put(name, objectsCache);
	}

	public void addCache(String name) {
		objectsCaches.put(name, new ObjectsCache());
	}

	public void addCaches(String[] names) {
		for (int i = 0; i < names.length; i++) {
			addCache(names[i]);
		}
	}

	public static ICacheManager createCacheManager(String[] names) {
		try {
			return new ObjectsCacheManager(names);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
