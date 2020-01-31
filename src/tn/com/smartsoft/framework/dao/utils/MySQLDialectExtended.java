package tn.com.smartsoft.framework.dao.utils;

import java.sql.Types;
import org.hibernate.dialect.MySQL5Dialect;

public class MySQLDialectExtended extends MySQL5Dialect{

	public MySQLDialectExtended() {
		super();
		registerColumnType(Types.LONGVARBINARY, "blob");
		registerHibernateType(Types.LONGVARBINARY, "blob");
		registerColumnType(Types.BLOB, "blob");
		registerHibernateType(Types.BLOB, "blob");
	}
}