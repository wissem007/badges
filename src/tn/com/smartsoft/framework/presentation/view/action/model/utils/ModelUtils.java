package tn.com.smartsoft.framework.presentation.view.action.model.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanAccessorModel;
import tn.com.smartsoft.framework.presentation.view.action.model.CompositeModel;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.action.model.RootBeanModel;
import tn.com.smartsoft.framework.security.Principal;

public class ModelUtils {
	public static final String NESTED_DELIM = ".";
	public static final String MAPPED_DELIM = "[";
	public static final String MAPPED_DELIM2 = "]";

	public static int getModelType(Class<?> javaType) {
		int type = BeanAccessorModel.BEAN_TYPE;
		if (javaType == null)
			type = BeanAccessorModel.BEAN_TYPE;
		else if (Map.class.isAssignableFrom(javaType)) {
			type = BeanAccessorModel.MAP_TYPE;
		} else if (List.class.isAssignableFrom(javaType)) {
			type = BeanAccessorModel.LIST_TYPE;
		}
		return type;
	}

	private static Object getSimplePropertyValue(CompositeModel beanModel, Object bean, PropertyDesc propertyDesc, boolean isSetterOfNull) throws PropertyModelException {
		try {
			BeanAccessorModel beanAccessorModel = beanModel.getBeanAccessorModel();
			if (propertyDesc.isIndexed())
				throw new PropertyModelException("invalid property :" + propertyDesc);

			if (!beanAccessorModel.isReadableProperty(bean, propertyDesc.getProperty()))
				throw new PropertyModelException("is Not Readable property :" + propertyDesc);
			Object childBean = beanAccessorModel.getPropertyValue(bean, propertyDesc.getProperty());
			ItemModel fieldModel = beanModel.getChildModel(propertyDesc.getProperty());
			if (childBean == null && isSetterOfNull && (fieldModel instanceof CompositeModel)) {
				CompositeModel fieldBeanModel = ((CompositeModel) fieldModel);
				if (fieldBeanModel.getBeanAccessorModel().getType() == BeanAccessorModel.MAP_TYPE) {
					childBean = new HashMap<Object, Object>();
				} else if (fieldBeanModel.getBeanAccessorModel().getType() == BeanAccessorModel.LIST_TYPE) {
					childBean = new ArrayList<Object>();
				} else {
					childBean = fieldBeanModel.getBeanAccessorModel().newValue();
				}
				beanAccessorModel.setPropertyValue(bean, propertyDesc.getProperty(), childBean);
			}
			return childBean;
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object getSimpleMappedValue(CompositeModel beanModel, Object bean, PropertyDesc propertyDesc, boolean isSetterOfNull) throws PropertyModelException {
		try {
			BeanAccessorModel beanAccessorModel = beanModel.getBeanAccessorModel();
			if (!propertyDesc.isIndexed())
				throw new PropertyModelException("invalid property :" + propertyDesc);
			if (!beanAccessorModel.isReadableProperty(bean, propertyDesc.getProperty()))
				throw new PropertyModelException("is Not Readable property :" + propertyDesc);
			Object values = beanAccessorModel.getPropertyValue(bean, propertyDesc.getProperty());
			ItemModel fieldModel = beanModel.getChildModel(propertyDesc.getProperty());
			if (!(fieldModel instanceof CompositeModel)) {
				throw new PropertyModelException("invalid  Mapped property :" + propertyDesc);
			}
			CompositeModel fieldBeanModel = ((CompositeModel) fieldModel);
			if (fieldBeanModel.getBeanAccessorModel().getType() == BeanAccessorModel.MAP_TYPE) {
				Map mapBean = (Map) values;
				if (!isSetterOfNull && mapBean == null) {
					return null;
				}
				if (mapBean == null) {
					if (!beanAccessorModel.isWriteableProperty(bean, propertyDesc.getProperty()))
						throw new PropertyModelException("is Not Writeable property :" + propertyDesc);
					mapBean = new HashMap();
					beanAccessorModel.setPropertyValue(bean, propertyDesc.getProperty(), mapBean);
				}
				Object beanValue = mapBean.get(propertyDesc.getKeyPoperty());
				if (beanValue == null && isSetterOfNull) {
					beanValue = fieldBeanModel.getBeanAccessorModel().newValue();
					mapBean.put(propertyDesc.getKeyPoperty(), beanValue);
				}
				return beanValue;
			} else if (fieldBeanModel.getBeanAccessorModel().getType() == BeanAccessorModel.LIST_TYPE) {
				List listBean = (List) values;
				if (!isSetterOfNull && listBean == null) {
					return null;
				}
				if (listBean == null) {
					if (!beanAccessorModel.isWriteableProperty(bean, propertyDesc.getProperty()))
						throw new PropertyModelException("is Not Writeable property :" + propertyDesc);
					listBean = new ArrayList();
					beanAccessorModel.setPropertyValue(bean, propertyDesc.getProperty(), listBean);
				}
				int index = propertyDesc.getIndexProperty();
				Object beanValue = listBean.size() > index ? listBean.get(index) : null;
				if (beanValue == null && isSetterOfNull) {
					beanValue = fieldBeanModel.getBeanAccessorModel().newValue();
					if (index > listBean.size()) {
						int start = listBean.size();
						for (int i = start; i < index; i++) {
							listBean.add(fieldBeanModel.getBeanAccessorModel().newValue());
						}
					}
					listBean.add(index, beanValue);
				}
				return beanValue;
			}
			throw new PropertyModelException("invalid property :" + propertyDesc);
		} catch (Exception e) {
			throw new PropertyModelException(e);
		}
	}

	private static Object getSimpleValue(CompositeModel beanModel, Object bean, PropertyDesc propertyDesc, boolean isSetterOfNull) throws PropertyModelException {
		if (propertyDesc.isIndexed()) {
			return getSimpleMappedValue(beanModel, bean, propertyDesc, isSetterOfNull);
		} else {
			return getSimplePropertyValue(beanModel, bean, propertyDesc, isSetterOfNull);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setSimpleMappedValue(CompositeModel beanModel, Object bean, PropertyDesc propertyDesc, Object value) throws PropertyModelException {
		try {
			BeanAccessorModel beanAccessorModel = beanModel.getBeanAccessorModel();
			ItemModel fieldModel = beanModel.getChildModel(propertyDesc.getProperty());
			if (!(fieldModel instanceof CompositeModel)) {
				throw new PropertyModelException("invalid  Mapped property :" + propertyDesc);
			}
			CompositeModel fieldBeanModel = ((CompositeModel) fieldModel);
			Object values = beanAccessorModel.getPropertyValue(bean, propertyDesc.getProperty());
			if (fieldBeanModel.getBeanAccessorModel().getType() == BeanAccessorModel.MAP_TYPE) {
				Map mapBean = (Map) values;
				if (mapBean == null) {
					if (!beanAccessorModel.isWriteableProperty(bean, propertyDesc.getProperty()))
						throw new PropertyModelException("is Not Writeable property :" + propertyDesc);
					mapBean = new HashMap();
					beanAccessorModel.setPropertyValue(bean, propertyDesc.getProperty(), mapBean);
				}
				mapBean.put(propertyDesc.getKeyPoperty(), value);
				return;
			} else if (fieldBeanModel.getBeanAccessorModel().getType() == BeanAccessorModel.LIST_TYPE) {
				List listBean = (List) values;
				if (listBean == null) {
					if (!beanAccessorModel.isWriteableProperty(bean, propertyDesc.getProperty()))
						throw new PropertyModelException("is Not Writeable property :" + propertyDesc);
					listBean = new ArrayList();
					beanAccessorModel.setPropertyValue(bean, propertyDesc.getProperty(), listBean);
				}
				int index = propertyDesc.getIndexProperty();
				if (index > listBean.size()) {
					int start = listBean.size();
					for (int i = start; i < index; i++) {
						listBean.add(fieldBeanModel.getBeanAccessorModel().newValue());
					}
				}
				listBean.add(index, value);
				return;
			}
			throw new PropertyModelException("invalid indexed");
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}

	public static void setSimplePropertyValue(CompositeModel beanModel, Object bean, PropertyDesc propertyDesc, Object value) throws PropertyModelException {
		if (propertyDesc.isIndexed())
			throw new PropertyModelException("invalid propertyDesc :" + propertyDesc);
		BeanAccessorModel beanAccessorModel = beanModel.getBeanAccessorModel();
		if (!beanAccessorModel.isWriteableProperty(bean, propertyDesc.getProperty()))
			throw new PropertyModelException("is Not Writeable property :" + propertyDesc + " from bean" + bean);
		beanAccessorModel.setPropertyValue(bean, propertyDesc.getProperty(), value);
	}

	public static void setSimpleValue(CompositeModel beanModel, Object bean, PropertyDesc propertyDesc, Object value) throws PropertyModelException {
		if (propertyDesc.isIndexed()) {
			setSimpleMappedValue(beanModel, bean, propertyDesc, value);
		} else {
			setSimplePropertyValue(beanModel, bean, propertyDesc, value);
		}
	}

	public static Object getValue(CompositeModel beanModel, Object bean, String property) {
		List<PropertyDesc> propertyDescs = getPropertyDesc(property);
		Object value = bean;
		ItemModel fieldModel = beanModel;
		for (int i = 0; i < propertyDescs.size(); i++) {
			PropertyDesc propertyDesc = propertyDescs.get(i);
			try {
				if (fieldModel instanceof CompositeModel) {
					value = getSimpleValue((CompositeModel) fieldModel, value, propertyDesc, true);
					fieldModel = ((CompositeModel) fieldModel).getChildModel(propertyDesc.getProperty());
				} else {
					throw new TechnicalException("invalid property:" + property);
				}
				if (value == null)
					break;
			} catch (PropertyModelException e) {
				throw new TechnicalException("invalid property:" + property, e);
			}
		}
		return value;
	}

	public static void setValue(CompositeModel beanModel, Object bean, String property, Object propertyValue) {
		List<PropertyDesc> propertyDescs = getPropertyDesc(property);
		Object value = bean;
		ItemModel fieldModel = beanModel;
		for (int i = 0; i < propertyDescs.size(); i++) {
			PropertyDesc propertyDesc = propertyDescs.get(i);
			try {
				if (fieldModel instanceof CompositeModel && i != propertyDescs.size() - 1) {
					value = getSimpleValue((CompositeModel) fieldModel, value, propertyDesc, true);
					fieldModel = ((CompositeModel) fieldModel).getChildModel(propertyDesc.getProperty());
				} else if (fieldModel instanceof CompositeModel && i == propertyDescs.size() - 1) {
					setSimpleValue((CompositeModel) fieldModel, value, propertyDesc, propertyValue);
				} else {
					throw new TechnicalException("invalid property:" + property);
				}
			} catch (PropertyModelException e) {
				throw new TechnicalException("invalid property:" + property, e);
			}
		}
	}

	public static Object getCustomValue(CompositeModel beanModel, Object bean, String property, Object value) {
		List<PropertyDesc> propertyDescs = getPropertyDesc(property);
		ItemModel fieldModel = beanModel;
		for (int i = 0; i < propertyDescs.size(); i++) {
			PropertyDesc propertyDesc = propertyDescs.get(i);
			if (fieldModel instanceof CompositeModel) {
				fieldModel = ((CompositeModel) fieldModel).getChildModel(propertyDesc.getProperty());
			} else {
				throw new TechnicalException("invalid property:" + property);
			}
		}
		if (fieldModel instanceof PropertyModel) {
			return ((PropertyModel) fieldModel).getAsString(value);
		}
		return value == null ? "" : value.toString();
	}

	public static String getCustomValue(CompositeModel beanModel, Object bean, String property) {
		List<PropertyDesc> propertyDescs = getPropertyDesc(property);
		Object value = bean;
		ItemModel fieldModel = beanModel;
		for (int i = 0; i < propertyDescs.size(); i++) {
			PropertyDesc propertyDesc = propertyDescs.get(i);
			try {
				if (fieldModel instanceof CompositeModel) {
					value = getSimpleValue((CompositeModel) fieldModel, value, propertyDesc, true);
					fieldModel = ((CompositeModel) fieldModel).getChildModel(propertyDesc.getProperty());
				} else {
					throw new TechnicalException("invalid property:" + property);
				}
				if (value == null)
					break;
			} catch (PropertyModelException e) {
				throw new TechnicalException("invalid property:" + property, e);
			}
		}
		if (fieldModel instanceof PropertyModel) {
			return ((PropertyModel) fieldModel).getAsString(value);
		}
		return value == null ? "" : value.toString();
	}

	public static void setCustomValue(CompositeModel beanModel, Object bean, String property, Object propertyValue) {
		List<PropertyDesc> propertyDescs = getPropertyDesc(property);
		Object value = bean;
		ItemModel fieldModel = beanModel;
		for (int i = 0; i < propertyDescs.size(); i++) {
			PropertyDesc propertyDesc = propertyDescs.get(i);
			try {
				if (fieldModel instanceof CompositeModel && i != propertyDescs.size() - 1) {
					value = getSimpleValue((CompositeModel) fieldModel, value, propertyDesc, true);
					fieldModel = ((CompositeModel) fieldModel).getChildModel(propertyDesc.getProperty());
				} else if (fieldModel instanceof CompositeModel && i != propertyDescs.size() - 1) {
					if (fieldModel instanceof PropertyModel) {
						Object objectValue = ((PropertyModel) fieldModel).getAsObject(propertyValue);
						setSimpleValue((CompositeModel) fieldModel, bean, propertyDesc, objectValue);
					} else
						setSimpleValue((CompositeModel) fieldModel, bean, propertyDesc, propertyValue);
				} else {
					throw new TechnicalException("invalid property:" + property);
				}
				if (value == null)
					break;
			} catch (PropertyModelException e) {
				throw new TechnicalException("invalid property:" + property, e);
			}
		}
	}

	public static ItemModel findFieldModel(CompositeModel beanModel, String property) {
		List<PropertyDesc> propertyDescs = getPropertyDesc(property);
		ItemModel fieldModel = beanModel;
		for (int i = 0; i < propertyDescs.size(); i++) {
			PropertyDesc propertyDesc = propertyDescs.get(i);
			if (fieldModel instanceof CompositeModel) {
				fieldModel = ((CompositeModel) fieldModel).getChildModel(propertyDesc.getProperty());
			} else {
				throw new TechnicalException("invalid property:" + property);
			}
		}
		return fieldModel;
	}

	public static boolean validateCustomValue(CompositeModel beanModel, String property, Object value) {
		ItemModel fieldModel = findFieldModel(beanModel, property);
		if (fieldModel instanceof PropertyModel) {
			PropertyModel propertyModel = (PropertyModel) fieldModel;
			return propertyModel.validate(value);
		} else {
			throw new TechnicalException("invalid property:" + property);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sort(RootBeanModel beanModel, String listProperty, String property, SorterType sort) {
		Object value = beanModel.getValue(listProperty);
		if (value instanceof List) {
			BeanComparator.sort((CompositeModel) beanModel.findFieldModel(listProperty), property, sort, (List) value);
		} else {
			throw new TechnicalException("invalid property List:" + property);
		}
	}

	public static void sort(RootBeanModel beanModel, String property, SorterType sort) {
		int delimNested = StringUtils.lastIndexOf(property, NESTED_DELIM);
		String listProperty = StringUtils.substring(property, 0, delimNested);
		property = StringUtils.substring(property, delimNested + 1);
		sort(beanModel, listProperty, property, sort);
	}

	public static boolean showPermission(RootBeanModel beanModel, Principal principal, String property) {
		int delimNested = StringUtils.lastIndexOf(property, NESTED_DELIM);
		property = StringUtils.substring(property, delimNested + 1);
		PropertyModel propertyModel = (PropertyModel) beanModel.getChildModel(property);
		return propertyModel.getPermission().isGranted(propertyModel.getMode());
	}

	public static boolean showPermission(RootBeanModel beanModel, Principal principal, String propertyBean, String property) {
		PropertyModel propertyModel = (PropertyModel) beanModel.getChildModel(property);
		return propertyModel.getPermission().isGranted(propertyModel.getMode());
	}

	public static List<PropertyDesc> getPropertyDesc(String property) {
		List<PropertyDesc> propertyDescs = new ArrayList<PropertyDesc>();
		String[] ps = StringUtils.split(property, ".");
		for (int j = 0; j < ps.length; j++) {
			propertyDescs.add(new PropertyDesc(ps[j], property));
		}
		return propertyDescs;
	}

}
