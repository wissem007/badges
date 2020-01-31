package tn.com.smartsoft.iap.administration.securite.profile.presentation.model;

import java.util.List;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class ProfileModel extends GenericEntiteModel{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	private List<DataBusinessBean>	listProfiles;
	private List<DataBusinessBean>	listModule;
	private List<DataBusinessBean>	listPermissons;

	public List<DataBusinessBean> getListModule() {
		return listModule;
	}
	public void setListModule(List<DataBusinessBean> listModule) {
		this.listModule = listModule;
	}
	public List<DataBusinessBean> getListProfiles() {
		return listProfiles;
	}
	public void setListProfiles(List<DataBusinessBean> listProfiles) {
		this.listProfiles = listProfiles;
	}
	public List<DataBusinessBean> getListPermissons() {
		return listPermissons;
	}
	public void setListPermissons(List<DataBusinessBean> listPermissons) {
		this.listPermissons = listPermissons;
	}
}
