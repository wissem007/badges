package tn.com.digivoip.comman.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.activation.DataSource;
import javax.mail.internet.MimeUtility;
import tn.com.digivoip.comman.file.FileUtil;
import tn.com.digivoip.comman.io.FastByteArrayOutputStream;
import tn.com.digivoip.comman.io.StreamUtil;

/** Email attachment. */
public abstract class EmailAttachment{

	/** Creates {@link EmailAttachmentBuilder builder} for convenient building of the email attachments. */
	public static EmailAttachmentBuilder attachment() {
		return new EmailAttachmentBuilder();
	}

	protected final String	name;
	protected final String	contentId;
	protected EmailMessage	targetMessage;
	protected int			size	= -1;

	/** Creates new attachment with given name and content id for inline attachments. Content id may be <code>null</code> if attachment is not embedded. Email name may be <code>null</code> as well. */
	protected EmailAttachment(String name, String contentId) {
		if (name != null) {
			try {
				this.name = MimeUtility.decodeText(name);
			} catch (UnsupportedEncodingException ueex) {
				throw new MailException(ueex);
			}
		} else {
			this.name = null;
		}
		this.contentId = contentId;
	}
	/** Returns content id for inline attachments. Equals to <code>null</code> when attachment is not embedded. */
	public String getContentId() {
		return contentId;
	}
	/** Returns <code>DataSource</code> implementation, depending of attachment source. */
	public abstract DataSource getDataSource();
	/** Returns encoded attachment name. May be <code>null</code>. */
	public String getEncodedName() {
		if (name == null) { return null; }
		try {
			return MimeUtility.encodeText(name);
		} catch (UnsupportedEncodingException ueex) {
			throw new MailException(ueex);
		}
	}
	/** Returns attachment name. May be <code>null</code>. */
	public String getName() {
		return name;
	}
	/** Returns size of <b>received</b> attachment, */
	public int getSize() {
		return size;
	}
	// ---------------------------------------------------------------- data source
	/** Returns <code>true</code> if attachment is embedded into provided message. */
	public boolean isEmbeddedInto(EmailMessage emailMessage) {
		return targetMessage == emailMessage;
	}
	// ---------------------------------------------------------------- size
	/** Returns <code>true</code> if it is inline attachment. */
	public boolean isInline() {
		return contentId != null;
	}
	/** Sets target message for embedded attachments. */
	public void setEmbeddedMessage(EmailMessage emailMessage) {
		if (isInline() == false) { throw new MailException("Only inline attachments may be embedded."); }
		targetMessage = emailMessage;
	}
	protected void setSize(int size) {
		this.size = size;
	}
	// ---------------------------------------------------------------- content methods
	/** Returns byte content of the attachment. */
	public byte[] toByteArray() {
		FastByteArrayOutputStream out = size != -1 ? new FastByteArrayOutputStream(size) : new FastByteArrayOutputStream();
		writeToStream(out);
		return out.toByteArray();
	}
	/** Saves attachment to a file. */
	public void writeToFile(File destination) {
		InputStream in = null;
		try {
			in = getDataSource().getInputStream();
			FileUtil.writeStream(destination, in);
		} catch (IOException ioex) {
			throw new MailException(ioex);
		} finally {
			StreamUtil.close(in);
		}
	}
	/** Saves attachment to output stream. */
	public void writeToStream(OutputStream out) {
		InputStream in = null;
		try {
			in = getDataSource().getInputStream();
			StreamUtil.copy(in, out);
		} catch (IOException ioex) {
			throw new MailException(ioex);
		} finally {
			StreamUtil.close(in);
		}
	}
}
