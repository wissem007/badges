package tn.com.smartsoft.framework.presentation.view.action.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.formater.Formater;
import tn.com.smartsoft.framework.presentation.formater.FormaterManger;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.security.Permission;
import tn.com.smartsoft.framework.security.StaticPermission;
import tn.com.smartsoft.iap.dictionary.decoupage.entite.beans.EntiteBeanId;

public class PropertyModel implements ItemModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String userType;
	protected String libelle;
	protected Object help;
	private boolean isEncrypted = false;
	private Object defaultValue = null;
	protected Permission permission = StaticPermission.GRANTED;
	protected ActionMode mode = ActionMode.CREATE;
	private Class<?> JavaType;
	private Boolean dynamique = false;
	private EntiteBeanId refEntiteId;
	private String listRefBeanName;
	private String refBeanName;
	private String libelleEntiteRefExp;

	public PropertyModel(String name) {
		super();
		this.name = name;
		this.libelle = name;
		this.help = name;
	}

	public PropertyModel(String name, String userType, Class<?> JavaType) {
		super();
		this.name = name;
		this.userType = userType;
		this.libelle = name;
		this.help = name;
		this.JavaType = JavaType;
	}

	public PropertyModel(String name, String userType, Class<?> JavaType, String libelle, Object help, Object defaultValue) {
		super();
		this.name = name;
		this.userType = userType;
		this.libelle = libelle;
		this.help = help;
		this.defaultValue = defaultValue;
		this.JavaType = JavaType;
	}

	public String getLibelleEntiteRefExp() {
		return libelleEntiteRefExp;
	}

	public void setLibelleEntiteRefExp(String libelleEntiteRefExp) {
		this.libelleEntiteRefExp = libelleEntiteRefExp;
	}

	public String getListRefBeanName() {
		return listRefBeanName;
	}

	public void setListRefBeanName(String listRefBeanName) {
		this.listRefBeanName = listRefBeanName;
	}

	public String getRefBeanName() {
		return refBeanName;
	}

	public void setRefBeanName(String refBeanName) {
		this.refBeanName = refBeanName;
	}

	public EntiteBeanId getRefEntiteId() {
		return refEntiteId;
	}

	public void setRefEntiteId(EntiteBeanId refEntiteId) {
		this.refEntiteId = refEntiteId;
	}

	public Class<?> getJavaType() {
		return JavaType;
	}

	public void setJavaType(Class<?> javaType) {
		JavaType = javaType;
	}

	public ActionMode getMode() {
		return mode;
	}

	public String getAsString(Object value) {
		return getFormatter().getAsString(value);
	}

	public Object getAsObject(Object value) {
		return getFormatter().getAsObject(value, getJavaType());
	}

	public boolean validate(Object value) {
		return getFormatter().validate(value, getJavaType());
	}

	public Formater getFormatter() {
		FormaterManger formaterManger = ApplicationConfiguration.applicationManager().formaterManger();
		return formaterManger.getFormatter(userType);
	}

	public void setMode(ActionMode mode) {
		this.mode = mode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Object getHelp() {
		return help;
	}

	public void setHelp(Object help) {
		this.help = help;
	}

	public boolean isEncrypted() {
		return isEncrypted;
	}

	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Boolean isDynamique() {
		return dynamique;
	}

	public void setDynamique(Boolean dynamique) {
		this.dynamique = dynamique;
	}
}
