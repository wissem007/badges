package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtRadioField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;

public class UIExtRadioFieldRender extends UIExtTextFieldRender {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtRadioFieldRender(UIExtRadioField renderComponent) {
		super(renderComponent, "Ext.form.RadioGroup");
	}

	public void loadConfigs(UIRenderContext context) {
		UIExtRadioField radioGroupField = (UIExtRadioField) renderComponent;
		super.loadConfigs(context);
		extComponentJs().addObjectValue("vertical", radioGroupField.isVertical());
		extComponentJs().addObjectValue("columns", radioGroupField.getColumns());
		extComponentJs().addObjectValue("itemCls", radioGroupField.getItemCls());
		extComponentJs().addStringValue("fieldLabel", radioGroupField.getLibelle());
	}

	public void renderChilds(UIRenderContext context) {
		UIExtRadioField extContainerComponent = (UIExtRadioField) renderComponent;
		for (int i = 0; i < extContainerComponent.itemIds().size(); i++) {
			UIComponent child = extContainerComponent.getItem((String) extContainerComponent.itemIds().get(i));
			if (child instanceof UIRendrableComponent && ((UIRendrableComponent) child).isRendred()) {
				UIRendrableComponent rendrableComponent = (UIRendrableComponent) child;
				UIRender uiRender = (UIRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context);
				if (child instanceof UIExtComponent && ((UIExtComponent) child).isAddedToParent()) {
					extComponentJs().addItem(((UIExtComponentRender) uiRender).extComponentJs());
				}
			}
		}
	}
}
