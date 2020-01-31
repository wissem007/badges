package tn.com.digivoip.comman.jsonrpc2;

import java.net.HttpURLConnection;
import org.apache.commons.codec.binary.Base64;
import tn.com.digivoip.comman.jsonrpc2.client.ConnectionConfigurator;

public class BasicAuthenticator implements ConnectionConfigurator{
	
	private String	siteAuthentication;
	
	public BasicAuthenticator(String login, String password) {
		super();
		this.siteAuthentication = login + ":" + password;
	}
	public void configure(HttpURLConnection connection) {
		// add custom HTTP header
		String encoded_site_authentication = new String(new Base64().encode(siteAuthentication.getBytes())).replaceAll("[\n\r]", "");
		connection.addRequestProperty("Authorization", "Basic " + encoded_site_authentication);
	}
}