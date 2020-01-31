package tn.com.smartsoft.commons.utils;

import java.io.Serializable;

public class Var implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1473571708191970439L;

	public static final String LEFT_BRACKET = "[";

	public static final String RIGHT_BRACKET = "]";

	public static final String TOKEN_START = "$F{";

	public static final String TOKEN_END = "}";

	private String name = null;

	private String value = null;

	public Var(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isField() {
		return (name.indexOf(TOKEN_START) > -1) && (name.indexOf(TOKEN_END) > -1);
	}

	public String getFieldName() {
		if (isField())
			return name.substring(name.indexOf(TOKEN_START) + 1, name.indexOf(TOKEN_END));
		if (isIndexed())
			return name.substring(0, name.indexOf(LEFT_BRACKET));
		return null;
	}

	public boolean isIndexed() {
		return (name.indexOf(LEFT_BRACKET) > -1) && (name.indexOf(RIGHT_BRACKET) > -1);
	}

	public int getIndex() {
		if (isIndexed())
			return Integer.parseInt(name.substring(name.indexOf(LEFT_BRACKET) + 1, name.indexOf(RIGHT_BRACKET)));
		else
			return -1;
	}
}
