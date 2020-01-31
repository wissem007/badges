package tn.com.smartsoft.framework.dao;

import java.util.Map;

import org.hibernate.LockMode;

public interface DbQueryAccessor {

	@SuppressWarnings("rawtypes")
	public abstract Map getLockModeEntite();

	@SuppressWarnings("rawtypes")
	public abstract void setLockModeEntite(Map lockModeEntite);

	public abstract boolean isCacheQueries();

	public abstract void setCacheQueries(boolean isCacheQueries);

	public abstract String getQueryCacheRegion();

	public abstract void setQueryCacheRegion(String queryCacheRegion);

	public abstract int getFetchSize();

	public abstract void setFetchSize(int fetchSize);

	public abstract int getMaxResults();

	public abstract void setMaxResults(int maxResults);

	public abstract void setLockMode(String alias, LockMode lockMode);

}