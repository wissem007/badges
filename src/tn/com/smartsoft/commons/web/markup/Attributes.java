package tn.com.smartsoft.commons.web.markup;

public interface Attributes {

	public static final String NO_ATTRIBUTE_VALUE = "ECS_NO_ATTRIBUTE_VALUE";

	public Element setAttributeFilterState(boolean filter_attribute_state);

	public Element setAttributeFilter(Filter attribute_filter);

	public Filter getAttributeFilter();

	public Element addAttribute(String name, Object element);

	public Element addAttribute(String name, int element);

	public Element addAttribute(String name, String element);

	public Element addAttribute(String name, Integer element);

	public Element removeAttribute(String name);

	public boolean hasAttribute(String name);

	public Element setAttributeQuoteChar(char quote_char);

	public char getAttributeQuoteChar();

	public Element setAttributeEqualitySign(char equality_sign);

	public char getAttributeEqualitySign();

	public boolean getAttributeQuote();

	public Element setAttributeQuote(boolean attribute_quote);
}
