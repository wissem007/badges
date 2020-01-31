// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

import tn.com.digivoip.comman.FwCore;

/** Represents e-mail message: string with mime type and encoding. */
public class EmailMessage{

	private final String	content;
	private final String	mimeType;
	private final String	encoding;

	/** Defines UTF-8 email content. */
	public EmailMessage(String content, String mimeType) {
		this.content = content;
		this.mimeType = mimeType;
		this.encoding = FwCore.encoding;
	}
	/** Defines email content. */
	public EmailMessage(String content, String mimeType, String encoding) {
		this.content = content;
		this.mimeType = mimeType;
		this.encoding = encoding;
	}
	// ---------------------------------------------------------------- getters
	/** Returns message content. */
	public String getContent() {
		return content;
	}
	/** Returns message encoding. */
	public String getEncoding() {
		return encoding;
	}
	/** Returns message mime type. */
	public String getMimeType() {
		return mimeType;
	}
}
