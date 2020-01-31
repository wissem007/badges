package tn.com.smartsoft.framework.presentation.view.action.model;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.view.action.model.accessor.MapBeanAccessorModel;

public abstract class CompositeModel implements Serializable, ItemModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, ItemModel> fieldsModels = new HashMap<String, ItemModel>();
	protected BeanAccessorModel beanAccessorModel;
	private UserActionDefinition userActionDefinition;
	private Map<String, PropertyDescriptor> propertyDescriptor;
	protected RootBeanModel beanModelRoot;

	public CompositeModel(RootBeanModel beanModelRoot, UserActionDefinition userActionDefinition) {
		super();
		this.userActionDefinition = userActionDefinition;
		this.beanModelRoot = beanModelRoot;
	}

	public CompositeModel(RootBeanModel beanModelRoot, BeanAccessorModel beanAccessorModel, UserActionDefinition userActionDefinition) {
		super();
		this.beanAccessorModel = beanAccessorModel;
		this.userActionDefinition = userActionDefinition;
		this.beanModelRoot = beanModelRoot;
	}

	public String[] getPropertyDescriptorNames() {
		Set<String> keySet = propertyDescriptor.keySet();
		String[] ns = new String[propertyDescriptor.size()];
		return keySet.toArray(ns);
	}

	public PropertyDescriptor getPropertyDescriptor(String name) {
		if (propertyDescriptor != null)
			return propertyDescriptor.get(name);
		return null;
	}

	public boolean containsPropertyDescriptor(String name) {
		return propertyDescriptor != null && propertyDescriptor.containsKey(name);
	}

	public void setPropertyDescriptor(Map<String, PropertyDescriptor> propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	public void addChildModel(String name, Class<?> type, String mode) {
		if (propertyDescriptor != null && propertyDescriptor.containsKey(name))
			addChildModel(BeanModelEntiteFactory.createBeanModel(this.beanModelRoot, propertyDescriptor.get(name).getPropertyType(), name, type, mode, userActionDefinition));
		else
			addChildModel(BeanModelEntiteFactory.createBeanModel(this.beanModelRoot, type, name, type, mode, userActionDefinition));
	}

	public void addChildModel(String name, Class<?> propertyType, String mode, Class<?> type) {
		addChildModel(BeanModelEntiteFactory.createBeanModel(this.beanModelRoot, propertyType, name, type, mode, userActionDefinition));
	}

	public void addChildModel(ItemModel childModel) {
		fieldsModels.put(childModel.getName(), childModel);
	}

	public BeanModel addBeanMapModel(String name) {
		BeanModel beanPropertyModel = new BeanModel(name, this.beanModelRoot, userActionDefinition);
		beanPropertyModel.setBeanAccessorModel(new MapBeanAccessorModel(beanPropertyModel, MapBeanAccessorModel.BEAN_TYPE));
		addChildModel(beanPropertyModel);
		return beanPropertyModel;
	}

	public PropertyModel addPropertyModel(String name, String userType, Class<?> JavaType, String libelle, Object help, Object defaultValue) {
		PropertyModel propertyModel = new PropertyModel(name, userType, JavaType, libelle, help, defaultValue);
		addChildModel(propertyModel);
		return propertyModel;
	}

	public PropertyModel addPropertyModel(String name, String userType, Class<?> JavaType, String libelle, Object help) {
		return addPropertyModel(name, userType, JavaType, libelle, help, null);
	}

	public PropertyModel addPropertyModel(String name, String userType, Class<?> JavaType, String libelle) {
		return addPropertyModel(name, userType, JavaType, libelle, libelle, null);
	}

	public ItemModel getChildModel(String name) {
		return fieldsModels.get(name);
	}

	public boolean containsChildModel(String name) {
		return fieldsModels.containsKey(name);
	}

	public BeanAccessorModel getBeanAccessorModel() {
		return beanAccessorModel;
	}

	public void setBeanAccessorModel(BeanAccessorModel beanAccessorModel) {
		this.beanAccessorModel = beanAccessorModel;
	}

	public String[] getFieldsNames() {
		Set<String> keySet = fieldsModels.keySet();
		String[] ns = new String[fieldsModels.size()];
		return keySet.toArray(ns);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
