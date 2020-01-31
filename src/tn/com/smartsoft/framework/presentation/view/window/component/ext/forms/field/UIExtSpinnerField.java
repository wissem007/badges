package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtSpinnerFieldRender;

public class UIExtSpinnerField extends UIExtNumberField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Double incrementValue;
	private Double alternateIncrementValue;
	private Boolean accelerate = Boolean.TRUE;

	public Double getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(Double incrementValue) {
		this.incrementValue = incrementValue;
	}

	public Double getAlternateIncrementValue() {
		return alternateIncrementValue;
	}

	public void setAlternateIncrementValue(Double alternateIncrementValue) {
		this.alternateIncrementValue = alternateIncrementValue;
	}

	public Boolean isAccelerate() {
		return accelerate;
	}

	public void setAccelerate(Boolean accelerate) {
		this.accelerate = accelerate;
	}

	public UIRender getRender() {
		return new UIExtSpinnerFieldRender(this);
	}
}
