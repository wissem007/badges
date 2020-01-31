package tn.com.smartsoft.iap.dictionary.decoupage.entite.presentation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;

public class EntiteModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;

	private List listModule;
	private List listSubModule;
	private List listSujet;
	private List listNiveauApplicatif;
	private List<DataBusinessBean> listTypeEntites;
	private List<DataBusinessBean> listRefEntites;

	private List listSequence;
	private List listUserType;
	private List listProprietes;
	private List listFieldRoles;

	public List getListFieldRoles() {
		return listFieldRoles;
	}

	public void setListFieldRoles(List listFieldRoles) {
		this.listFieldRoles = listFieldRoles;
	}

	public List getListSequence() {
		return listSequence;
	}

	public void setListSequence(List listSequence) {
		this.listSequence = listSequence;
	}

	public List getListUserType() {
		return listUserType;
	}

	public void setListUserType(List listUserType) {
		this.listUserType = listUserType;
	}

	public List getListModule() {
		return listModule;
	}

	public void setListModule(List listModule) {
		this.listModule = listModule;
	}

	public List getListSubModule() {
		return listSubModule;
	}

	public void setListSubModule(List listSubModule) {
		this.listSubModule = listSubModule;
	}

	public List getListSujet() {
		return listSujet;
	}

	public void setListSujet(List listSujet) {
		this.listSujet = listSujet;
	}

	public List getListProprietes() {
		return listProprietes;
	}

	public void setListProprietes(List listProprietes) {
		this.listProprietes = listProprietes;
	}

	public List<DataBusinessBean> getListRefEntites() {
		return listRefEntites;
	}

	public void setListRefEntites(List<DataBusinessBean> listRefEntites) {
		this.listRefEntites = listRefEntites;
	}

	public void setDetailBean(DataBusinessBean detailBean) {
		if (detailBean != null) {
			Map<String, PropertyBean> propertys = ((EntiteBean) detailBean).getPropertys();
			if (propertys != null)
				this.listProprietes = new ArrayList(propertys.values());
			else
				this.listProprietes = null;
		}
		super.setDetailBean(detailBean);
	}

	public void setListTypeEntites(List<DataBusinessBean> listTypeEntites) {
		this.listTypeEntites = listTypeEntites;
	}

	public List<DataBusinessBean> getListTypeEntites() {
		return listTypeEntites;
	}

	public void setListNiveauApplicatif(List listNiveauApplicatif) {
		this.listNiveauApplicatif = listNiveauApplicatif;
	}

	public List getListNiveauApplicatif() {
		return listNiveauApplicatif;
	}
}
