package tn.com.smartsoft.commons.utils.cache.object;

public interface CacheStorage {
	public Object get(Object key);

	public void put(Object key, Object value);

	public void remove(Object key);

	public void clear();
}
