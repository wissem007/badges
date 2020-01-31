package tn.com.smartsoft.framework.presentation.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.Cookie;

public interface Response {

	public void addCookie(Cookie cookie);

	public void setStatus(int code);

	public void setHeader(String name, String value);

	public void setHeader(String name, String[] values);

	public void setHeader(String name, Date value);

	public void setHeader(String name, int value);

	public void setHeader(String name, long value);

	public OutputStream getOutputStream();

	public void write(CharSequence string);

	void writeFrom(InputStream in);

	public void sendRedirect(String path) throws IOException;
}
