package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.LockMode;

import tn.com.smartsoft.framework.dao.DbQueryAccessor;

public class DbQueryAccessorImpl implements DbQueryAccessor, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isCacheQueries = false;
	private String queryCacheRegion = null;
	private int fetchSize = -1;
	private int maxResults = -1;
	private Map lockModeEntite = null;

	public Map getLockModeEntite() {
		return lockModeEntite;
	}

	public void setLockModeEntite(Map lockModeEntite) {
		this.lockModeEntite = lockModeEntite;
	}

	public boolean isCacheQueries() {
		return isCacheQueries;
	}

	public void setCacheQueries(boolean isCacheQueries) {
		this.isCacheQueries = isCacheQueries;
	}

	public String getQueryCacheRegion() {
		return queryCacheRegion;
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setLockMode(String alias, LockMode lockMode) {
		if (lockModeEntite == null)
			lockModeEntite = new HashMap();
		lockModeEntite.put(alias, lockMode);
	}
}
