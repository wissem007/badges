package tn.com.smartsoft.commons.web.extjs;

public class JsValue implements JsVar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;
	private boolean expected;
	
	private JsValue(Object value, boolean expected) {
		super();
		this.value = value;
		this.expected = expected;
	}
	
	public static JsValue newString(Object value) {
		return new JsValue(value, true);
	}
	
	public static JsValue newObject(Object value) {
		return new JsValue(value, false);
	}
	
	public boolean isNull() {
		return value == null;
	}
	
	public void append(ScriptBuffer sb) {
		if (isNull())
			return;
		if (expected) {
			sb.appendQuoteChar();
			sb.appendAndEncodeValue(value.toString());
			sb.appendQuoteChar();
		} else if (value instanceof JsVar) {
			((JsVar) value).append(sb);
		} else
			sb.append(value.toString());
		return;
	}
}