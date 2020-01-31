package tn.com.digivoip.comman.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import tn.com.digivoip.comman.string.StringPool;

/** Represents simple plain SMTP server for sending emails. */
public class SmtpServer implements SendMailSessionProvider{
	
	protected static final String	MAIL_HOST				= "mail.host";
	protected static final String	MAIL_SMTP_HOST			= "mail.smtp.host";
	protected static final String	MAIL_SMTP_PORT			= "mail.smtp.port";
	protected static final String	MAIL_SMTP_AUTH			= "mail.smtp.auth";
	protected static final String	MAIL_TRANSPORT_PROTOCOL	= "mail.transport.protocol";
	protected static final String	MAIL_ADDRESS_STRICT		= "mail.mime.address.strict";
	protected static final String	PROTOCOL_SMTP			= "smtp";
	protected static final int		DEFAULT_SMTP_PORT		= 25;
	
	public static void main(String[] args) {
		SmtpServer pop3Server = new SmtpServer("invitro-tunisie.com", 25, "devis@invitro-tunisie.com", "yol0CGOH8EvP");
		SendMailSession session = pop3Server.createSession();
		session.open();
		Email addText = new Email().from("devis@invitro-tunisie.com").subject("test failer mail").to("sddd@3d.com.tn").addText("dddddddddddddd");
		addText.setHeader("id-agent-digi", "0120120");
		addText.setHeader("id-msg-digi", "0120120");
		session.sendMail(addText);
		System.out.println();
	}
	
	protected final String			host;
	protected final int				port;
	protected final Authenticator	authenticator;
	protected final Properties		sessionProperties;
	
	/** SMTP server defined with its host and default port. */
	public SmtpServer(String host) {
		this(host, SmtpServer.DEFAULT_SMTP_PORT, null);
	}
	public SmtpServer(String host, Authenticator authenticator) {
		this(host, SmtpServer.DEFAULT_SMTP_PORT, authenticator);
	}
	/** SMTP server defined with its host and port. */
	public SmtpServer(String host, int port) {
		this(host, port, null);
	}
	/** SMTP server defined with its host and authentication. */
	public SmtpServer(String host, int port, Authenticator authenticator) {
		this.host = host;
		this.port = port;
		this.authenticator = authenticator;
		sessionProperties = createSessionProperties();
	}
	public SmtpServer(String host, int port, String username, String password) {
		this(host, port, new SimpleAuthenticator(username, password));
	}
	public SmtpServer(String host, String username, String password) {
		this(host, SmtpServer.DEFAULT_SMTP_PORT, new SimpleAuthenticator(username, password));
	}
	/** {@inheritDoc} */
	public SendMailSession createSession() {
		Session mailSession = Session.getInstance(sessionProperties, authenticator);
		Transport mailTransport;
		try {
			mailTransport = getTransport(mailSession);
		} catch (NoSuchProviderException nspex) {
			throw new MailException(nspex);
		}
		return new SendMailSession(mailSession, mailTransport);
	}
	/** Prepares mail session properties. */
	protected Properties createSessionProperties() {
		Properties props = new Properties();
		props.setProperty(SmtpServer.MAIL_TRANSPORT_PROTOCOL, SmtpServer.PROTOCOL_SMTP);
		props.setProperty(SmtpServer.MAIL_HOST, host);
		props.setProperty(SmtpServer.MAIL_SMTP_HOST, host);
		props.setProperty(SmtpServer.MAIL_ADDRESS_STRICT, String.valueOf(false));
		props.setProperty("mail.debug", String.valueOf(false));
		props.setProperty(SmtpServer.MAIL_SMTP_PORT, String.valueOf(port));
		if (authenticator != null) {
			props.setProperty(SmtpServer.MAIL_SMTP_AUTH, StringPool.TRUE);
		}
		return props;
	}
	// ---------------------------------------------------------------- getters
	/** Returns authenticator. */
	public Authenticator getAuthenticator() {
		return authenticator;
	}
	/** Returns SMTP host address. */
	public String getHost() {
		return host;
	}
	/** Returns current port. */
	public int getPort() {
		return port;
	}
	/** Returns mail transport. */
	protected Transport getTransport(Session session) throws NoSuchProviderException {
		return session.getTransport(SmtpServer.PROTOCOL_SMTP);
	}
}
