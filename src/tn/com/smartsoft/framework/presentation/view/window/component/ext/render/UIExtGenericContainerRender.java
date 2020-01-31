package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtContainer;

public abstract class UIExtGenericContainerRender extends UIExtGenericBoxComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtGenericContainerRender(UIExtContainer renderComponent, String jsObjectName) {
		super(renderComponent, jsObjectName);
	}

	public void renderStart(UIRenderContext context) {
		super.renderStart(context);
	}

	public void renderChilds(UIRenderContext context) {
		UIExtContainer extContainerComponent = (UIExtContainer) renderComponent;
		for (int i = 0; i < extContainerComponent.itemIds().size(); i++) {
			UIComponent child = extContainerComponent.getItem((String) extContainerComponent.itemIds().get(i));
			if (child instanceof UIRendrableComponent && ((UIRendrableComponent) child).isRendred()) {
				UIRendrableComponent rendrableComponent = (UIRendrableComponent) child;
				UIRender uiRender = (UIRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.setRenderToComponentBufferJs(renderToComponentBufferJs);
				uiRender.render(context);
				if (child instanceof UIExtComponent && ((UIExtComponent) child).isAddedToParent()) {
					extComponentJs().addItem(((UIExtComponentRender) uiRender).extComponentJs());
				}
			}
		}
	}

	public void renderEnd(UIRenderContext context) {
		super.renderEnd(context);
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtContainer extRenderComponent = (UIExtContainer) renderComponent;
		extComponentJs().addStringValue("layout", extRenderComponent.getLayout());
		if (StringUtils.isNotBlank(extRenderComponent.getLayout()) && StringUtils.equalsIgnoreCase(extRenderComponent.getLayout(), "form"))
			extComponentJs().addStringValue("labelSeparator", " ");
		if (!extRenderComponent.getLayoutConfigs().isEmpty())
			extComponentJs().addObjectValue("layoutConfig", extRenderComponent.getLayoutConfigs().toJs());
	}

}
