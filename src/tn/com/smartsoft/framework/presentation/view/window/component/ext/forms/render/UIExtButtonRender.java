package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render;

import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtButton;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericBoxComponentRender;
import tn.com.smartsoft.iap.dictionary.graphique.actionTemplate.beans.ActionTemplateBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewActionBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;

public class UIExtButtonRender extends UIExtGenericBoxComponentRender{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public UIExtButtonRender(UIExtButton renderComponent) {
		super(renderComponent, "Ext.Button");
	}
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtButton uiButton = (UIExtButton) renderComponent;
		ActionTemplateBean actionTemplateBean = null;
		if (StringUtils.isNotBlank(uiButton.getViewActionId())) {
			ViewBean viewBean = uiButton.getWindow().viewBean();
			ViewActionBean viewActionBean = viewBean.getViewAction(uiButton.getViewActionId());
			if (viewActionBean != null) {
				actionTemplateBean = context.getApplicationCacheDictionaryManager().getActionTemplateBean(viewActionBean.getActionTemplateId());
			}
		} else if (uiButton.getActionTemplateId() != null) {
			actionTemplateBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getActionTemplateBean(uiButton.getActionTemplateId());
		}
		String text = uiButton.getText();
		String icon = uiButton.getIconPath();
		if (actionTemplateBean != null) {
			text = actionTemplateBean.getLibelle();
			WebDefinition webDefinition = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition();
			ImageDefinition imageDefinition = webDefinition.getImageDefinition(actionTemplateBean.getIconUrl());
			if (imageDefinition != null) icon = imageDefinition.getPath();
		}
		if (uiButton.isLabel()) {
			extComponentJs().addStringValue("text", text);
		}
		if (uiButton.isIcon() && StringUtils.isNotBlank(icon)) {
			extComponentJs().addStringValue("icon", icon);
		}
	}
}
