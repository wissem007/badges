package tn.com.smartsoft.framework.presentation.view.desktop.menubars.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.utils.HtmlEncodeUtil;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuAction;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIMenuActionRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIMenuActionRender(UIMenuAction renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UIMenuAction menu = (UIMenuAction) renderComponent;
		if (!menu.isRendred())
			return;
		String libelle = HtmlEncodeUtil.encodeHtml(menu.getLibelle());
		if (getParentRender() instanceof UIMenuItemRender) {
			UIExtComponentJs componentJs = new UIExtComponentJs("Ext.menu.Item", "menuItem" + menu.getId());
			ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(menu.getIconUrl());
			componentJs.addStringValue("text", menu.getLibelle());
			componentJs.addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
			UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, menu.getEventHandeler(), componentJs);
			context.addComponentsToBufferJs(componentJs.newInstance());
			((UIMenuItemRender) getParentRender()).getComponentJs().addItem(componentJs);
		} else if (getParentRender() instanceof UISplitButtonRender) {
			JsMap action = new JsMap();
			action.addStringValue("text", libelle);
			action.addStringValue("icon", UIRenderUtils.getImagePath(menu.getIconUrl()).getPath());
			UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, menu.getEventHandeler(), action);
			((UISplitButtonRender) getParentRender()).getComponentJs().addItem(action);
		}

	}

}
