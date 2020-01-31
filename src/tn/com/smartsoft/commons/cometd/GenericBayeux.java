package tn.com.smartsoft.commons.cometd;

import java.io.Serializable;

import org.cometd.Bayeux;

public class GenericBayeux implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bayeux bayeux;
	private GenericBayeuxService bayeuxService;

	public GenericBayeux(Bayeux bayeux) {
		super();
		this.bayeux = bayeux;
		this.bayeuxService = new GenericBayeuxService(this.bayeux);
	}

	public void addSubscribee(String name) {
		bayeuxService.addSubscribee(name);
	}

	public void publish(String name, Object value) {
		bayeuxService.publish(name, value);
	}

	public void publish(AbstractPublishBayeux publishBayeux, Object value) {
		bayeuxService.publish(publishBayeux.getChannelId(), publishBayeux.createPublishData(value));
	}

}
