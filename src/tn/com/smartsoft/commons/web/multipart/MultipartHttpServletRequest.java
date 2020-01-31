package tn.com.smartsoft.commons.web.multipart;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface MultipartHttpServletRequest extends HttpServletRequest {

	public Iterator<String> getFileNames();

	public MultipartFile getFile(String name);

	public Map<String, MultipartFile> getFileMap();
}
