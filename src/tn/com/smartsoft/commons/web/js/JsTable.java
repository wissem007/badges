package tn.com.smartsoft.commons.web.js;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class JsTable implements Serializable, JsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String varName;
	private HashMap<Integer, String> types;
	private ArrayList<String> listValue;

	public JsTable(String varName) {
		super();
		this.varName = varName;
		types = new HashMap<Integer, String>();
		listValue = new ArrayList<String>();
	}

	public JsTable() {
		this(null);
	}

	public String getVarName() {
		return varName;
	}

	public void addValue(Object value, boolean expected) {
		if (value != null) {
			types.put(listValue.size(), String.valueOf(expected));
			listValue.add(value.toString());
		}
	}

	public void addObjectValue(Object value) {
		addValue(value, false);
	}

	public boolean getBooleanValue(int index) {
		if (listValue.get(index) == null)
			return false;
		return Boolean.getBoolean((String) listValue.get(index));
	}

	public int getIntValue(int index) {
		if (listValue.get(index) == null)
			return 0;
		return Integer.parseInt((String) listValue.get(index));
	}

	public String getStringValue(int index) {
		if (listValue.get(index) == null)
			return "";
		return (String) listValue.get(index);
	}

	public void addStringOption(String value) {
		addValue(value, true);
	}

	public void addBooleanOption(boolean value) {
		addValue(String.valueOf(value), false);
	}

	public void addIntOption(int value) {
		addValue(String.valueOf(value), false);
	}

	public String toJsParams() {
		StringBuffer stringbuffer = new StringBuffer();
		for (int j = 0; j < listValue.size(); j++) {
			String value = (String) listValue.get(j);
			if (types.get(j).equals("true"))
				stringbuffer.append("'").append(JSEncodeUtil.encodeJavaScript(value)).append("',");
			else
				stringbuffer.append(value).append(",");
		}
		if (!isEmpty())
			stringbuffer.replace(stringbuffer.length() - 1, stringbuffer.length(), "");
		return stringbuffer.toString();
	}

	public String toJs() {
		StringBuffer stringbuffer = new StringBuffer();
		if (getVarName() != null)
			stringbuffer.append("var ").append(getVarName()).append("=[");
		else
			stringbuffer.append("[");
		for (int j = 0; j < listValue.size(); j++) {
			String value = (String) listValue.get(j);
			if (types.get(j).equals("true"))
				stringbuffer.append("'").append(JSEncodeUtil.encodeJavaScript(value)).append("',");
			else
				stringbuffer.append(value).append(",");
		}
		if (!isEmpty())
			stringbuffer.replace(stringbuffer.length() - 1, stringbuffer.length(), "");
		stringbuffer.append("]");
		return stringbuffer.toString();
	}

	public String toString() {
		return toJs();
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public boolean isEmpty() {
		return types.isEmpty();
	}

}
