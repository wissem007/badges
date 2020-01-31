package tn.com.smartsoft.commons.utils;

import java.io.Serializable;

public class SorterType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;

	public final static SorterType DESC = new SorterType("desc");
	public final static SorterType ASC = new SorterType("asc");
	private static final SorterType ALL[] = (new SorterType[] { DESC, ASC });

	private SorterType(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof String)
			return type.equalsIgnoreCase((String) obj);
		if (obj instanceof SorterType)
			return type.equals(((SorterType) obj).type);
		else
			return false;
	}

	public static SorterType parse(String s) {
		for (int i = 0; i < ALL.length; i++)
			if (ALL[i].equals(s))
				return ALL[i];
		throw new RuntimeException("Invalid Target Type: " + s);
	}
}
