package tn.com.smartsoft.commons.web.multipart.commons;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tn.com.smartsoft.commons.web.multipart.MultipartFile;

public class CommonsMultipartFile implements MultipartFile, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final Log logger = LogFactory.getLog(CommonsMultipartFile.class);

	private final FileItem fileItem;

	private final long size;

	public CommonsMultipartFile(FileItem fileItem) {
		this.fileItem = fileItem;
		this.size = this.fileItem.getSize();
	}

	public final FileItem getFileItem() {
		return this.fileItem;
	}

	public String getName() {
		return this.fileItem.getFieldName();
	}

	public String getOriginalFilename() {
		String filename = this.fileItem.getName();
		if (filename == null) {
			return "";
		}
		int pos = filename.lastIndexOf("/");
		if (pos == -1) {
			pos = filename.lastIndexOf("\\");
		}
		if (pos != -1) {
			return filename.substring(pos + 1);
		} else {
			return filename;
		}
	}

	public String getContentType() {
		return this.fileItem.getContentType();
	}

	public boolean isEmpty() {
		return (this.size == 0);
	}

	public long getSize() {
		return this.size;
	}

	public byte[] getBytes() {
		if (!isAvailable()) {
			throw new IllegalStateException("File has been moved - cannot be read again");
		}
		byte[] bytes = this.fileItem.get();
		return (bytes != null ? bytes : new byte[0]);
	}

	public InputStream getInputStream() throws IOException {
		if (!isAvailable()) {
			throw new IllegalStateException("File has been moved - cannot be read again");
		}
		InputStream inputStream = this.fileItem.getInputStream();
		return (inputStream != null ? inputStream : new ByteArrayInputStream(new byte[0]));
	}

	public void transferTo(File dest) throws IOException, IllegalStateException {
		if (!isAvailable()) {
			throw new IllegalStateException("File has already been moved - cannot be transferred again");
		}

		if (dest.exists() && !dest.delete()) {
			throw new IOException("Destination file [" + dest.getAbsolutePath() + "] already exists and could not be deleted");
		}

		try {
			this.fileItem.write(dest);
			if (logger.isDebugEnabled()) {
				String action = "transferred";
				if (!this.fileItem.isInMemory()) {
					action = isAvailable() ? "copied" : "moved";
				}
				logger.debug("Multipart file '" + getName() + "' with original filename [" + getOriginalFilename() + "], stored " + getStorageDescription() + ": "
						+ action + " to [" + dest.getAbsolutePath() + "]");
			}
		} catch (FileUploadException ex) {
			throw new IllegalStateException(ex.getMessage());
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Could not transfer to file", ex);
			throw new IOException("Could not transfer to file: " + ex.getMessage());
		}
	}

	protected boolean isAvailable() {
		// If in memory, it's available.
		if (this.fileItem.isInMemory()) {
			return true;
		}
		// Check actual existence of temporary file.
		if (this.fileItem instanceof DiskFileItem) {
			return ((DiskFileItem) this.fileItem).getStoreLocation().exists();
		}
		// Check whether current file size is different than original one.
		return (this.fileItem.getSize() == this.size);
	}

	public String getStorageDescription() {
		if (this.fileItem.isInMemory()) {
			return "in memory";
		} else if (this.fileItem instanceof DiskFileItem) {
			return "at [" + ((DiskFileItem) this.fileItem).getStoreLocation().getAbsolutePath() + "]";
		} else {
			return "on disk";
		}
	}

}
