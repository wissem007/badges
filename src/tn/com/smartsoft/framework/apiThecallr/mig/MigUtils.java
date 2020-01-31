package tn.com.smartsoft.framework.apiThecallr.mig;

public class MigUtils {
	
	public static GenerateurDB createOracleConnection(String ip, String sid, String login, String passWord) {
		String uRL = "jdbc:oracle:thin:@" + ip + ":1521:" + sid;
		String driver = "oracle.jdbc.driver.OracleDriver";
		return new GenerateurDB(uRL, driver, login, passWord);
	}
	
	public static GenerateurDB createCtreeConnection(String ip, String sid, String login, String passWord) {
		String uRL = "jdbc:ctree:6597@" + ip + ":" + sid;
		String driver = "ctree.jdbc.ctreeDriver";
		return new GenerateurDB(uRL, driver, login, passWord);
	}
	
	public static GenerateurDB createPgConnection(String ip, String sid, String login, String passWord) {
		String uRL = "jdbc:postgresql://" + ip + "/" + sid;
		String driver = "org.postgresql.Driver";
		GenerateurDB generateurDB = new GenerateurDB(uRL, driver, login, passWord);
		generateurDB.setSid(sid);
		generateurDB.setIp(ip);
		return generateurDB;
	}
	
	public static GenerateurDB createMySqlConnection(String ip, String sid, String login, String passWord) {
		String uRL = "jdbc:mysql://" + ip + ":3306/" + sid + "?useUnicode=true&characterEncoding=utf-8";
		String driver = "com.mysql.jdbc.Driver";
		GenerateurDB generateurDB = new GenerateurDB(uRL, driver, login, passWord);
		generateurDB.setSid(sid);
		generateurDB.setIp(ip);
		return generateurDB;
	}
}
