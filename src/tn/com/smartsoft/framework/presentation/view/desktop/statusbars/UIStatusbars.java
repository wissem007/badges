package tn.com.smartsoft.framework.presentation.view.desktop.statusbars;

import tn.com.smartsoft.framework.presentation.UIObject;

public class UIStatusbars implements UIObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String displayText;
	private boolean isDisplayed = true;

	public String getDisplayText() {
		if (displayText == null)
			displayText = "";
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public boolean isDisplayed() {
		return isDisplayed;
	}

	public void setDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}

}
