package tn.com.smartsoft.iap.system.business;

public interface PasswordHashing {
	String encryptPassword(String password);

	boolean isPasswordValid(String password, String encryptedPassword);
}