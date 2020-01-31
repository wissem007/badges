package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtTextEditorField;

public class UIExtTextEditorFieldRender extends UIExtTextFieldRender {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public UIExtTextEditorFieldRender(UIExtTextEditorField renderComponent) {
		super(renderComponent, "Ext.form.HtmlEditor");
	}
	
	@Override
	public void loadConfigs(UIRenderContext context) {
		// UIExtTextAreaField extTextAreaField = (UIExtTextAreaField) renderComponent;
		super.loadConfigs(context);
	}
}
