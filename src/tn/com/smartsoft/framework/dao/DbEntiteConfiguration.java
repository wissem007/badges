package tn.com.smartsoft.framework.dao;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.metadata.ClassMetadata;

import tn.com.smartsoft.framework.dao.definition.DbDefinition;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBean;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.PropertyBean;

public class DbEntiteConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PersistentClass persistentClass;
	private DbDefinition dbDefinition;
	private ClassMetadata classMetadata;

	public DbEntiteConfiguration(PersistentClass persistentClass, ClassMetadata classMetadata, DbDefinition dbDefinition) {
		super();
		this.persistentClass = persistentClass;
		this.dbDefinition = dbDefinition;
		this.classMetadata = classMetadata;
	}

	public EntiteBean getAdEntite() {
		return dbDefinition.getEntiteBean(persistentClass.getMappedClass());
	}

	public String getEntiteDictionaryLibelle() {
		EntiteBean adEntites = getAdEntite();
		if (adEntites != null)
			return adEntites.getLibelle();
		return null;
	}

	public PropertyBean getPropertyDictionary(String name) {
		EntiteBean adEntites = getAdEntite();
		if (adEntites != null)
			return adEntites.getProperty(name);
		return null;
	}

	public Map<String, PropertyBean> getPropertyDictionarys() {
		EntiteBean adEntites = getAdEntite();
		if (adEntites != null)
			return adEntites.getPropertys();
		return null;
	}

	public ClassMetadata getClassMetadata() {
		return classMetadata;
	}
}
