package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.context.Request;
import tn.com.smartsoft.framework.presentation.context.Response;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.context.Session;
import tn.com.smartsoft.framework.presentation.context.WebPath;
import tn.com.smartsoft.framework.presentation.servlets.ControlServlet;
import tn.com.smartsoft.framework.presentation.view.desktop.DesktopPartType;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;

public class HttpWebContext implements WebContext, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(getClass());
	private Session session;
	private Request request;
	private Response response;
	private ControlServlet controlServlet;
	public static final String SESSIN_PRINCIPAL_NAME = "tn.com.smartsoft.framework.desktop";
	private WebPath contextPath;

	public HttpWebContext(ControlServlet controlServlet, HttpServletRequest request, HttpServletResponse response, boolean isPost) {
		super();
		this.request = new HttpServletRequestAdaptor(request, isPost, this);
		this.response = new HttpServletResponseAdaptor(response, this);
		this.controlServlet = controlServlet;
		this.session = new HttpSessionAdaptor(request, this);
		this.contextPath = new WebPath(request.getContextPath());
	}

	public WebPath getContextPath() {
		return contextPath;
	}

	public Request request() {
		return request;
	}

	public Response response() {
		return response;
	}

	public ControlServlet controlServlet() {
		return controlServlet;
	}

	public Session session() {
		return this.session;
	}

	public void sendRedirect(String actionId, String name) {
		try {
			response.sendRedirect(resolveViewPath(actionId, name));
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	public UserDesktop userDesktop() {
		return (UserDesktop) session.getAttribute(SESSIN_PRINCIPAL_NAME);
	}

	public void registerDesktop(UserDesktop desktop) {
		session.setAttribute(SESSIN_PRINCIPAL_NAME, desktop);
	}

	public void unregisterDesktop() {
		session.removeAttribute(SESSIN_PRINCIPAL_NAME);
	}

	public void response(ResponseView handler, Object model) {
		handler.setContext(this);
		handler.response(model);
	}

	public void response(String name, Object model) {
		response(ApplicationConfiguration.applicationManager().presentationBeanFactory().createResponseView(name), model);
	}

	public void responseWindow() {
		response(ApplicationConfiguration.applicationManager().presentationBeanFactory().createWindowResponseView(), null);
	}

	public String resolveViewPath(String actionId, String pageId) {
		return getContextPath().getChildPath(actionId + HttpRequestEventInfo.SEP_PATH + pageId + HttpRequestEventInfo.SEP_PATH + DesktopPartType.VIEW).getPath();
	}
}
