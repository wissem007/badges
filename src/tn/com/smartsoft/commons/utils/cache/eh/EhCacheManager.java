package tn.com.smartsoft.commons.utils.cache.eh;

import net.sf.ehcache.CacheManager;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.commons.utils.cache.ICache;
import tn.com.smartsoft.commons.utils.cache.ICacheManager;

public class EhCacheManager implements ICacheManager {
	private CacheManager delegate;
	private static FileLocator fileLocator = new FileLocator();

	public EhCacheManager(CacheManager delegate) {
		super();
		this.delegate = delegate;
	}

	public ICache getCache(String name) {
		return new EhCache(delegate.getCache(name));
	}

	public static ICacheManager createCacheManager(String ressourcePath) {
		try {
			return new EhCacheManager(new CacheManager(fileLocator.getConfURL(ressourcePath)));
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
