package tn.com.smartsoft.framework.presentation.view.action.model.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.comparators.ComparableComparator;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.framework.presentation.view.action.model.CompositeModel;

public class BeanComparator implements Comparator<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(BeanComparator.class);

	private String fieldName;

	private SorterType sort;

	private CompositeModel beanModel;

	public BeanComparator(CompositeModel beanModel, String fieldName, SorterType sort) {
		super();
		this.fieldName = fieldName;
		this.sort = sort;
		this.beanModel = beanModel;
	}

	public int compare(Object obj, Object obj1) {
		try {
			Object value1 = ModelUtils.getValue(beanModel, obj, fieldName);
			Object value2 = ModelUtils.getValue(beanModel, obj1, fieldName);
			boolean ascendante = sort.equals(SorterType.ASC);
			if (value1 == null && value2 == null)
				return 0;
			if (value1 == null)
				return ascendante ? -1 : 1;
			if (value2 == null)
				return ascendante ? 1 : -1;
			ComparableComparator comparableComparator = new ComparableComparator();
			int cmp = comparableComparator.compare(value1, value2);
			return ascendante ? cmp : -cmp;
		} catch (Exception e) {
			log.error(e);
		}
		return 0;
	}

	public void sort(List<Object> list) {
		Collections.sort(list, this);
	}

	public static void sort(CompositeModel beanModel, String fieldName, SorterType sort, List<Object> list) {
		BeanComparator beanComparator = new BeanComparator(beanModel, fieldName, sort);
		beanComparator.sort(list);
	}

	public Object[] getSortArray(List<Object> l) {
		Object[] a = l.toArray();
		Arrays.sort(a, this);
		return a;
	}
}
