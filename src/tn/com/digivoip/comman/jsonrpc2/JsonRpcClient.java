package tn.com.digivoip.comman.jsonrpc2;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import tn.com.digivoip.comman.jsonrpc2.client.JSONRPC2Session;
import tn.com.digivoip.comman.jsonrpc2.client.JSONRPC2SessionException;
import tn.com.digivoip.framework.exception.TechnicalException;

public class JsonRpcClient implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private String				serverURL;
	private String				login;
	private String				password;
	private JSONRPC2Session		mySession;
	
	public JsonRpcClient(String serverURL, String login, String password) {
		super();
		this.serverURL = serverURL;
		this.login = login;
		this.password = password;
	}
	public void open() {
		try {
			URL url = new URL(serverURL);
			this.mySession = new JSONRPC2Session(url);
			this.mySession.getOptions().setRequestContentType("application/json-rpc; charset=utf-8");
			this.mySession.getOptions().ignoreVersion(true);
			this.mySession.setConnectionConfigurator(new BasicAuthenticator(this.login, this.password));
		} catch (MalformedURLException e) {
			throw new TechnicalException(e);
		}
	}
	public void close() {
		this.mySession = null;
	}
	public Object execute(String method, Object[] params) {
		try {
			if (this.mySession == null) throw new TechnicalException("Session close");
			int requestID = (int) (Math.random() * 1000);
			JSONRPC2Request request = new JSONRPC2Request(method, Arrays.asList(params), requestID);
			JSONRPC2Response response = this.mySession.send(request);
			if (response.indicatesSuccess()) {
				return response.getResult();
			}
			else {
				throw new TechnicalException(response.getError());
			}
		} catch (JSONRPC2SessionException e) {
			throw new TechnicalException(e);
		}
	}
	public Object execute(String method, Object param0, Object param1, Object param2, Object param3, Object param4) {
		return this.execute(method, new Object[] { param0, param1, param2, param3, param4 });
	}
	public Object execute(String method, Object param0, Object param1, Object param2, Object param3) {
		return this.execute(method, new Object[] { param0, param1, param2, param3 });
	}
	public Object execute(String method, Object param0, Object param1, Object param2) {
		return this.execute(method, new Object[] { param0, param1, param2 });
	}
	public Object execute(String method, Object param0, Object param1) {
		return this.execute(method, new Object[] { param0, param1 });
	}
	public Object execute(String method, Object param0) {
		return this.execute(method, new Object[] { param0 });
	}
	public Object execute(String method) {
		return this.execute(method, new Object[] {});
	}
	public static void main(String[] args) {
		JsonRpcClient mySession = new JsonRpcClient("https://api.thecallr.com", "OPTIMIS", "qvvtwtvv");
		mySession.open();
		Object result = mySession.execute("analytics/calls.outbound_destinations", new Object[] { "DURATION", "2014-01-01 00:00:00", "2014-08-01 00:00:00", 100 });
		System.out.println(result);
		result = mySession.execute("analytics/calls.outbound_countries", new Object[] { "DURATION", "2014-01-01 00:00:00", "2014-08-01 00:00:00", 1000000000 });
		System.out.println(result);
	}
}
