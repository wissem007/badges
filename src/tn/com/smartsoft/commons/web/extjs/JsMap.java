package tn.com.smartsoft.commons.web.extjs;

import java.util.HashMap;
import java.util.Iterator;

public class JsMap implements JsVar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, JsVar> values;
	private boolean isReturn;
	
	public JsMap() {
		super();
		this.values = new HashMap<String, JsVar>();
	}
	
	public boolean isReturn() {
		return isReturn;
	}
	
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}
	
	public void addValue(String key, JsVar value) {
		this.values.put(key, value);
	}
	
	public void addStringValue(String key, Object value) {
		this.values.put(key, JsValue.newString(value));
	}
	
	public void addObjectValue(String key, Object value) {
		this.values.put(key, JsValue.newObject(value));
	}
	
	public void append(ScriptBuffer sb) {
		sb.appendStartMapChar();
		Iterator<String> iterator = values.keySet().iterator();
		boolean isAdd = false;
		while (iterator.hasNext()) {
			String key = iterator.next();
			JsVar value = values.get(key);
			if (value == null || value.isNull())
				continue;
			if (isAdd)
				sb.appendSepValueChar();
			if (isReturn()) {
				sb.appendReturnLigne();
				sb.append(" ");
			}
			sb.appendToMap(key, value);
			isAdd = true;
		}
		if (isReturn())
			sb.appendReturnLigne();
		sb.appendEndMapChar();
	}
	
	public boolean isNull() {
		return values.isEmpty();
	}
	
	public static void main(String[] args) {
		JsMap params = new JsMap();
		params.setReturn(true);
		params.addObjectValue("nom", "value");
		params.addStringValue("prenom", "value");
		params.addObjectValue("isMa", true);
		ScriptBuffer sb = new ScriptBuffer();
		params.append(sb);
		// sb.appendVariable("name", params);
		// sb.appendEndLigneChar();
		System.out.println(sb.getBodyBuffer());
		
	}
}
