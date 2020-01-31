package tn.com.smartsoft.commons.web.extjs;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JSEncodeUtil;

public class ScriptBuffer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String LINE_FEED_CHAR = "\n";
	public static String EQUALS_CHAR = "=";
	public static String QUOT_CHAR = "\"";
	public static String APOS_CHAR = "'";
	public static String SEP_VALUE_CHAR = ",";
	public static String START_MAP_CHAR = "{";
	public static String END_MAP_CHAR = "}";
	public static String START_ARRAY_CHAR = "[";
	public static String END_ARRAY_CHAR = "]";
	public static String KEY_MAP_CHAR = ":";
	public static String END_LIGNE_CHAR = ";";
	public static String SPACE_CHAR = " ";
	public static String TAB_CHAR = " ";
	
	private StringBuffer headBuffer = new StringBuffer();
	private StringBuffer bodyBuffer = new StringBuffer();
	
	public ScriptBuffer appendHead(String value) {
		if (StringUtils.isNotBlank(value))
			this.headBuffer.append(value);
		return this;
	}
	
	public ScriptBuffer append(String value) {
		if (value != null)
			this.bodyBuffer.append(value);
		return this;
	}
	
	public ScriptBuffer appendAndEncodeValue(String value) {
		return this.append(JSEncodeUtil.encodeJavaScript(value.toString()));
	}
	
	public ScriptBuffer appendVariable(String name, JsVar object) {
		this.append("var ").append(name);
		this.append(EQUALS_CHAR);
		object.append(this);
		return this;
	}
	
	public ScriptBuffer appendStartMapChar() {
		return this.append(START_MAP_CHAR);
	}
	
	public ScriptBuffer appendEndMapChar() {
		return this.append(END_MAP_CHAR);
	}
	
	public ScriptBuffer appendStartArrayChar() {
		return this.append(START_ARRAY_CHAR);
	}
	
	public ScriptBuffer appendEndArrayChar() {
		return this.append(END_ARRAY_CHAR);
	}
	
	public ScriptBuffer appendReturnLigne() {
		return this.append(
				LINE_FEED_CHAR);
	}
	
	public ScriptBuffer appendSepValueChar() {
		return this.append(SEP_VALUE_CHAR);
	}
	
	public ScriptBuffer appendQuoteChar() {
		return this.append(APOS_CHAR);
	}
	
	public ScriptBuffer appendEndLigneChar() {
		return this.append(END_LIGNE_CHAR);
	}
	
	public ScriptBuffer appendToMap(String name, JsVar object) {
		this.append(name);
		this.append(KEY_MAP_CHAR);
		object.append(this);
		return this;
	}
	
	public StringBuffer getHeadBuffer() {
		return headBuffer;
	}
	
	public StringBuffer getBodyBuffer() {
		return bodyBuffer;
	}
}
