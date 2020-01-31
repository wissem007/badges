package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.commons.web.js.JSEncodeUtil;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtContainer;

public class UIExtContainerRender extends UIExtGenericContainerRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIExtContainerRender(UIExtContainer renderComponent, String jsObjectName) {
		super(renderComponent, jsObjectName);
	}
	
	public UIExtContainerRender(UIExtContainer renderComponent) {
		super(renderComponent, "Ext.Container");
	}
	
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtContainer extRenderComponent = (UIExtContainer) renderComponent;
		if (extRenderComponent.getAutoEl() != null) {
			JsMap autoElMap = new JsMap();
			String[] atts = extRenderComponent.getAutoEl().geAttributesName();
			for (int i = 0; i < atts.length; i++) {
				autoElMap.addStringValue(atts[i], extRenderComponent.getAutoEl().getAttribut(atts[i]));
			}
			extComponentJs().addObjectValue("autoEl", autoElMap);
		} else {
			extComponentJs().addStringValue("autoEl", extRenderComponent.getTag());
			extComponentJs().addStringValue("html", JSEncodeUtil.encodeJavaScript(extRenderComponent.getHtml()));
		}
	}
}
