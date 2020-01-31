package tn.com.digivoip.comman.mail.att;

import java.io.File;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import tn.com.digivoip.comman.mail.EmailAttachment;

public class FileAttachment extends EmailAttachment{

	protected final File	file;

	public FileAttachment(File file, String name, String contentId) {
		super(name, contentId);
		this.file = file;
	}
	public DataSource getDataSource() {
		return new FileDataSource(file);
	}
	/** Returns attached file. */
	public File getFile() {
		return file;
	}
}
