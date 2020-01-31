package tn.com.smartsoft.commons.cometd;

import java.io.Serializable;
 
import org.cometd.Bayeux;
import org.cometd.Client;
import org.cometd.server.BayeuxService;

import tn.com.smartsoft.commons.log.Logger;

public class EchoRPC extends BayeuxService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass());

	public Object doEcho(Client client, Object data) {
		logger.info((new StringBuilder()).append("ECHO from ").append(client).append(" ").append(data).toString());
		return data;
	}

	public EchoRPC(Bayeux bayeux) {
		super(bayeux, "echo");
		subscribe("/service/echo", "doEcho");
	}
}