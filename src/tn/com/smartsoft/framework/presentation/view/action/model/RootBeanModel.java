package tn.com.smartsoft.framework.presentation.view.action.model;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;
import tn.com.smartsoft.framework.presentation.view.action.model.accessor.GenericBeanAccessorModel;
import tn.com.smartsoft.framework.presentation.view.action.model.expression.ExpressionEvalidator;
import tn.com.smartsoft.framework.presentation.view.action.model.utils.ModelUtils;
import tn.com.smartsoft.framework.security.Principal;

public class RootBeanModel extends BeanModel implements UserActionModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String USER_CREATED_BY_ID_NAME = "userCreatedById";
	private Object value;
	private UserAction userAction;

	public RootBeanModel(UserActionDefinition userActionDefinition) {
		super(null, userActionDefinition);
		this.beanModelRoot = this;
	}

	public RootBeanModel(Class<?> javaClass, UserActionDefinition userActionDefinition) {
		super(null, userActionDefinition);
		this.beanModelRoot = this;
		this.beanAccessorModel = new GenericBeanAccessorModel(this, javaClass, GenericBeanAccessorModel.BEAN_TYPE);
		this.value = beanAccessorModel.newValue();
	}

	public void load(Object value) {
		this.value = value;
		this.beanAccessorModel = new GenericBeanAccessorModel(this, value.getClass(), GenericBeanAccessorModel.BEAN_TYPE);
	}

	public void sort(String property, SorterType sort) {
		ModelUtils.sort(this, property, sort);
	}

	public void sort(String property, String listProperty, SorterType sort) {
		ModelUtils.sort(this, listProperty, property, sort);
	}

	public Object getValue() {
		return value;
	}

	public UserAction getUserAction() {
		return userAction;
	}

	public void setUserAction(UserAction userAction) {
		this.userAction = userAction;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setValue(String property, Object value) {
		ModelUtils.setValue(this, getValue(), property, value);
	}

	public boolean validateCustomValue(String property, Object value) {
		return ModelUtils.validateCustomValue(this, property, value);
	}

	public ItemModel findFieldModel(String property) {
		return (ItemModel) ModelUtils.findFieldModel(this, property);
	}

	public void setCustomValue(String property, Object value) {
		ModelUtils.setCustomValue(this, getValue(), property, value);
	}

	public Object getValue(String property) {
		return ModelUtils.getValue(this, getValue(), property);
	}

	public String getCustomValue(String property) {
		return (String) ModelUtils.getCustomValue(this, getValue(), property);
	}

	public Object getValue(String modelName, Object bean, String property) {
		return ModelUtils.getValue((CompositeModel) findFieldModel(modelName), bean, property);
	}

	public String getCustomValue(String modelName, Object bean, String property) {
		return (String) ModelUtils.getCustomValue((CompositeModel) findFieldModel(modelName), bean, property);
	}

	public Object getExpressionValue(String expression, Map<Object, Object> values) {
		return ExpressionEvalidator.evaluate(expression, this, values);
	}

	public Object getExpressionValue(String expression) {
		return ExpressionEvalidator.evaluate(expression, this);
	}

	public boolean showPermission(Principal principal, String property) {
		return ModelUtils.showPermission(this, principal, property);
	}

	public Class<?> getJavaType() {
		return value.getClass();
	}

	public String getName() {
		return "root";
	}

	public String getLibelle() {
		return "root";
	}

	public ActionMode getMode() {
		return ActionMode.OTHER;
	}

	public void setMode(ActionMode mode) {}

	public String evalExpressionToString(String expression, String windowId) {
		Object libelle = this.evalExpression(expression, windowId);
		if (libelle != null)
			return libelle.toString();
		else
			return "invalid expression label with :" + expression;

	}

	public Object evalExpression(String expression, String windowId) {
		if (StringUtils.isBlank(expression))
			return "";
		return ExpressionEvalidator.evaluate(expression, userAction.getModel(), windowId);
	}
}
