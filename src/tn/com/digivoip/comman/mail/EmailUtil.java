// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import tn.com.digivoip.comman.io.StringInputStream;
import tn.com.digivoip.comman.string.CharUtil;
import tn.com.digivoip.comman.string.StringPool;

/** Email utilities. */
public class EmailUtil{

	protected static final String	ATTR_CHARSET	= "charset=";

	/** Converts mail address to strings. */
	public static String[] address2String(Address[] addresses) {
		if (addresses == null) { return null; }
		if (addresses.length == 0) { return null; }
		String[] res = new String[addresses.length];
		for (int i = 0; i < addresses.length; i++) {
			Address address = addresses[i];
			res[i] = address.toString();
		}
		return res;
	}
	/** Parses content type for encoding. May return <code>null</code> if encoding is not specified in content type. */
	public static String extractEncoding(String contentType) {
		int ndx = contentType.indexOf(';');
		String charset = ndx != -1 ? contentType.substring(ndx + 1) : StringPool.EMPTY;
		String encoding = null;
		ndx = charset.indexOf(EmailUtil.ATTR_CHARSET);
		if (ndx != -1) {
			ndx += EmailUtil.ATTR_CHARSET.length();
			int len = charset.length();
			if (charset.charAt(ndx) == '"') {
				ndx++;
			}
			int start = ndx;
			while (ndx < len) {
				char c = charset.charAt(ndx);
				if ((c == '"') || (CharUtil.isWhitespace(c) == true) || (c == ';')) {
					break;
				}
				ndx++;
			}
			encoding = charset.substring(start, ndx);
		}
		return encoding;
	}
	/** Extracts mime type from parts content type. */
	public static String extractMimeType(String contentType) {
		int ndx = contentType.indexOf(';');
		String mime;
		if (ndx != -1) {
			mime = contentType.substring(0, ndx);
		} else {
			mime = contentType;
		}
		return mime;
	}
	/** Reads EML from a file and parses it into {@link ReceivedEmail}. */
	public static ReceivedEmail parseEML(File emlFile) throws FileNotFoundException, MessagingException {
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props, null);
		Message message = new MimeMessage(session, new FileInputStream(emlFile));
		return new ReceivedEmail(message, "UNKNOWN");
	}
	/** Parse EML from content into {@link ReceivedEmail}. */
	public static ReceivedEmail parseEML(String emlContent) throws MessagingException {
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props, null);
		Message message = new MimeMessage(session, new StringInputStream(emlContent, StringInputStream.Mode.ASCII));
		return new ReceivedEmail(message, "UNKNOWN");
	}
}