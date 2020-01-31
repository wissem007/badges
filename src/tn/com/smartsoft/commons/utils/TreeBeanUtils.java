package tn.com.smartsoft.commons.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeBeanUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void loadChild(List<Object> list, Object parent, String childPropertyName, String keyProperty, String parentProperty) {
		Object parentKeyValue = BeanObjectUtils.getPropertyValue(parent, keyProperty);
		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);
			Object parentValue = BeanObjectUtils.getPropertyValue(bean, parentProperty);
			if (parentValue != null && parentKeyValue.equals(parentValue)) {
				List childs = (List) BeanObjectUtils.getPropertyValue(parent, childPropertyName);
				if (childs == null) {
					childs = new ArrayList();
					BeanObjectUtils.setPropertyValue(parent, childPropertyName, childs);
				}
				childs.add(bean);
				loadChild(list, bean, childPropertyName, keyProperty, parentProperty);
			}
		}
	}

	public static List<Object> loadTree(List<Object> list, String childPropertyName, String keyProperty, String parentProperty) {
		List<Object> listRes = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);
			Object parentValue = BeanObjectUtils.getPropertyValue(bean, parentProperty);
			if (parentValue == null) {
				loadChild(list, bean, childPropertyName, keyProperty, parentProperty);
				listRes.add(bean);
			}
		}
		return listRes;
	}
}
