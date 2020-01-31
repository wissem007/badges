package tn.com.smartsoft.framework.presentation.view.window;

import tn.com.smartsoft.framework.presentation.UIObject;

public abstract class UIRender implements UIObject, UIRenderValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabRender = " ";
	protected UIRendrableComponent renderComponent;
	protected UIRender parentRender;

	protected boolean renderToComponentBufferJs = true;


	public UIRender(UIRendrableComponent renderComponent) {
		super();
		this.renderComponent = renderComponent;
	}

	public void setRenderToComponentBufferJs(boolean renderToComponentBufferJs) {
		this.renderToComponentBufferJs = renderToComponentBufferJs;
	}
	public UIRender getParentRender() {
		return parentRender;
	}

	public void setParentRender(UIRender parentRender) {
		this.parentRender = parentRender;
	}

	public String getTabRender() {
		return tabRender;
	}

	public void setTabRender(String tabRender) {
		this.tabRender = tabRender;
	}

	public void addTabRender() {
		tabRender = tabRender + "  ";
	}

	public UIRendrableComponent getRenderComponent() {
		return renderComponent;
	}

	public abstract void render(UIRenderContext context);

}
