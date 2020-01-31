package tn.com.smartsoft.commons.utils.cache.object;

public class CacheEntry {

	private Object object;
	private int ttl;
	private long insertTime;
	private long lastRequestTime;

	public CacheEntry(Object object, long insertTime) {
		this(object, insertTime, -1);
	}

	public CacheEntry(Object object, long insertTime, int ttl) {
		this.object = object;
		this.insertTime = insertTime;
		this.ttl = ttl;
		setLastRequestTime(insertTime);
	}

	public Object getObject() {
		return object;
	}

	public long getInsertTime() {
		return insertTime;
	}

	public int getTTL() {
		return ttl;
	}

	public void setTTL(int ttl) {
		this.ttl = ttl;
	}

	public long getLastRequestTime() {
		return lastRequestTime;
	}

	public void setLastRequestTime(long lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}

	public String toString() {
		return object.toString();
	}

}
