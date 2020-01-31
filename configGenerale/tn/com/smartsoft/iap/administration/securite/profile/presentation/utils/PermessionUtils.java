package tn.com.smartsoft.iap.administration.securite.profile.presentation.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.utils.ValueUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.security.GrantedAuthorityUtils;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.EntiteRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.GroupedRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBeanId;

public class PermessionUtils{

	public static List<DataBusinessBean> createAutorisationRoles(Map<?, ?> mapedPermissons, Map<?, ?> listRoles, Long profileId) {
		List<DataBusinessBean> listRolePer = new ArrayList<DataBusinessBean>();
		List<DataBusinessBean> listPermissons = new ArrayList<DataBusinessBean>();
		Iterator<?> iterator = listRoles.keySet().iterator();
		while (iterator.hasNext()) {
			RoleBean bean = (RoleBean) listRoles.get(iterator.next());
			if (bean instanceof ActionRoleBean || bean instanceof FieldRoleBean) {
				PermissionBean findBean = (PermissionBean) mapedPermissons.get(new PermissionBeanId(profileId, bean.getModuleId(), bean.getRoleId()));
				Long permission = findBean != null ? findBean.getPermission() : new Long(0);
				if (bean instanceof ActionRoleBean) {
					bean.setPermission(permission);
				} else if (bean instanceof FieldRoleBean) {
					char[] permissionFields = GrantedAuthorityUtils.createFieldsPermission(permission);
					bean.setPermission(new Long(ArrayUtils.contains(permissionFields, '1') ? 1 : 0));
					PermessionUtils.addOptionFieldRole(listPermissons, ((FieldRoleBean) bean), permissionFields);
				}
				listRolePer.add(bean);
			}
			listPermissons.add(bean);
		}
		for (int i = 0; i < listRolePer.size(); i++) {
			RoleBean bean = (RoleBean) listRolePer.get(i);
			PermessionUtils.updateParentRolePermission(listRoles, bean);
		}
		return listPermissons;
	}
	public static void addOptionFieldRole(List<DataBusinessBean> ListPermissons, FieldRoleBean fieldRole, char[] permissionBinaire) {
		EntiteBean entiteBean = ApplicationConfiguration.applicationManager().presentationBeanFactory().getCacheDictionaryManager().getEntiteBean(fieldRole.getClass());
		String[] pnames = { "isSearcheble", "isReadable", "isInsertable", "isUpdatable" };
		String[] natures = { "S", "R", "I", "U" };
		Boolean[] options = { fieldRole.isSearcheble(), fieldRole.isReadable(), fieldRole.isInsertable(), fieldRole.isUpdatable() };
		for (int i = 0; i < options.length; i++) {
			if (fieldRole.isUpdatable()) {
				ItemRoleBean roleBean = new ItemRoleBean();
				roleBean.setNatureRole("FI");
				roleBean.setRang(new Long(i));
				roleBean.setModuleId(fieldRole.getModuleId());
				roleBean.setPrentRoleId(fieldRole.getRoleId());
				roleBean.setRoleId("9999" + i);
				roleBean.setNatureRole(natures[i]);
				roleBean.setPermission((new Long(Integer.parseInt(String.valueOf(permissionBinaire[i])))));
				PropertyBean p = entiteBean.getProperty(pnames[i]);
				if (p != null) {
					roleBean.setHelp(p.getHelp());
					roleBean.setLibelle(p.getLibelle());
				}
				ListPermissons.add(roleBean);
			}
		}
	}
	public static void updateParentRolePermission(Map<?, ?> listRoles, RoleBean bean) {
		String parentRoleIdValue = null;
		if (bean instanceof ItemRoleBean) {
			parentRoleIdValue = ((ItemRoleBean) bean).getPrentRoleId();
		}
		if (bean instanceof GroupedRoleBean) {
			parentRoleIdValue = ((GroupedRoleBean) bean).getPrentRoleId();
		}
		if (bean instanceof EntiteRoleBean) {
			parentRoleIdValue = ((EntiteRoleBean) bean).getPrentRoleId();
		}
		if (StringUtils.isBlank(parentRoleIdValue)) { return; }
		RoleBean parentRoleBean = (RoleBean) listRoles.get(new RoleBeanId(parentRoleIdValue, bean.getModuleId()));
		if (ValueUtils.equals(parentRoleBean.getPermission(), new Long(1)) && ValueUtils.equals(parentRoleBean.getPermission(), new Long(1))) { return; }
		if (ValueUtils.equals(bean.getPermission(), new Long(1))) {
			parentRoleBean.setPermission(new Long(1));
			PermessionUtils.updateParentRolePermission(listRoles, parentRoleBean);
		}
	}
}
