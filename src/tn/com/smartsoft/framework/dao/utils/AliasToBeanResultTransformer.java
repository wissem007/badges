package tn.com.smartsoft.framework.dao.utils;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

public class AliasToBeanResultTransformer implements ResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Class resultClass;

	public AliasToBeanResultTransformer(Class resultClass) {
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		try {
			result = resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				if (aliases[i] != null) {
					String alias = aliases[i];
					Object valueDb = tuple[i];
					//System.out.println("alias:"+alias);
					//System.out.println("valueDb:"+valueDb);
					if( valueDb != null)
						BeanUtils.setProperty(result, alias, valueDb);
				}
			}
		} catch (Exception e) {
			throw new HibernateException(e);
		}
		return result;
	}

	public List transformList(List collection) {
		return collection;
	}

}
