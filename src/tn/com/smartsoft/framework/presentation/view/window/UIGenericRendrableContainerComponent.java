package tn.com.smartsoft.framework.presentation.view.window;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public abstract class UIGenericRendrableContainerComponent extends UIGenericRendrableComponent implements UIComponentHandler<UIComponent> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIDefaultComponentHandler<UIComponent> componentHandler = new UIDefaultComponentHandler<UIComponent>(this);

	public void clearItems() {
		componentHandler.clearItems();
	}

	public void addItem(UIComponent component) {
		componentHandler.addItem(component);
	}

	public void addItem(String contenu) {
		componentHandler.addItem(contenu);
	}

	public List<String> itemIds() {
		return componentHandler.itemIds();
	}

	public void removeItem(String[] id) {
		componentHandler.removeItem(id);
	}

	public void removeItem(int start, int end) {
		componentHandler.removeItem(start, end);
	}

	public void removeItem(int start) {
		componentHandler.removeItem(start);
	}

	public int itemSize() {
		return componentHandler.itemSize();
	}

	public UIComponent getItem(String id) {
		return componentHandler.getItem(id);
	}

	public UIComponent findItem(String id) {
		return componentHandler.findItem(id);
	}

	public List<UIComponent> getItems() {
		return componentHandler.getItems();
	}

	public boolean hasItem(String id) {
		return componentHandler.hasItem(id);
	}

	public void removeItem(String id) {
		componentHandler.removeItem(id);
	}

}