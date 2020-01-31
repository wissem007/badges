package tn.com.smartsoft.framework.presentation.view.window.component.ext.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.commons.utils.ValueUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.utils.StoreUtils;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public abstract class UIExtStore extends UIGenericRendrableContainerComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean autoDestroy;
	private Boolean autoSave;
	private Boolean remote = Boolean.TRUE;
	private String modelName;
	private String queryMethod;
	private UIDefaultComponentHandler<UIExtFieldStore> fieldHandler = new UIDefaultComponentHandler<UIExtFieldStore>(this);
	private String indexName = "i";
	private Boolean autoLoad = Boolean.TRUE;
	private Boolean isLiveGrid = Boolean.FALSE;
	private String idProperty;
	private String queryParam = "query";
	private Boolean isTree = Boolean.FALSE;
	private String childrenProperty;
	private String groupField;
	private Integer typeTree = 0;
	private String parentProperty;
	private Boolean formatedValue = Boolean.TRUE;

	public String getParentProperty() {
		return parentProperty;
	}

	public void setParentProperty(String parentProperty) {
		this.parentProperty = parentProperty;
	}

	public String getQueryMethod() {
		return queryMethod;
	}

	public Boolean isTree() {
		return isTree;
	}

	public Boolean isFormatedValue() {
		return formatedValue;
	}

	public Boolean getFormatedValue() {
		return formatedValue;
	}

	public void setFormatedValue(Boolean formatedValue) {
		this.formatedValue = formatedValue;
	}

	public Integer getTypeTree() {
		return typeTree;
	}

	public void setTypeTree(Integer typeTree) {
		this.typeTree = typeTree;
	}

	public void setTree(Boolean isTree) {
		this.isTree = isTree;
	}

	public String getChildrenProperty() {
		return childrenProperty;
	}

	public void setChildrenProperty(String childrenProperty) {
		this.childrenProperty = childrenProperty;
	}

	public void setQueryMethod(String queryMethod) {
		this.queryMethod = queryMethod;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public Boolean isAutoLoad() {
		return autoLoad;
	}

	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public Boolean isLiveGrid() {
		return isLiveGrid;
	}

	public void setLiveGrid(Boolean isLiveGrid) {
		this.isLiveGrid = isLiveGrid;
	}

	public void setAutoLoad(Boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public void sortByProperty(String fieldProperty, SorterType sort) {
		if (StringUtils.isNotBlank(fieldProperty))
			getUserAction().getModel().sort(fieldProperty, modelName, sort);
	}

	public void sortByPropertyIndex(String fieldIndex, SorterType sort) {
		if (StringUtils.isNotBlank(fieldIndex) && getField(fieldIndex) != null) {
			String property = getField(fieldIndex).getProperty();
			property = StringUtils.remove(property, "[$i]");
			getUserAction().getModel().sort(property, modelName, sort);
		}
	}

	public Object findByProperty(final String fieldProperty, final Object value) {
		if (StringUtils.isNotBlank(fieldProperty)) {
			Predicate testPredicate = new Predicate() {
				public boolean evaluate(Object bean) {
					Object value1;
					try {
						value1 = PropertyUtils.getProperty(bean, fieldProperty);
					} catch (Exception e) {
						throw new TechnicalException(e);
					}
					return ValueUtils.equals(value1, value);
				}
			};
			return CollectionUtils.find(getValue(), testPredicate);
		}
		return null;
	}

	public int findIndexByProperty(final String fieldProperty, final Object value) {
		return getValue().indexOf(findByProperty(fieldProperty, value));
	}

	public void removeField(String id) {
		fieldHandler.removeItem(id);
	}

	public void removeField(String[] id) {
		fieldHandler.removeItem(id);
	}

	public void removeField(int start, int end) {
		fieldHandler.removeItem(start, end);
	}

	public void removeField(int start) {
		fieldHandler.removeItem(start);
	}

	public Object findByPropertyIndex(String fieldIndex, final Object value) {
		if (StringUtils.isNotBlank(fieldIndex) && getField(fieldIndex) != null) {
			String property = getField(fieldIndex).getProperty();
			property = StringUtils.remove(property, "[$i]");
			return findByProperty(property, value);
		}
		return null;
	}

	public int findIndexByPropertyIndex(final String fieldIndex, final Object value) {
		return getValue().indexOf(findByPropertyIndex(fieldIndex, value));
	}

	public Object findById(final Object value) {
		return findIndexByPropertyIndex(getIdProperty(), value);
	}

	public int findIndexById(final Object value) {
		return findIndexByPropertyIndex(getIdProperty(), value);
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public List<?> getValue() {
		System.out.println("zzzzzzzz:" + modelName);
		List<?> value = (List<?>) getUserAction().getModel().getValue(modelName);
		if (value == null)
			value = new ArrayList<Object>();
		return value;
	}

	public Object getValue(int index) {
		return getValue().get(index);
	}

	public int size() {
		return getValue().size();
	}

	public void addField(UIExtFieldStore fieldStore) {
		fieldHandler.addItem(fieldStore);
	}

	public List<String> getFieldIds() {
		return fieldHandler.itemIds();
	}

	public int fieldSize() {
		return fieldHandler.itemSize();
	}

	public Object findNodeFormTree(int[] treeIndex) {
		return StoreUtils.findNode(treeIndex, getValue(), childrenProperty);
	}

	public UIExtFieldStore getField(String id) {
		UIExtFieldStore fieldStore = (UIExtFieldStore) fieldHandler.getItem(id);
		if (fieldStore == null)
			throw new TechnicalException("invalid column data-index :" + id);
		return fieldStore;
	}

	public boolean hasField(String id) {
		return fieldHandler.hasItem(id);
	}

	public UIExtFieldStore getField(int rang) {
		return (UIExtFieldStore) fieldHandler.getItem(getFieldIds().get(rang));
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public UIExtStore() {
		super();
	}

	public Boolean isRemote() {
		return remote;
	}

	public void setRemote(Boolean remote) {
		this.remote = remote;
	}

	public Boolean isAutoDestroy() {
		return autoDestroy;
	}

	public void setAutoDestroy(Boolean autoDestroy) {
		this.autoDestroy = autoDestroy;
	}

	public Boolean isAutoSave() {
		return autoSave;
	}

	public void setAutoSave(Boolean autoSave) {
		this.autoSave = autoSave;
	}

	public String getGroupField() {
		return groupField;
	}

	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

	public boolean isGouped() {
		if (this.groupField != null && this.groupField.length() > 0) {
			UIExtFieldStore fieldStore = (UIExtFieldStore) fieldHandler.getItem(groupField);
			if (fieldStore == null)
				throw new TechnicalException("invalid column group field :" + groupField);
			return true;
		}
		return false;
	}

	public void createOrgJsonDatas(Object parentKeyValue, int indexPr, List<Map<String, Object>> values) {
		List<?> listValue = getValue();
		Object bean = listValue.get(indexPr);
		Map<String, Object> row = StoreUtils.getJsonMap(String.valueOf(indexPr), this, getModelName(), bean);
		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listValue.size(); i++) {
			Object child = listValue.get(i);
			Object keyValue = BeanObjectUtils.getPropertyValue(child, this.getIdProperty());
			Object parentValue = BeanObjectUtils.getPropertyValue(child, this.getParentProperty());
			if (parentValue != null && parentKeyValue.equals(parentValue)) {
				createOrgJsonDatas(keyValue, i, childs);
			}
			row.put("children", childs);
		}
		values.add(row);
	}

	public List<Map<String, Object>> createOrgJsonDatas() {
		List<?> listValue = getValue();
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listValue.size(); i++) {
			Object child = listValue.get(i);
			Object parentValue = BeanObjectUtils.getPropertyValue(child, getParentProperty());
			Object parentKeyValue = BeanObjectUtils.getPropertyValue(child, this.getIdProperty());
			if (parentValue == null) {
				createOrgJsonDatas(parentKeyValue, i, values);
			}
		}
		return values;
	}
}
