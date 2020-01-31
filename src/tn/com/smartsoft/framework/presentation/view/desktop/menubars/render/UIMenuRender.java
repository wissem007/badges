package tn.com.smartsoft.framework.presentation.view.desktop.menubars.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenu;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuNode;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIMenuRender extends UIRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected UIExtComponentJs componentJs;
	
	public UIMenuRender(UIMenu renderComponent) {
		super(renderComponent);
	}
	
	public void render(UIRenderContext context) {
		UIMenu menu = (UIMenu) renderComponent;
		if (!menu.isRendred())
			return;
		componentJs = new UIExtComponentJs("Ext.menu.Menu", menu.getMenuName());
		for (int i = 0; i < menu.getMenus().size(); i++) {
			UIMenuNode menuChild = menu.getMenus().get(i);
			UIRender render = menuChild.getRender();
			render.setParentRender(this);
			render.render(context);
		}
		JsMap jsMap = new JsMap();
		jsMap.addStringValue("text", menu.getLibelle());
		context.addComponentsToBufferJs(componentJs.newInstance());
		jsMap.addObjectValue("menu", componentJs.getInstanceName());
		if (getParentRender() instanceof UIMenuBarRender) {
			context.addMenu(jsMap);
		} else if (getParentRender() instanceof UIMenuRender)
			((UIMenuRender) getParentRender()).getComponentJs().addItem(jsMap);
	}
	
	public UIExtComponentJs getComponentJs() {
		return componentJs;
	}
}
