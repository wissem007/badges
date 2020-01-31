package tn.com.digivoip.comman.js;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsMap implements Serializable,JsObject{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private String					varName;
	private HashMap<String, String>	types;
	private HashMap<String, String>	values;

	public JsMap(String varName) {
		super();
		this.varName = varName;
		types = new HashMap<String, String>();
		values = new HashMap<String, String>();
	}
	public JsMap() {
		this(null);
	}
	public String getVarName() {
		return varName;
	}
	public void addValue(String name, Object value, boolean expected) {
		if (value == null) {
			value = "";
		}
		values.put(name, value.toString());
		types.put(name, String.valueOf(expected));
	}
	public boolean getBooleanValue(String name) {
		if (values.get(name) == null) { return false; }
		return Boolean.getBoolean(values.get(name));
	}
	public int getIntValue(String name) {
		if (values.get(name) == null) { return 0; }
		return Integer.parseInt(values.get(name));
	}
	public String getStringValue(String name) {
		if (values.get(name) == null) { return ""; }
		return values.get(name);
	}
	public void addStringValue(String name, String value) {
		addValue(name, value, true);
	}
	public void addObjectValue(String name, Object value) {
		addValue(name, value, false);
	}
	public void addStringValues(Map<String, ?> vars) {
		Iterator<String> it = vars.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = "";
			if (vars.get(key) instanceof String) {
				value = (String) vars.get(key);
			}
			this.addStringValue(key, value);
		}
	}
	public void addBooleanValue(String name, boolean value) {
		addValue(name, String.valueOf(value), false);
	}
	public void addIntValue(String name, int value) {
		addValue(name, String.valueOf(value), false);
	}
	public String toJs() {
		StringBuffer stringbuffer = new StringBuffer();
		if (getVarName() != null) {
			stringbuffer.append("var ").append(getVarName()).append("={");
		} else {
			stringbuffer.append("{");
		}
		Iterator<String> iterator = values.keySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = values.get(key);
			if (types.get(key).equals("true")) {
				stringbuffer.append("'").append(key).append("':'").append(JSEncodeUtil.encodeJavaScript(value)).append("',");
			} else {
				stringbuffer.append("'").append(key).append("':").append(value).append(",");
			}
			i++;
		}
		if (!isEmpty()) {
			stringbuffer.replace(stringbuffer.length() - 1, stringbuffer.length(), "");
		}
		stringbuffer.append("}");
		return stringbuffer.toString();
	}
	public String toString() {
		return toJs();
	}
	public HashMap<String, String> getValues() {
		return values;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public boolean isEmpty() {
		return types.isEmpty();
	}
}
