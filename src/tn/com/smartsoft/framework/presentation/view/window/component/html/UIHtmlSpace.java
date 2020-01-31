package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlSpaceRender;

public class UIHtmlSpace extends UIComponent implements UIRendrableComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int repeate = 1;
	private boolean rendred = true;

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public UIHtmlSpace() {
		super();
	}

	public int getRepeate() {
		return repeate;
	}

	public void setRepeate(int repeate) {
		this.repeate = repeate;
	}

	public UIRender getRender() {
		return new UIHtmlSpaceRender(this);
	}
}
