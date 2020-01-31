package tn.com.smartsoft.commons.utils;

import java.io.Serializable;

public class ResourcesBuffer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] data;
	private long timestamp = -1;

	public ResourcesBuffer() {
		clean();
	}

	public void update(byte[] data, long timestamp) {
		this.data = data;
		this.timestamp = timestamp;
	}

	public void clean() {
		data = null;
		timestamp = -1;
	}

	public byte[] getData() {
		return data;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
