package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtTemplateArgRender;

public class UIExtTemplateArg extends UIGenericRendrableContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIRender getRender() {
		return new UIExtTemplateArgRender(this);
	}

}
