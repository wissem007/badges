package tn.com.smartsoft.framework.presentation.view.window.component.ext.store.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;

public class StoreUtils {
	private static final String PREFIX_TREE_INDEX = "_";
	private static final String INDEX_FIELD_NAME = "index";

	public static int[] getBorneIndex(int limitRow, int startRow, List<?> listValue) {
		int sizeList = listValue.size();
		if (limitRow == -1)
			limitRow = sizeList;
		if (limitRow > sizeList)
			limitRow = sizeList;
		if (limitRow != sizeList)
			limitRow = limitRow + startRow;
		startRow = startRow > limitRow ? 0 : startRow;
		return new int[] { startRow, limitRow };
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> findChildrenNode(List<?> listValue, int parentIndex, String idProperty, String parentProperty) {
		if (parentIndex == -1 || parentIndex > listValue.size())
			return listValue;
		Object parent = listValue.get(parentIndex);
		Object parentKeyValue = BeanObjectUtils.getPropertyValue(parent, idProperty);
		List childs = new ArrayList();
		for (int i = 0; i < listValue.size(); i++) {
			Object bean = listValue.get(i);
			Object parentValue = BeanObjectUtils.getPropertyValue(bean, parentProperty);
			if (parentValue != null && parentKeyValue.equals(parentValue)) {
				childs.add(bean);
			}
		}
		return childs;
	}

	public static List<?> findChildrenNode(int[] treeIndex, Object parentObject, String childrenProperty) {
		if (treeIndex.length == 0 && parentObject instanceof List<?>)
			return ((List<?>) parentObject);
		if (treeIndex.length == 0)
			return null;
		List<?> listValue;
		if (parentObject instanceof List<?>) {
			listValue = ((List<?>) parentObject);
		} else {
			listValue = (List<?>) BeanObjectUtils.getPropertyValue(parentObject, childrenProperty);
		}
		for (int i = 0; i < treeIndex.length; i++) {
			if (listValue == null || listValue.size() <= treeIndex[i]) {
				throw new TechnicalException("invalid index Object :" + treeIndex[i]);
			}
			parentObject = listValue.get(treeIndex[i]);
			listValue = (List<?>) BeanObjectUtils.getPropertyValue(parentObject, childrenProperty);
		}
		return listValue;
	}

	public static Object findNode(int[] treeIndex, Object parentObject, String childrenProperty) {
		if (treeIndex.length == 0)
			return parentObject;
		List<?> listValue;
		if (parentObject instanceof List<?>) {
			listValue = ((List<?>) parentObject);
		} else {
			listValue = (List<?>) BeanObjectUtils.getPropertyValue(parentObject, childrenProperty);
		}
		for (int i = 0; i < treeIndex.length; i++) {
			if (listValue == null || listValue.size() <= treeIndex[i]) {
				throw new TechnicalException("invalid index Object :" + treeIndex[i]);
			}
			parentObject = listValue.get(treeIndex[i]);
			listValue = (List<?>) BeanObjectUtils.getPropertyValue(parentObject, childrenProperty);
		}
		return parentObject;
	}

	public static int[] getTreeIndex(String indexString) {
		int[] treeIndex = new int[0];
		if (StringUtils.isNotBlank(indexString)) {
			String[] treeIndexString = StringUtils.split(indexString, PREFIX_TREE_INDEX);
			treeIndex = new int[treeIndexString.length];
			for (int i = 0; i < treeIndexString.length; i++) {
				if (StringUtils.isNotBlank(treeIndexString[i]) && StringUtils.isNumeric(treeIndexString[i]))
					treeIndex[i] = Integer.parseInt(treeIndexString[i]);
				else
					return new int[0];
			}
		}
		return treeIndex;
	}

	public static String getTreeDisplayIndex(String parentIndexString, int indexChild) {
		if (StringUtils.isNotBlank(parentIndexString))
			return parentIndexString + PREFIX_TREE_INDEX + indexChild;
		return String.valueOf(indexChild);
	}

	public static Map<String, Object> getJsonMap(String index, UIExtStore store, String modelName, Object bean) {
		Map<String, Object> row = new HashMap<String, Object>();
		row.put(INDEX_FIELD_NAME, index);
		for (int j = 0; j < store.fieldSize(); j++) {
			UIExtFieldStore fieldStore = store.getField(j);
			if (store.isFormatedValue()) {
				String customValue = store.getUserAction().getModel().getCustomValue(modelName, bean, fieldStore.getProperty());
				if (StringUtils.isNotBlank(customValue))
					row.put(fieldStore.getId(), customValue);
			} else {
				Object customValue = store.getUserAction().getModel().getValue(modelName, bean, fieldStore.getProperty());
				row.put(fieldStore.getId(), customValue);
			}
		}
		return row;

	}
	
	public static List<Map<String, Object>> findChildrenNode(List<?> listValue, int parentIndex, String modelName, UIExtStore store) {
		Object parent = listValue.get(parentIndex);
		Object parentKeyValue = BeanObjectUtils.getPropertyValue(parent, store.getIdProperty());
		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listValue.size(); i++) {
			Object bean = listValue.get(i);
			Object parentValue = BeanObjectUtils.getPropertyValue(bean, store.getParentProperty());
			if (parentValue != null && parentKeyValue.equals(parentValue)) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int j = 0; j < store.fieldSize(); j++) {
					UIExtFieldStore fieldStore = store.getField(j);
					if (store.isFormatedValue()) {
						String customValue = store.getUserAction().getModel().getCustomValue(modelName, bean, fieldStore.getProperty());
						if (StringUtils.isNotBlank(customValue))
							row.put(fieldStore.getId(), customValue);
					} else {
						Object customValue = store.getUserAction().getModel().getValue(modelName, bean, fieldStore.getProperty());
						row.put(fieldStore.getId(), customValue);
					}
				}
				childs.add(row);
			}
		}
		return childs;
	}
}
