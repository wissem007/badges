package tn.com.smartsoft.framework.presentation.context;

import java.util.Enumeration;

import javax.servlet.ServletContext;

public interface Session {

	public abstract Object getAttribute(String arg0);

	public abstract Enumeration<String> getAttributeNames();

	public abstract long getCreationTime();

	public abstract String getId();

	public abstract long getLastAccessedTime();

	public abstract int getMaxInactiveInterval();

	public abstract ServletContext getServletContext();

	public abstract void invalidate();

	public abstract boolean isNew();

	public abstract void removeAttribute(String arg0);

	public abstract void setAttribute(String arg0, Object arg1);

	public abstract void setMaxInactiveInterval(int arg0);

	public boolean isSessionExpired();

	public void renews();
}
