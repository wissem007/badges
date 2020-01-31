package tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericComponentRender;

public class UIExtToolbarSpacer extends UIExtComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIRender getRender() {
		return new UIExtGenericComponentRender(this, "Ext.Toolbar.Spacer");
	}

}
