package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tn.com.smartsoft.framework.presentation.context.Session;

public class HttpSessionAdaptor implements Session, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	private boolean isSessionExpired;
	protected HttpWebContext context;

	public HttpSessionAdaptor(HttpServletRequest request, HttpWebContext context) {
		super();
		this.context = context;
		this.request = request;
		this.isSessionExpired = !request.isRequestedSessionIdValid();
		this.session = this.request.getSession(true);
	}

	public boolean isSessionExpired() {
		return isSessionExpired;
	}

	public void renews() {
		this.session = this.request.getSession(true);
	}

	public Object getAttribute(String arg0) {
		return session.getAttribute(arg0);
	}

	@SuppressWarnings("unchecked")
	public Enumeration<String> getAttributeNames() {
		return session.getAttributeNames();
	}

	public long getCreationTime() {
		return session.getCreationTime();
	}

	public String getId() {
		return session.getId();
	}

	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return session.getServletContext();
	}

	public void invalidate() {
		session.invalidate();
	}

	public boolean isNew() {
		return session.isNew();
	}

	public void removeAttribute(String arg0) {
		session.removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		session.setAttribute(arg0, arg1);
	}

	public void setMaxInactiveInterval(int arg0) {
		session.setMaxInactiveInterval(arg0);
	}

}
