package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtCheckboxFieldRender;

public class UIExtCheckboxField extends UIExtTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String boxLabelExp;
	private String checkedValue = "true";
	private String unCheckedValue = "false";
	private Boolean isBoxLabel = Boolean.FALSE;

	public UIRender getRender() {
		return new UIExtCheckboxFieldRender(this);
	}

	public String getCheckedValue() {
		return checkedValue;
	}

	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}

	public String getUnCheckedValue() {
		return unCheckedValue;
	}

	public void setUnCheckedValue(String unCheckedValue) {
		this.unCheckedValue = unCheckedValue;
	}

	public String getBoxLabelExp() {
		return boxLabelExp;
	}

	public String getBoxLabel() {
		return getWindow().evalExpressionToString(boxLabelExp);
	}

	public Boolean isBoxLabel() {
		return isBoxLabel;
	}

	public void setBoxLabel(Boolean isBoxLabel) {
		this.isBoxLabel = isBoxLabel;
	}

	public void setBoxLabelExp(String boxLabelExp) {
		this.boxLabelExp = boxLabelExp;
	}
}
