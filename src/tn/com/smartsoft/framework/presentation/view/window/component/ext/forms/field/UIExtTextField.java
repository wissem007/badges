package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtTextFieldRender;

public class UIExtTextField extends UIExtField{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected int				size;
	protected boolean			hidden;
	
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public UIRender getRender() {
		return new UIExtTextFieldRender(this);
	}
}
