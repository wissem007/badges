// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/** IMAP Server. */
public class ImapServer implements ReceiveMailSessionProvider{

	protected static final String	MAIL_IMAP_PORT		= "mail.imap.port";
	protected static final String	MAIL_IMAP_HOST		= "mail.imap.host";
	protected static final String	PROTOCOL_IMAP		= "imap";
	protected static final int		DEFAULT_IMAP_PORT	= 143;
	protected final String			host;
	protected final int				port;
	protected final Authenticator	authenticator;
	protected final Properties		sessionProperties;

	/** POP3 server defined with its host and default port. */
	public ImapServer(String host) {
		this(host, ImapServer.DEFAULT_IMAP_PORT, null);
	}
	public ImapServer(String host, Authenticator authenticator) {
		this(host, ImapServer.DEFAULT_IMAP_PORT, authenticator);
	}
	/** POP3 server defined with its host and port. */
	public ImapServer(String host, int port) {
		this(host, port, null);
	}
	/** SMTP server defined with its host and authentication. */
	public ImapServer(String host, int port, Authenticator authenticator) {
		this.host = host;
		this.port = port;
		this.authenticator = authenticator;
		sessionProperties = createSessionProperties();
	}
	public ImapServer(String host, int port, String username, String password) {
		this(host, port, new SimpleAuthenticator(username, password));
	}
	/** {@inheritDoc} */
	public ReceiveMailSession createSession() {
		Session session = Session.getInstance(sessionProperties, authenticator);
		Store store;
		try {
			store = getStore(session);
		} catch (NoSuchProviderException nspex) {
			throw new MailException("Unable to create IMAP session", nspex);
		}
		return new ReceiveMailSession(session, store);
	}
	/** Prepares mail session properties. */
	protected Properties createSessionProperties() {
		Properties props = new Properties();
		props.setProperty(ImapServer.MAIL_IMAP_HOST, host);
		props.setProperty(ImapServer.MAIL_IMAP_PORT, String.valueOf(port));
		return props;
	}
	/** Returns authenticator. */
	public Authenticator getAuthenticator() {
		return authenticator;
	}
	// ---------------------------------------------------------------- getters
	/** Returns POP host address. */
	public String getHost() {
		return host;
	}
	/** Returns current port. */
	public int getPort() {
		return port;
	}
	/** Returns email store. */
	protected Store getStore(Session session) throws NoSuchProviderException {
		return session.getStore(ImapServer.PROTOCOL_IMAP);
	}
}