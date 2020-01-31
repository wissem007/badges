package tn.com.digivoip.comman.mail.att;

import java.io.IOException;
import java.io.InputStream;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import tn.com.digivoip.comman.mail.EmailAttachment;
import tn.com.digivoip.comman.mail.MailException;

public class InputStreamAttachment extends EmailAttachment{

	protected final InputStream	inputStream;
	protected final String		contentType;

	public InputStreamAttachment(InputStream inputStream, String contentType, String name, String contentId) {
		super(name, contentId);
		this.inputStream = inputStream;
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}
	public DataSource getDataSource() {
		try {
			return new ByteArrayDataSource(inputStream, contentType);
		} catch (IOException ioex) {
			throw new MailException(ioex);
		}
	}
}