package tn.com.digivoip.comman.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import tn.com.digivoip.comman.string.StringPool;

public abstract class CommonEmail{
	
	public static final String			X_PRIORITY			= "X-Priority";
	public static final int				PRIORITY_HIGHEST	= 1;
	public static final int				PRIORITY_HIGH		= 2;
	public static final int				PRIORITY_NORMAL		= 3;
	public static final int				PRIORITY_LOW		= 4;
	public static final int				PRIORITY_LOWEST		= 5;
	// ---------------------------------------------------------------- from
	protected String					uid;
	protected String					from;
	protected String[]					to					= StringPool.EMPTY_ARRAY;
	protected String[]					replyTo				= StringPool.EMPTY_ARRAY;
	// ---------------------------------------------------------------- to
	protected String[]					cc					= StringPool.EMPTY_ARRAY;
	protected String[]					bcc					= StringPool.EMPTY_ARRAY;
	protected String					subject;
	// ---------------------------------------------------------------- reply-to
	protected LinkedList<EmailMessage>	messages			= new LinkedList<EmailMessage>();
	protected Map<String, String>		headers;
	protected Date						sentDate;
	
	// ---------------------------------------------------------------- cc
	public void addMessage(EmailMessage emailMessage) {
		messages.add(emailMessage);
	}
	public void addMessage(String text, String mimeType) {
		messages.add(new EmailMessage(text, mimeType));
	}
	public void addMessage(String text, String mimeType, String encoding) {
		messages.add(new EmailMessage(text, mimeType, encoding));
	}
	// ---------------------------------------------------------------- bcc
	/** Returns all headers as a <code>HashMap</code>. */
	public Map<String, String> getAllHeaders() {
		return headers;
	}
	/** Returns all messages. */
	public LinkedList<EmailMessage> getAllMessages() {
		return messages;
	}
	/** Returns BCC addresses. */
	public String[] getBcc() {
		return bcc;
	}
	// ---------------------------------------------------------------- subject
	/** Returns CC addresses. */
	public String[] getCc() {
		return cc;
	}
	/** Returns FROM address. */
	public String getFrom() {
		return from;
	}
	public String getHeader(String name) {
		if (headers == null) { return null; }
		return headers.get(name);
	}
	// ---------------------------------------------------------------- message
	/** Returns emails priority (1 - 5) or <code>-1</code> if priority not available.
	 * @see #setPriority(int) */
	public int getPriority() {
		if (headers == null) { return -1; }
		try {
			return Integer.parseInt(headers.get(CommonEmail.X_PRIORITY));
		} catch (NumberFormatException ignore) {
			return -1;
		}
	}
	/** Returns REPLY-TO addresses. */
	public String[] getReplyTo() {
		return replyTo;
	}
	/** Returns e-mails sent date. If return value is <code>null</code> then date will be set during the process of sending.
	 * @return email's sent date or null if it will be set later. */
	public Date getSentDate() {
		return sentDate;
	}
	/** Returns message subject. */
	public String getSubject() {
		return this.subject;
	}
	/** Returns TO addresses. */
	public String[] getTo() {
		return to;
	}
	// ---------------------------------------------------------------- headers
	public String getUid() {
		return uid;
	}
	/** Sets BCC addresses. */
	public void setBcc(String[] bccs) {
		if (bccs == null) {
			bccs = StringPool.EMPTY_ARRAY;
		}
		bcc = bccs;
	}
	/** Sets CC addresses. */
	public void setCc(String[] ccs) {
		if (ccs == null) {
			ccs = StringPool.EMPTY_ARRAY;
		}
		cc = ccs;
	}
	/** Sets the FROM address. */
	public void setFrom(String from) {
		this.from = from;
	}
	/** Sets a new header value. */
	public void setHeader(String name, String value) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put(name, value);
	}
	public void setHeader(String name, Object value) {
		if (value == null) return;
		this.setHeader(name, String.valueOf(value));
	}
	public void setHeaders(Map<String, String> headers) {
		if (headers == null) { return; }
		if (this.headers == null) {
			this.headers = new HashMap<String, String>();
		}
		this.headers.putAll(headers);
	}
	/** Sets email priority. Values of 1 through 5 are acceptable, with 1 being the highest priority, 3 = normal and 5 = lowest priority. */
	public void setPriority(int priority) {
		setHeader(CommonEmail.X_PRIORITY, String.valueOf(priority));
	}
	// ---------------------------------------------------------------- date
	/** Sets REPLY-TO addresses. */
	public void setReplyTo(String[] replyTo) {
		if (replyTo == null) {
			replyTo = StringPool.EMPTY_ARRAY;
		}
		this.replyTo = replyTo;
	}
	/** Sets e-mails sent date. If input parameter is <code>null</code> then date will be when email is physically sent. */
	public void setSentDate(Date date) {
		sentDate = date;
	}
	/** Sets message subject. */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/** Sets TO addresses. */
	public void setTo(String[] tos) {
		if (tos == null) {
			tos = StringPool.EMPTY_ARRAY;
		}
		to = tos;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}
