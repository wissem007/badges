package tn.com.smartsoft.framework.presentation.view.action.model.accessor;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanAccessorModel;
import tn.com.smartsoft.framework.presentation.view.action.model.CompositeModel;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;

public class MapBeanAccessorModel implements BeanAccessorModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CompositeModel beanModel;
	private int type;
	
	public MapBeanAccessorModel(CompositeModel beanModel, int type) {
		super();
		this.type = type;
		this.beanModel = beanModel;
	}
	
	public Object newValue() {
		try {
			return new HashMap<Object, Object>();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
	
	public int getType() {
		return type;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPropertyValue(Object bean, String property, Object value) {
		((Map) bean).put(property, value);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Object getPropertyValue(Object bean, String property) {
		return ((Map) bean).get(property);
	}
	
	public Class<?> getPropertyType(Object bean, String property) {
		PropertyModel mapPropertyModel = (PropertyModel) beanModel.getChildModel(property);
		return mapPropertyModel.getJavaType();
	}
	
	public boolean isReadableProperty(Object bean, String property) {
		return beanModel.containsChildModel(property);
	}
	
	public boolean isWriteableProperty(Object bean, String property) {
		return beanModel.containsChildModel(property);
	}
	
}
