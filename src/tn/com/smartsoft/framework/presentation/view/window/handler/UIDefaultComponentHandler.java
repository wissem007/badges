package tn.com.smartsoft.framework.presentation.view.window.handler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlTextBox;

public class UIDefaultComponentHandler<T extends UIComponent> implements UIObject, UIComponentHandler<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, T> items = new Hashtable<String, T>();
	private ArrayList<String> itemsRang = new ArrayList<String>();
	private UIComponent parentComponent;

	public UIDefaultComponentHandler(UIComponent parentComponent) {
		super();
		this.parentComponent = parentComponent;
	}

	public void clearItems() {
		List<String> itemId = itemIds();
		for (int i = 0; i < itemId.size(); i++) {
			try {
				items.remove(itemId.get(i));
				itemsRang.remove(itemId.get(i));
				getItem(itemId.get(i)).dettachedFromWindow();
			} catch (Exception e) {
			}
		}
	}

	public void addItem(T component) {
		if (component == null)
			return;
		((UIComponent) component).setParent(parentComponent);
		itemsRang.add(component.getId());
		items.put(component.getId(), component);
		return;
	}

	public void removeItem(String id) {
		try {
			items.remove(id);
			itemsRang.remove(id);
			getItem(id).dettachedFromWindow();
		} catch (Exception e) {
		}
	}

	public void removeItem(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			removeItem(ids[i]);
		}
	}

	public void removeItem(int start, int end) {
		List<String> ids = itemIds();
		if (start >= itemIds().size() || start > end)
			return;
		if (end >= itemIds().size())
			end = itemIds().size();
		for (int i = start; i < end; i++) {
			removeItem(ids.get(i));
		}
	}

	public void removeItem(int start) {
		removeItem(start, itemIds().size());
	}

	public boolean hasItem(String id) {
		return items.containsKey(id);
	}

	public T getItem(String id) {
		return items.get(id);
	}

	@SuppressWarnings("unchecked")
	public void addItem(String contenu) {
		addItem((T) new UIHtmlTextBox(contenu));
	}

	public List<String> itemIds() {
		return new ArrayList<String>(itemsRang);
	}

	public List<T> getItems() {
		return new ArrayList<T>(items.values());
	}

	public int itemSize() {
		return items.size();
	}

	@SuppressWarnings("unchecked")
	public T findItem(String id) {
		if (hasItem(id))
			return getItem(id);
		for (int i = 0; i < itemsRang.size(); i++) {
			T itemParent = getItem((String) itemsRang.get(i));
			if (itemParent instanceof UIComponentHandler) {
				T item = ((UIComponentHandler<T>) itemParent).findItem(id);
				if (item != null)
					return (T) item;
			}
		}
		return null;
	}

}
