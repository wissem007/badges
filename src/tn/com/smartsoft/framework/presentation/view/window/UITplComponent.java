package tn.com.smartsoft.framework.presentation.view.window;

import tn.com.smartsoft.framework.presentation.view.window.render.UITplComponentRender;

public class UITplComponent extends UIGenericRendrableContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public UIRender getRender() {
		return new UITplComponentRender(this);
	}

}
