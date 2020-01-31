package tn.com.smartsoft.framework.beans.definition;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class MapEntryDefinition extends ListEntryDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;

	public MapEntryDefinition() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
