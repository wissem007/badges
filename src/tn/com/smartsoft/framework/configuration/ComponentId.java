package tn.com.smartsoft.framework.configuration;

import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBeanId;
import tn.com.smartsoft.iap.dictionary.decoupage.sujet.beans.SujetBeanId;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBeanId;

public interface ComponentId {

	public abstract SujetBeanId getSujetId();

	public abstract String getId();

	public abstract ViewBeanId getViewBeanId();

	public abstract EntiteBeanId getEntiteBeanId();

	public abstract ActionBeanId getActionBeanId();

}