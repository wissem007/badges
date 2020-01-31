// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

/** Create {@link tn.com.digivoip.comman.mail.SendMailSession email seding sessions}. */
public interface SendMailSessionProvider{

	/** Creates new sending mail session. */
	SendMailSession createSession();
}
