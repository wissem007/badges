package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtPanel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.render.UIExtFieldSetRender;

public class UIExtFieldSet extends UIExtPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIRender getRender() {
		return new UIExtFieldSetRender(this);
	}
}
