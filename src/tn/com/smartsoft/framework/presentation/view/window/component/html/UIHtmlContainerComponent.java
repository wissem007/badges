package tn.com.smartsoft.framework.presentation.view.window.component.html;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.html.render.UIHtmlContainerComponentRender;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIHtmlContainerComponent extends UIHtmlComponent implements UIComponentHandler<UIComponent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UIDefaultComponentHandler<UIComponent> componentHandler = new UIDefaultComponentHandler<UIComponent>(this);

	public UIHtmlContainerComponent(String htmlTag) {
		super(htmlTag);
	}

	public UIHtmlContainerComponent() {
		super(null);
	}

	public void addItem(UIComponent component) {
		componentHandler.addItem(component);
	}

	public void addItem(String contenu) {
		componentHandler.addItem(contenu);
	}

	public void clearItems() {
		componentHandler.clearItems();
	}

	public List<String> itemIds() {
		return componentHandler.itemIds();
	}

	public int itemSize() {
		return componentHandler.itemSize();
	}

	public UIComponent getItem(String id) {
		return componentHandler.getItem(id);
	}

	public boolean hasItem(String id) {
		return componentHandler.hasItem(id);
	}

	public void removeItem(String id) {
		componentHandler.removeItem(id);
	}

	public UIRender getRender() {
		return new UIHtmlContainerComponentRender(this);
	}

	public UIComponent findItem(String id) {
		return componentHandler.findItem(id);
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

}
