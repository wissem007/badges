package tn.com.smartsoft.commons.utils.cache;

import java.util.List;

import tn.com.smartsoft.framework.beans.IdentifiableBean;

public interface ICache {

	public abstract Object get(Object key);

	public abstract void put(IdentifiableBean value);

	public abstract void put(Object key, Object value);

	public abstract void remove(Object key);

	public abstract List<?> getKeys();

	public boolean isKeyInCache(Object key);
}