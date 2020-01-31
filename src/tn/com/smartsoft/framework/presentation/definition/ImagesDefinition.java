package tn.com.smartsoft.framework.presentation.definition;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class ImagesDefinition implements IDefinition, ParserDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> variableSystem = new HashMap<String, String>();
	private WebDefinition webDefinition;

	public void addVariableSystem(String name, String value) {
		variableSystem.put(name, value);
	}

	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this);
	}

	public String getVariableSystem(String name) {
		return (String) variableSystem.get(name);
	}

	public void addImageDefinition(ImageDefinition imageDefinition) {
		webDefinition.addImageDefinition(imageDefinition);
	}

	public void setWebDefinition(WebDefinition webDefinition) {
		this.webDefinition = webDefinition;
	}
}
