package tn.com.smartsoft.framework.dao.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

public class AliasSqlToBeanResultTransformer implements ResultTransformer{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private final Class			resultClass;
	private boolean				isCtree				= false;

	public AliasSqlToBeanResultTransformer(Class resultClass, boolean isCtree) {
		if (resultClass == null) throw new IllegalArgumentException("resultClass cannot be null");
		this.isCtree = isCtree;
		this.resultClass = resultClass;
	}
	public Object transformTuple(Object[] tuple, String[] aliases) {
		try {
			Object result = resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				if (aliases[i] != null) {
					String alias = aliases[i];
					Object valueDb = tuple[i];
					alias = getJavaPropertyName(alias);
					if (result instanceof HashMap) {
						((HashMap) result).put(alias.toUpperCase(), valueDb);
					} else {
						Class typedClass = PropertyUtils.getPropertyType(result, alias);
						if (typedClass != null && typedClass.isAssignableFrom(Date.class) && isCtree) {
							valueDb = transformCtreeDate(valueDb, typedClass);
						}
						if (typedClass != null && valueDb != null) BeanUtils.setProperty(result, alias, valueDb);
					}
				}
			}
			return result;
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}
	private Object transformCtreeDate(Object valueDb, Class typedClass) {
		String value = String.valueOf(valueDb);
		if (value.length() == 8) {
			int an = Integer.parseInt(value.substring(0, 4));
			int mo = Integer.parseInt(value.substring(4, 6));
			int jo = Integer.parseInt(value.substring(6, 8));
			GregorianCalendar cal = new GregorianCalendar(an, mo - 1, jo);
			valueDb = cal.getTime();
			return valueDb;
		}
		return null;
	}
	private String getJavaPropertyName(String name) {
		return getJavaPropertyName(name, false);
	}
	private String getJavaPropertyName(String name, boolean uppercaseFirst) {
		StringBuffer buf = new StringBuffer(name.length());
		int length = name.length();
		char c = name.charAt(0);
		buf.append((uppercaseFirst) ? (Character.toUpperCase(c)) : (Character.toLowerCase(c)));
		boolean uppercaseNext = false;
		for (int i = 1; i < length; i++) {
			c = name.charAt(i);
			if (c == '_') uppercaseNext = true;
			else if (uppercaseNext) {
				buf.append(Character.toUpperCase(c));
				uppercaseNext = false;
			} else buf.append(Character.toLowerCase(c));
		}
		return buf.toString();
	}
	public List transformList(List collection) {
		return collection;
	}
}
