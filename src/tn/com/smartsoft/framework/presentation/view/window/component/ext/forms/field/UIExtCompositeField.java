package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtContainer;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtCompositeFieldRender;

public class UIExtCompositeField extends UIExtContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String labelExp;

	public String getLabelExp() {
		return labelExp;
	}

	public String getLabel() {
		return getWindow().evalExpressionToString(getLabelExp());
	}

	public void setLabelExp(String labelExp) {
		this.labelExp = labelExp;
	}

	public UIRender getRender() {
		return new UIExtCompositeFieldRender(this);
	}
}
