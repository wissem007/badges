package tn.com.smartsoft.iap.dictionary.outils.parametrage.presentation.model;

import java.util.ArrayList;
import java.util.List;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class OutilModel extends GenericEntiteModel{

	private static final long		serialVersionUID	= 1L;
	private List<DataBusinessBean>	listModule			= new ArrayList<DataBusinessBean>();

	public List<DataBusinessBean> getListModule() {
		return listModule;
	}
	public void setListModule(List<DataBusinessBean> listModule) {
		this.listModule = listModule;
	}
}
