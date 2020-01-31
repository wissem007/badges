package tn.com.smartsoft.framework.presentation.message;

import tn.com.smartsoft.framework.presentation.UIObject;

public class UIMessage implements UIObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String[] arguments;
	private String fieldId;

	public UIMessage(String code) {
		super();
		this.code = code;
	}

	public UIMessage(String code, String[] arguments) {
		super();
		this.code = code;
		this.arguments = arguments;
	}

	public UIMessage(String code, String arg0, String arg1, String arg2) {
		super();
		this.code = code;
		this.arguments = new String[] { arg0, arg1, arg2 };
	}

	public UIMessage(String code, String arg0, String arg1) {
		super();
		this.code = code;
		this.arguments = new String[] { arg0, arg1 };
	}

	public UIMessage(String code, String arg0) {
		super();
		this.code = code;
		this.arguments = new String[] { arg0 };
	}

	public String getCode() {
		return code;
	}

	public String getFieldId() {
		return fieldId;
	}

	public UIMessage setFieldId(String fieldId) {
		this.fieldId = fieldId;
		return this;
	}

	public String[] getArguments() {
		if (arguments == null)
			arguments = new String[0];
		return arguments;
	}

	public void setArguments(String arg0, String arg1, String arg2) {
		this.arguments = new String[] { arg0, arg1, arg2 };
	}

	public void setArguments(String arg0, String arg1) {
		this.arguments = new String[] { arg0, arg1 };
	}

	public void setArguments(String arg0) {
		this.arguments = new String[] { arg0 };
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
}
