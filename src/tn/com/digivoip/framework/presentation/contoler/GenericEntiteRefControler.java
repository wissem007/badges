package tn.com.digivoip.framework.presentation.contoler;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;

public abstract class GenericEntiteRefControler extends tn.com.smartsoft.framework.presentation.contoler.GenericEntiteControler {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public void onRendeListWindow(ListenerContext context) throws FunctionalException {
		GenericEntiteModel entiteModel = (GenericEntiteModel) context.userAction().getDataObject();
		List<DataBusinessBean> list = entiteBusiness.getByCriteria(getInstanceBean(), context.userAction().getActionBeanId(), context.userDesktop().userContext());
		entiteModel.setListBean(list);
		UIExtGrid grid = (UIExtGrid) context.userAction().getWindow(controlerParams.getListWindowId()).findChild(controlerParams.getDisplayGridId());
		grid.setHidden(entiteModel.getListBean() == null || entiteModel.getListBean().size() == 0);
		grid.getStore().setAutoLoad(!(entiteModel.getListBean() == null || entiteModel.getListBean().size() == 0));
	}
	
	public abstract DataBusinessBean getInstanceBean();
}
