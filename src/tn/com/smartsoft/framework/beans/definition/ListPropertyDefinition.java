package tn.com.smartsoft.framework.beans.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPropertyDefinition extends GenericPropertyDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ListEntryDefinition> mapEntrys = new ArrayList<ListEntryDefinition>();
	private String type;

	public ListPropertyDefinition() {
		super();
	}

	public void addEntry(ListEntryDefinition mapEntryConf) {
		mapEntrys.add(mapEntryConf);
	}

	public List<ListEntryDefinition> getEntrys() {
		return Collections.unmodifiableList(mapEntrys);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
