package tn.com.smartsoft.commons.web.multipart.commons;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tn.com.smartsoft.commons.web.multipart.MultipartFile;

public abstract class CommonsFileUploadSupport {

	protected final Log logger = LogFactory.getLog(getClass());

	private final DiskFileItemFactory fileItemFactory;

	private final FileUpload fileUpload;

	private boolean uploadTempDirSpecified = false;

	public CommonsFileUploadSupport() {
		this.fileItemFactory = newFileItemFactory();
		this.fileUpload = newFileUpload(getFileItemFactory());
	}

	public DiskFileItemFactory getFileItemFactory() {
		return fileItemFactory;
	}

	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void setMaxUploadSize(long maxUploadSize) {
		this.fileUpload.setSizeMax(maxUploadSize);
	}

	public void setMaxInMemorySize(int maxInMemorySize) {
		this.fileItemFactory.setSizeThreshold(maxInMemorySize);
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.fileUpload.setHeaderEncoding(defaultEncoding);
	}

	protected String getDefaultEncoding() {
		String encoding = getFileUpload().getHeaderEncoding();
		if (encoding == null) {
			encoding = "ISO-8859-1";
		}
		return encoding;
	}

	protected boolean isUploadTempDirSpecified() {
		return uploadTempDirSpecified;
	}

	protected DiskFileItemFactory newFileItemFactory() {
		return new DiskFileItemFactory();
	}

	protected abstract FileUpload newFileUpload(FileItemFactory fileItemFactory);

	protected FileUpload prepareFileUpload(String encoding) {
		FileUpload fileUpload = getFileUpload();
		FileUpload actualFileUpload = fileUpload;
		if (encoding != null && !encoding.equals(fileUpload.getHeaderEncoding())) {
			actualFileUpload = newFileUpload(getFileItemFactory());
			actualFileUpload.setSizeMax(fileUpload.getSizeMax());
			actualFileUpload.setHeaderEncoding(encoding);
		}

		return actualFileUpload;
	}

	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems, String encoding) {
		Map<String, MultipartFile> multipartFiles = new HashMap<String, MultipartFile>();
		Map<String, String[]> multipartParameters = new HashMap<String, String[]>();

		for (Iterator<FileItem> it = fileItems.iterator(); it.hasNext();) {
			FileItem fileItem = (FileItem) it.next();
			if (fileItem.isFormField()) {
				String value = null;
				if (encoding != null) {
					try {
						value = fileItem.getString(encoding);
					} catch (UnsupportedEncodingException ex) {
						if (logger.isWarnEnabled()) {
							logger.warn("Could not decode multipart item '" + fileItem.getFieldName() + "' with encoding '" + encoding + "': using platform default");
						}
						value = fileItem.getString();
					}
				} else {
					value = fileItem.getString();
				}
				String[] curParam = (String[]) multipartParameters.get(fileItem.getFieldName());
				if (curParam == null) {
					// simple form field
					multipartParameters.put(fileItem.getFieldName(), new String[] { value });
				} else {
					// array of simple form fields
					String[] newParam = (String[]) ArrayUtils.add(curParam, value);
					multipartParameters.put(fileItem.getFieldName(), newParam);
				}
			} else {
				// multipart file field
				CommonsMultipartFile file = new CommonsMultipartFile(fileItem);
				multipartFiles.put(file.getName(), file);
				if (logger.isDebugEnabled()) {
					logger.debug("Found multipart file [" + file.getName() + "] of size " + file.getSize() + " bytes with original filename ["
							+ file.getOriginalFilename() + "], stored " + file.getStorageDescription());
				}
			}
		}
		return new MultipartParsingResult(multipartFiles, multipartParameters);
	}

	protected void cleanupFileItems(Collection<MultipartFile> multipartFiles) {
		for (Iterator<MultipartFile> it = multipartFiles.iterator(); it.hasNext();) {
			CommonsMultipartFile file = (CommonsMultipartFile) it.next();
			if (logger.isDebugEnabled()) {
				logger.debug("Cleaning up multipart file [" + file.getName() + "] with original filename [" + file.getOriginalFilename() + "], stored "
						+ file.getStorageDescription());
			}
			file.getFileItem().delete();
		}
	}

	protected static class MultipartParsingResult {

		private Map<String, MultipartFile> multipartFiles;

		private final Map<String, String[]> multipartParameters;

		public MultipartParsingResult(Map<String, MultipartFile> multipartFiles, Map<String, String[]> multipartParameters) {
			this.multipartFiles = multipartFiles;
			this.multipartParameters = multipartParameters;
		}

		public Map<String, MultipartFile> getMultipartFiles() {
			return multipartFiles;
		}

		public Map<String, String[]> getMultipartParameters() {
			return multipartParameters;
		}
	}

}
