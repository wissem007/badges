package tn.com.smartsoft.framework.presentation.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowserCacheFilter implements Filter {

	private long lastExpiresCalculated = 0;
	private static final int EXPIRES_HEADER_PRECISION = 60 * 60 * 24 * 1000; // one
	// day
	private String lastExpiresFormatted;

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String expiresFormatted = getExpiresFormatted();
		((HttpServletResponse) servletResponse).addHeader("Expires", expiresFormatted);
		// TODO delete
		filterChain.doFilter(servletRequest, servletResponse);

		if (((HttpServletRequest) servletRequest).getRequestURI().endsWith(".gz")) {
			((HttpServletResponse) servletResponse).addHeader("content-encoding", "gzip");
		}
	}

	private String getExpiresFormatted() {
		long now = System.currentTimeMillis();
		if (lastExpiresCalculated + EXPIRES_HEADER_PRECISION > now)
			return lastExpiresFormatted;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		lastExpiresCalculated = now;
		lastExpiresFormatted = simpleDateFormat.format(new Date(System.currentTimeMillis() + 365L * 60 * 60 * 24 * 1000));
		return lastExpiresFormatted;
	}

	public void destroy() {

	}
}
