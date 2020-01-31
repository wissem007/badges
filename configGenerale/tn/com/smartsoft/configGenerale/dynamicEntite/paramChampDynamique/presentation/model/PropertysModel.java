package tn.com.smartsoft.configGenerale.dynamicEntite.paramChampDynamique.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class PropertysModel extends GenericEntiteModel {

	private static final long serialVersionUID = 1L;
	private List<DataBusinessBean> listModule;
	private List<DataBusinessBean> listModuleRef;
	private List<DataBusinessBean> listEntite;
	private List<DataBusinessBean> listEntiteRef;
	private List<DataBusinessBean> listUserType;

	public void setListModule(List<DataBusinessBean> listModule) {
		this.listModule = listModule;
	}

	public List<DataBusinessBean> getListModule() {
		return listModule;
	}

	public void setListEntite(List<DataBusinessBean> listEntite) {
		this.listEntite = listEntite;
	}

	public List<DataBusinessBean> getListEntite() {
		return listEntite;
	}

	public void setListUserType(List<DataBusinessBean> listUserType) {
		this.listUserType = listUserType;
	}

	public List<DataBusinessBean> getListUserType() {
		return listUserType;
	}

	public void setListModuleRef(List<DataBusinessBean> listModuleRef) {
		this.listModuleRef = listModuleRef;
	}

	public List<DataBusinessBean> getListModuleRef() {
		return listModuleRef;
	}

	public void setListEntiteRef(List<DataBusinessBean> listEntiteRef) {
		this.listEntiteRef = listEntiteRef;
	}

	public List<DataBusinessBean> getListEntiteRef() {
		return listEntiteRef;
	}

}
