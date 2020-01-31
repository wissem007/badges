package tn.com.smartsoft.commons.web.utils;

import java.util.Iterator;
import java.util.Map;

public class HtmlStringBuffer {

	static final String[] JS_ATTRIBUTES = { "onload", "onunload", "onclick", "ondblclick", "onmousedown", "onmouseup", "onmouseover", "onmousemove", "onmouseout",
			"onfocus", "onblur", "onkeypress", "onkeydown", "onkeyup", "onsubmit", "onreset", "onselect", "onchange" };
	protected char[] characters;
	protected int count;

	public HtmlStringBuffer(int length) {
		characters = new char[length];
	}

	public HtmlStringBuffer() {
		characters = new char[128];
	}

	public HtmlStringBuffer append(double value) {
		append(String.valueOf(value));

		return this;
	}

	public HtmlStringBuffer append(char value) {
		int newcount = count + 1;
		if (newcount > characters.length) {
			expandCapacity(newcount);
		}
		characters[count++] = value;

		return this;
	}

	public HtmlStringBuffer append(int value) {
		append(String.valueOf(value));

		return this;
	}

	public HtmlStringBuffer append(long value) {
		append(String.valueOf(value));

		return this;
	}

	public HtmlStringBuffer append(Object value) {
		String string = String.valueOf(value);
		int length = string.length();

		int newCount = count + length;
		if (newCount > characters.length) {
			expandCapacity(newCount);
		}
		string.getChars(0, length, characters, count);
		count = newCount;

		return this;
	}

	public HtmlStringBuffer append(String value) {
		String string = (value != null) ? value : "null";
		int length = string.length();

		int newCount = count + length;
		if (newCount > characters.length) {
			expandCapacity(newCount);
		}
		string.getChars(0, length, characters, count);
		count = newCount;

		return this;
	}

	public HtmlStringBuffer appendEscaped(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Null value parameter");
		}
		append(HtmlEncodeUtil.encodeHtml(value));
		return this;
	}

	public HtmlStringBuffer appendAttribute(String name, Object value) {
		if (name == null) {
			throw new IllegalArgumentException("Null name parameter");
		}

		if (value != null) {
			append(" ");
			append(name);
			append("=\"");
			append(value);
			append("\"");
		}

		return this;
	}

	public HtmlStringBuffer appendAttributeEscaped(String name, Object value) {
		if (name == null) {
			throw new IllegalArgumentException("Null name parameter");
		}
		if (value != null) {
			append(" ");
			append(name);
			append("=\"");
			if (isJavaScriptAttribute(name)) {
				append(value);
			} else {
				appendEscaped(value.toString());
			}
			append("\"");
		}

		return this;
	}

	public HtmlStringBuffer appendAttribute(String name, int value) {
		if (name == null) {
			throw new IllegalArgumentException("Null name parameter");
		}
		append(" ");
		append(name);
		append("=\"");
		append(value);
		append("\"");

		return this;
	}

	public HtmlStringBuffer appendAttributeDisabled() {
		append(" disabled=\"disabled\"");

		return this;
	}

	public HtmlStringBuffer appendAttributeReadonly() {
		append(" readonly=\"readonly\"");

		return this;
	}

	public HtmlStringBuffer appendAttributes(Map attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("Null attributes parameter");
		}
		for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			String name = entry.getKey().toString();

			if (!name.equals("id")) {
				Object value = entry.getValue();
				appendAttributeEscaped(name, value);
			}
		}

		return this;
	}

	public HtmlStringBuffer appendStyleAttributes(Map attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("Null attributes parameter");
		}

		if (!attributes.isEmpty()) {
			append(" style=\"");

			for (Iterator i = attributes.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				String name = entry.getKey().toString();
				String value = entry.getValue().toString();

				append(name);
				append(":");
				append(value);
				append(";");
			}

			append("\"");
		}

		return this;
	}

	public HtmlStringBuffer elementEnd(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Null name parameter");
		}
		append("</");
		append(name);
		append(">");

		return this;
	}

	public HtmlStringBuffer closeTag() {
		append(">");

		return this;
	}

	public HtmlStringBuffer elementEnd() {
		append("/>");

		return this;
	}

	public HtmlStringBuffer elementStart(String name) {
		append("<");
		append(name);

		return this;
	}

	public boolean isJavaScriptAttribute(String name) {
		if (name.length() < 6 || name.length() > 11) {
			return false;
		}

		if (!name.startsWith("on")) {
			return false;
		}

		for (int i = 0; i < JS_ATTRIBUTES.length; i++) {
			if (JS_ATTRIBUTES[i].equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}

	public int length() {
		return count;
	}

	public String toString() {
		return new String(characters, 0, count);
	}

	protected void expandCapacity(int minimumCapacity) {
		int newCapacity = (characters.length + 1) * 2;

		if (newCapacity < 0) {
			newCapacity = Integer.MAX_VALUE;
		} else if (minimumCapacity > newCapacity) {
			newCapacity = minimumCapacity;
		}

		char newValue[] = new char[newCapacity];
		System.arraycopy(characters, 0, newValue, 0, count);
		characters = newValue;
	}

}
