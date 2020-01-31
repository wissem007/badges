package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import tn.com.smartsoft.framework.presentation.context.RequestInfo;

public class HttpRequestInfo implements Serializable, RequestInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	private URI requestURI;
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	private boolean isPost;

	public HttpRequestInfo(HttpServletRequest request, boolean isPost) {
		super();
		this.request = request;
		String query = request.getQueryString();
		URI uri = null;
		while (null == uri) {
			try {
				uri = URI.create(request.getRequestURL().toString());
			} catch (NullPointerException e) {

				uri = URI.create("http://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI());
			}
		}
		requestURI = (query == null ? uri : URI.create(uri + "?" + query));
		this.isPost = isPost;
	}

	public Cookie[] getCookies() {
		return request.getCookies();
	}

	public String getHeader(String name) {
		return request.getHeader(name);
	}

	public Date getHeaderAsDate(String name) {
		try {
			return DATE_FORMAT.parse(request.getHeader(name));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public int getHeaderAsInteger(String name) {
		return Integer.parseInt(request.getHeader(name));
	}

	@SuppressWarnings("unchecked")
	public String[] getHeaderAsStrings(String name) {
		Enumeration<String> e = request.getHeaders(name);
		ArrayList<String> values = new ArrayList<String>();
		while (e.hasMoreElements())
			values.add(e.nextElement());
		return (String[]) values.toArray(new String[values.size()]);
	}

	@SuppressWarnings("unchecked")
	public String[] getHeaderNames() {
		List<String> headerNames = new ArrayList<String>();
		Enumeration<String> e = request.getHeaderNames();
		while (e.hasMoreElements()) {
			headerNames.add(e.nextElement());
		}
		return (String[]) headerNames.toArray(new String[headerNames.size()]);
	}

	public String getLocalAddr() {
		return request.getLocalAddr();
	}

	public String getLocalName() {
		return request.getLocalName();
	}

	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}

	public String getRemoteHost() {
		return request.getRemoteHost();
	}

	public String getServerName() {
		return request.getServerName();
	}

	public URI getURI() {
		return requestURI;
	}

	public InputStream readBody() throws IOException {
		return request.getInputStream();
	}

	public void readBodyInto(OutputStream out) throws IOException {
		copy(readBody(), out);
	}

	private static void copy(InputStream input, OutputStream output) throws IOException {
		byte[] buf = new byte[4096];
		int len = 0;
		while ((len = input.read(buf)) > -1)
			output.write(buf, 0, len);
	}

	public String getMethod() {
		return request.getMethod();
	}

	public boolean isPostRequest() {
		return isPost;
	}
}
