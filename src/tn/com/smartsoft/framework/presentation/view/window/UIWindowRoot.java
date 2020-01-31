package tn.com.smartsoft.framework.presentation.view.window;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;
import tn.com.smartsoft.framework.presentation.view.window.render.UIWindowRootRender;

public class UIWindowRoot extends UIGenericRendrableContainerComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String layout = "hbox";
	private String firstFocus;
	protected JsMap layoutConfigs = new JsMap();
	private UIDefaultComponentHandler<UIComponent> toolAction = new UIDefaultComponentHandler<UIComponent>(this);

	public UIWindowRoot() {
		super();
	}

	public UIWindow getWindow() {
		return (UIWindow) getParent();
	}

	public UIRender getRender() {
		return new UIWindowRootRender(this);
	}

	public String getFirstFocus() {
		return firstFocus;
	}

	public void addToolItem(UIExtComponent component) {
		toolAction.addItem(component);
	}

	public void clearToolItems() {
		toolAction.clearItems();
	}

	public void addToolItem(UIComponent component) {
		toolAction.addItem(component);
	}

	public void removeToolItem(String id) {
		toolAction.removeItem(id);
	}

	public void removeToolItem(String[] ids) {
		toolAction.removeItem(ids);
	}

	public void removeToolItem(int start, int end) {
		toolAction.removeItem(start, end);
	}

	public void removeToolItem(int start) {
		toolAction.removeItem(start);
	}

	public boolean hasToolItem(String id) {
		return toolAction.hasItem(id);
	}

	public UIComponent getToolItem(String id) {
		return toolAction.getItem(id);
	}

	public void addToolItem(String contenu) {
		toolAction.addItem(contenu);
	}

	public List<String> itemToolIds() {
		return toolAction.itemIds();
	}

	public List<UIComponent> getToolItems() {
		return toolAction.getItems();
	}

	public int itemToolSize() {
		return toolAction.itemSize();
	}

	public boolean isToolBar() {
		return itemToolSize() > 0;
	}

	public UIComponent findToolItem(String id) {
		return toolAction.findItem(id);
	}

	public void setFirstFocus(String firstFocus) {
		this.firstFocus = firstFocus;
	}

	public String getLayout() {
		if (StringUtils.isBlank(layout))
			this.layout = "hbox";
		return layout;
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

	public boolean isRendred() {
		return true;
	}

}
