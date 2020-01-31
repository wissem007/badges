package tn.com.smartsoft.iap.administration.securite.profile.business;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.ListUtils;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.business.GenericEntiteBusiness;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbPersistantSupport;
import tn.com.smartsoft.framework.dao.DbSequenceSupport;
import tn.com.smartsoft.iap.administration.securite.profile.beans.PermissionBean;
import tn.com.smartsoft.iap.administration.securite.profile.beans.ProfileBean;
import tn.com.smartsoft.iap.administration.securite.profile.dao.ProfileDao;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.ActionRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.beans.FieldRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.ItemRoleBean;
import tn.com.smartsoft.iap.dictionary.securite.role.beans.RoleBean;

public class ProfileBusiness extends GenericEntiteBusiness{
	
	private static final long	serialVersionUID	= 1L;
	protected DaoParseBean		parsePermissionBean;
	
	public void setParsePermissionBean(DaoParseBean parsePermissionBean) {
		this.parsePermissionBean = parsePermissionBean;
	}
	public Map<?, ?> getPermissions(DataBusinessBean bean, ActionBeanId actionId, UserContext userContext) throws FunctionalException {
		List<DataBusinessBean> dataList = ((ProfileDao) entiteDao).getPermissions(daoSession, userContext.userId(), false, bean);
		return parsePermissionBean.parseMap(dataList);
	}
	public void deletePermissions(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		((ProfileDao) entiteDao).deletePermissions(daoSession, userContext.userId(), false, bean);
	}
	public Serializable create(DataBusinessBean bean, UserContext userContext) throws FunctionalException {
		DbSequenceSupport dbSequenceSupport = daoSession.sequenceSupport(userContext);
		dbSequenceSupport.setSequenceValues(bean);
		return super.create(bean, userContext);
	}
	public Serializable createPermission(DataBusinessBean bean, List<DataBusinessBean> listPermissons, UserContext userContext) throws FunctionalException {
		Serializable save = null;
		try {
			DbPersistantSupport persistantSupport = daoSession.persistantSupport(userContext);
			daoSession.beginTransaction();
			ProfileBean detailBean = (ProfileBean) bean;
			PermissionBean searcheBean = new PermissionBean();
			searcheBean.setProfileId(detailBean.getProfileId());
			searcheBean.setModuleId(detailBean.getModuleId());
			deletePermissions(searcheBean, userContext);
			for (int i = 0; i < listPermissons.size(); i++) {
				RoleBean roleBean = (RoleBean) listPermissons.get(i);
				Long permission = roleBean.getPermission();
				permission = permission == null ? permission = new Long(0) : permission;
				if (permission.intValue() > 0) {
					if (roleBean instanceof ActionRoleBean || roleBean instanceof FieldRoleBean) {
						PermissionBean permissionBean = new PermissionBean();
						permissionBean.setModuleId(detailBean.getModuleId());
						permissionBean.setProfileId(detailBean.getProfileId());
						permissionBean.setRoleId(roleBean.getRoleId());
						if (roleBean instanceof ActionRoleBean) {
							permissionBean.setPermission(roleBean.getPermission());
						} else {
							permissionBean.setPermission(getPermissionFieldRole(listPermissons, roleBean.getRoleId()));
						}
						persistantSupport.save(permissionBean);
						saveAudit(permissionBean, userContext, businessParams.getCreateActionId());
					}
				}
			}
			daoSession.commitTransaction();
			return save;
		} catch (DaoFunctionalException e) {
			daoSession.rollbackTransaction();
			throw e;
		}
	}
	@SuppressWarnings({ "rawtypes" })
	public Long getPermissionFieldRole(List<DataBusinessBean> listPermissons, String roleId) {
		String strSearch = "1";
		String strRead = "1";
		String strInsert = "1";
		String strUpdate = "1";
		List list = ListUtils.selectByProperty(listPermissons, "prentRoleId", roleId);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ItemRoleBean bean = (ItemRoleBean) list.get(i);
				if (bean.getNatureRole().equals("S")) {
					strSearch = bean.getPermission().toString();
				}
				if (bean.getNatureRole().equals("R")) {
					strRead = bean.getPermission().toString();
				}
				if (bean.getNatureRole().equals("I")) {
					strInsert = bean.getPermission().toString();
				}
				if (bean.getNatureRole().equals("U")) {
					strUpdate = bean.getPermission().toString();
				}
			}
		}
		String str = strSearch + strRead + strInsert + strUpdate;
		return new Long(Integer.parseInt(str, 2));
	}
}
