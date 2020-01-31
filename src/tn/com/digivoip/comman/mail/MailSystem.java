package tn.com.digivoip.comman.mail;

public class MailSystem{

	protected boolean			initialized;
	public static MailSystem	mailSystem	= new MailSystem();

	public final void defineJavaMailSystemProperties() {
		if (initialized) { return; }
		defineSystemProperties();
		initialized = true;
	}
	/** Defines system properties. Invoked only once. */
	protected void defineSystemProperties() {
		// System.setProperty("mail.mime.encodefilename", "true");
		// System.setProperty("mail.mime.decodefilename", "true");
	}
}