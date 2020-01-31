package tn.com.smartsoft.framework.test;

import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.context.Request;
import tn.com.smartsoft.framework.presentation.context.Response;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.context.Session;
import tn.com.smartsoft.framework.presentation.context.WebPath;
import tn.com.smartsoft.framework.presentation.context.defauld.ConsoleResponse;
import tn.com.smartsoft.framework.presentation.servlets.ControlServlet;
import tn.com.smartsoft.framework.presentation.view.desktop.DefaultUserDesktop;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;

public class TestContext implements WebContext {
	private ApplicationManager applicationManager;

	public TestContext(ApplicationManager applicationManager) {
		super();
		this.applicationManager = applicationManager;
	}

	public ControlServlet controlServlet() {
		return null;
	}

	public WebPath getContextPath() {
		return null;
	}

	public void registerDesktop(UserDesktop desktop) {
	}

	public Request request() {
		return null;
	}

	public Response response() {
		return ConsoleResponse.getInstance();
	}

	public Session session() {
		return null;
	}

	public void unregisterDesktop() {

	}

	public UserDesktop userDesktop() {
		return new DefaultUserDesktop();
	}

	public ApplicationManager applicationManager() {
		return applicationManager;
	}

	public void sendRedirect(String actionId, String name) {
		// TODO Auto-generated method stub

	}

	public void response(ResponseView responseHandle, Object model) {
		// TODO Auto-generated method stub

	}

	public void response(String name, Object model) {
		// TODO Auto-generated method stub

	}

	public void responseWindow() {
		// TODO Auto-generated method stub

	}

}
