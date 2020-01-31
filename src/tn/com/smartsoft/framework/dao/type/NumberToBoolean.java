package tn.com.smartsoft.framework.dao.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.usertype.UserType;

public class NumberToBoolean implements UserType {

	public int[] sqlTypes() {
		return new int[] { Types.INTEGER };
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return Boolean.class;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor si, Object owner) throws HibernateException, SQLException {
		return (rs.getInt(names[0]) != 0);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor si) throws HibernateException, SQLException {
		st.setInt(index, Boolean.TRUE.equals(value) ? 1 : 0);
	}

	public boolean isMutable() {
		return false;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null || y == null) {
			return false;
		} else {
			return x.equals(y);
		}
	}

	public int hashCode(Object x) throws HibernateException {
		assert (x != null);
		return x.hashCode();
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		return null;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
	}
}