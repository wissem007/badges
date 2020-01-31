package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.Cookie;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.InputStreamUtil;
import tn.com.smartsoft.framework.presentation.context.Response;
import tn.com.smartsoft.framework.presentation.context.ResponseView;

public class ConsoleResponse implements Response ,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ConsoleResponse instance = new ConsoleResponse();

	public static final ConsoleResponse getInstance() {
		return instance;
	}

	private ConsoleResponse() {
	}

	public void write(CharSequence string) {
		System.out.print(string);
	}

	public OutputStream getOutputStream() {
		return System.out;
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

	public void addCookie(Cookie cookie) {
	}

	public void setHeader(String name, String value) {
	}

	public void setHeader(String name, String[] values) {
	}

	public void setHeader(String name, Date value) {
	}

	public void setHeader(String name, int value) {
	}

	public void setHeader(String name, long value) {
	}

	public void setStatus(int code) {

	}

	public void sendRedirect(String path) throws IOException {
		// TODO Auto-generated method stub

	}

	public void response(ResponseView handler, Object model) {
		// TODO Auto-generated method stub

	}

}
