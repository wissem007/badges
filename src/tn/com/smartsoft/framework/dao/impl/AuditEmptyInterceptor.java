package tn.com.smartsoft.framework.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import tn.com.smartsoft.framework.context.UserContext;

/** @author Rob Hibernate Interceptor for logging saves, updates and deletes to the AuditLogRecord Table */
public class AuditEmptyInterceptor extends EmptyInterceptor{
	
	private Log					log		= LogFactory.getLog(AuditEmptyInterceptor.class);
	private DbSessionImpl		session;
	private static final String	UPDATE	= "update";
	private static final String	INSERT	= "insert";
	private static final String	DELETE	= "delete";
	
	/** @param sessionFactory The sessionFactory to set. */
	public void setSession(DbSessionImpl session, UserContext userContext) {
		this.session = session;
		this.userContext = userContext;
	}
	
	private Set			inserts	= new HashSet();
	private Set			updates	= new HashSet();
	private Set			deletes	= new HashSet();
	private UserContext	userContext;
	
	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onLoad(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[]) */
	public boolean onLoad(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4)
			throws CallbackException {
		// TODO Auto-generated method stub
		return false;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[]) */
	public boolean onFlushDirty(Object obj, Serializable id, Object[] newValues, Object[] oldValues,
			String[] properties, Type[] types) throws CallbackException {
		if (obj instanceof Object) {
			Class objectClass = obj.getClass();
			String className = objectClass.getName();
			// Just get the class name without the package structure
			String[] tokens = className.split("\\.");
			int lastToken = tokens.length - 1;
			className = tokens[lastToken];
			// Use the id and class to get the pre-update state from the database
			Serializable persistedObjectId = getObjectId(obj);
			try {
				Object preUpdateState = session.persistantSupport(userContext).get(objectClass, persistedObjectId);
				logChanges(obj, preUpdateState, null, persistedObjectId.toString(), UPDATE, className);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[]) */
	public boolean onSave(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
			throws CallbackException {
		if (obj instanceof Object) {
			try {
				Class objectClass = obj.getClass();
				String className = objectClass.getName();
				String[] tokens = className.split("\\.");
				int lastToken = tokens.length - 1;
				className = tokens[lastToken];
				logChanges(obj, null, null, null, INSERT, className);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see net.sf.hibernate.Interceptor#onDelete(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], net.sf.hibernate.type.Type[]) */
	public void onDelete(Object obj, Serializable id, Object[] newValues, String[] properties, Type[] types)
			throws CallbackException {
		if (obj instanceof Object) {
			try {
				Class objectClass = obj.getClass();
				String className = objectClass.getName();
				String[] tokens = className.split("\\.");
				int lastToken = tokens.length - 1;
				className = tokens[lastToken];
				logChanges(obj, null, null, id.toString(), DELETE, className);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void postFlush(Iterator arg0) throws CallbackException {
		log.debug("In postFlush of AuditLogInterceptor..");
		try {
			for (Iterator itr = inserts.iterator(); itr.hasNext();) {
				AuditLogRecord logRecord = (AuditLogRecord) itr.next();
				logRecord.setEntityId(getObjectId(logRecord.getEntity()).toString());
				System.out.println(logRecord);
				///session.persistantSupport(userContext).save(logRecord);
			}
			for (Iterator itr = updates.iterator(); itr.hasNext();) {
				AuditLogRecord logRecord = (AuditLogRecord) itr.next();
				//session.persistantSupport(userContext).save(logRecord);
				System.out.println(logRecord);
			}
			for (Iterator itr = deletes.iterator(); itr.hasNext();) {
				AuditLogRecord logRecord = (AuditLogRecord) itr.next();
				//session.persistantSupport(userContext).save(logRecord);
				System.out.println(logRecord);
			}
		} catch (Exception e) {
			throw new CallbackException(e);
		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}
	public void afterTransactionCompletion(Transaction arg0) {
		// clear any audit log records potentially remaining from a rolled back
		// transaction
		inserts.clear();
		updates.clear();
		deletes.clear();
	}
	/** Logs changes to persistent data
	 * @param newObject the object being saved, updated or deleted
	 * @param existingObject the existing object in the database. Used only for updates
	 * @param parentObject the parent object. Set only if passing a Component object as the newObject
	 * @param persistedObjectId the id of the persisted object. Used only for update and delete
	 * @param event the type of event being logged. Valid values are "update", "delete", "save"
	 * @param className the name of the class being logged. Used as a reference in the auditLogRecord
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException */
	private void logChanges(Object newObject, Object existingObject, Object parentObject,
			String persistedObjectId, String event, String className)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class objectClass = newObject.getClass();
		// get an array of all fields in the class including those in superclasses if this is a subclass.
		Field[] fields = getAllFields(objectClass, null);
		// Iterate through all the fields in the object
		fieldIteration: for (int ii = 0; ii < fields.length; ii++) {
			// make private fields accessible so we can access their values
			fields[ii].setAccessible(true);
			// if the current field is static, transient or final then don't log it as
			// these modifiers are v.unlikely to be part of the data model.
			if (Modifier.isTransient(fields[ii].getModifiers())
					|| Modifier.isFinal(fields[ii].getModifiers())
					|| Modifier.isStatic(fields[ii].getModifiers())) {
				continue fieldIteration;
			}
			String fieldName = fields[ii].getName();
			if (!fieldName.equals("id")) {
				Class interfaces[] = fields[ii].getType().getInterfaces();
				for (int i = 0; i < interfaces.length;) {
					if (interfaces[i].getName().equals("java.util.Collection")) {
						continue fieldIteration;
						// If the field is a class that is a component (Hibernate mapping type) then iterate through its fields and log them
					} else if (interfaces[i].getName().equals("com.mycompany.model.audit.Component")) {
						Object newComponent = fields[ii].get(newObject);
						Object existingComponent = null;
						if (event.equals(UPDATE)) {
							existingComponent = fields[ii].get(existingObject);
							if (existingComponent == null && newComponent != null) {
								try {
									existingComponent = newComponent.getClass().newInstance();
								} catch (InstantiationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								// if neither objects contain the component then don't log anything
								continue fieldIteration;
							}
						}
						if (newComponent == null) {
							continue fieldIteration;
						}
						logChanges(newComponent, existingComponent, newObject, persistedObjectId, event, className);
						continue fieldIteration;
					}
					i++;
				}
				String propertyNewState;
				String propertyPreUpdateState;
				// get new field values
				try {
					Object objPropNewState = fields[ii].get(newObject);
					if (objPropNewState != null) {
						propertyNewState = objPropNewState.toString();
					} else {
						propertyNewState = "";
					}
				} catch (Exception e) {
					propertyNewState = "";
				}
				if (event.equals(UPDATE)) {
					try {
						Object objPreUpdateState = fields[ii].get(existingObject);
						if (objPreUpdateState != null) {
							propertyPreUpdateState = objPreUpdateState.toString();
						} else {
							propertyPreUpdateState = "";
						}
					} catch (Exception e) {
						propertyPreUpdateState = "";
					}
					// Now we have the two property values - compare them
					if (propertyNewState.equals(propertyPreUpdateState)) {
						continue; // Values haven't changed so loop to next property
					} else {
						AuditLogRecord logRecord = new AuditLogRecord();
						logRecord.setEntityName(className);
						logRecord.setEntityAttribute(fieldName);
						logRecord.setMessage(event);
						logRecord.setUpdatedBy(this.getUserName());
						logRecord.setUpdatedDate(new Date());
						logRecord.setNewValue(propertyNewState);
						logRecord.setOldValue(propertyPreUpdateState);
						logRecord.setEntityId(persistedObjectId);
						if (parentObject == null) {
							logRecord.setEntity(newObject);
						} else {
							logRecord.setEntity(parentObject);
						}
						updates.add(logRecord);
					}
				} else if (event.equals(DELETE)) {
					Object returnValue = fields[ii].get(newObject);
					AuditLogRecord logRecord = new AuditLogRecord();
					logRecord.setEntityName(className);
					logRecord.setEntityAttribute(fieldName);
					logRecord.setMessage(event);
					logRecord.setUpdatedBy(this.getUserName());
					logRecord.setUpdatedDate(new Date());
					logRecord.setNewValue("");
					if (returnValue != null) logRecord.setOldValue(returnValue.toString());
					if (persistedObjectId != null) logRecord.setEntityId(persistedObjectId);
					if (parentObject == null) {
						logRecord.setEntity(newObject);
					} else {
						logRecord.setEntity(parentObject);
					}
					deletes.add(logRecord);
				} else if (event.equals(INSERT)) {
					Object returnValue = fields[ii].get(newObject);
					AuditLogRecord logRecord = new AuditLogRecord();
					logRecord.setEntityName(className);
					logRecord.setEntityAttribute(fieldName);
					logRecord.setMessage(event);
					logRecord.setUpdatedBy(this.getUserName());
					logRecord.setUpdatedDate(new Date());
					logRecord.setOldValue("");
					if (returnValue != null) {
						logRecord.setNewValue(returnValue.toString());
					} else logRecord.setNewValue("");
					if (parentObject == null) {
						logRecord.setEntity(newObject);
					} else {
						logRecord.setEntity(parentObject);
					}
					inserts.add(logRecord);
				}
			}
		}
	}
	/** Returns an array of all fields used by this object from it's class and all superclasses.
	 * @param objectClass the class
	 * @param fields the current field list
	 * @return an array of fields */
	private Field[] getAllFields(Class objectClass, Field[] fields) {
		Field[] newFields = objectClass.getDeclaredFields();
		int fieldsSize = 0;
		int newFieldsSize = 0;
		if (fields != null) {
			fieldsSize = fields.length;
		}
		if (newFields != null) {
			newFieldsSize = newFields.length;
		}
		Field[] totalFields = new Field[fieldsSize + newFieldsSize];
		if (fieldsSize > 0) {
			System.arraycopy(fields, 0, totalFields, 0, fieldsSize);
		}
		if (newFieldsSize > 0) {
			System.arraycopy(newFields, 0, totalFields, fieldsSize, newFieldsSize);
		}
		Class superClass = objectClass.getSuperclass();
		Field[] finalFieldsArray;
		if (superClass != null && !superClass.getName().equals("java.lang.Object")) {
			finalFieldsArray = getAllFields(superClass, totalFields);
		} else {
			finalFieldsArray = totalFields;
		}
		return finalFieldsArray;
	}
	/** Gets the id of the persisted object
	 * @param obj the object to get the id from
	 * @return object Id */
	private Serializable getObjectId(Object obj) {
		Class objectClass = obj.getClass();
		Method[] methods = objectClass.getMethods();
		Serializable persistedObjectId = null;
		for (int ii = 0; ii < methods.length; ii++) {
			// If the method name equals 'getId' then invoke it to get the id of the object.
			if (methods[ii].getName().equals("getId")) {
				try {
					persistedObjectId = (Serializable) methods[ii].invoke(obj, null);
					break;
				} catch (Exception e) {
					log.warn("Audit Log Failed - Could not get persisted object id: " + e.getMessage());
				}
			}
		}
		return persistedObjectId;
	}
	/** Gets the current user's id from the Acegi secureContext
	 * @return current user's userId */
	private String getUserName() {
		return "anonymousUser";
	}
}
