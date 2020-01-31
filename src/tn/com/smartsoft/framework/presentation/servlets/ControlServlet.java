package tn.com.smartsoft.framework.presentation.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tn.com.smartsoft.commons.cometd.BayeuxUtils;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.web.js.JSEncodeUtil;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.context.defauld.HttpWebContext;
import tn.com.smartsoft.framework.presentation.view.desktop.UserDesktop;

public class ControlServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long						serialVersionUID	= 1L;
	private static final ThreadLocal<WebContext>	currentContextPath	= new ThreadLocal<WebContext>();
	protected static final Logger					logger				= Logger.getLogger(ControlServlet.class);
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BayeuxUtils.addBayeuxUtility(getServletContext());
		BayeuxUtils.addGenericBayeux(getServletContext());
		ApplicationConfiguration.startUp();
		if (!ApplicationConfiguration.isStartUp()) {
			try {
				throw ApplicationConfiguration.getExceptionStartUp();
			} catch (Exception e) {
				new TechnicalException(e).printLogTrace(logger);
			}
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response, false);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response, true);
	}
	protected void doRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws IOException {
		try {
			HttpWebContext requestContext = createRequestContext(request, response, isPost);
			attachRequestContext(requestContext);
			handleRequest(request, response, isPost);
		} finally {
			detachRequestContext();
		}
	}
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws IOException {
		WebContext context = lookupRequestContext();
		UserDesktop userDesktop = context.userDesktop();
		if (!ApplicationConfiguration.isStartUp()) {
			RequestDispatcher rd = request.getRequestDispatcher("initErreur.jsp");
			request.setAttribute("erreur", JSEncodeUtil.encodeJavaScript(ApplicationConfiguration.getExceptionStartUp().getMessage()));
			try {
				rd.forward(request, response);
			} catch (ServletException e) {}
		} else {
			if (userDesktop == null) {
				userDesktop = ApplicationConfiguration.applicationManager().presentationBeanFactory().createUserDesktop();
				lookupRequestContext().registerDesktop(userDesktop);
			}
			userDesktop.userDesktopNavigation().fireActionEvent(context);
		}
	}
	protected HttpWebContext createRequestContext(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws IOException {
		return new HttpWebContext(this, request, response, isPost);
	}
	public static HttpWebContext lookupRequestContext() {
		return (HttpWebContext) currentContextPath.get();
	}
	private static void attachRequestContext(HttpWebContext requestContext) {
		currentContextPath.set(requestContext);
	}
	private static void detachRequestContext() {
		currentContextPath.set(null);
	}
}
