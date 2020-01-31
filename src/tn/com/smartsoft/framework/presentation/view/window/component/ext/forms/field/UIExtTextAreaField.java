package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtTextAreaFieldRender;

public class UIExtTextAreaField extends UIExtTextField{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Boolean				isHtml				= Boolean.TRUE;
	
	public UIRender getRender() {
		return new UIExtTextAreaFieldRender(this, isHtml);
	}
	public Boolean getIsHtml() {
		return isHtml;
	}
	public void setIsHtml(Boolean isHtml) {
		this.isHtml = isHtml;
	}
}
