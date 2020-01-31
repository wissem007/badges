package tn.com.smartsoft.framework.presentation.view.tags.parser;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;
import tn.com.smartsoft.framework.presentation.definition.UserActionParserDefinition;

public class UIParserParameter implements ParserDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> parameter;
	private ParserDefinition parserDefinition;

	public UIParserParameter(Map<String, String> parameter, ParserDefinition parserDefinition) {
		super();
		this.parameter = parameter;
		this.parserDefinition = parserDefinition;
	}

	public UIParserParameter() {
		super();
		this.parameter = new HashMap<String, String>();
		this.parserDefinition = new UserActionParserDefinition();
	}

	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this);
	}

	public String getVariableSystem(String name) {
		return parserDefinition.parse((String) parameter.get(name));
	}
}
