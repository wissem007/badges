package tn.com.smartsoft.framework.presentation.view.tags;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.configuration.definition.IDefinition;

public class UITags implements IDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, UITag> uiTags = new HashMap<String, UITag>();

	public UITags() {
		super();
	}

	public void addTag(UITag uiTag) {
		uiTags.put(uiTag.getTag(), uiTag);
	}

	public void addTags(UITags uiTag) {
		uiTags.putAll(uiTag.uiTags);
	}

	public UITag getTag(String name) {
		UITag uiTag = (UITag) uiTags.get(name);
		return uiTag != null ? uiTag : new UITag();
	}
}
