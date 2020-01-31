package tn.com.smartsoft.commons.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.cayenne.property.PropertyUtils;

import tn.com.smartsoft.commons.log.Logger;

public class BeanComparator implements Comparator<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(BeanComparator.class);

	private String fieldName;

	private SorterType sort;

	private boolean isLowerCase;

	public BeanComparator(String fieldName, SorterType sort, boolean isLowerCase) {
		super();
		this.fieldName = fieldName;
		this.sort = sort;
		this.isLowerCase = isLowerCase;
	}

	public BeanComparator(String fieldName, SorterType sort) {
		this(fieldName, sort, false);
	}

	public int compare(Object obj, Object obj1) {
		try {
			Object value1 = PropertyUtils.getProperty(obj, fieldName);
			Object value2 = PropertyUtils.getProperty(obj1, fieldName);
			if (value1 == null && value2 == null)
				return 0;
			boolean ascendante = sort.equals(SorterType.ASC);
			if (value1 == null)
				return ascendante ? -1 : 1;
			if (value2 == null)
				return ascendante ? 1 : -1;
			if (value1 instanceof Comparable) {
				if (isLowerCase)
					return ascendante ? ((Comparable) (value1.toString()).toLowerCase()).compareTo(value2.toString().toLowerCase()) : -((Comparable) (value1.toString()).toLowerCase())
							.compareTo(value2.toString().toLowerCase());
				else
					return ascendante ? ((Comparable) value1).compareTo(value2) : -((Comparable) value1).compareTo(value2);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return 0;
	}

	public void sort(List list) {
		Collections.sort(list, this);
	}

	public static void sort(String fieldName, SorterType sort, List list) {
		BeanComparator beanComparator = new BeanComparator(fieldName, sort);
		beanComparator.sort(list);
	}

	public static void sort(String[] fieldName, SorterType sort, List list) {
		for (int i = 0; i < fieldName.length; i++) {
			BeanComparator beanComparator = new BeanComparator(fieldName[i], sort);
			beanComparator.sort(list);
		}
	}

	public Object[] getSortArray(List l) {
		Object[] a = l.toArray();
		Arrays.sort(a, this);
		return a;
	}
}
