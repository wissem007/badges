package tn.com.smartsoft.iap.administration.securite.utilisateur.presentation.model;

import java.util.ArrayList;
import java.util.List;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class UserModel extends GenericEntiteModel{
	
	private static final long		serialVersionUID	= 1L;
	private List					listPays;
	private List					listLocalite;
	private List					listGouvernorat;
	private List					listSociete;
	private List					listProfile;
	private List					listOrganisme;
	private List					listUser;
	private List<DataBusinessBean>	profileModules		= new ArrayList<DataBusinessBean>();
	private List<DataBusinessBean>	listModule;
	private List<DataBusinessBean>	listFonctionUtil;
	
	public List getListUser() {
		return listUser;
	}
	public void setListUser(List listUser) {
		this.listUser = listUser;
	}
	public List getListSociete() {
		return listSociete;
	}
	public void setListSociete(List listSociete) {
		this.listSociete = listSociete;
	}
	public List getListProfile() {
		return listProfile;
	}
	public void setListProfile(List listProfile) {
		this.listProfile = listProfile;
	}
	public List getListOrganisme() {
		return listOrganisme;
	}
	public void setListOrganisme(List listOrganisme) {
		this.listOrganisme = listOrganisme;
	}
	public List getListPays() {
		return listPays;
	}
	public void setListPays(List listPays) {
		this.listPays = listPays;
	}
	public List getListLocalite() {
		return listLocalite;
	}
	public void setListLocalite(List listLocalite) {
		this.listLocalite = listLocalite;
	}
	public List getListGouvernorat() {
		return listGouvernorat;
	}
	public void setListGouvernorat(List listGouvernorat) {
		this.listGouvernorat = listGouvernorat;
	}
	public List<DataBusinessBean> getProfileModules() {
		return profileModules;
	}
	public void setProfileModules(List<DataBusinessBean> profileModules) {
		this.profileModules = profileModules;
	}
	public List<DataBusinessBean> getListModule() {
		return listModule;
	}
	public void setListModule(List<DataBusinessBean> listModule) {
		this.listModule = listModule;
	}
	public List<DataBusinessBean> getListFonctionUtil() {
		return listFonctionUtil;
	}
	public void setListFonctionUtil(List<DataBusinessBean> listFonctionUtil) {
		this.listFonctionUtil = listFonctionUtil;
	}
}
