package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtPanel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.UIExtToolbar;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;

public class UIExtPanelRender extends UIExtGenericContainerRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtPanelRender(UIExtPanel renderComponent, String jsObjectName) {
		super(renderComponent, jsObjectName);
	}

	public UIExtPanelRender(UIExtPanel renderComponent) {
		this(renderComponent, "Ext.Panel");
	}

	public void afterRender(UIRenderContext context) {
		super.afterRender(context);
		UIExtPanel extPanel = (UIExtPanel) renderComponent;
		UIExtToolbar bBar = extPanel.getBottomBar();
		UIExtToolbar tBar = extPanel.getTopBar();
		UIExtToolbar navbar = extPanel.getNavbar();
		if (bBar != null && bBar.isRendred()) {
			UIExtComponentRender bBarRender = (UIExtComponentRender) bBar.getRender();
			bBarRender.setParentRender(this);
			bBarRender.render(context);
			extComponentJs().setBottomBar(bBarRender.extComponentJs());
		}
		if (tBar != null && tBar.isRendred()) {
			UIExtComponentRender tBarRender = (UIExtComponentRender) tBar.getRender();
			tBarRender.setParentRender(this);
			tBarRender.render(context);
			extComponentJs().setTopBar(tBarRender.extComponentJs());
		}
		if (navbar != null && navbar.isRendred()) {
			UIExtComponentRender navbarrRender = (UIExtComponentRender) navbar.getRender();
			navbarrRender.setParentRender(this);
			navbarrRender.render(context);
		}
		renderButtonTools(context);
	}

	public void renderButtonTools(UIRenderContext context) {
		UIExtPanel extPanel = (UIExtPanel) renderComponent;
		List<String> buttonIds = extPanel.getButtonIds();
		for (int i = 0; i < buttonIds.size(); i++) {
			UIExtButton extButton = (UIExtButton) extPanel.getButton((String) buttonIds.get(i));
			if (!extButton.isRendred())
				break;
			UIExtComponentRender extButtonRender = (UIExtComponentRender) extButton.getRender();
			extButtonRender.setParentRender(this);
			extButtonRender.render(context);
			extComponentJs().addButton(extButtonRender.extComponentJs());
		}
		buttonIds = extPanel.getToolIds();
		for (int i = 0; i < buttonIds.size(); i++) {
			UIExtButton extButton = (UIExtButton) extPanel.getTool((String) buttonIds.get(i));
			if (!extButton.isRendred())
				break;
			String icone = extButton.getIconPath();
			String qtip = "";
			ActionTemplateBean actionTemplateBean = null;
			if (StringUtils.isNotBlank(extButton.getViewActionId())) {
				ViewBean viewBean = extButton.getWindow().viewBean();
				ViewActionBean viewActionBean = viewBean.getViewAction(extButton.getViewActionId());
				if (viewActionBean != null) {
					actionTemplateBean = context.getApplicationCacheDictionaryManager().getActionTemplateBean(viewActionBean.getActionTemplateId());
				}
			} else if (extButton.getActionTemplateId() != null) {
				actionTemplateBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getActionTemplateBean(extButton.getActionTemplateId());
			}
			if (actionTemplateBean != null) {
				qtip = actionTemplateBean.getLibelle();
				ImageDefinition imageDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getImageDefinition(actionTemplateBean.getIconUrl());
				if (imageDefinition != null && StringUtils.isEmpty(icone))
					icone = imageDefinition.getPath();

			} else {
				extComponentJs().addStringValue("text", "invalid ViewActionId :" + extButton.getViewActionId());
			}
			JsMap jsMap = new JsMap();
			jsMap.addStringValue("id", extButton.getId());
			jsMap.addStringValue("qtip", qtip);
			context.createToolSheet(icone, extButton.getId());
			UIEventRenderUtils.renderClickEventToHandelConfig(context, extButton, extButton.getRender(), jsMap);
			extComponentJs().addTools(jsMap);
		}
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtPanel extRenderComponent = (UIExtPanel) renderComponent;
		extComponentJs().addStringValue("title", extRenderComponent.getTitle());
		extComponentJs().addObjectValue("border", extRenderComponent.isBorder());
		extComponentJs().addObjectValue("frame", extRenderComponent.isFrame());
		extComponentJs().addStringValue("bodyStyle", extRenderComponent.getBodyStyle());
		extComponentJs().addStringValue("buttonAlign", extRenderComponent.getButtonAlign());
		extComponentJs().addStringValue("labelAlign", extRenderComponent.getLabelAlign());
		extComponentJs().addValue("labelWidth", extRenderComponent.getLabelWidth());
	}

}
