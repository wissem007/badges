package tn.com.smartsoft.commons.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListHandler<T extends NamedObject> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> orderItems = new HashMap<String, Integer>();
	protected List<T> items = new ArrayList<T>();

	public List<T> getAll() {
		return items;
	}

	public Iterator<T> iterator() {
		return items.iterator();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int size() {
		return items.size();
	}

	public T get(String name) {
		Integer pos = orderItems.get(name);
		if (pos != null)
			return items.get(pos.intValue());
		return null;
	}

	public boolean contains(String name) {
		return orderItems.containsKey(name);
	}

	public void add(T element) {
		if (contains(element.getName()))
			return;
		orderItems.put(element.getName(), new Integer(items.size()));
		items.add(element);
	}

	public String toString() {
		return items.toString();
	}
}
