package tn.com.smartsoft.framework.presentation.view.action;

import java.util.Map;

import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.security.Principal;

public interface UserActionModel {
	public UserAction getUserAction();

	public Object getExpressionValue(String expression, Map<Object, Object> values);

	public Object getExpressionValue(String expression);

	public abstract void setValue(String property, Object value);

	public abstract void setCustomValue(String property, Object value);

	public abstract Object getValue(String property);

	public abstract String getCustomValue(String property);

	public Object getValue(String modelName, Object bean, String property);

	public String getCustomValue(String modelName, Object bean, String property);

	public void sort(String property, SorterType sort);

	public void sort(String property, String listProperty, SorterType sort);

	public void addChildModel(ItemModel childModel);

	public void addChildModel(String name, Class<?> type, String mode);

	public Object getValue();

	public ItemModel findFieldModel(String property);

	public boolean validateCustomValue(String property, Object value);

	public boolean showPermission(Principal principal, String property);

	public String evalExpressionToString(String expression, String windowId);

	public Object evalExpression(String expression, String windowId);

}