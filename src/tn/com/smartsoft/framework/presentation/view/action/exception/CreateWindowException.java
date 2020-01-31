package tn.com.smartsoft.framework.presentation.view.action.exception;

import tn.com.smartsoft.commons.exceptions.FunctionalException;

public class CreateWindowException extends FunctionalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable causeCreateWindow;

	public CreateWindowException(Throwable cause) {
		super("", cause);
		causeCreateWindow=cause;
	}

	public Throwable getCauseCreateWindow() {
		return causeCreateWindow;
	}

	public void setCauseCreateWindow(Throwable causeCreateWindow) {
		this.causeCreateWindow = causeCreateWindow;
	}
}
