package tn.com.smartsoft.commons.web.extjs;

import java.io.Serializable;
import java.util.ArrayList;

public class JsArray implements Serializable, JsVar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JsVar> values;
	
	public JsArray() {
		super();
		values = new ArrayList<JsVar>();
	}
	
	public void addValue(JsVar value) {
		this.values.add(value);
	}
	
	public void addStringValue(Object value) {
		this.addValue(JsValue.newString(value));
	}
	
	public void addObjectValue(Object value) {
		this.addValue(JsValue.newObject(value));
	}
	
	public void append(ScriptBuffer sb) {
		sb.appendStartArrayChar();
		boolean isAdd = false;
		for (int i = 0; i < values.size(); i++) {
			JsVar value = values.get(i);
			if (value == null || value.isNull())
				continue;
			if (isAdd)
				sb.appendSepValueChar();
			value.append(sb);
			isAdd = true;
		}
		sb.appendEndArrayChar();
	}
	
	public boolean isNull() {
		return values.isEmpty();
	}
	
	public static void main(String[] args) {
		JsArray params = new JsArray();
		params.addObjectValue("value");
		params.addStringValue("value");
		params.addObjectValue(true);
		ScriptBuffer sb = new ScriptBuffer();
		sb.appendVariable("name", params);
		sb.appendEndLigneChar();
		System.out.println(sb.getBodyBuffer());
	}
}
