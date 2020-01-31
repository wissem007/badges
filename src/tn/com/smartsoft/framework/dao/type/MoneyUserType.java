package tn.com.smartsoft.framework.dao.type;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import tn.com.smartsoft.framework.beans.Money;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;

public class MoneyUserType implements CompositeUserType {

	public MoneyUserType() {
	}

	public String[] getPropertyNames() {
		return (new String[] { "devise", "montant" });
	}

	public Type[] getPropertyTypes() {
		return (new Type[] { Hibernate.LONG, Hibernate.BIG_DECIMAL });
	}

	public Object getPropertyValue(Object component, int property) throws HibernateException {
		Money money = (Money) component;
		if (property == 0)
			return money.getDevise();
		else
			return money.getMontant();
	}

	public void setPropertyValue(Object obj, int i, Object obj1) throws HibernateException {
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return Money.class;
	}

	public boolean equals(Object money1, Object money2) throws HibernateException {
		if (money1 == money2)
			return true;
		if (money1 == null || money2 == null)
			return false;
		else
			return money1.equals(money2);
	}

	public int hashCode(Object arg0) throws HibernateException {
		return 0;
	}

	public Object nullSafeGet(ResultSet resultSet, String names[], SessionImplementor session, Object owner) throws HibernateException, SQLException {
		DeviseBean devise = null;
		if (resultSet == null)
			return null;
		Long deviseId = Long.valueOf(resultSet.getLong(names[0]));
		if (deviseId == null || deviseId.longValue() == 0) {
			devise = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getDeviseBase();
		} else {
			devise = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getDeviseBean(deviseId);
		}
		BigDecimal value = resultSet.getBigDecimal(names[1]);
		return value != null ? new Money(devise, value) : new Money(devise, BigDecimal.valueOf(0L));
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, 2);
			statement.setNull(index + 1, 12);
		} else {
			Money money = (Money) value;
			Long deviseId = money.getDevise().getDeviseId();
			statement.setLong(index, deviseId.longValue());
			statement.setBigDecimal(index + 1, money.getMontant());
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(Object value, Object arg1, SessionImplementor session, Object obj) throws HibernateException {
		return null;
	}
}
