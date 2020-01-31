package tn.com.smartsoft.framework.presentation.view.window.component.ext.menu.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.menu.UIExtMenu;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;

public class UIExtMenuRender extends UIExtComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsMap menuConfig = new JsMap();

	public UIExtMenuRender(UIExtMenu renderComponent) {
		super(renderComponent, "Ext.menu.Menu");
		 
	}

	public void render(UIRenderContext context) {
		UIExtMenu extMenu = (UIExtMenu) renderComponent;
		if (!extMenu.isRendred())
			return;

		renderChilds(context);
		renderEnd(context);
	}

	public void renderEnd(UIRenderContext context) {
		UIExtMenu extMenu = (UIExtMenu) renderComponent;
		UIEventRenderUtils.renderEvents(context, extMenu, getParentRender(), extComponentJs);
		extComponentJs().addStringValue("id", extMenu.getId());
		context.addComponentsToBufferJs(extComponentJs.newInstance());

	}

	public void renderChilds(UIRenderContext context) {
		UIExtMenu extMenu = (UIExtMenu) renderComponent;
		ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(
				extMenu.getIconUrl());
		menuConfig.addStringValue("text", extMenu.getLibelle());
		menuConfig.addObjectValue("menu", extMenu.getId());
		menuConfig.addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
		for (int i = 0; i < extMenu.menuItemIds().size(); i++) {
			UIComponent child = extMenu.getMenuItem((String) extMenu.menuItemIds().get(i));
			if (child instanceof UIRendrableComponent && ((UIRendrableComponent) child).isRendred()) {
				UIRendrableComponent rendrableComponent = (UIRendrableComponent) child;
				UIRender uiRender = (UIRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context);
				if (child instanceof UIExtComponent) {
					extComponentJs().addItem(((UIExtComponentRender) uiRender).extComponentJs());
				} else if (child instanceof UIExtMenu) {
					extComponentJs().addItem(menuConfig);
				}
			}
		}
	}
}
