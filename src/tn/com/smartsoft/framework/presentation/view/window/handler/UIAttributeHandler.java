package tn.com.smartsoft.framework.presentation.view.window.handler;

public interface UIAttributeHandler {

	public abstract String[] geAttributesName();

	public abstract String getAttribut(String name);

	public abstract void setAttribut(String name, String value);

	public abstract void setAttribut(String name, Object value);

	public abstract void setAttribut(String name, boolean value);

	public abstract void setAttribut(String name, int value);

}