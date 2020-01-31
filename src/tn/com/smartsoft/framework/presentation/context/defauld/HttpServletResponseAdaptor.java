package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.InputStreamUtil;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.context.Response;

public class HttpServletResponseAdaptor implements Response, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	private static Pattern HEADER_FIXER = null;
	protected HttpWebContext context;
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
		HEADER_FIXER = Pattern.compile("[\r\n]");
	}
	
	public HttpServletResponseAdaptor(HttpServletResponse response, HttpWebContext context) {
		super();
		this.response = response;
		this.context = context;
		if (ApplicationConfiguration.isStartUp())
			response.setCharacterEncoding(ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getEncoding());
	}
	
	public void addCookie(Cookie cookie) {
		response.addCookie(cookie);
	}
	
	public void sendRedirect(String path) throws IOException {
		response.sendRedirect(path);
	}
	
	public void setHeader(String name, String value) {
		if (ignoreHeader(name, value))
			return;
		// CR and LF embedded in headers can corrupt the HTTP response
		value = HEADER_FIXER.matcher(value).replaceAll("");
		if ("Content-Type".equals(name)) {
			response.setContentType(value);
		} else if ("Content-Length".equals(name)) {
			response.setContentLength(Integer.parseInt(value));
		} else {
			response.setHeader(name, value);
		}
	}
	
	public void setHeader(String name, String[] values) {
		if (ignoreHeader(name, values))
			return;
		for (int i = 0; i < values.length; i++) {
			String safeValue = HEADER_FIXER.matcher(values[i]).replaceAll("");
			response.addHeader(name, safeValue);
		}
	}
	
	public void setHeader(String name, Date value) {
		if (ignoreHeader(name, value))
			return;
		response.setDateHeader(name, value.getTime());
	}
	
	public void setHeader(String name, int value) {
		response.setIntHeader(name, value);
	}
	
	public void setHeader(String name, long value) {
		response.setHeader(name, String.valueOf(value));
	}
	
	public void setStatus(int code) {
		response.setStatus(code);
	}
	
	public OutputStream getOutputStream() {
		try {
			return response.getOutputStream();
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void writeFrom(InputStream in) {
		try {
			InputStreamUtil.copy(in, getOutputStream());
		} catch (Exception e) {
			throw new TechnicalException(e);
		} finally {
			InputStreamUtil.close(in);
		}
	}
	
	private static boolean ignoreHeader(String name, Object value) {
		return name == null || value == null;
	}
	
	public void write(final CharSequence string) {
		try {
			response.getWriter().append(string);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	
}
