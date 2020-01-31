package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtRadioRender;

public class UIExtRadio extends UIExtComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String inputValue;
	private String boxLabelExp = "";

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public boolean isChecked() {
		UIExtRadioField radioField = (UIExtRadioField) getParent();
		return radioField.isChecked(inputValue);
	}

	public String getBoxLabelExp() {
		return boxLabelExp;
	}

	public String getBoxLabel() {
		return getWindow().evalExpressionToString(getBoxLabelExp());
	}

	public void setBoxLabelExp(String boxLabelExp) {
		this.boxLabelExp = boxLabelExp;
	}

	public String getName() {
		UIExtRadioField radioField = (UIExtRadioField) getParent();
		return radioField.getId();
	}

	public UIRender getRender() {
		return new UIExtRadioRender(this);
	}

}
