package tn.com.smartsoft.commons.cometd;

import java.io.Serializable;

import org.cometd.Bayeux;
import org.cometd.Channel;
import org.cometd.server.BayeuxService;

public abstract class AbstractBayeuxService extends BayeuxService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SERVICE_PREFIX_NAME = "/service/";
	public static final String CHANNEL_PREFIX_NAME = "/sss/";

	public AbstractBayeuxService(Bayeux bayeux) {
		super(bayeux, "sss");

	}

	public void addSubscribee(String name) {
		subscribe(SERVICE_PREFIX_NAME + name, name);
	}

	public void publish(String name, Object value) {
		Channel channel = getBayeux().getChannel(CHANNEL_PREFIX_NAME + name, false);
		if (channel != null)
			channel.publish(getClient(), value, null);
	}
}
