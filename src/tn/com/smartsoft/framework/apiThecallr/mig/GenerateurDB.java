package tn.com.smartsoft.framework.apiThecallr.mig;

import java.sql.Connection;
import java.sql.SQLException;

public class GenerateurDB {
	
	private Connection connection;
	private String sid;
	private String ip;
	
	public GenerateurDB(String connectionURL, String oracleDriver, String userName, String password) {
		super();
		ConnectionConf ConnectionConf = new ConnectionConf();
		ConnectionConf.setConnectionURL(connectionURL);
		ConnectionConf.setDriver(oracleDriver);
		ConnectionConf.setPassword(password);
		ConnectionConf.setUserName(userName);
		try {
			connection = ConnectionConf.creeConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getName() {
		return ip + " " + sid;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void close() {
		try {
			if (connection != null)
				getConnection().close();
		} catch (SQLException e) {
		}
	}
	
}
