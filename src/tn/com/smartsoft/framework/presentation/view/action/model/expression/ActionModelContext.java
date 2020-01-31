package tn.com.smartsoft.framework.presentation.view.action.model.expression;

public interface ActionModelContext{

	public Object getProperty(String property);
	public abstract Object getValue(String property);
	public abstract String getCustomValue(String property);
	public abstract Object getLibelle(String libelleKey);
	public abstract Object getUser(String property);
	public abstract Object getOrganisme(String property);
	public abstract Object getSociete(String property);
	public abstract Object getViewLibelle(String windowId, String labelId);
	public abstract Object getViewLibelle(String labelId);
	public abstract Object getViewComponetProperty(String windowId, String componetId, String property);
	public abstract Object getViewComponetProperty(String componetId);
	public abstract Object getPropertyApplication(String property);
	public abstract Object getPropertyLibelle(String propertyId);
	public abstract Object getRandom();
}