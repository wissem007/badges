package tn.com.smartsoft.framework.presentation.definition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.view.tags.UIAbstractTagManager;

public class WebDefinition extends UIAbstractTagManager implements IDefinition, Serializable, ParserDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, GroupScriptDefinition> groupScripts = new HashMap<String, GroupScriptDefinition>();
	protected Map<Serializable, BeanDefinition> beansDefinition = new HashMap<Serializable, BeanDefinition>();
	private Map<String, String> variableSystem = new HashMap<String, String>();
	private Map<String, ImageDefinition> imagesDefinition = new HashMap<String, ImageDefinition>();
	private Map<String, ResponseViewDefinition> responseViewDefinition = new HashMap<String, ResponseViewDefinition>();

	public void addResponseViewDefinition(ResponseViewDefinition value) {
		responseViewDefinition.put(value.getName(), value);
	}

	public ResponseViewDefinition getResponseViewDefinition(String name) {
		return (ResponseViewDefinition) responseViewDefinition.get(name);
	}

	public void addBeanDefinition(BeanDefinition value) {
		beansDefinition.put(value.getId(), value);
	}

	public BeanDefinition getBeanDefinition(String name) {
		return (BeanDefinition) beansDefinition.get(name);
	}

	public void addGroupScriptDefinition(GroupScriptDefinition groupScrip) {
		groupScripts.put(groupScrip.getName(), groupScrip);
	}

	public void addImageDefinition(ImageDefinition imageDefinition) {
		imagesDefinition.put(imageDefinition.getId(), imageDefinition);
	}

	public ImageDefinition getImageDefinition(String id) {
		return (ImageDefinition) imagesDefinition.get(id);
	}

	public GroupScriptDefinition getGroupScriptDefinition(String name) {
		return (GroupScriptDefinition) groupScripts.get(name);
	}

	public void addGroupScriptsDefinition(GroupScriptsDefinition groupScrip) {

	}

	public void addWebBeansDefinition(WebBeansDefinition groupScrip) {

	}

	public void addImagesDefinition(ImagesDefinition imagesDefinition) {

	}

	public void addVariableSystem(String name, String value) {
		variableSystem.put(name, value);
	}

	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this);
	}

	public String getVariableSystem(String name) {
		return (String) variableSystem.get(name);
	}

}
