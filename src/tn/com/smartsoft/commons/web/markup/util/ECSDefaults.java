package tn.com.smartsoft.commons.web.markup.util;

import java.util.ResourceBundle;

public final class ECSDefaults {

	private static ECSDefaults defaults = new ECSDefaults();

	// now follows the private methods called by the
	private ResourceBundle resource;

	// assign original default values in case the props can't be loaded.
	private boolean filter_state = false;
	private boolean filter_attribute_state = false;
	private char attribute_equality_sign = '=';
	private char begin_start_modifier = ' ';
	private char end_start_modifier = ' ';
	private char begin_end_modifier = ' ';
	private char end_end_modifier = ' ';
	private char attribute_quote_char = '\"';
	private boolean attribute_quote = true;
	private boolean end_element = true;
	private String codeset = "UTF-8";
	private int position = 4;
	private int case_type = 3;
	private char start_tag = '<';
	private char end_tag = '>';
	private boolean pretty_print = false;

	public static boolean getDefaultFilterState() {
		return defaults.filter_state;
	}

	public static boolean getDefaultFilterAttributeState() {
		return defaults.filter_attribute_state;
	}

	public static char getDefaultAttributeEqualitySign() {
		return defaults.attribute_equality_sign;
	}

	public static char getDefaultBeginStartModifier() {
		return defaults.begin_start_modifier;
	}

	public static char getDefaultEndStartModifier() {
		return defaults.end_start_modifier;
	}

	public static char getDefaultBeginEndModifier() {
		return defaults.begin_end_modifier;
	}

	public static char getDefaultEndEndModifier() {
		return defaults.end_end_modifier;
	}

	public static char getDefaultAttributeQuoteChar() {
		return defaults.attribute_quote_char;
	}

	public static boolean getDefaultAttributeQuote() {
		return defaults.attribute_quote;
	}

	public static boolean getDefaultEndElement() {
		return defaults.end_element;
	}

	public static String getDefaultCodeset() {
		return defaults.codeset;
	}

	public static int getDefaultPosition() {
		return defaults.position;
	}

	public static int getDefaultCaseType() {
		return defaults.case_type;
	}

	public static char getDefaultStartTag() {
		return defaults.start_tag;
	}

	public static char getDefaultEndTag() {
		return defaults.end_tag;
	}

	public static boolean getDefaultPrettyPrint() {
		return defaults.pretty_print;
	}

}
