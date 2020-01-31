package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Cookie;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.AppendingStringBuffer;
import tn.com.smartsoft.commons.utils.InputStreamUtil;
import tn.com.smartsoft.framework.presentation.context.Response;
import tn.com.smartsoft.framework.presentation.context.ResponseView;

public class StringResponse implements Response ,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final AppendingStringBuffer out;

	public StringResponse() {
		this.out = new AppendingStringBuffer(128);
	}

	public void write(final CharSequence string) {
		out.append(string);
	}

	public void reset() {
		out.clear();
	}

	public String toString() {
		return out.toString();
	}

	public CharSequence getBuffer() {
		return out;
	}

	public OutputStream getOutputStream() {
		throw new UnsupportedOperationException("Cannot get output stream on StringResponse");
	}

	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String name, String[] values) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String name, Date value) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String name, int value) {
		// TODO Auto-generated method stub

	}

	public void setHeader(String name, long value) {
		// TODO Auto-generated method stub

	}

	public void setStatus(int code) {
		// TODO Auto-generated method stub

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

	public void sendRedirect(String path) throws IOException {
		// TODO Auto-generated method stub

	}

	public void response(ResponseView handler, Object model) {
		// TODO Auto-generated method stub

	}
}
