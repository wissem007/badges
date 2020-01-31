package tn.com.digivoip.comman.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListTools{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void intersect(List a, List b) {
		ListIterator aIt;
		ListIterator bIt;
		if (a.size() == 0) { return; }
		if (b.size() == 0) {
			a.clear();
			return;
		}
		Collections.sort(a);
		Collections.sort(b);
		aIt = a.listIterator();
		bIt = b.listIterator();
		Comparable aVal;
		Comparable bVal;
		aVal = (Comparable) aIt.next();
		bVal = (Comparable) bIt.next();
		boolean loop = true;
		int compareResult;
		while (loop) {
			compareResult = aVal.compareTo(bVal);
			if (compareResult < 0) { // a < b
				aIt.remove();
				if (aIt.hasNext()) {
					aVal = (Comparable) aIt.next();
				} else {
					return;
				}
			} else if (compareResult == 0) { // a == b
				if (aIt.hasNext()) {
					aVal = (Comparable) aIt.next();
				} else {
					loop = false;
					return;
				}
				if (bIt.hasNext()) {
					bVal = (Comparable) bIt.next();
				} else {
					loop = false;
					aIt.remove();
				}
			} else { // a > b
				if (bIt.hasNext()) {
					bVal = (Comparable) bIt.next();
				} else {
					loop = false;
					aIt.remove();
				}
			}
		}
		while (aIt.hasNext()) {
			aIt.next();
			aIt.remove();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void substract(List a, List b) {
		ListIterator aIt;
		ListIterator bIt;
		if ((a.size() == 0) || (b.size() == 0)) { return; }
		Collections.sort(a);
		Collections.sort(b);
		aIt = a.listIterator();
		bIt = b.listIterator();
		Comparable aVal;
		Comparable bVal;
		aVal = (Comparable) aIt.next();
		bVal = (Comparable) bIt.next();
		boolean loop = true;
		int compareResult;
		while (loop) {
			compareResult = aVal.compareTo(bVal);
			if (compareResult < 0) { // a < b
				if (aIt.hasNext()) {
					aVal = (Comparable) aIt.next();
				} else {
					return;
				}
			} else if (compareResult == 0) { // a == b
				aIt.remove();
				if (aIt.hasNext()) {
					aVal = (Comparable) aIt.next();
				} else {
					return;
				}
				if (bIt.hasNext()) {
					bVal = (Comparable) bIt.next();
				} else {
					return;
				}
			} else { // a > b
				if (bIt.hasNext()) {
					bVal = (Comparable) bIt.next();
				} else {
					return;
				}
			}
		}
	}
	@SuppressWarnings({ "rawtypes" })
	public static void intersect_astable(List a, List b) {
		if (a.size() == 0) { return; }
		if (b.size() == 0) {
			a.clear();
			return;
		}
		Iterator ita = a.iterator();
		Iterator itb;
		Object acta;
		boolean found;
		while (ita.hasNext()) {
			acta = ita.next();
			itb = b.iterator();
			found = false;
			while (itb.hasNext() && !found) {
				found = acta.equals(itb.next());
			}
			if (!found) {
				ita.remove();
			} else {
				itb.remove();
			}
		}
	}
}
