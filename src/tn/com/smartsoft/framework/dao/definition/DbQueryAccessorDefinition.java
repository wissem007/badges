package tn.com.smartsoft.framework.dao.definition;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.LockMode;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class DbQueryAccessorDefinition implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isCacheQueries = false;
	private String queryCacheRegion = null;
	private int fetchSize = -1;
	private int maxResults = -1;
	private Map<String, LockMode> lockModeEntite = null;

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

	public Map<String, LockMode> getLockModeEntite() {
		if (lockModeEntite == null)
			lockModeEntite = new HashMap<String, LockMode>();
		return lockModeEntite;
	}

	public void addLockModeEntite(String alias, String lockMode) {
		getLockModeEntite().put(alias, LockMode.parse(lockMode));
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public void setLockModeEntite(Map<String, LockMode> lockModeEntite) {
		this.lockModeEntite = lockModeEntite;
	}
}
