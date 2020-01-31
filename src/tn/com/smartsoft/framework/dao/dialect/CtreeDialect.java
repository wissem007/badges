package tn.com.smartsoft.framework.dao.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;

public class CtreeDialect extends org.hibernate.dialect.Dialect {

	public CtreeDialect() {
		super();
		registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
		// registerColumnType(Types.NVARCHAR, "character");
	}

}