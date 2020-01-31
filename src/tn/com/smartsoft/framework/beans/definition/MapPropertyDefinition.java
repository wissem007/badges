package tn.com.smartsoft.framework.beans.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapPropertyDefinition extends GenericPropertyDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MapEntryDefinition> mapEntrys = new ArrayList<MapEntryDefinition>();
	private String type;
	private String keyType;

	public MapPropertyDefinition() {
		super();
	}

	public void addEntry(MapEntryDefinition mapEntryConf) {
		mapEntrys.add(mapEntryConf);
	}

	public List<MapEntryDefinition> getEntrys() {
		return Collections.unmodifiableList(mapEntrys);
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
