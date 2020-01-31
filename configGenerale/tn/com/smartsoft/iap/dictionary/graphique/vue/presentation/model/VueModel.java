package tn.com.smartsoft.iap.dictionary.graphique.vue.presentation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;

public class VueModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;

	private List listModule;
	private List listSubModule;
	private List listSujet;
	private List listActionTemplate;

	private List listViewsLibelles;
	private List listViewsActions;

	
	
	public List getListActionTemplate() {
		return listActionTemplate;
	}

	public void setListActionTemplate(List listActionTemplate) {
		this.listActionTemplate = listActionTemplate;
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

	/***************************/

	public List getListViewsLibelles() {
		return listViewsLibelles;
	}

	public void setListViewsLibelles(List listViewsLibelles) {
		this.listViewsLibelles = listViewsLibelles;
	}

	public List getListViewsActions() {
		return listViewsActions;
	}

	public void setListViewsActions(List listViewsActions) {
		this.listViewsActions = listViewsActions;
	}

//	public void setDetailBean(DataBusinessBean detailBean) {
//		if (detailBean != null) {
//			Map<String, PropertyBean> propertys = ((EntiteBean) detailBean).getPropertys();
//			if (propertys != null) {
//				this.listViewsLibelles = new ArrayList(propertys.values());
//				this.listViewsActions = new ArrayList(propertys.values());
//			} else {
//				this.listViewsLibelles = null;
//				this.listViewsActions = null;
//			}
//
//		}
//		super.setDetailBean(detailBean);
//	}
	/***************************/
}

/***************************/

