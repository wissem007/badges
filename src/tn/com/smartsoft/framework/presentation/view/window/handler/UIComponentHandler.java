package tn.com.smartsoft.framework.presentation.view.window.handler;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;

public interface UIComponentHandler<T extends UIComponent> {
	public void clearItems();

	public boolean hasItem(String id);

	public abstract int itemSize();

	public abstract void addItem(T component);

	public abstract void removeItem(String id);

	public abstract void removeItem(String id[]);

	public abstract void removeItem(int start, int end);

	public abstract void removeItem(int start);

	public abstract T getItem(String id);

	public abstract T findItem(String id);

	public abstract void addItem(String contenu);

	public abstract List<String> itemIds();

}
