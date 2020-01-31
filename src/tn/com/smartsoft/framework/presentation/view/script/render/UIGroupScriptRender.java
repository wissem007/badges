package tn.com.smartsoft.framework.presentation.view.script.render;

import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;
import tn.com.smartsoft.framework.presentation.view.script.UIGroupScript;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UIGroupScriptRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIGroupScriptRender(UIGroupScript renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIGroupScript javaScript = (UIGroupScript) renderComponent;
		GroupScriptDefinition group = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getGroupScriptDefinition(javaScript.getGroupName());
		for (int j = 0; j < group.getDeepCssNames().size(); j++) {
			context.renderCss(String.valueOf(group.getDeepCssNames().get(j)), getTabRender());
		}
		for (int j = 0; j < group.getDeepJsNames().size(); j++) {
			context.renderJs(String.valueOf(group.getDeepJsNames().get(j)), getTabRender());
		}

	}

}
