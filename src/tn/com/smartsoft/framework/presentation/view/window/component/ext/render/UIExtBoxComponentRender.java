package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;

public class UIExtBoxComponentRender extends UIExtGenericBoxComponentRender{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public UIExtBoxComponentRender(UIExtBoxComponent renderComponent) {
		super(renderComponent, "Ext.BoxComponent");
	}
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtBoxComponent extRenderComponent = (UIExtBoxComponent) renderComponent;
		if (extRenderComponent.getAutoEl() != null) {
			JsMap autoElMap = new JsMap();
			String[] atts = extRenderComponent.getAutoEl().geAttributesName();
			for (int i = 0; i < atts.length; i++) {
				autoElMap.addStringValue(atts[i], extRenderComponent.getAutoEl().getAttribut(atts[i]));
			}
			extComponentJs().addObjectValue("autoEl", autoElMap);
		} else {
			extComponentJs().addStringValue("autoEl", extRenderComponent.getTag());
			if (StringUtils.isNotBlank(extRenderComponent.getHtml()))
			extComponentJs().addStringValue("html", StringUtils.trim(extRenderComponent.getWindow().evalExpressionToString(extRenderComponent.getHtml())));
		}
	}
}
