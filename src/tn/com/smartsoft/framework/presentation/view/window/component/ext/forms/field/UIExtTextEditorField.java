package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtTextEditorFieldRender;

public class UIExtTextEditorField extends UIExtTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public UIRender getRender() {
		return new UIExtTextEditorFieldRender(this);
	}

}
