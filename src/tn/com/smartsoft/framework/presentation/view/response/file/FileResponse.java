package tn.com.smartsoft.framework.presentation.view.response.file;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class FileResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String FILE_RESPONSE = "file";
	protected WebContext context;
	private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	private static final String DEFAULT_HEADER_CONTENT_DISPOSITION = "attachment; filename";
	protected Logger log = Logger.getLogger(getClass());
	protected Map<String, Object> headers = new HashMap<String, Object>();
	protected StringWriter out;

	public FileResponse() {
		super();
		this.out = new StringWriter();
	}

	public FileResponse(FileBean fileBean) {
		this();
		setFileName(fileBean.getName());
		setContentType(fileBean.getContentType());
		setContentLength(fileBean.getSizeData().intValue());
		 
		write(new String(fileBean.getBinaryData()));
	}

	public void setFileName(String fileName) {
		this.setHeader(HEADER_CONTENT_DISPOSITION, DEFAULT_HEADER_CONTENT_DISPOSITION + "=" + fileName);
	}

	public void setFileName(String fileName, String extension) {
		this.setHeader(HEADER_CONTENT_DISPOSITION, DEFAULT_HEADER_CONTENT_DISPOSITION + "=" + fileName + "." + extension);
	}

	public void setContentLength(int contentLength) {
		this.setHeader("Content-Length", "" + contentLength);
	}

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	public void setContentType(String contentType) {
		this.setHeader("Content-Type", contentType);
	}

	public void setHeader(String name, Date value) {
		headers.put(name, value);
	}

	public void setHeader(String name, int value) {
		headers.put(name, new Integer(value));
	}

	public void setHeader(String name, long value) {
		headers.put(name, new Long(value));
	}

	public void write(int c) {
		out.write(c);
	}

	public void write(char[] cbuf, int off, int len) {
		out.write(cbuf, off, len);
	}

	public void write(String str) {
		out.write(str);
	}

	public void write(String str, int off, int len) {
		out.write(str, off, len);
	}

	public StringWriter append(CharSequence csq) {
		return out.append(csq);
	}

	public void write(char[] cbuf) throws IOException {
		out.write(cbuf);
	}

	public StringWriter append(CharSequence csq, int start, int end) {
		return out.append(csq, start, end);
	}

	public StringWriter append(char c) {
		return out.append(c);
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public StringWriter getWriter() {
		return out;
	}

}
