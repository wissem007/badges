package tn.com.smartsoft.framework.dao.factory;

import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.framework.configuration.ComponentId;
import tn.com.smartsoft.framework.dao.DaoParseBean;
import tn.com.smartsoft.framework.dao.DbSession;

public interface DaoFactory {

	public abstract Object createDaoBean(ComponentId daoBeanId) throws DaoFunctionalException;

	public abstract DbSession createDbSession(String sesionName) throws DaoFunctionalException;

	public DaoParseBean createDaoParseBean(ComponentId daoParseBeanId) throws DaoFunctionalException;
}