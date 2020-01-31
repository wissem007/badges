package tn.com.smartsoft.framework.presentation.view.window.component.ext.menu.render;

import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.menu.UIExtMenuAction;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;

public class UIExtMenuActionRender extends UIExtComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtMenuActionRender(UIExtMenuAction renderComponent) {
		super(renderComponent, "Ext.menu.Item");
	}

	public void loadConfigs(UIRenderContext context) {
		UIExtMenuAction menuAction = (UIExtMenuAction) renderComponent;
		ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(
				menuAction.getIconUrl());
		extComponentJs().addStringValue("text", menuAction.getLibelle());
		extComponentJs().addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
	}

	public void renderChilds(UIRenderContext context) {

	}
}
