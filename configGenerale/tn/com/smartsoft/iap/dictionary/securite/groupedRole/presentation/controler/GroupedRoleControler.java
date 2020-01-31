package tn.com.smartsoft.iap.dictionary.securite.groupedRole.presentation.controler;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.commons.utils.ValueUtils;
import tn.com.smartsoft.configGenerale.dynamicEntite.entiteSimple.beans.DataValuesBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.iap.dictionary.decoupage.module.beans.ModuleBean;
import tn.com.smartsoft.iap.dictionary.decoupage.module.business.ModuleBusiness;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.EntiteRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.GroupedRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.business.GroupedRoleBusiness;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.presentation.model.GroupedRoleModel;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class GroupedRoleControler extends GenericEntiteControler{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private ModuleBusiness		moduleBusiness;

	public void setModuleBusiness(ModuleBusiness moduleBusiness) {
		this.moduleBusiness = moduleBusiness;
	}
	public void onInitList(ListenerContext context) throws FunctionalException {
		GroupedRoleModel entiteModel = (GroupedRoleModel) context.userAction().getDataObject();
		List<DataBusinessBean> listModules = moduleBusiness.getByCriteria(new ModuleBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		DataValuesBean data1 = new DataValuesBean();
		DataValuesBean data2 = new DataValuesBean();
		data1.setDataValueId("A");
		data1.setLibelle("Action");
		data2.setDataValueId("F");
		data2.setLibelle("Filed");
		List<DataBusinessBean> listNatureRoles = new ArrayList<DataBusinessBean>();
		listNatureRoles.add(data1);
		listNatureRoles.add(data2);
		entiteModel.setListModules(listModules);
		entiteModel.setListNatureRoles(listNatureRoles);
	}
	public void onInitListParentByModule(ListenerContext context) throws FunctionalException {
		GroupedRoleModel entiteModel = (GroupedRoleModel) context.userAction().getDataObject();
		String moduleId = ((RoleBean) entiteModel.getDetailBean()).getModuleId();
		if (StringUtils.isNotBlank(moduleId)) {
			RoleBean roleBean = new RoleBean();
			roleBean.setModuleId(moduleId);
			List<DataBusinessBean> listRoles = ((GroupedRoleBusiness) entiteBusiness).getGroupedRoles(roleBean, context.userAction().getUserActionId().getActionBeanId(), context
					.userDesktop().userContext());
			BeanComparator roleComparator = new BeanComparator("rang", SorterType.ASC, false);
			roleComparator.sort(listRoles);
			entiteModel.setListRoles(listRoles);
		}
		JsonItemResponseModel.reponseEmpty(context);
	}
	public void doSelectedBean(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		UIExtGrid grid = (UIExtGrid) context.source();
		entiteModel.setBeanIndex(grid.getSelectedRow());
		DataBusinessBean rowBean = entiteModel.getListBean().get(entiteModel.getBeanIndex());
		rowBean = entiteBusiness.getById(rowBean, context.userAction().getActionBeanId(), context.userContext());
		entiteModel.setDetailBean(rowBean);
		this.onInitListParentByModule(context);
		if (rowBean instanceof GroupedRoleBean) {
			context.userAction().getModel().addChildModel("detailBean", GroupedRoleBean.class, "r");
			context.userAction().goToWindow(this.controlerParams.getDetailWindowId(), context.webContext());
		} else {
			context.userAction().getModel().addChildModel("detailBean", EntiteRoleBean.class, "r");
			BeanModel beanModel = (BeanModel) context.userAction().getModel().findFieldModel("detailBean");
			beanModel.addChildModel("itemActionRoles", ActionRoleBean.class, "r");
			beanModel.addChildModel("itemFieldRoles", FieldRoleBean.class, "r");
			EntiteRoleBean entiteRoleBean = (EntiteRoleBean) entiteModel.getDetailBean();
			affectListItemRoles(entiteRoleBean);
			context.userAction().goToWindow("detailEntiteWindow", context.webContext());
		}
	}
	public void affectListItemRoles(EntiteRoleBean rowBean) {
		List<ItemRoleBean> itemRoles = rowBean.getItemRoles();
		List<ItemRoleBean> itemActionRoles = new ArrayList<ItemRoleBean>();
		List<ItemRoleBean> itemFieldRoles = new ArrayList<ItemRoleBean>();
		if (itemRoles != null && itemRoles.size() > 0) for (int i = 0; i < itemRoles.size(); i++) {
			if (itemRoles.get(i) != null) {
				if (itemRoles.get(i) instanceof ActionRoleBean) itemActionRoles.add(itemRoles.get(i));
				else if (itemRoles.get(i) instanceof FieldRoleBean) itemFieldRoles.add(itemRoles.get(i));
			}
		}
		affectDependenceActionRoles(itemActionRoles);
		affectDependenceFilednRoles(itemActionRoles, itemFieldRoles);
		rowBean.setItemActionRoles(itemActionRoles);
		rowBean.setItemFieldRoles(itemFieldRoles);
	}
	public void affectDependenceActionRoles(List<ItemRoleBean> itemActionRoles) {
		if (itemActionRoles != null && itemActionRoles.size() > 0) for (int i = 0; i < itemActionRoles.size(); i++) {
			ActionRoleBean actionRoleBean = (ActionRoleBean) itemActionRoles.get(i);
			String dependenceRoleId = actionRoleBean.getDependenceRoleId();
			if (!StringUtils.isEmpty(dependenceRoleId)) {
				int index = getIndex(itemActionRoles, "roleId", dependenceRoleId);
				actionRoleBean.setDepId(new Long(index));
			}
		}
	}
	public void affectDependenceFilednRoles(List<ItemRoleBean> itemActionRoles, List<ItemRoleBean> itemFieldRoles) {
		if (itemFieldRoles != null && itemFieldRoles.size() > 0) for (int i = 0; i < itemFieldRoles.size(); i++) {
			FieldRoleBean fieldRoleBean = (FieldRoleBean) itemFieldRoles.get(i);
			String dependenceRoleId = fieldRoleBean.getDependenceRoleId();
			if (!StringUtils.isEmpty(dependenceRoleId)) {
				int index = getIndex(itemActionRoles, "roleId", dependenceRoleId);
				if (index > 0) {
					fieldRoleBean.setNatureDepRole("A");
				} else {
					index = getIndex(itemActionRoles, "roleId", dependenceRoleId);
					if (index > 0) fieldRoleBean.setNatureDepRole("F");
				}
				fieldRoleBean.setDepId(new Long(index));
			}
		}
	}
	public static int getIndex(List<?> list, final String property, final Object value) {
		Object value1;
		try {
			for (int i = 0; i < list.size(); i++) {
				Object bean = list.get(i);
				value1 = PropertyUtils.getProperty(bean, property);
				if (ValueUtils.equals(value1, value)) return i + 1;
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
}
