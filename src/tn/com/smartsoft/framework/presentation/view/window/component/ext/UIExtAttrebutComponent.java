package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableContainerComponent;

public abstract class UIExtAttrebutComponent extends UIGenericRendrableContainerComponent{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected String			extAttrebut;
	protected Boolean			expected			= true;
	
	public UIExtAttrebutComponent() {
		super();
	}
	public String getExtAttrebut() {
		return extAttrebut;
	}
	public void setExtAttrebut(String extAttrebut) {
		this.extAttrebut = extAttrebut;
	}
	public Boolean getExpected() {
		return expected;
	}
	public void setExpected(Boolean expected) {
		this.expected = expected;
	}
}