package tn.com.smartsoft.framework.presentation.view.action.request.data;

public interface DataControlerRequestField {
	public abstract void validateDataType();
	
	public abstract void validate();
	
	public abstract void commit();
}
