package tn.com.smartsoft.framework.presentation.view.window.component.html;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlTextBoxRender;

public class UIHtmlTextBox extends UIComponent implements UIRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contenu;
	private int repeate = 1;
	private boolean rendred = true;

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public UIHtmlTextBox(String contenu) {
		super();
		this.contenu = contenu;
	}

	public UIHtmlTextBox() {
		this("");
	}

	public int getRepeate() {
		return repeate;
	}

	public void setRepeate(int repeate) {
		this.repeate = repeate;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public UIRender getRender() {
		return new UIHtmlTextBoxRender(this);
	}
}
