package tn.com.smartsoft.commons.web.multipart.commons;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.web.multipart.DefaultMultipartHttpServletRequest;
import tn.com.smartsoft.commons.web.multipart.MultipartHttpServletRequest;
import tn.com.smartsoft.commons.web.multipart.MultipartResolver;

public class CommonsMultipartResolver extends CommonsFileUploadSupport implements MultipartResolver {

	public CommonsMultipartResolver() {
		super();
	}

	public CommonsMultipartResolver(ServletContext servletContext) {
		this();
		setServletContext(servletContext);
	}

	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		return new ServletFileUpload(fileItemFactory);
	}

	public void setServletContext(ServletContext servletContext) {
		if (!isUploadTempDirSpecified()) {
			try {
				getFileItemFactory().setRepository(File.createTempFile("SSS", ".tmp"));
			} catch (IOException e) {

			}
		}
	}

	public boolean isMultipart(HttpServletRequest request) {
		return ServletFileUpload.isMultipartContent(request);
	}

	@SuppressWarnings("unchecked")
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) {
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		try {
			List fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			MultipartParsingResult parsingResult = parseFileItems(fileItems, encoding);
			return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(), parsingResult.getMultipartParameters());
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new TechnicalException(ex);
		} catch (FileUploadException e) {
			throw new TechnicalException(e);
		}
	}

	protected String determineEncoding(HttpServletRequest request) {
		String encoding = request.getCharacterEncoding();
		if (encoding == null) {
			encoding = getDefaultEncoding();
		}
		return encoding;
	}

	public void cleanupMultipart(MultipartHttpServletRequest request) {
		cleanupFileItems(request.getFileMap().values());
	}

}
