package tn.com.smartsoft.iap.dictionary.decoupage.application.presentation.model;

import java.util.List;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class ApplicationModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;
	private List listModule;
	private List listSystemModule;
	private List listDevise;
	List<DataBusinessBean> listPays;

	public List getListModule() {
		return listModule;
	}

	public void setListModule(List listModule) {
		this.listModule = listModule;
	}

	public List getListSystemModule() {
		return listSystemModule;
	}

	public void setListSystemModule(List listSystemModule) {
		this.listSystemModule = listSystemModule;
	}

	public List getListDevise() {
		return listDevise;
	}

	public void setListDevise(List listDevise) {
		this.listDevise = listDevise;
	}

	public List<DataBusinessBean> getListPays() {
		return listPays;
	}

	public void setListPays(List<DataBusinessBean> listPays) {
		this.listPays = listPays;
	}

}
