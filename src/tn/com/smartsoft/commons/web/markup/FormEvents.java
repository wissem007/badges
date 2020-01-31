package tn.com.smartsoft.commons.web.markup;

public interface FormEvents {
	public abstract void setOnSubmit(String script);

	public abstract void setOnReset(String script);

	public abstract void setOnSelect(String script);

	public abstract void setOnChange(String script);
}
