package tn.com.smartsoft.commons.utils.cache.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.framework.beans.IdentifiableBean;

public class ObjectsCache implements Serializable, ICache {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<Object, CacheEntry> cache;
	public static final int DEFAULT_TTL = -1;
	protected int ttl = DEFAULT_TTL;

	public ObjectsCache() {
		cache = new HashMap<Object, CacheEntry>();
	}

	public int getTTL() {
		return ttl;
	}

	public void setTTL(int ttl) {
		this.ttl = ttl;
	}

	public void setTTL(String ttl) {
		if (ttl == null) {
			setTTL(DEFAULT_TTL);
		} else {
			setTTL(Integer.parseInt(ttl));
		}
	}

	protected synchronized boolean isExpired(CacheEntry cacheEntry) {
		int entryTTL = ttl;

		int cacheTTL = cacheEntry.getTTL();
		if (cacheTTL >= 0) {
			entryTTL = cacheTTL;
		}
		if (entryTTL < 0) {
			return false;
		}

		return System.currentTimeMillis() - cacheEntry.getLastRequestTime() > entryTTL;
	}

	public synchronized Object get(Object key) {
		CacheEntry cacheEntry = (CacheEntry) cache.get(key);
		if (cacheEntry != null) {
			if (isExpired(cacheEntry)) {
				remove(key);
				return null;
			} else {
				return cacheEntry.getObject();
			}
		}
		return null;
	}

	public synchronized void put(IdentifiableBean value) {
		put(value.getId(), value);
	}

	public synchronized void put(Object key, Object value) {
		cache.put(key, new CacheEntry(value, System.currentTimeMillis()));
	}

	public synchronized void put(Object key, Object value, int ttl) {
		cache.put(key, new CacheEntry(value, System.currentTimeMillis(), ttl));
	}

	public synchronized void remove(Object key) {
		cache.remove(key);
	}

	public String toString() {
		return cache.toString();
	}

	public List<Object> getKeys() {
		return new ArrayList<Object>(cache.keySet());
	}

	public boolean isKeyInCache(Object key) {
		return cache.containsKey(key);
	}

}
