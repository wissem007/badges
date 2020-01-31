package tn.com.digivoip.comman.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import tn.com.digivoip.comman.string.StringPool;

/** Represents simple plain POP server for sending emails. */
public class Pop3Server implements ReceiveMailSessionProvider{
	
	protected static final String	MAIL_POP3_PORT		= "mail.pop3.port";
	protected static final String	MAIL_POP3_HOST		= "mail.pop3.host";
	protected static final String	MAIL_POP3_AUTH		= "mail.pop3.auth";
	protected static final String	MAIL_ADDRESS_STRICT	= "mail.mime.address.strict";
	protected static final String	PROTOCOL_POP3		= "pop3";
	protected static final int		DEFAULT_POP3_PORT	= 110;
	protected final boolean			isDebug				= true;
	protected final String			host;
	protected final int				port;
	protected final Authenticator	authenticator;
	protected final Properties		sessionProperties;
	
	/** POP3 server defined with its host and default port. */
	public Pop3Server(String host) {
		this(host, Pop3Server.DEFAULT_POP3_PORT, null);
	}
	public Pop3Server(String host, Authenticator authenticator) {
		this(host, Pop3Server.DEFAULT_POP3_PORT, authenticator);
	}
	/** POP3 server defined with its host and port. */
	public Pop3Server(String host, int port) {
		this(host, port, null);
	}
	/** SMTP server defined with its host and authentication. */
	public Pop3Server(String host, int port, Authenticator authenticator) {
		this.host = host;
		this.port = port;
		this.authenticator = authenticator;
		sessionProperties = createSessionProperties();
	}
	public Pop3Server(String host, int port, String username, String password) {
		this(host, port, new SimpleAuthenticator(username, password));
	}
	/** {@inheritDoc} */
	public ReceiveMailSession createSession() {
		Session session = Session.getInstance(sessionProperties, authenticator);
		Store store;
		try {
			store = getStore(session);
		} catch (Exception nspex) {
			throw new MailException("Unable to create POP3 session", nspex);
		}
		return new ReceiveMailSession(session, store);
	}
	/** Prepares mail session properties. */
	protected Properties createSessionProperties() {
		Properties props = new Properties();
		props.setProperty(Pop3Server.MAIL_POP3_HOST, host);
		System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
		System.setProperty("mail.mime.multipart.allowempty", "true");
		props.setProperty(Pop3Server.MAIL_POP3_PORT, String.valueOf(port));
		props.setProperty("mail.debug", String.valueOf(isDebug));
		props.setProperty(SmtpServer.MAIL_ADDRESS_STRICT, String.valueOf(false));
		if (authenticator != null) {
			props.setProperty(Pop3Server.MAIL_POP3_AUTH, StringPool.TRUE);
		}
		return props;
	}
	// ---------------------------------------------------------------- getters
	/** Returns authenticator. */
	public Authenticator getAuthenticator() {
		return authenticator;
	}
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
		return session.getStore(Pop3Server.PROTOCOL_POP3);
	}
}