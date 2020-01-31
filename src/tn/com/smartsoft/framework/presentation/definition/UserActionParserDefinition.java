package tn.com.smartsoft.framework.presentation.definition;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;

public class UserActionParserDefinition implements ParserDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> params = new HashMap<String, String>();
	static Pattern PATTERN = Pattern.compile("(\\$AP\\{)(\\w+)(\\})");

	public String getVariableSystem(String name) {
		return params.get(name);
	}

	public void addParam(String name, String value) {
		params.put(name, value);
	}

	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this, PATTERN);
	}
}
