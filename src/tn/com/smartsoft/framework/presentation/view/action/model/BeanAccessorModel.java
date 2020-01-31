package tn.com.smartsoft.framework.presentation.view.action.model;

import java.io.Serializable;

public interface BeanAccessorModel extends Serializable{
	public static final int BEAN_TYPE = 0;
	public static final int LIST_TYPE = 1;
	public static final int MAP_TYPE = 2;

	public abstract void setPropertyValue(Object bean, String property, Object value);

	public abstract Object getPropertyValue(Object bean, String property);

	public abstract boolean isReadableProperty(Object bean, String property);

	public abstract boolean isWriteableProperty(Object bean, String property);

	public abstract Object newValue();

	public abstract int getType();
}
