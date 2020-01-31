package tn.com.digivoip.comman.mail;

import java.util.Date;
import java.util.LinkedList;
import tn.com.digivoip.comman.io.MimeTypes;

/** E-mail holds all parts of an email and handle attachments. */
public class Email extends CommonEmail{
	
	/** Static constructor for fluent interface. */
	public static Email create() {
		return new Email();
	}
	
	// ---------------------------------------------------------------- from, to, cc, bcc
	protected LinkedList<EmailAttachment>	attachments;
	
	/** Adds HTML message. */
	public Email addHtml(String message) {
		messages.add(new EmailMessage(message, MimeTypes.MIME_TEXT_HTML));
		return this;
	}
	public Email addHtml(String message, String encoding) {
		messages.add(new EmailMessage(message, MimeTypes.MIME_TEXT_HTML, encoding));
		return this;
	}
	/** Adds plain message text. */
	public Email addText(String text) {
		messages.add(new EmailMessage(text, MimeTypes.MIME_TEXT_PLAIN));
		return this;
	}
	public Email addText(String text, String encoding) {
		messages.add(new EmailMessage(text, MimeTypes.MIME_TEXT_PLAIN, encoding));
		return this;
	}
	/** Adds attachment. */
	public Email attach(EmailAttachment emailAttachment) {
		if (attachments == null) {
			attachments = new LinkedList<EmailAttachment>();
		}
		attachments.add(emailAttachment);
		return this;
	}
	public Email attach(EmailAttachmentBuilder emailAttachmentBuilder) {
		emailAttachmentBuilder.setInline(false);
		attach(emailAttachmentBuilder.create());
		return this;
	}
	public Email bcc(String bcc) {
		setBcc(new String[] { bcc });
		return this;
	}
	public Email bcc(String[] bccs) {
		setBcc(bccs);
		return this;
	}
	// ---------------------------------------------------------------- subject
	public Email cc(String cc) {
		setCc(new String[] { cc });
		return this;
	}
	// ---------------------------------------------------------------- message
	public Email cc(String[] ccs) {
		setCc(ccs);
		return this;
	}
	/** Embed attachment to last message. */
	public Email embed(EmailAttachment emailAttachment) {
		attach(emailAttachment);
		if (emailAttachment.isInline()) {
			if (!messages.isEmpty()) {
				emailAttachment.setEmbeddedMessage(messages.getLast());
			}
		}
		return this;
	}
	public Email embed(EmailAttachmentBuilder emailAttachmentBuilder) {
		emailAttachmentBuilder.setInline(true);
		embed(emailAttachmentBuilder.create());
		return this;
	}
	public Email from(String from) {
		setFrom(from);
		return this;
	}
	/** Returns an array of attachments or <code>null</code> if no attachment enclosed with this email. */
	public LinkedList<EmailAttachment> getAttachments() {
		return attachments;
	}
	public Email header(String name, String value) {
		setHeader(name, value);
		return this;
	}
	// ---------------------------------------------------------------- attachments
	public Email message(String text, String mimeType) {
		addMessage(text, mimeType);
		return this;
	}
	public Email message(String text, String mimeType, String encoding) {
		addMessage(text, mimeType, encoding);
		return this;
	}
	public Email priority(int priority) {
		super.setPriority(priority);
		return this;
	}
	public Email replyTo(String replyTo) {
		setReplyTo(new String[] { replyTo });
		return this;
	}
	public Email replyTo(String[] replyTos) {
		setReplyTo(replyTos);
		return this;
	}
	public Email sentOn(Date date) {
		setSentDate(date);
		return this;
	}
	// ---------------------------------------------------------------- headers
	/** Sets current date as e-mails sent date. */
	public Email setCurrentSentDate() {
		sentDate = new Date();
		return this;
	}
	public Email subject(String subject) {
		setSubject(subject);
		return this;
	}
	// ---------------------------------------------------------------- date
	public Email to(String to) {
		setTo(new String[] { to });
		return this;
	}
	public Email to(String[] tos) {
		setTo(tos);
		return this;
	}
	// ---------------------------------------------------------------- toString
	public String toString() {
		return "Email{'" + from + "\', subject='" + subject + "\'}";
	}
}
