package tn.com.smartsoft.framework.apiThecallr.mig;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

import oracle.jdbc.driver.OracleConnection;

public class ConnectionConf {
	private String connectionURL;
	private String userName;
	private String password;
	private String driver;
	
	public ConnectionConf(String connectionURL, String driver, String userName, String password) {
		super();
		this.connectionURL = connectionURL;
		this.userName = userName;
		this.password = password;
		this.driver = driver;
	}
	
	public ConnectionConf() {
	}
	
	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}
	
	public String getConnectionURL() {
		return connectionURL;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public Connection creeConnection() throws Exception {
		Driver driver = (Driver) Class.forName(getDriver()).newInstance();
		Properties info = new Properties();
		info.put("user", getUserName());
		info.put("password", getPassword());
		Connection connection = driver.connect(getConnectionURL(), info);
		if (connection instanceof OracleConnection) {
			((oracle.jdbc.OracleConnection) connection).setRemarksReporting(true);
		}
		
		return connection;
	}
}