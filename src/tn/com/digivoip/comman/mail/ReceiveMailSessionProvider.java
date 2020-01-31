// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.
package tn.com.digivoip.comman.mail;

/** Create {@link ReceiveMailSession email receiving sessions}. */
public interface ReceiveMailSessionProvider{

	/** Creates new receiving mail session. */
	ReceiveMailSession createSession();
}
