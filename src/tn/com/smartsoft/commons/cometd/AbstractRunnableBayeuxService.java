package tn.com.smartsoft.commons.cometd;

import org.cometd.Bayeux;

public abstract class AbstractRunnableBayeuxService extends AbstractBayeuxService implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractRunnableBayeuxService(Bayeux bayeux) {
		super(bayeux);
	}
}
