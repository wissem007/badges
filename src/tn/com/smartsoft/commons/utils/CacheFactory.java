package tn.com.smartsoft.commons.utils;

import tn.com.smartsoft.commons.utils.cache.ICacheManager;
import tn.com.smartsoft.commons.utils.cache.eh.EhCacheManager;
import tn.com.smartsoft.commons.utils.cache.object.ObjectsCacheManager;

public class CacheFactory {
	public static final int OBJECT_CACHE_TYPE = 1;
	public static final int EH_CACHE_TYPE = 2;

	public static ICacheManager createCache(int type, Object params) {
		if (type == EH_CACHE_TYPE)
			return EhCacheManager.createCacheManager((String) params);
		return ObjectsCacheManager.createCacheManager((String[]) params);

	}

}
