package tn.com.smartsoft.commons.utils;

import java.io.Serializable;

public abstract class NamedObject implements Serializable {
	protected String name;

	public NamedObject() {
		super();
	}

	public NamedObject(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
