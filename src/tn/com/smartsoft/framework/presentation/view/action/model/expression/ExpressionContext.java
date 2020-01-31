package tn.com.smartsoft.framework.presentation.view.action.model.expression;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExpressionContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String expression;
	private Map<Object, Object> contextValues = new HashMap<Object, Object>();
	private int conter = 0;
	private String windowId;

	public ExpressionContext(String expression) {
		super();
		this.expression = expression;
	}

	public String getWindowId() {
		return windowId;
	}

	public void setWindowId(String windowId) {
		this.windowId = windowId;
	}

	public String getExpression() {
		return expression;
	}

	public String generateName() {
		return "sssValue" + conter;
	}

	public void addContextValue(String name, Object value) {
		this.contextValues.put(name, value);
	}

	public void addContextValues(Map<Object, Object> values) {
		this.contextValues.putAll(values);
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Map<Object, Object> getContextValues() {
		return contextValues;
	}

}
