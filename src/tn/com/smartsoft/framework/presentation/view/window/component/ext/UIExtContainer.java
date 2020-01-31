package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import java.util.List;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UISubmitedEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtContainerRender;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIComponentHandler;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtContainer extends UIExtBoxComponent implements UIComponentHandler<UIComponent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UIDefaultComponentHandler<UIComponent> componentHandler = new UIDefaultComponentHandler<UIComponent>(this);
	private String layout;
	protected JsMap layoutConfigs = new JsMap();

	public UIExtContainer() {
		super();
		UISubmitedEvent submitEvent = new UISubmitedEvent(ClientEvent.ON_LOAD, this, "onLoad");
		submitEvent.setRendred(false);
		addEvent(submitEvent);
	}

	public void onLoad(ListenerContext context) throws FunctionalException {
		context.webContext().response("remoteContainerResponse", this);
	}

	public String getLayout() {
		return layout;
	}

	public void clearItems() {
		componentHandler.clearItems();
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void addLayoutConfig(String name, String value, Boolean expected) {
		layoutConfigs.addValue(name, value, expected);
	}

	public JsMap getLayoutConfigs() {
		return layoutConfigs;
	}

	public void addItem(UIComponent component) {
		componentHandler.addItem(component);
	}

	public void addItem(String contenu) {
		componentHandler.addItem(contenu);
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

	public List<String> itemIds() {
		return componentHandler.itemIds();
	}

	public int itemSize() {
		return componentHandler.itemSize();
	}

	public UIComponent getItem(String id) {
		return componentHandler.getItem(id);
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

	public UIComponent findItem(String id) {
		return componentHandler.findItem(id);
	}

	public UIRender getRender() {
		return new UIExtContainerRender(this);
	}
}
