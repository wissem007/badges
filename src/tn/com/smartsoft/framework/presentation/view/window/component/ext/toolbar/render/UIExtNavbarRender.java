package tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.render;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.view.utils.UIRenderContextUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericContainerRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.UIExtNavbar;
import tn.com.smartsoft.framework.utils.ApplicationCacheUtils;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;

public class UIExtNavbarRender extends UIExtGenericContainerRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIExtNavbarRender(UIExtNavbar renderComponent) {
		super(renderComponent, "Ext.ux.NavToolbar");
	}
	
	@Override
	public void renderStart(UIRenderContext context) {
		this.loadConfigs(context);
	}
	
	@Override
	public void renderChilds(UIRenderContext context) {
		
	}
	
	@Override
	public void renderEnd(UIRenderContext context) {
		//UIExtComponent extComponent = (UIExtComponent) renderComponent;
		//UIEventRenderUtils.renderEvents(context, extComponent, getParentRender(), extComponentJs);
		//context.addComponentsToBufferJs(extComponentJs.newInstance());
	}
	
	@Override
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtNavbar extRenderComponent = (UIExtNavbar) renderComponent;
		boolean isDisabledLast = false;
		boolean isDisabledFirst = false;
		if (StringUtils.isNotBlank(extRenderComponent.getListProperty()) && StringUtils.isNotBlank(extRenderComponent.getIndexProperty())) {
			List<?> list = (List<?>) context.webContext().userDesktop().curentUserAction().getModel().getValue(extRenderComponent.getListProperty());
			int index = (Integer) context.webContext().userDesktop().curentUserAction().getModel().getValue(extRenderComponent.getIndexProperty());
			if (list == null || list.size() == 0) {
				isDisabledLast = true;
				isDisabledFirst = true;
			} else {
				isDisabledLast = (list.size() - 1 == index);
				isDisabledFirst = (0 == index);
			}
		}
		extComponentJs().addBooleanValue("isLabelAction", extRenderComponent.isLabelAction());
		extComponentJs().addStringValue("waitMsg", "Requéte en cours de traitement...");
		addActionConfig(context, new Long(20), "doFirst", isDisabledFirst);
		addActionConfig(context, new Long(18), "doPrevious", isDisabledFirst);
		addActionConfig(context, new Long(19), "doNext", isDisabledLast);
		addActionConfig(context, new Long(21), "doLast", isDisabledLast);
		context.addNavBarItem(extComponentJs().getConfigs());
	}
	
	public void addActionConfig(UIRenderContext context, Long actionTplId, String event, boolean disabled) {
		JsMap actionConfig = new JsMap();
		ActionTemplateBean actionTemplateBean = null;
		actionTemplateBean = ApplicationCacheUtils.dictionaryManager().getActionTemplateBean(actionTplId);
		if (actionTemplateBean != null) {
			actionConfig.addStringValue("label", actionTemplateBean.getLibelle());
			ImageDefinition imageDefinition = UIRenderContextUtils.webDefinition().getImageDefinition(actionTemplateBean.getIconUrl());
			if (imageDefinition != null)
				actionConfig.addStringValue("icon", imageDefinition.getPath());
		} else {
			actionConfig.addStringValue("text", "invalid ViewActionId :" + actionTplId);
		}
		actionConfig.addStringValue("action", event);
		actionConfig.addBooleanValue("disabled", disabled);
		// context.getDesktopJs().getConfigs().addObjectValue(event + "ActionConfig", actionConfig.toJs());
		extComponentJs().addObjectValue(event + "ActionConfig", actionConfig.toJs());
	}
}
