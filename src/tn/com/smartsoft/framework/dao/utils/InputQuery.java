package tn.com.smartsoft.framework.dao.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InputQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String request;

	private Map values;

	public InputQuery(String request, Map values) {
		this.request = request;
		this.values = values;
	}

	public String request() {
		return request;
	}

	public Map getValues() {
		if (values == null)
			values = new HashMap();
		return values;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("InputQuery:[");
		sb.append("\n           Request: ").append(request);
		sb.append("\n           Parameter Values :").append(getValues());
		sb.append("\n           ];");
		return sb.toString();
	}
}
