package tn.com.smartsoft.commons.ref;

public class ReferenceSet<E> extends SetMapAdapter<E> {
	
	public ReferenceSet(ReferenceType valueReferenceType) {
		super(new ReferenceMap<E, Object>(valueReferenceType, ReferenceType.STRONG));
	}
	
}
