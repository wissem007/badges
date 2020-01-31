package tn.com.digivoip.comman.jsonrpc2;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import tn.com.digivoip.comman.jsonrpc2.client.JSONRPC2Session;

public class Tests{
	
	public static void maine(String[] args) throws Exception {
		URL serverURL = null;
		serverURL = new URL("https://api.thecallr.com");
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
		mySession.getOptions().setRequestContentType("application/json-rpc; charset=utf-8");
		mySession.getOptions().ignoreVersion(true);
		mySession.setConnectionConfigurator(new BasicAuthenticator("OPTIMIS", "qvvtwtvv"));
		String method = "cdr.get";
		int requestID = 42;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);
		List<Object> prs = Arrays.asList(new Object[] { "OUT", "2014-06-02 00:00:00", "2014-06-02 23:59:59", null, null });
		request.setPositionalParams(prs);
		// Send request
		JSONRPC2Response response = null;
		response = mySession.send(request);
		// Print response result / error
		long o = System.currentTimeMillis();
		if (response.indicatesSuccess()) System.out.println(response.getResult());
		else System.out.println(response.getError().getMessage());
		System.out.println(System.currentTimeMillis() - o);
	}
	public static void mainss(String[] args) throws Exception {
		URL serverURL = new URL("https://api.thecallr.com");
		String login = "OPTIMIS";
		String password = "qvvtwtvv";
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
		mySession.getOptions().setRequestContentType("application/json-rpc; charset=utf-8");
		mySession.getOptions().ignoreVersion(true);
		mySession.setConnectionConfigurator(new BasicAuthenticator(login, password));
		String method = "analytics/calls.outbound_destinations";
		int requestID = 42;
		List<Object> prs = Arrays.asList(new Object[] { "DURATION", "2014-01-01 00:00:00", "2014-08-01 00:00:00", 100 });
		JSONRPC2Request request = new JSONRPC2Request(method, prs, requestID);
		JSONRPC2Response response = mySession.send(request);
		long o = System.currentTimeMillis();
		if (response.indicatesSuccess()) {
			Object result = response.getResult();
			System.out.println(result);
		}
		else {
			JSONRPC2Error error = response.getError();
			System.out.println(error.getMessage());
		}
		System.out.println(System.currentTimeMillis() - o);
	}
}
