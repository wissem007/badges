package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtTextFieldRender;

public class UIExtHiddenField extends UIExtTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIRender getRender() {
		return new UIExtTextFieldRender(this, "Ext.form.Hidden");
	}
}
