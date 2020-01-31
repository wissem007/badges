package tn.com.smartsoft.framework.presentation.view.desktop.menubars.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UIMenuNode;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UISocieteMenu;
import tn.com.smartsoft.framework.presentation.view.desktop.menubars.UISplitButton;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;

public class UISocieteMenuRender extends UISplitButtonRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UISocieteMenuRender(UISocieteMenu renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UISplitButton menu = (UISplitButton) renderComponent;
		if (!menu.isRendred())
			return;
		if (menu.getMenus().size() == 1) {
			componentJs = new UIExtComponentJs("Ext.Button", menu.getId());
			componentJs.addStringValue("text", menu.getLibelle());
			if (StringUtils.isNotBlank(menu.getIconUrl())) {
				ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(menu.getIconUrl());
				componentJs.addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
			}
			context.addComponentsToBufferJs(componentJs.newInstance());
			if (getParentRender() instanceof UIMenuBarRender) {
				context.addObjectAction(componentJs.getInstanceName());
			} else if (getParentRender() instanceof UIMenuRender)
				parentComponentJs.addItem(componentJs.getInstanceName());
			return;
		}
		componentJs = new UIExtComponentJs("Ext.menu.Menu", menu.getMenuName());
		UIExtComponentJs componentSplitJs = new UIExtComponentJs("Ext.SplitButton", menu.getId());
		componentSplitJs.addStringValue("text", menu.getLibelle());
		if (StringUtils.isNotBlank(menu.getIconUrl())) {
			ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(menu.getIconUrl());
			componentSplitJs.addStringValue("icon", imageDefinition != null ? imageDefinition.getPath() : null);
		}
		componentSplitJs.addStringValue("menuAlign", menu.getMenuAlign());
		componentSplitJs.addObjectValue("menu", componentJs.getInstanceName());
		for (int i = 0; i < menu.getMenus().size(); i++) {
			UIMenuNode menuChild = menu.getMenus().get(i);
			UIRender render = menuChild.getRender();
			render.setParentRender(this);
			render.render(context);
		}
		context.addComponentsToBufferJs(componentJs.newInstance());
		context.addComponentsToBufferJs(componentSplitJs.newInstance());
		if (getParentRender() instanceof UIMenuBarRender) {
			context.addObjectAction(componentSplitJs.getInstanceName());
		} else if (getParentRender() instanceof UIMenuRender)
			parentComponentJs.addItem(componentSplitJs.getInstanceName());
	}
}
