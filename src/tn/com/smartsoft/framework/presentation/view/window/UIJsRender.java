package tn.com.smartsoft.framework.presentation.view.window;


public abstract class UIJsRender extends UIRender implements UIAppenderJsRender {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIJsBufferRender bufferRender = new UIJsBufferRender();

	public UIJsRender(UIRendrableComponent renderComponent) {
		super(renderComponent);
	}

	public UIJsBufferRender bufferRender() {
		return bufferRender;
	}

}
