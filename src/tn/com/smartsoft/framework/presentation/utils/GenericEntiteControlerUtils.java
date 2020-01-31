package tn.com.smartsoft.framework.presentation.utils;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans.DataValuesBean;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.business.DataValuesBusiness;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanModel;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.tags.handler.GenericTagHandler;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;

public class GenericEntiteControlerUtils {
	public static void addDynamicColumnsGrid(ListenerContext context, String gridName, String beanName) {
		UIWindow window = (UIWindow) context.source();
		UIExtGrid grid = (UIExtGrid) window.findChild(gridName);
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel(beanName);
		String[] ns = beanModel.getFieldsNames();
		for (int i = 0; i < ns.length; i++) {
			String name = ns[i];
			addDynamicColumnGrid(grid, beanModel, name);
		}
	}
	
	public static void addDynamicPropertysList(ListenerContext context, DataValuesBusiness dataValuesBusiness, String beanName) throws FunctionalException {
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel(beanName);
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		String[] ns = beanModel.getFieldsNames();
		for (int i = 0; i < ns.length; i++) {
			String name = ns[i];
			addDynamicPropertyList(context, dataValuesBusiness, beanModel, entiteModel, name);
		}
	}
	
	public static void addDynamicPropertysFilerField(ListenerContext context, String parentComponentName, String beanName) {
		UIWindow window = (UIWindow) context.source();
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel(beanName);
		UIExtComponent parentComponent = (UIExtComponent) window.findChild(parentComponentName);
		String[] ns = beanModel.getFieldsNames();
		for (int i = 0; i < ns.length; i++) {
			String name = ns[i];
			ItemModel itemModel = beanModel.getChildModel(name);
			if (itemModel instanceof PropertyModel) {
				addDynamicPropertyField(parentComponent, beanModel, beanName, (PropertyModel) itemModel);
			}
		}
	}
	
	public static void loadDynamicPropertysRefObjectValue(ListenerContext context, String beanName, DataBusinessBean rowBean) {
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel(beanName);
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		String[] ns = beanModel.getFieldsNames();
		for (int i = 0; i < ns.length; i++) {
			String name = ns[i];
			ItemModel itemModel = beanModel.getChildModel(name);
			if (itemModel instanceof PropertyModel) {
				loadDynamicPropertyRefObjectValue(context, beanModel, entiteModel, name, rowBean);
			}
		}
	}
	
	public static void addDynamicPropertysField(ListenerContext context, String parentComponentName, String beanName) {
		UIWindow window = (UIWindow) context.source();
		BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel(beanName);
		UIExtComponent parentComponent = (UIExtComponent) window.findChild(parentComponentName);
		String[] ns = beanModel.getFieldsNames();
		for (int i = 0; i < ns.length; i++) {
			String name = ns[i];
			ItemModel itemModel = beanModel.getChildModel(name);
			if (itemModel instanceof PropertyModel) {
				addDynamicPropertyField(parentComponent, beanModel, beanName, (PropertyModel) itemModel);
			}
		}
	}
	
	public static void addDynamicColumnGrid(UIExtGrid gridDispaly, BeanModel beanModel, String name) {
		ItemModel itemModel = beanModel.getChildModel(name);
		if (itemModel instanceof PropertyModel && ((PropertyModel) itemModel).isDynamique()) {
			PropertyModel propertyModel = (PropertyModel) itemModel;
			String propertyName = propertyModel.getName();
			String labelExp = "";
			String id = propertyName + "Index";
			if (StringUtils.isNotBlank(((PropertyModel) itemModel).getRefBeanName())) {
				EntiteBean entiteBean = UIRenderUtils.getApplicationCacheDictionaryManager().getEntiteBean(propertyModel.getRefEntiteId());
				propertyName = propertyModel.getRefBeanName() + "." + "libelle";
				labelExp = entiteBean.getLibelle();
			}
			gridDispaly.getStore().addTag("ext-store-field", "id", "property", id, propertyName);
			if (StringUtils.equals(propertyModel.getUserType(), "boolean"))
				gridDispaly.addTag("ext-grid-column", new String[] { "data-index", "width", "label-exp", "xtype", "hideable" }, new String[] { id, "80", labelExp, "check", "true" });
			else
				gridDispaly.addTag("ext-grid-column", "data-index", "width", "label-exp", id, "150", labelExp);
		}
	}
	
	public static void addDynamicPropertyList(ListenerContext context, DataValuesBusiness dataValuesBusiness, BeanModel beanModel, GenericEntiteModel entiteModel, String name)
			throws FunctionalException {
		ItemModel itemModel = beanModel.getChildModel(name);
		if (itemModel instanceof PropertyModel && ((PropertyModel) itemModel).isDynamique() && StringUtils.isNotBlank(((PropertyModel) itemModel).getRefBeanName())) {
			PropertyModel propertyModel = (PropertyModel) itemModel;
			DataValuesBean bean = new DataValuesBean(propertyModel.getRefEntiteId());
			List<?> listDataValues = dataValuesBusiness.getListRefEntite(bean, context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
			entiteModel.setDynamicPropertyValue(propertyModel.getListRefBeanName(), listDataValues);
		}
	}
	
	public static void loadDynamicPropertyRefObjectValue(ListenerContext context, BeanModel beanModel, GenericEntiteModel entiteModel, String name, DataBusinessBean rowBean) {
		ItemModel itemModel = beanModel.getChildModel(name);
		if (itemModel instanceof PropertyModel && ((PropertyModel) itemModel).isDynamique() && StringUtils.isNotBlank(((PropertyModel) itemModel).getRefBeanName())) {
			PropertyModel propertyModel = (PropertyModel) itemModel;
			Object pvalue = rowBean.getDynamicPropertyValue(name);
			Object refObject = null;
			if (pvalue != null) {
				List<?> listDataValues = (List<?>) entiteModel.getDynamicPropertyValue(propertyModel.getListRefBeanName());
				refObject = ListUtils.findByProperty(listDataValues, "dataValueId", pvalue);
			}
			rowBean.setDynamicPropertyValue(propertyModel.getRefBeanName(), refObject);
		}
	}
	
	public static void addDynamicPropertyField(UIExtComponent parentComponent, BeanModel beanModel, String beanName, PropertyModel propertyModel) {
		if (propertyModel.isDynamique()) {
			if (StringUtils.isBlank(propertyModel.getRefBeanName())) {
				if (StringUtils.equals(propertyModel.getUserType(), "boolean"))
					parentComponent.addTag("ext-field-checkbox", "property", beanName + "." + propertyModel.getName());
				else if (StringUtils.equals(propertyModel.getUserType(), "date"))
					parentComponent.addTag("ext-field-date", "id", "property", propertyModel.getName(), beanName + "." + propertyModel.getName());
				else if (StringUtils.equals(propertyModel.getUserType(), "numeric"))
					parentComponent.addTag("ext-field-number", "id", "property", propertyModel.getName(), beanName + "." + propertyModel.getName());
				else
					parentComponent.addTag("ext-field-text", "id", "property", propertyModel.getName(), beanName + "." + propertyModel.getName());
			} else {
				String dynStoreId = propertyModel.getName() + "DynStore";
				GenericTagHandler storeFil = new GenericTagHandler("ext-store", "id", "model-name", "id-property", dynStoreId, propertyModel.getListRefBeanName(), "dataValueIdIndex");
				storeFil.addChildren("ext-store-field", "id", "property", "dataValueIdIndex", "dataValueId");
				storeFil.addChildren("ext-store-field", "id", "property", "dataValueLibelleIndex", "libelle");
				parentComponent.getWindow().getRoot().addTag(storeFil);
				EntiteBean entiteBean = UIRenderUtils.getApplicationCacheDictionaryManager().getEntiteBean(propertyModel.getRefEntiteId());
				GenericTagHandler comboFil = new GenericTagHandler("ext-field-combo", new String[] { "id", "property", "store-id", "display-field", "label-exp" }, new String[] {
						propertyModel.getName(), beanName + "." + propertyModel.getName(), dynStoreId, "dataValueLibelleIndex", entiteBean.getLibelle() });
				comboFil.addChildren(storeFil);
				parentComponent.addTag(comboFil);
			}
		}
	}
}
