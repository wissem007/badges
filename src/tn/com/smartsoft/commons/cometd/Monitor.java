package tn.com.smartsoft.commons.cometd;

import org.cometd.Bayeux;
import org.cometd.Client;
import org.cometd.Message;
import org.cometd.server.BayeuxService;

import tn.com.smartsoft.commons.log.Logger;

public class Monitor extends BayeuxService {
	private Logger logger = Logger.getLogger(this.getClass());

	public Monitor(Bayeux bayeux) {
		super(bayeux, "monitor");
		subscribe("/meta/subscribe", "monitorSubscribe");
		subscribe("/meta/unsubscribe", "monitorUnsubscribe");
		subscribe("/meta/*", "monitorMeta");
	}

	public void monitorSubscribe(Client client, Message message) {
		logger.info((new StringBuilder()).append("Subscribe from ").append(client).append(" for ").append(message.get("subscription")).toString());
	}

	public void monitorUnsubscribe(Client client, Message message) {
		logger.info((new StringBuilder()).append("Unsubscribe from ").append(client).append(" for ").append(message.get("subscription")).toString());
	}

	public void monitorMeta(Client client, Message message) {
		logger.debug(message.toString());
	}

}