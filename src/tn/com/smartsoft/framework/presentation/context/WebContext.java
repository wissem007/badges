package tn.com.smartsoft.framework.presentation.context;

import tn.com.smartsoft.framework.presentation.servlets.ControlServlet;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;

public interface WebContext {

	public ControlServlet controlServlet();

	public Request request();

	public Response response();

	public Session session();

	public UserDesktop userDesktop();

	public void registerDesktop(UserDesktop desktop);

	public void unregisterDesktop();

	public WebPath getContextPath();

	public void sendRedirect(String viewId, String name);

	public void response(ResponseView responseView, Object model);

	public void response(String name, Object model);

	public void responseWindow();
}
