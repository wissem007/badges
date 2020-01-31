package tn.com.digivoip.comman.mail.att;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import tn.com.digivoip.comman.mail.EmailAttachment;

/** Byte array {@link EmailAttachment email attachment}. */
public class ByteArrayAttachment extends EmailAttachment{

	protected final byte[]	content;
	protected final String	contentType;

	public ByteArrayAttachment(byte[] content, String contentType, String name, String contentId) {
		super(name, contentId);
		this.content = content;
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}
	public DataSource getDataSource() {
		return new ByteArrayDataSource(content, contentType);
	}
}