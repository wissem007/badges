package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtDropDownField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtComponentRender;

public class UIExtDropDownFieldRender extends UIExtTextFieldRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIExtDropDownFieldRender(UIExtDropDownField renderComponent) {
		super(renderComponent, "Ext.sss.DropDownField");
	}
	
	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtDropDownField dropDownField = (UIExtDropDownField) renderComponent;
		extComponentJs().addBooleanValue("lazyInit", dropDownField.getLazyInit());
		extComponentJs().addStringValue("componentAlign", dropDownField.getComponentAlign());
		extComponentJs().addBooleanValue("allowBlur", dropDownField.getAllowBlur());
		extComponentJs().addStringValue("mode", dropDownField.getMode());
		extComponentJs().addObjectValue("syncValue", dropDownField.getSyncValue());
	}
	
	public void renderChilds(UIRenderContext context) {
		UIExtDropDownField dropDownField = (UIExtDropDownField) renderComponent;
		UIExtComponent child = dropDownField.getComponent();
		if (child != null && ((UIRendrableComponent) child).isRendred()) {
			UIRender uiRender = (UIRender) child.getRender();
			uiRender.setParentRender(this);
			uiRender.setTabRender(getTabRender() + "  ");
			uiRender.render(context);
			extComponentJs().addObjectValue("component", ((UIExtComponentRender) uiRender).extComponentJs().getInstanceName());
			
		}
	}
}
