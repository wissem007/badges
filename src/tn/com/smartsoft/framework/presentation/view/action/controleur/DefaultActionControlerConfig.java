package tn.com.smartsoft.framework.presentation.view.action.controleur;

public class DefaultActionControlerConfig implements ActionControlerConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object contolerBean;
	private String methodName;
	
	public DefaultActionControlerConfig(Object contolerBean, String methodName) {
		super();
		this.contolerBean = contolerBean;
		this.methodName = methodName;
	}
	
	public Object getContolerBean() {
		return contolerBean;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
}
