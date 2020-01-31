package tn.com.smartsoft.commons.web.js;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.utils.Var;

public class JsMap implements Serializable, JsObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String varName;
	
	private HashMap<String, String> types;
	private HashMap<String, String> values;
	
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
		if (value != null) {
			values.put(name, StringUtils.removeEnd(StringUtils.remove(value.toString(), "\n"), ";"));
			types.put(name, String.valueOf(expected));
		} else {
			values.remove(name);
			types.remove(name);
		}
		
	}
	
	public void addValue(String name, String value) {
		addValue(name, value, !StringUtils.isNumeric(value));
	}
	
	public boolean getBooleanValue(String name) {
		if (values.get(name) == null)
			return false;
		return Boolean.getBoolean((String) values.get(name));
	}
	
	public int getIntValue(String name) {
		if (values.get(name) == null)
			return 0;
		return Integer.parseInt((String) values.get(name));
	}
	
	public String getStringValue(String name) {
		if (values.get(name) == null)
			return "";
		return (String) values.get(name);
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
			String key = (String) it.next();
			String value = "";
			if (vars.get(key) instanceof String)
				value = (String) vars.get(key);
			else if (vars.get(key) instanceof Var)
				value = ((Var) vars.get(key)).getValue();
			this.addStringValue(key, value);
		}
	}
	
	public void addBooleanValue(String name, boolean value) {
		addValue(name, String.valueOf(value), false);
	}
	
	public void addBooleanValue(String name, Boolean value) {
		addValue(name, value, false);
	}
	
	public void addIntValue(String name, int value) {
		addValue(name, String.valueOf(value), false);
	}
	
	public String toJs() {
		StringBuffer stringbuffer = new StringBuffer();
		if (getVarName() != null)
			stringbuffer.append("var ").append(getVarName()).append("={");
		else
			stringbuffer.append("{");
		Iterator<String> iterator = values.keySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) values.get(key);
			if (types.get(key).equals("true"))
				stringbuffer.append("'").append(key).append("':'").append(JSEncodeUtil.encodeJavaScript(value)).append("',");
			else
				stringbuffer.append("'").append(key).append("':").append(value).append(",");
			i++;
		}
		if (!isEmpty())
			stringbuffer.replace(stringbuffer.length() - 1, stringbuffer.length(), "");
		stringbuffer.append("}");
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
