package tn.com.smartsoft.framework.presentation.view.action.model;

import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.presentation.view.action.model.accessor.GenericBeanAccessorModel;

public class BeanModel extends CompositeModel implements ItemModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	protected ActionMode mode = ActionMode.CREATE;
	protected String libelle;
	protected Object help;
	private Class<?> javaType;

	public BeanModel(RootBeanModel beanModelRoot, UserActionDefinition userActionDefinition) {
		super(beanModelRoot, userActionDefinition);
	}

	public BeanModel(String name, RootBeanModel beanModelRoot, UserActionDefinition userActionDefinition) {
		super(beanModelRoot, userActionDefinition);
		this.name = name;
	}

	public BeanModel(String name, Class<?> javaType, int type, RootBeanModel beanModelRoot, UserActionDefinition userActionDefinition) {
		super(beanModelRoot, userActionDefinition);
		this.beanAccessorModel = new GenericBeanAccessorModel(this, javaType, type);
		this.name = name;
	}

	public BeanModel(String name, BeanAccessorModel beanAccessorModel, RootBeanModel beanModelRoot, UserActionDefinition userActionDefinition) {
		super(beanModelRoot, beanAccessorModel, userActionDefinition);
		this.name = name;
	}

	public BeanModel(String name, ActionMode mode, String libelle, Object help, Class<?> javaType, int type, RootBeanModel beanModelRoot, UserActionDefinition userActionDefinition) {
		super(beanModelRoot, userActionDefinition);
		this.beanAccessorModel = new GenericBeanAccessorModel(this, javaType, type);
		this.name = name;
		this.mode = mode;
		this.libelle = libelle;
		this.help = help;
		this.javaType = javaType;
	}

	public ActionMode getMode() {
		return mode;
	}

	public void setMode(ActionMode mode) {
		this.mode = mode;
	}

	public Class<?> getJavaType() {
		return this.javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
