package tn.com.smartsoft.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class ListUtils {
	
	@SuppressWarnings("unchecked")
	public static List<?> selectByProperty(List<?> list, final String property, final Object value) {
		if (list == null)
			return null;
		Predicate testPredicate = new Predicate() {
			
			public boolean evaluate(Object bean) {
				Object value1;
				try {
					value1 = PropertyUtils.getProperty(bean, property);
				} catch (Exception e) {
					throw new TechnicalException(e);
				}
				return ValueUtils.equals(value1, value);
			}
		};
		return new ArrayList<Object>(CollectionUtils.select(list, testPredicate));
	}
	
	@SuppressWarnings("unchecked")
	public static List<DataBusinessBean> selectDataBusinessByProperty(List<DataBusinessBean> list, final String property, final Object value) {
		if (list == null)
			return null;
		Predicate testPredicate = new Predicate() {
			
			public boolean evaluate(Object bean) {
				Object value1;
				try {
					value1 = PropertyUtils.getProperty(bean, property);
				} catch (Exception e) {
					throw new TechnicalException(e);
				}
				return ValueUtils.equals(value1, value);
			}
		};
		return new ArrayList<DataBusinessBean>(CollectionUtils.select(list, testPredicate));
	}
	
	public static Object findByProperty(List<?> list, final String property, final Object value) {
		if (list == null)
			return null;
		Predicate testPredicate = new Predicate() {
			
			public boolean evaluate(Object bean) {
				Object value1;
				try {
					value1 = PropertyUtils.getProperty(bean, property);
				} catch (Exception e) {
					throw new TechnicalException(e);
				}
				return ValueUtils.equals(value1, value);
			}
		};
		return CollectionUtils.find(list, testPredicate);
	}
	
	public static Map<Object, Object> toMap(List<?> list, final String property) {
		Map<Object, Object> maps = new HashMap<Object, Object>();
		if (list == null)
			return maps;
		for (int i = 0; i < list.size(); i++) {
			Object value = list.get(i);
			Object key = BeanObjectUtils.getPropertyValue(value, property);
			maps.put(key, value);
		}
		return maps;
	}
}
