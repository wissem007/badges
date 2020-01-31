package tn.com.smartsoft.framework.presentation.view.desktop.menubars.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuAction;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuItem;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuNode;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIMenuItemRender extends UIRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIExtComponentJs componentJs;
	
	public UIMenuItemRender(UIMenuItem renderComponent) {
		super(renderComponent);
	}
	
	public void render(UIRenderContext context) {
		UIMenuItem menuItem = (UIMenuItem) renderComponent;
		if (!menuItem.isRendred())
			return;
		if (menuItem.getMenuActions().size() == 1) {
			componentJs = new UIExtComponentJs("Ext.menu.Item", "menuItem" + menuItem.getId());
			UIMenuAction menuAction = (UIMenuAction) menuItem.getMenuActions().getMenus().get(0);
			componentJs.addStringValue("text", menuItem.getLibelle());
			ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(menuAction.getIconUrl());
			componentJs.addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
			UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, menuItem.getEventHandeler(), componentJs);
			context.addComponentsToBufferJs(componentJs.newInstance());
			((UIMenuRender) getParentRender()).getComponentJs().addItem(componentJs);
			if (getParentRender() instanceof UIMenuBarRender) {
				context.addMenu(componentJs.getConfigs());
			} else if (getParentRender() instanceof UIMenuRender)
				((UIMenuRender) getParentRender()).getComponentJs().addItem(componentJs);
		} else {
			componentJs = new UIExtComponentJs("Ext.menu.Menu", menuItem.getMenuName());
			for (int i = 0; i < menuItem.getMenuActions().size(); i++) {
				UIMenuNode menuChild = menuItem.getMenuActions().getMenu(i);
				UIRender render = menuChild.getRender();
				render.setParentRender(this);
				render.render(context);
			}
			JsMap jsMap = new JsMap();
			jsMap.addStringValue("text", menuItem.getLibelle());
			jsMap.addObjectValue("menu", menuItem.getMenuName());
			ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(menuItem.getIconUrl());
			jsMap.addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
			if (getParentRender() instanceof UIMenuRender) {
				UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, menuItem.getEventHandeler(), jsMap);
			}
			context.addComponentsToBufferJs(componentJs.newInstance());
			jsMap.addObjectValue("menu", componentJs.getInstanceName());
			if (getParentRender() instanceof UIMenuBarRender) {
				context.addMenu(jsMap);
			} else if (getParentRender() instanceof UIMenuRender) {
				((UIMenuRender) getParentRender()).getComponentJs().addItem(jsMap);
			}
		}
		
	}
	
	public UIExtComponentJs getComponentJs() {
		return componentJs;
	}
}
