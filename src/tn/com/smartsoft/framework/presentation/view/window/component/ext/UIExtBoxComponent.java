package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtBoxComponentRender;

public class UIExtBoxComponent extends UIExtComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String height;
	protected String width;

	protected Boolean autoHeight;
	protected Boolean autoWidth;

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Boolean isAutoHeight() {
		return autoHeight;
	}

	public void setAutoHeight(Boolean autoHeight) {
		this.autoHeight = autoHeight;
	}

	public Boolean isAutoWidth() {
		return autoWidth;
	}

	public void setAutoWidth(Boolean autoWidth) {
		this.autoWidth = autoWidth;
	}

	public UIRender getRender() {
		return new UIExtBoxComponentRender(this);
	}
}
