package tn.com.smartsoft.framework.presentation.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

import javax.servlet.http.Cookie;

public interface RequestInfo {

	public abstract Cookie[] getCookies();

	public abstract String getHeader(String name);

	public abstract Date getHeaderAsDate(String name);

	public abstract int getHeaderAsInteger(String name);

	public abstract String[] getHeaderAsStrings(String name);

	public abstract String[] getHeaderNames();

	public abstract String getLocalAddr();

	public abstract String getLocalName();

	public abstract String getRemoteAddr();

	public abstract String getRemoteHost();

	public abstract String getServerName();

	public abstract URI getURI();

	public abstract InputStream readBody() throws IOException;

	public abstract void readBodyInto(OutputStream out) throws IOException;

	public abstract String getMethod();

	public abstract boolean isPostRequest();

}