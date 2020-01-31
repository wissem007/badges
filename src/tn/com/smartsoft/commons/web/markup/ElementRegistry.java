package tn.com.smartsoft.commons.web.markup;

public interface ElementRegistry {
	public abstract Element addElementToRegistry(Element element);

	public abstract Element addElementToRegistry(String element);

	public abstract Element removeElementFromRegistry(Element element);

	public abstract Element removeElementFromRegistry(String element);

	public abstract boolean registryHasElement(Element element);

	public abstract boolean registryHasElement(String element);

}
