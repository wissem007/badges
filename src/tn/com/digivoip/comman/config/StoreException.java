package tn.com.digivoip.comman.config;

public class StoreException extends RuntimeException{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5242149136815514452L;

	public StoreException(String s, Throwable e) {
		super(s, e);
	}
	public StoreException(String string) {
		super(string);
	}
}
