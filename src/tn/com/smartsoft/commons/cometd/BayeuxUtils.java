package tn.com.smartsoft.commons.cometd;

import javax.servlet.ServletContext;

import org.cometd.Bayeux;
import org.cometd.server.ext.AcknowledgedMessagesExtension;
import org.cometd.server.ext.TimesyncExtension;

public class BayeuxUtils {

	public static final String ATTRIBUTE_BAYEUX = "tn.sss.bayeux";

	public static Bayeux getBayeux(ServletContext servletContext) {
		return (Bayeux) servletContext.getAttribute(Bayeux.ATTRIBUTE);
	}

	public static GenericBayeux getGenericBayeux(ServletContext servletContext) {
		return (GenericBayeux) servletContext.getAttribute(ATTRIBUTE_BAYEUX);
	}

	public static void addGenericBayeux(ServletContext servletContext) {
		servletContext.setAttribute(ATTRIBUTE_BAYEUX, new GenericBayeux(getBayeux(servletContext)));
	}

	public static void addBayeuxUtility(ServletContext servletContext) {
		addGenericBayeux(servletContext);
		addBayeuxUtility(getBayeux(servletContext));
	}

	public static void addBayeuxUtility(Bayeux bayeux) {
		new EchoRPC(bayeux);
		new Monitor(bayeux);
		bayeux.addExtension(new TimesyncExtension());
		bayeux.addExtension(new AcknowledgedMessagesExtension());
	}
}
