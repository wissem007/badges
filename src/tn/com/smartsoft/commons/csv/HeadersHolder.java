package tn.com.smartsoft.commons.csv;

import java.util.HashMap;

public class HeadersHolder {
	public String[] Headers;

	public int Length;

	public HashMap IndexByName;

	public HeadersHolder() {
		Headers = null;
		Length = 0;
		IndexByName = new HashMap();
	}
}