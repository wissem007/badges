// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import tn.com.digivoip.comman.io.FastByteArrayOutputStream;
import tn.com.digivoip.comman.io.StreamUtil;
import tn.com.digivoip.comman.mail.att.ByteArrayAttachment;
import tn.com.digivoip.comman.string.StringPool;
import tn.com.smartsoft.commons.log.Logger;

/** Received email. */
public class ReceivedEmail extends CommonEmail{
	
	protected Flags					flags;
	protected int					messageNumber;
	protected Date					recvDate;
	protected List<EmailAttachment>	attachments;
	private static Logger			logger	= Logger.getLogger(ReceivedEmail.class);
	// ---------------------------------------------------------------- flags
	protected List<ReceivedEmail>	attachedMessages;
	
	public ReceivedEmail() {}
	public ReceivedEmail(Message message, String uid) {
		try {
			parseMessage(message);
			this.setUid(uid);
		} catch (Exception ex) {
			throw new MailException("Message parsing failed", ex);
		}
	}
	/** Adds received attachment. */
	public void addAttachment(String filename, String mimeType, String contentId, byte[] content) {
		if (attachments == null) {
			attachments = new ArrayList<EmailAttachment>();
		}
		EmailAttachment emailAttachment = new ByteArrayAttachment(content, mimeType, filename, contentId);
		emailAttachment.setSize(content.length);
		attachments.add(emailAttachment);
	}
	/** Adds attached messages. */
	public void addAttachmentMessage(ReceivedEmail receivedEmail) {
		if (attachedMessages == null) {
			attachedMessages = new ArrayList<ReceivedEmail>();
		}
		attachedMessages.add(receivedEmail);
	}
	/** Returns the list of attached messages. If not attached message is available, returns <code>null</code>. */
	public List<ReceivedEmail> getAttachedMessages() {
		return attachedMessages;
	}
	/** Returns the list of all attachments. If no attachment is available, returns <code>null</code>. */
	public List<EmailAttachment> getAttachments() {
		return attachments;
	}
	public Flags getFlags() {
		return flags;
	}
	/** Returns message number. */
	public int getMessageNumber() {
		return messageNumber;
	}
	// ---------------------------------------------------------------- additional properties
	/** Returns emails received date. */
	public Date getReceiveDate() {
		return recvDate;
	}
	/** Returns <code>true</code> if message is answered. */
	public boolean isAnswered() {
		return flags.contains(Flags.Flag.ANSWERED);
	}
	/** Returns <code>true</code> if message is deleted. */
	public boolean isDeleted() {
		return flags.contains(Flags.Flag.DELETED);
	}
	/** Returns <code>true</code> if message is draft. */
	public boolean isDraf() {
		return flags.contains(Flags.Flag.DRAFT);
	}
	/** Returns <code>true</code> is message is flagged. */
	public boolean isFlagged() {
		return flags.contains(Flags.Flag.FLAGGED);
	}
	/** Returns <code>true</code> if message is recent. */
	public boolean isRecent() {
		return flags.contains(Flags.Flag.RECENT);
	}
	// ---------------------------------------------------------------- attachments
	/** Returns <code>true</code> if message is seen. */
	public boolean isSeen() {
		return flags.contains(Flags.Flag.SEEN);
	}
	/** Parse java <code>Message</code> and extracts all data for the received message. */
	@SuppressWarnings("unchecked")
	protected void parseMessage(Message msg) throws MessagingException, IOException {
		// flags
		setFlags(msg.getFlags());
		// msg no
		setMessageNumber(msg.getMessageNumber());
		// single from
		Address[] addresses = msg.getFrom();
		if (addresses != null && addresses.length > 0) {
			setFrom(addresses[0].toString());
		}
		// common field
		try {
			setTo(EmailUtil.address2String(msg.getRecipients(Message.RecipientType.TO)));
		} catch (Exception e1) {}
		try {
			setTo(EmailUtil.address2String(msg.getRecipients(Message.RecipientType.CC)));
		} catch (Exception e1) {}
		try {
			setTo(EmailUtil.address2String(msg.getRecipients(Message.RecipientType.BCC)));
		} catch (Exception e1) {}
		setSubject(msg.getSubject());
		Date recvDate = msg.getReceivedDate();
		if (recvDate == null) {
			recvDate = new Date();
		}
		setReceiveDate(recvDate);
		setSentDate(msg.getSentDate());
		// copy headers
		Enumeration<Header> headers = msg.getAllHeaders();
		while (headers.hasMoreElements()) {
			Header header = headers.nextElement();
			setHeader(header.getName(), header.getValue());
		}
		// content
		try {
			processPart(this, msg);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	/** Process single part of received message. All parts are simple added to the message, i.e. hierarchy is not saved. */
	protected void processPart(ReceivedEmail email, Part part) throws IOException, MessagingException {
		Object content = part.getContent();
		if (content instanceof String) {
			String stringContent = (String) content;
			String disposition = part.getDisposition();
			if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
				String contentType = part.getContentType();
				String mimeType = EmailUtil.extractMimeType(contentType);
				String encoding = EmailUtil.extractEncoding(contentType);
				String fileName = part.getFileName();
				String contentId = (part instanceof MimePart) ? ((MimePart) part).getContentID() : null;
				if (encoding == null) {
					encoding = StringPool.US_ASCII;
				}
				email.addAttachment(fileName, mimeType, contentId, stringContent.getBytes(encoding));
			} else {
				String contentType = part.getContentType();
				String encoding = EmailUtil.extractEncoding(contentType);
				String mimeType = EmailUtil.extractMimeType(contentType);
				if (encoding == null) {
					encoding = StringPool.US_ASCII;
				}
				email.addMessage(stringContent, mimeType, encoding);
			}
		} else if (content instanceof Multipart) {
			Multipart mp = (Multipart) content;
			int count = 0;
			try {
				count = mp.getCount();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < count; i++) {
				Part innerPart = mp.getBodyPart(i);
				processPart(email, innerPart);
			}
		} else if (content instanceof InputStream) {
			String fileName = part.getFileName();
			String contentId = (part instanceof MimePart) ? ((MimePart) part).getContentID() : null;
			String mimeType = EmailUtil.extractMimeType(part.getContentType());
			InputStream is = (InputStream) content;
			FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
			StreamUtil.copy(is, fbaos);
			email.addAttachment(fileName, mimeType, contentId, fbaos.toByteArray());
		} else if (content instanceof MimeMessage) {
			MimeMessage mimeMessage = (MimeMessage) content;
			addAttachmentMessage(new ReceivedEmail(mimeMessage, "UNKNOWN"));
		}
	}
	// ---------------------------------------------------------------- inner messages
	public void setFlags(Flags flags) {
		this.flags = flags;
	}
	/** Sets message number. */
	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}
	/** Sets e-mails receive date. */
	public void setReceiveDate(Date date) {
		recvDate = date;
	}
}