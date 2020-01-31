package tn.com.smartsoft.framework.dao;

import java.io.Serializable;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.iap.administration.securite.audit.beans.AuditEntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.action.beans.ActionBeanId;

public interface AuditSupport extends Serializable {

	public void begin(ActionBeanId id, Object beanRoot) throws FunctionalException;

	public AuditEntiteBean addAuditEntite(DataBusinessBean bean, DataBusinessBean oldBean, ActionMode mode) throws FunctionalException;

	public AuditEntiteBean addAuditEntite(AuditEntiteBean mAuditEntiteBean, DataBusinessBean bean, DataBusinessBean oldBean, ActionMode mode) throws FunctionalException;

	public void save() throws FunctionalException;
}
