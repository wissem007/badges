package tn.com.smartsoft.commons.web.multipart;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public abstract class AbstractMultipartHttpServletRequest extends HttpServletRequestWrapper implements MultipartHttpServletRequest {

	private Map<String, MultipartFile> multipartFiles;

	public AbstractMultipartHttpServletRequest(HttpServletRequest request) {
		super(request);
	}

	protected void setMultipartFiles(Map<String, MultipartFile> multipartFiles) {
		this.multipartFiles = Collections.unmodifiableMap(multipartFiles);
	}

	public Iterator<String> getFileNames() {
		return this.multipartFiles.keySet().iterator();
	}

	public MultipartFile getFile(String name) {
		return (MultipartFile) this.multipartFiles.get(name);
	}

	public Map<String, MultipartFile> getFileMap() {
		return this.multipartFiles;
	}

}
