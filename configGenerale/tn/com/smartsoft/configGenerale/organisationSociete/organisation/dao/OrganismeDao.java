package tn.com.smartsoft.configGenerale.organisationSociete.organisation.dao;

import java.util.List;
import tn.com.smartsoft.configGenerale.organisationSociete.organisation.beans.OrganismeBean;
import tn.com.smartsoft.framework.beans.DataBusinessBean;
import tn.com.smartsoft.framework.dao.DbSession;
import tn.com.smartsoft.framework.dao.GenericEntiteDao;

public interface OrganismeDao extends GenericEntiteDao{

	public List<DataBusinessBean> getListOrganisme(DbSession dbSession, String userId, boolean istherEvenn, OrganismeBean bean);
}
