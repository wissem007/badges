package tn.com.smartsoft.commons.web.multipart;

import javax.servlet.http.HttpServletRequest;

public interface MultipartResolver {

	boolean isMultipart(HttpServletRequest request);

	MultipartHttpServletRequest resolveMultipart(HttpServletRequest request);

	void cleanupMultipart(MultipartHttpServletRequest request);
}
