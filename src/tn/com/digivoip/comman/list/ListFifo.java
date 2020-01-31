package tn.com.digivoip.comman.list;

import java.util.ArrayList;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ListFifo extends ArrayList<Object>{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6297745897193182438L;
	private int					size;

	public ListFifo(int size) {
		this.size = size;
	}
	public boolean add(Object o) {
		if (size() < size) {
			return super.add(o);
		} else {
			remove();
			return add(o);
		}
	}
	public Object remove() {
		return remove(0);
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
