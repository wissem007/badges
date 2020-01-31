package tn.com.smartsoft.framework.presentation.view.action.model;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ClassUtils;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.ConverterUtil;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans.DataValuesBean;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.configuration.impl.ComponentIdImpl;
import tn.com.smartsoft.framework.presentation.definition.BindingModelDefinition;
import tn.com.smartsoft.framework.presentation.definition.BindingPropertyModelDefinition;
import tn.com.smartsoft.framework.presentation.definition.BindingRefModelDefinition;
import tn.com.smartsoft.framework.presentation.definition.PresentationDefinition;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.formater.FormaterManger;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;
import tn.com.smartsoft.framework.presentation.view.action.model.accessor.MapBeanAccessorModel;
import tn.com.smartsoft.framework.presentation.view.action.model.utils.ModelUtils;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;

public class BeanModelEntiteFactory implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private static void loadBeanModel(RootBeanModel beanModelRoot, BindingModelDefinition bindingModelDef, EntiteBean entiteBean, FormaterManger formaterManger, CompositeModel beanModel,
			UserActionDefinition userActionDefinition) {
		beanModel.setPropertyDescriptor(BeanObjectUtils.getPropertyDescriptors(bindingModelDef.getType()));
		String[] propertyNames = beanModel.getPropertyDescriptorNames();
		for (int i = 0; i < propertyNames.length; i++) {
			PropertyDescriptor propertyDescriptor = beanModel.getPropertyDescriptor(propertyNames[i]);
			if (ConverterUtil.isFiledType(propertyDescriptor.getPropertyType())) {
				beanModel.addChildModel(createPropertyModel(beanModelRoot, bindingModelDef.getBindingPropertyModels(), bindingModelDef.getMode(), entiteBean, formaterManger,
						userActionDefinition, propertyDescriptor));
			} else if (bindingModelDef.getBindingModels().containsKey(propertyDescriptor.getName())) {
				beanModel.addChildModel(createBeanModel(beanModelRoot, propertyDescriptor.getPropertyType(), bindingModelDef.getBindingModels().get(propertyDescriptor.getName()),
						userActionDefinition));
			}
		}
		if (entiteBean.getPropertys() != null) {
			Set<String> keySet = entiteBean.getPropertys().keySet();
			String[] pnames = keySet.toArray(new String[entiteBean.getPropertys().size()]);
			for (int i = 0; i < pnames.length; i++) {
				PropertyBean propertyBean = entiteBean.getPropertys().get(pnames[i]);
				if (!beanModel.containsPropertyDescriptor(pnames[i]) && propertyBean.getDynamique()) {
					BindingPropertyModelDefinition bindingPropertyModel = new BindingPropertyModelDefinition(propertyBean.getPropertyName(), formaterManger.getFormatter(
							propertyBean.getUserTypeId()).getDefaultTargetClass(), bindingModelDef.getMode(), propertyBean.getLibelle(), propertyBean.getUserTypeId());
					bindingPropertyModel.setMode(bindingModelDef.getMode());
					addBindingPropertyModel(beanModelRoot, entiteBean, formaterManger, beanModel, userActionDefinition, bindingPropertyModel);
				}
			}
		}
		Set<String> keySet = bindingModelDef.getBindingPropertyModels().keySet();
		String[] pnames = keySet.toArray(new String[userActionDefinition.getBindingPropertyModels().size()]);
		for (int i = 0; i < pnames.length; i++) {
			BindingPropertyModelDefinition bindingPropertyModel = bindingModelDef.getBindingPropertyModels().get(pnames[i]);
			if (!beanModel.containsPropertyDescriptor(pnames[i])) addBindingPropertyModel(beanModelRoot, entiteBean, formaterManger, beanModel, userActionDefinition, bindingPropertyModel);
		}
	}
	private static void loadBeanMapModel(RootBeanModel beanModelRoot, BindingModelDefinition bindingModelDef, EntiteBean entiteBean, FormaterManger formaterManger, CompositeModel beanModel,
			UserActionDefinition userActionDefinition) {
		Set<String> modelDefChilds = bindingModelDef.getBindingModels().keySet();
		for (Iterator<String> iterator = modelDefChilds.iterator(); iterator.hasNext();) {
			String childName = (String) iterator.next();
			BindingModelDefinition modelDefChild = bindingModelDef.getBindingModels().get(childName);
			CompositeModel beanPropertyModel = createBeanModel(beanModelRoot, modelDefChild.getType(), modelDefChild, userActionDefinition);
			beanModel.addChildModel(beanPropertyModel);
		}
		Set<String> bindingPropertyModels = bindingModelDef.getBindingPropertyModels().keySet();
		for (Iterator<String> iterator = bindingPropertyModels.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			BindingPropertyModelDefinition bindingPropertyModel = bindingModelDef.getBindingPropertyModels().get(name);
			addBindingPropertyModel(beanModelRoot, entiteBean, formaterManger, beanModel, userActionDefinition, bindingPropertyModel);
		}
	}
	private static void addBindingPropertyModel(RootBeanModel beanModelRoot, EntiteBean entiteBean, FormaterManger formaterManger, CompositeModel beanModel,
			UserActionDefinition userActionDefinition, BindingPropertyModelDefinition bindingPropertyModel) {
		PropertyBean propertyBean = entiteBean.getPropertys().get(bindingPropertyModel.getName());
		String userType = userActionDefinition.getUserActionParser().parse(bindingPropertyModel.getUserType());
		Class<?> javaType = bindingPropertyModel.getType();
		String libelle = userActionDefinition.getUserActionParser().parse(bindingPropertyModel.getLibelleExp());
		Object help = userActionDefinition.getUserActionParser().parse(bindingPropertyModel.getLibelleExp());
		Object defaultValue = null;
		boolean encrypted = false;
		if (propertyBean != null) {
			userType = propertyBean.getUserTypeId();
			libelle = propertyBean.getLibelle();
			help = propertyBean.getHelp();
			defaultValue = propertyBean.getDefaultValue();
			encrypted = propertyBean.getEncrypted() != null ? propertyBean.getEncrypted().booleanValue() : false;
		}
		userType = userType == null ? formaterManger.getMapedTypeClass(javaType) : userType;
		PropertyModel propertyModel = new PropertyModel(bindingPropertyModel.getName(), userType, javaType, libelle, help, defaultValue);
		propertyModel.setEncrypted(encrypted);
		propertyModel.setMode(ActionMode.parse(bindingPropertyModel.getMode()));
		propertyModel.setDynamique(true);
		beanModel.addChildModel(propertyModel);
		if (propertyBean.getRefEntiteId() != null) {
			propertyModel.setListRefBeanName(propertyBean.getPropertyName() + "ListRefBean");
			propertyModel.setRefBeanName(propertyBean.getPropertyName() + "RefBean");
			propertyModel.setRefEntiteId(propertyBean.getRefEntiteBeanId());
			propertyModel.setLibelleEntiteRefExp(propertyBean.getLibelleEntiteRef());
			BindingModelDefinition bindingModelDef = new BindingModelDefinition(propertyModel.getRefBeanName(), DataValuesBean.class, bindingPropertyModel.getMode());
			beanModel.addChildModel(createBeanModel(beanModelRoot, DataValuesBean.class, bindingModelDef, userActionDefinition));
			bindingModelDef.setName(propertyModel.getListRefBeanName());
			beanModelRoot.addChildModel(createBeanModel(beanModelRoot, List.class, bindingModelDef, userActionDefinition));
		}
	}
	private static CompositeModel createInstanceBeanModel(RootBeanModel beanModelRoot, Class<?> propertyType, BindingModelDefinition bindingModelDef,
			UserActionDefinition userActionDefinition, EntiteBean entiteBean) {
		CompositeModel beanModel;
		String nameBean = userActionDefinition.getUserActionParser().parse(bindingModelDef.getName());
		ActionMode modeBean = ActionMode.parse(userActionDefinition.getUserActionParser().parse(bindingModelDef.getMode()));
		String libelle = entiteBean.getLibelle();
		String help = entiteBean.getHelp();
		Class<?> type = bindingModelDef.getType();
		int modelType = ModelUtils.getModelType(propertyType);
		beanModel = new BeanModel(nameBean, modeBean, libelle, help, type, modelType, beanModelRoot, userActionDefinition);
		return beanModel;
	}
	public static CompositeModel createBeanModel(RootBeanModel beanModelRoot, Class<?> propertyType, BindingModelDefinition bindingModelDef, UserActionDefinition userActionDefinition) {
		EntiteBean entiteBean = UIRenderUtils.getApplicationCacheDictionaryManager().getEntiteBean(bindingModelDef.getType());
		entiteBean = entiteBean == null ? new EntiteBean() : entiteBean;
		FormaterManger formaterManger = ApplicationConfiguration.applicationManager().formaterManger();
		CompositeModel beanModel = null;
		beanModel = createInstanceBeanModel(beanModelRoot, propertyType, bindingModelDef, userActionDefinition, entiteBean);
		if (ClassUtils.isAssignable(bindingModelDef.getType(), Map.class)) {
			int typeBean = ClassUtils.isAssignable(propertyType, bindingModelDef.getType()) ? BeanAccessorModel.BEAN_TYPE : ModelUtils.getModelType(propertyType);
			beanModel.setBeanAccessorModel(new MapBeanAccessorModel(beanModel, typeBean));
			loadBeanMapModel(beanModelRoot, bindingModelDef, entiteBean, formaterManger, beanModel, userActionDefinition);
		} else {
			loadBeanModel(beanModelRoot, bindingModelDef, entiteBean, formaterManger, beanModel, userActionDefinition);
		}
		return beanModel;
	}
	private static PropertyModel createPropertyModel(RootBeanModel beanModelRoot, Map<String, BindingPropertyModelDefinition> bindingPropertyModels, String mode, EntiteBean entiteBean,
			FormaterManger formaterManger, UserActionDefinition userActionDefinition, PropertyDescriptor propertyDescriptor) {
		PropertyBean propertyBean = null;
		if (entiteBean.getPropertys() != null) propertyBean = entiteBean.getPropertys().get(propertyDescriptor.getName());
		PropertyModel propertyModel = null;
		if (propertyBean != null) {
			propertyModel = new PropertyModel(propertyBean.getPropertyName(), propertyBean.getUserTypeId(), propertyDescriptor.getPropertyType(), propertyBean.getLibelle(),
					propertyBean.getHelp(), propertyBean.getDefaultValue());
			propertyModel.setEncrypted(propertyBean.getEncrypted() != null ? propertyBean.getEncrypted().booleanValue() : false);
		} else if (bindingPropertyModels.containsKey(propertyDescriptor.getName())) {
			BindingPropertyModelDefinition bindingPropertyModel = bindingPropertyModels.get(propertyDescriptor.getName());
			String userType = bindingPropertyModel.getUserType();
			userType = userType == null ? formaterManger.getMapedTypeClass(propertyDescriptor.getPropertyType()) : userType;
			String libelle = bindingPropertyModel.getLibelleExp();
			propertyModel = new PropertyModel(propertyDescriptor.getName(), userType, propertyDescriptor.getPropertyType(), libelle, libelle, null);
		} else {
			propertyModel = new PropertyModel(propertyDescriptor.getName(), formaterManger.getMapedTypeClass(propertyDescriptor.getPropertyType()), propertyDescriptor.getPropertyType(),
					propertyDescriptor.getName(), propertyDescriptor.getName(), null);
		}
		propertyModel.setMode(ActionMode.parse(userActionDefinition.getUserActionParser().parse(mode)));
		return propertyModel;
	}
	public static CompositeModel createBeanModel(RootBeanModel beanModelRoot, Class<?> propertyType, String name, Class<?> type, String mode, UserActionDefinition userActionDefinition) {
		return createBeanModel(beanModelRoot, propertyType, new BindingModelDefinition(name, type, mode), userActionDefinition);
	}
	public static CompositeModel createBeanMapModel(RootBeanModel beanModelRoot, Class<?> propertyType, String name, Class<?> type, String mode, UserActionDefinition userActionDefinition) {
		BeanModel beanPropertyModel = new BeanModel(name, beanModelRoot, userActionDefinition);
		int typeBean = propertyType.equals(type) ? BeanAccessorModel.BEAN_TYPE : ModelUtils.getModelType(propertyType);
		beanPropertyModel.setBeanAccessorModel(new MapBeanAccessorModel(beanPropertyModel, typeBean));
		return beanPropertyModel;
	}
	public static UserActionModel createBeanModel(UserActionDefinition userActionDefinition, UserAction userAction, PresentationDefinition presentationDefinition) throws Exception {
		RootBeanModel beanModelRoot = new RootBeanModel(userActionDefinition.getModelClass(), userActionDefinition);
		EntiteBean entiteBean = new EntiteBean();
		FormaterManger formaterManger = ApplicationConfiguration.applicationManager().formaterManger();
		beanModelRoot.setPropertyDescriptor(BeanObjectUtils.getPropertyDescriptors(userActionDefinition.getModelClass()));
		String[] propertyNames = beanModelRoot.getPropertyDescriptorNames();
		for (int i = 0; i < propertyNames.length; i++) {
			PropertyDescriptor propertyDescriptor = beanModelRoot.getPropertyDescriptor(propertyNames[i]);
			if (ConverterUtil.isFiledType(propertyDescriptor.getPropertyType())) {
				PropertyModel propertyModel = createPropertyModel(beanModelRoot, userActionDefinition.getBindingPropertyModels(), "c", entiteBean, formaterManger, userActionDefinition,
						propertyDescriptor);
				beanModelRoot.addChildModel(propertyModel);
			} else if (userActionDefinition.getBindingModels().containsKey(propertyDescriptor.getName())) {
				BindingModelDefinition bindingModelDefinition = userActionDefinition.getBindingModels().get(propertyDescriptor.getName());
				if (bindingModelDefinition instanceof BindingRefModelDefinition) {
					String name = bindingModelDefinition.getName();
					BindingRefModelDefinition bindingRefModel = (BindingRefModelDefinition) bindingModelDefinition;
					BindingModelDefinition bindingRefModelDefinition = presentationDefinition.getBindingModelDefinition(new ComponentIdImpl(userAction.getUserActionId().getSujetId(),
							bindingRefModel.getRef()));
					bindingModelDefinition = new BindingModelDefinition();
					bindingRefModelDefinition.copyTo(bindingModelDefinition);
					bindingModelDefinition.setName(name);
				}
				CompositeModel beanModelChild = createBeanModel(beanModelRoot, propertyDescriptor.getPropertyType(), bindingModelDefinition, userActionDefinition);
				beanModelRoot.addChildModel(beanModelChild);
			}
		}
		Set<String> keySet = userActionDefinition.getBindingPropertyModels().keySet();
		String[] pnames = keySet.toArray(new String[userActionDefinition.getBindingPropertyModels().size()]);
		for (int i = 0; i < pnames.length; i++) {
			BindingPropertyModelDefinition bindingPropertyModel = userActionDefinition.getBindingPropertyModels().get(pnames[i]);
			if (!beanModelRoot.containsPropertyDescriptor(pnames[i])) addBindingPropertyModel(beanModelRoot, entiteBean, formaterManger, beanModelRoot, userActionDefinition, bindingPropertyModel);
		}
		beanModelRoot.setUserAction(userAction);
		return beanModelRoot;
	}
}
