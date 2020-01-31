// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import tn.com.digivoip.comman.string.StringPool;

/** Secure SMTP server (STARTTLS) for sending emails. */
public class SmtpSslServer extends SmtpServer{

	protected static final String	MAIL_SMTP_STARTTLS_ENABLE			= "mail.smtp.starttls.enable";
	protected static final String	MAIL_SMTP_SOCKET_FACTORY_PORT		= "mail.smtp.socketFactory.port";
	protected static final String	MAIL_SMTP_SOCKET_FACTORY_CLASS		= "mail.smtp.socketFactory.class";
	protected static final String	MAIL_SMTP_SOCKET_FACTORY_FALLBACK	= "mail.smtp.socketFactory.fallback";
	protected static final int		DEFAULT_SSL_PORT					= 465;

	public SmtpSslServer(String host, Authenticator authenticator) {
		super(host, SmtpSslServer.DEFAULT_SSL_PORT, authenticator);
	}
	public SmtpSslServer(String host, int port, Authenticator authenticator) {
		super(host, port, authenticator);
	}
	public SmtpSslServer(String host, int port, String username, String password) {
		super(host, port, username, password);
	}
	public SmtpSslServer(String host, String username, String password) {
		super(host, SmtpSslServer.DEFAULT_SSL_PORT, username, password);
	}
	@Override
	protected Properties createSessionProperties() {
		Properties props = super.createSessionProperties();
		props.setProperty(SmtpSslServer.MAIL_SMTP_STARTTLS_ENABLE, StringPool.TRUE);
		props.setProperty(SmtpSslServer.MAIL_SMTP_SOCKET_FACTORY_PORT, String.valueOf(port));
		props.setProperty(SmtpServer.MAIL_SMTP_PORT, String.valueOf(port));
		props.setProperty(SmtpSslServer.MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
		props.setProperty(SmtpSslServer.MAIL_SMTP_SOCKET_FACTORY_FALLBACK, StringPool.FALSE);
		props.setProperty(SmtpServer.MAIL_HOST, host);
		return props;
	}
}
