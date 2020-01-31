package tn.com.smartsoft.commons.web.markup;

import java.util.Enumeration;

import tn.com.smartsoft.commons.web.markup.util.ECSDefaults;

public abstract class ElementAttributes extends GenericElement implements Attributes {

	public ElementAttributes() {
	}

	private Filter attribute_filter = getFilter(); // By default the attribute

	private boolean filter_attribute_state = ECSDefaults.getDefaultFilterAttributeState();

	private char attribute_equality_sign = ECSDefaults.getDefaultAttributeEqualitySign();

	private char attribute_quote_char = ECSDefaults.getDefaultAttributeQuoteChar();

	private boolean attribute_quote = ECSDefaults.getDefaultAttributeQuote();

	public Element setAttributeQuoteChar(char quote_char) {
		attribute_quote_char = quote_char;
		return (this);
	}

	public char getAttributeQuoteChar() {
		return (attribute_quote_char);
	}

	public Element setAttributeEqualitySign(char equality_sign) {
		attribute_equality_sign = equality_sign;
		return (this);
	}

	public char getAttributeEqualitySign() {
		return (attribute_equality_sign);
	}

	public boolean getAttributeQuote() {
		return (attribute_quote);
	}

	public Element setAttributeQuote(boolean attribute_quote) {
		this.attribute_quote = attribute_quote;
		return (this);
	}

	public Element setID(String id) {
		addAttribute("id", id);
		return (this);
	}

	public Element setClass(String element_class) {
		addAttribute("class", element_class);
		return (this);
	}

	public Element setLang(String lang) {
		addAttribute("lang", lang);
		return (this);
	}

	public Element setStyle(String style) {
		addAttribute("style", style);
		return (this);
	}

	public Element setDir(String dir) {
		addAttribute("dir", dir);
		return (this);
	}

	public Element setTitle(String title) {
		addAttribute("title", title);
		return (this);
	}

	public boolean getAttributeFilterState() {
		return (filter_attribute_state);
	}

	public Element setAttributeFilterState(boolean filter_attribute_state) {
		this.filter_attribute_state = filter_attribute_state;
		return (this);
	}

	public Element setAttributeFilter(Filter attribute_filter) {
		filter_attribute_state = true; // If your setting up a filter you must
		// want to filter.
		this.attribute_filter = attribute_filter;
		return (this);
	}

	public Filter getAttributeFilter() {
		return (this.attribute_filter);
	}

	public Element addAttribute(String attribute_name, Object attribute_value) {
		if (attribute_value != null)
			getElementHashEntry().put(attribute_name, attribute_value);
		return (this);
	}

	public Element addAttribute(String attribute_name, int attribute_value) {
		getElementHashEntry().put(attribute_name, new Integer(attribute_value));
		return (this);
	}

	public Element addAttribute(String attribute_name, String attribute_value) {
		if (attribute_value != null)
		getElementHashEntry().put(attribute_name, attribute_value);
		return (this);
	}

	public Element addAttribute(String attribute_name, Integer attribute_value) {
		if (attribute_value != null)
		getElementHashEntry().put(attribute_name, attribute_value);
		return (this);
	}

	public Element removeAttribute(String attribute_name) {
		try {
			getElementHashEntry().remove(attribute_name);
		} catch (Exception e) {}
		return (this);
	}

	public boolean hasAttribute(String attribute) {
		return (getElementHashEntry().containsKey(attribute));
	}

	public Enumeration attributes() {
		return getElementHashEntry().keys();
	}

	public String getAttribute(String attribute) {
		return (String) getElementHashEntry().get(attribute);
	}

	public String createStartTag() {

		StringBuffer out = new StringBuffer();
		out.append(getStartTagChar());

		if (getBeginStartModifierDefined()) {
			out.append(getBeginStartModifier());
		}
		out.append(getElementType());

		Enumeration enume = getElementHashEntry().keys();
		String value = null; // avoid creating a new string object on each
		// pass through the loop

		while (enume.hasMoreElements()) {
			String attr = (String) enume.nextElement();
			if (getAttributeFilterState()) {
				value = getAttributeFilter().process(getElementHashEntry().get(attr).toString());
			} else {
				Object valueObj = getElementHashEntry().get(attr);
				if (valueObj == null) {
					return null;
				}
				value = valueObj.toString();
			}
			out.append(' ');
			out.append(alterCase(attr));
			int iStartPos = out.length();
			if (!value.equalsIgnoreCase(NO_ATTRIBUTE_VALUE)) {
				// we have a value
				// we might still enclose in quotes
				boolean quoteThisAttribute = attribute_quote;
				int singleQuoteFound = 0;
				int doubleQuoteFound = 0;
				if (value.length() == 0) { // quote a null string
					quoteThisAttribute = true;
				}
				for (int ii = 0; ii < value.length(); ii++) {
					char c = value.charAt(ii);
					if ('a' <= c && c <= 'z') {
						continue;
					}
					if ('A' <= c && c <= 'Z') {
						continue;
					}
					if ('0' <= c && c <= '9') {
						continue;
					}
					if (c == ':' || c == '-' || c == '_' || c == '.') {
						continue;
					}
					if (c == '\'') {
						singleQuoteFound++;
					}
					if (c == '"') {
						doubleQuoteFound++;
					}
					quoteThisAttribute = true;
				}
				out.append(getAttributeEqualitySign());
				if (!quoteThisAttribute) { // no need to append quotes
					out.append(value);
				} else { // use single quotes if possible
					if (singleQuoteFound == 0) { // no single quotes, safe to
						// use them to quote
						out.append('\'');
						out.append(value);
						out.append('\'');
					} else if (doubleQuoteFound == 0) { // no double quotes,
						// safe to use them to
						// quote
						out.append('"');
						out.append(value);
						out.append('"');
					} else if (singleQuoteFound <= doubleQuoteFound) { // use
						// single
						// quotes,
						// convert
						// embedded
						// single
						// quotes
						out.append('\'');
						int startPos = out.length();
						out.append(value);
						for (int ii = startPos; ii < out.length(); ii++) { // note
							// out.length()
							// may
							// change
							// during
							// processing
							if (out.charAt(ii) == '\'') {
								out.setCharAt(ii, '&');
								out.insert(ii + 1, "#39;");
								ii++;
							}
						}
						out.append('\'');
					} else { // use double quotes, convert embedded double
						// quotes
						out.append('"');
						int startPos = out.length();
						out.append(value);
						for (int ii = startPos; ii < out.length(); ii++) { // note
							// out.length()
							// may
							// change
							// during
							// processing
							if (out.charAt(ii) == '"') {
								out.setCharAt(ii, '&');
								out.insert(ii + 1, "#34;");
								ii++;
							}
						}
						out.append('"');
					}
				}
			}
		}
		if (getBeginEndModifierDefined()) {
			out.append(getBeginEndModifier());
		}
		out.append(getEndTagChar());

		return (out.toString());
	}
}
