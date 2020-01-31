package tn.com.smartsoft.commons.utils.cache.eh;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.framework.beans.IdentifiableBean;

public class EhCache implements ICache {
	private Cache ehCacheDelegate;

	public EhCache(Cache ehCacheDelegate) {
		super();
		this.ehCacheDelegate = ehCacheDelegate;
	}

	public Object get(Object key) {
		if (!isKeyInCache(key))
			return null;
		Element element = ehCacheDelegate.get(key);
		return element.getValue();
	}

	public boolean isKeyInCache(Object key) {
		return ehCacheDelegate.isKeyInCache(key);
	}

	public List<?> getKeys() {
		return ehCacheDelegate.getKeys();
	}

	public void put(IdentifiableBean value) {
		ehCacheDelegate.put(new Element(value.getId(), value));
	}

	public void put(Object key, Object value) {
		ehCacheDelegate.put(new Element(key, value));
	}

	public void remove(Object key) {
		ehCacheDelegate.remove(key);
	}

}
