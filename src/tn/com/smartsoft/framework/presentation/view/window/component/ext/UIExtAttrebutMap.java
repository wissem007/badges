package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtAttrebutMapRender;

public class UIExtAttrebutMap extends UIExtAttrebutComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIRender getRender() {
		return new UIExtAttrebutMapRender(this);
	}

}
