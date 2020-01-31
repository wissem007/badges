package tn.com.smartsoft.framework.presentation.view.window;

public abstract class UIGenericRendrableComponent extends UIGenericComponent implements UIRendrableComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean rendred = true;
	
	public boolean isRendred() {
		return rendred;
	}
	
	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}
}
