package tn.com.smartsoft.framework.dao.exception;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.PropertyValueException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.hibernate.TransactionException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;

import tn.com.smartsoft.commons.exceptions.DaoException;
import tn.com.smartsoft.commons.exceptions.DaoFunctionalException;
import tn.com.smartsoft.commons.exceptions.DaoTechnicalException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.dao.DbEntiteConfiguration;
import tn.com.smartsoft.framework.dao.definition.DataSourceDefinition;
import tn.com.smartsoft.framework.dao.definition.DbConstraintDefinition;
import tn.com.smartsoft.framework.dao.definition.DbCustomSqlErrorDefinition;
import tn.com.smartsoft.framework.dao.definition.DbDefinition;

public class DbExceptionTranslator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSourceDefinition dataSourceDefinition;
	protected Logger logger = Logger.getLogger(DbExceptionTranslator.class);
	private DbDefinition dbDefinition;
	public static final String CONNECTION_FAILURE_ERROR_CODE = "ConnectionFailureErrorCode";
	public static final String CANNOT_ACQUIRE_LOCK_ERROR_CODE = "CannotAcquireLockErrorCode";
	public static final String INTEGRITY_VIOLATION_ERROR_CODE = "IntegrityViolationErrorCode";
	public static final String DUPLICATE_KEY_ERROR_CODE = "DuplicateKeyErrorCode";
	public static final String GENERALE_ERROR_CODE = "GeneraleErrorCode";
	public static final String OPTIMISTIC_LOCKING_FAILURE_ERROR_CODE = "OptimisticLockingFailureErrorCode";
	public static final String DATA_PROPERTY_VALUE_ERROR_CODE = "DataPropertyValueErrorCode";

	public DbExceptionTranslator(DataSourceDefinition dataSourceDefinition, DbDefinition dbDefinition) {
		super();
		this.dataSourceDefinition = dataSourceDefinition;
		this.dbDefinition = dbDefinition;
	}

	public DaoException translateDaoException(Exception ex) {
		Throwable cause = ex.getCause();
		if (ex instanceof TransactionException && cause instanceof SQLException) {
			return translateJDBCException(new JDBCException(ex.getMessage(), (SQLException) cause));
		}
		if (ex instanceof JDBCException) {
			return translateJDBCException((JDBCException) ex);
		} else if (ex instanceof HibernateException) {
			return translateHibernateException((HibernateException) ex);
		} else {
			return new DaoTechnicalException(ex);
		}
	}

	public boolean isDAOFunctionalException(DaoException daoException) {
		return (daoException instanceof DaoFunctionalException);
	}

	public DaoException translateHibernateException(HibernateException ex) {

		if (ex instanceof StaleObjectStateException)
			return new OptimisticLockingFailureDbException(dataSourceDefinition.getCustomSqlErrorCategories(OPTIMISTIC_LOCKING_FAILURE_ERROR_CODE), ex);
		if (ex instanceof StaleStateException)
			return new OptimisticLockingFailureDbException(dataSourceDefinition.getCustomSqlErrorCategories(OPTIMISTIC_LOCKING_FAILURE_ERROR_CODE), ex);
		if (ex instanceof PropertyValueException) {
			PropertyValueException exp = (PropertyValueException) ex;
			DbEntiteConfiguration entiteDefinition = new DbEntiteConfiguration(dataSourceDefinition.getClassMapping(exp.getEntityName()), dataSourceDefinition.getSessionFactory().getClassMetadata(
					exp.getEntityName()), dbDefinition);
			String entiteId = entiteDefinition.getEntiteDictionaryLibelle() != null ? entiteDefinition.getEntiteDictionaryLibelle() : exp.getEntityName();
			String propertyName = entiteDefinition.getPropertyDictionary(exp.getPropertyName()) == null ? exp.getPropertyName() : entiteDefinition.getPropertyDictionary(exp.getPropertyName())
					.getLibelle();

			return (DataPropertyValueDbException) new DataPropertyValueDbException(dataSourceDefinition.getCustomSqlErrorCategories(DATA_PROPERTY_VALUE_ERROR_CODE), entiteId, propertyName, ex);
		}
		if (ex instanceof org.hibernate.id.IdentifierGenerationException) {
			return new GeneraleFunctionalDbException(dataSourceDefinition.getCustomSqlErrorCategories(GENERALE_ERROR_CODE), ex, "");
		}
		return new DaoTechnicalException(ex, null);
	}

	public DaoException translateJDBCException(JDBCException jdbcException) {
		DbCustomSqlErrorDefinition customSqlErrorValue = getCustomSqlErrorValue(jdbcException);
		if (customSqlErrorValue != null) {
			Class<?> exceptionClass = customSqlErrorValue.exceptionClass();
			if (exceptionClass == null)
				exceptionClass = DaoFunctionalException.class;
			try {
				return (DaoException) ConstructorUtils.invokeConstructor(exceptionClass,
						new Object[] { customSqlErrorValue.applicationErrorCode(), jdbcException.getSQLException(), jdbcException.getSQL() },
						new Class[] { String.class, Throwable.class, String.class });
			} catch (Exception e) {
				return new GeneraleFunctionalDbException(dataSourceDefinition.getCustomSqlErrorCategories(GENERALE_ERROR_CODE), jdbcException, jdbcException.getSQL());
			}
		}
		if (jdbcException instanceof JDBCConnectionException)
			return new ConnectionFailureDbException(dataSourceDefinition.getCustomSqlErrorCategories(CONNECTION_FAILURE_ERROR_CODE), jdbcException, ((JDBCConnectionException) jdbcException).getSQL());
		if (jdbcException instanceof LockAcquisitionException)
			return new CannotAcquireLockDbException(dataSourceDefinition.getCustomSqlErrorCategories(CANNOT_ACQUIRE_LOCK_ERROR_CODE), jdbcException, ((JDBCConnectionException) jdbcException).getSQL());
		else if (jdbcException instanceof ConstraintViolationException) {
			return translateConstraintViolationException((ConstraintViolationException) jdbcException);
		} else if (jdbcException instanceof SQLGrammarException) {
			return new DaoTechnicalException(jdbcException.getSQLException(), jdbcException.getSQL());
		}
		return new GeneraleFunctionalDbException(dataSourceDefinition.getCustomSqlErrorCategories(GENERALE_ERROR_CODE), jdbcException, jdbcException.getSQL());
	}

	private DaoException translateConstraintViolationException(ConstraintViolationException jdbcException) {
		String sqlMessage = jdbcException.getSQLException().getMessage();
		String sqlQuery = jdbcException.getSQL();
		boolean isUpdateQuery = true;
		if (sqlQuery != null) {
			isUpdateQuery = sqlQuery.toUpperCase().indexOf("DELETE") < 0;
		}
		Map<String, DbConstraintDefinition> constraintDefinitions = dataSourceDefinition.getConstraintDefinitions();
		DbConstraintDefinition constraintDefinition = null;
		for (Iterator<String> iterator = constraintDefinitions.keySet().iterator(); iterator.hasNext();) {
			String constraintName = (String) iterator.next();
			if (sqlMessage.indexOf(constraintName) > -1) {
				constraintDefinition = (DbConstraintDefinition) constraintDefinitions.get(constraintName);
				break;
			}
		}
		if (constraintDefinition == null) {
			if (sqlMessage.toUpperCase().indexOf("PK_") > -1)
				return new DuplicateKeyDbException(dataSourceDefinition.getCustomSqlErrorCategories(DUPLICATE_KEY_ERROR_CODE), jdbcException.getCause(), jdbcException.getSQL());
			else
				return new IntegrityViolationDbException(dataSourceDefinition.getCustomSqlErrorCategories(INTEGRITY_VIOLATION_ERROR_CODE), jdbcException.getCause(), jdbcException.getSQL());
		}
		Class<?> exceptionClass = constraintDefinition.getClassException();
		String errorId = null;
		if (constraintDefinition.isPkType()) {
			exceptionClass = exceptionClass == null ? DuplicateKeyDbException.class : exceptionClass;
			errorId = constraintDefinition.getErrorId();
		} else {
			exceptionClass = exceptionClass == null ? IntegrityViolationDbException.class : exceptionClass;
			errorId = isUpdateQuery ? constraintDefinition.getOnUpdateErrorId() : constraintDefinition.getOnDeleteErrorId();
		}
		try {
			return (DaoException) ConstructorUtils.invokeConstructor(exceptionClass, new Object[] { errorId, jdbcException.getSQLException(), jdbcException.getSQL() }, new Class[] { String.class,
					Throwable.class, String.class });
		} catch (Exception e) {
			return new GeneraleFunctionalDbException(dataSourceDefinition.getCustomSqlErrorCategories(GENERALE_ERROR_CODE), jdbcException, jdbcException.getSQL());
		}
	}

	public DbCustomSqlErrorDefinition getCustomSqlErrorValue(JDBCException jdbcException) {
		DbCustomSqlErrorDefinition sqlErrorValue = null;
		if (dataSourceDefinition.getDialectDefinition().isUseSqlStateForTranslation()) {
			String sqlState = JDBCExceptionHelper.extractSqlState(jdbcException.getSQLException());
			sqlErrorValue = dataSourceDefinition.getDialectDefinition().getCustomSqlErrorValue(sqlState);
		} else {
			int errorCode = JDBCExceptionHelper.extractErrorCode(jdbcException.getSQLException());
			sqlErrorValue = dataSourceDefinition.getDialectDefinition().getCustomSqlErrorValue(String.valueOf(errorCode));
		}
		return sqlErrorValue;
	}
}
