package tn.com.smartsoft.framework.presentation.view.script.builder;

import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;

public class ProdGroupBuilder extends AbstractGroupBuilder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected boolean mustBuildCssGroup(GroupScriptDefinition group) {
		return (group.getLastCssLoadTime() == 0);
	}

	protected boolean mustBuildJsGroup(GroupScriptDefinition group) {
		return (group.getLastJsLoadTime() == 0);
	}
}
