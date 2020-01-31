package tn.com.smartsoft.iap.dictionary.securite.role.presentation.controler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.iap.dictionary.securite.groupedRole.presentation.controler.GroupedRoleControler;

public class ItemRoleControler extends GroupedRoleControler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onInitTreeRoles(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();

		List<DataBusinessBean> listRolees = entiteBusiness.getByCriteria(entiteModel.getSearcheBean(), context.userAction().getUserActionId().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListBean(listRolees);

	}

}
