package tn.com.smartsoft.framework.presentation.view.script.render;

import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;
import tn.com.smartsoft.framework.presentation.view.script.UICssScript;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;

public class UICssScriptRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UICssScriptRender(UICssScript renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UICssScript javaScript = (UICssScript) renderComponent;
		GroupScriptDefinition group = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getGroupScriptDefinition(
				javaScript.getGroupName());
		for (int j = 0; j < group.getDeepCssNames().size(); j++) {
			context.renderCss(String.valueOf(group.getDeepCssNames().get(j)), getTabRender());
		}
	}

}
