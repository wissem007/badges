// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import tn.com.digivoip.comman.file.FileNameUtil;
import tn.com.digivoip.comman.file.FileUtil;
import tn.com.digivoip.comman.io.MimeTypes;
import tn.com.digivoip.comman.io.StreamUtil;
import tn.com.digivoip.comman.mail.att.ByteArrayAttachment;
import tn.com.digivoip.comman.mail.att.FileAttachment;
import tn.com.digivoip.comman.mail.att.InputStreamAttachment;

/** Helper class for convenient attachment creation. */
public class EmailAttachmentBuilder{

	private byte[]		sourceBytes;
	private File		sourceFile;
	private InputStream	sourceInputStream;
	private String		contentType;
	private String		name;
	private String		contentId;
	private boolean		inline;

	protected EmailAttachmentBuilder() {}
	// ---------------------------------------------------------------- bytes
	public EmailAttachmentBuilder bytes(byte[] bytes) {
		checkIfSourceSpecified();
		sourceBytes = bytes;
		return this;
	}
	public EmailAttachmentBuilder bytes(File file) {
		checkIfSourceSpecified();
		try {
			sourceBytes = FileUtil.readBytes(file);
			name = file.getName();
		} catch (IOException ioex) {
			throw new MailException(ioex);
		}
		return this;
	}
	public EmailAttachmentBuilder bytes(InputStream inputStream) {
		checkIfSourceSpecified();
		try {
			sourceBytes = StreamUtil.readBytes(inputStream);
		} catch (IOException ioex) {
			throw new MailException(ioex);
		}
		return this;
	}
	// ---------------------------------------------------------------- file
	/** Checks if no source content is specified. Throws an exception if content is already specified. */
	protected void checkIfSourceSpecified() {
		int count = 0;
		if (sourceFile != null) {
			count++;
		}
		if (sourceBytes != null) {
			count++;
		}
		if (sourceInputStream != null) {
			count++;
		}
		if (count > 0) { throw new MailException("Attachment source already specified."); }
	}
	/** Creates {@link EmailAttachment}. */
	public EmailAttachment create() {
		if (sourceBytes != null) { return createByteArrayAttachment(); }
		if (sourceInputStream != null) { return createInputStreamAttachment(); }
		if (sourceFile != null) { return createFileAttachment(); }
		throw new MailException("No source.");
	}
	// ---------------------------------------------------------------- stream
	/** Creates {@link ByteArrayAttachment}. */
	protected ByteArrayAttachment createByteArrayAttachment() {
		String name = this.name;
		String contentType = resolveContentType();
		String contentId = resolveContentId();
		return new ByteArrayAttachment(sourceBytes, contentType, name, contentId);
	}
	/** Creates {@link FileAttachment}. Content type is ignored, as it is set by <code>javax.mail</code>. */
	protected FileAttachment createFileAttachment() {
		String name = this.name;
		String contentId = resolveContentId();
		return new FileAttachment(sourceFile, name, contentId);
	}
	// ---------------------------------------------------------------- properties
	/** Creates {@link InputStreamAttachment}. */
	protected InputStreamAttachment createInputStreamAttachment() {
		String name = this.name;
		String contentType = resolveContentType();
		String contentId = resolveContentId();
		return new InputStreamAttachment(sourceInputStream, contentType, name, contentId);
	}
	public EmailAttachmentBuilder file(File file) {
		checkIfSourceSpecified();
		sourceFile = file;
		name = file.getName();
		return this;
	}
	public EmailAttachmentBuilder file(String fileName) {
		file(new File(fileName));
		return this;
	}
	/** Resolves content id from all data. */
	protected String resolveContentId() {
		if (inline) {
			if (contentId != null) { return contentId; }
			return FileNameUtil.getName(name);
		}
		return null;
	}
	// ---------------------------------------------------------------- factory
	/** Resolves content type from all data. */
	protected String resolveContentType() {
		if (contentType != null) { return contentType; }
		if (name == null) { return MimeTypes.MIME_APPLICATION_OCTET_STREAM; }
		String extension = FileNameUtil.getExtension(name);
		return MimeTypes.getMimeType(extension);
	}
	public EmailAttachmentBuilder setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	public EmailAttachmentBuilder setInline(boolean inline) {
		this.inline = inline;
		if (inline == false) {
			contentId = null;
		}
		return this;
	}
	public EmailAttachmentBuilder setInline(String contentId) {
		if (contentId != null) {
			this.inline = true;
			this.contentId = contentId;
		} else {
			this.inline = false;
			this.contentId = null;
		}
		return this;
	}
	public EmailAttachmentBuilder setName(String name) {
		this.name = name;
		return this;
	}
	// ---------------------------------------------------------------- tools
	public EmailAttachmentBuilder stream(File file) {
		checkIfSourceSpecified();
		try {
			sourceInputStream = new FileInputStream(file);
			name = file.getName();
		} catch (FileNotFoundException fnfex) {
			throw new MailException(fnfex);
		}
		return this;
	}
	public EmailAttachmentBuilder stream(InputStream inputStream) {
		checkIfSourceSpecified();
		sourceInputStream = inputStream;
		return this;
	}
}
