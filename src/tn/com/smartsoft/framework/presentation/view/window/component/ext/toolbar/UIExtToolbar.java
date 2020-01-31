package tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtContainer;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.toolbar.render.UIExtToolbarRender;

public class UIExtToolbar extends UIExtContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String buttonAlign = "right";


	public String getButtonAlign() {
		return buttonAlign;
	}

	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}
	public UIRender getRender() {
		return new UIExtToolbarRender(this);
	}

}
