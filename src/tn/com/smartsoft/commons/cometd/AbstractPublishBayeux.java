package tn.com.smartsoft.commons.cometd;

import java.io.Serializable;

public abstract class AbstractPublishBayeux implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractBayeuxService bayeuxService;

	public AbstractPublishBayeux(AbstractBayeuxService bayeuxService) {
		super();
		this.bayeuxService = bayeuxService;
	}

	public abstract String getChannelId();

	public abstract Object createPublishData(Object value);

	public void publish(Object value) {
		bayeuxService.publish(getChannelId(), createPublishData(value));
	}
}
