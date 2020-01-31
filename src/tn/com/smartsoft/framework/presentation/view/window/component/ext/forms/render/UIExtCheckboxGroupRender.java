package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render;

import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.UIExtCheckboxGroup;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;

public class UIExtCheckboxGroupRender extends UIExtComponentRender{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public UIExtCheckboxGroupRender(UIExtCheckboxGroup renderComponent) {
		super(renderComponent, "Ext.form.CheckboxGroup");
	}
	public void loadConfigs(UIRenderContext context) {
		UIExtCheckboxGroup checkboxGroupField = (UIExtCheckboxGroup) renderComponent;
		super.loadConfigs(context);
		extComponentJs().addObjectValue("vertical", checkboxGroupField.isVertical());
		extComponentJs().addObjectValue("columns", checkboxGroupField.getColumns());
		extComponentJs().addObjectValue("itemCls", checkboxGroupField.getItemCls());
		extComponentJs().addStringValue("fieldLabel", checkboxGroupField.getLibelle());
	}
	public void renderChilds(UIRenderContext context) {
		UIExtCheckboxGroup extContainerComponent = (UIExtCheckboxGroup) renderComponent;
		JsTable items = new JsTable();
		for (int i = 0; i < extContainerComponent.itemIds().size(); i++) {
			UIComponent child = extContainerComponent.getItem((String) extContainerComponent.itemIds().get(i));
			if (child instanceof UIRendrableComponent && ((UIRendrableComponent) child).isRendred()) {
				UIRendrableComponent rendrableComponent = (UIRendrableComponent) child;
				UIRender uiRender = (UIRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context);
				items.addObjectValue(((UIExtComponentRender) uiRender).extComponentJs().getInstanceName());
			}
		}
		if (!items.isEmpty()) extComponentJs().addObjectValue("items", items.toJs());
	}
}
