package tn.com.smartsoft.iap.system.business;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class DefaultPasswordHashing implements PasswordHashing {

	public String encryptPassword(String password) {
		if (StringUtils.isEmpty(password))
			return "";
		byte[] encryptedPassword = Base64.encodeBase64(DigestUtils.md5(password));
		return new String(encryptedPassword);
	}

	public boolean isPasswordValid(String password, String encryptedPassword) {
		if (encryptedPassword == null && password == null)
			return true;
		else if (encryptedPassword.equals("") && password.equals(""))
			return true;
		else if (encryptedPassword == null || encryptedPassword.equals("") || encryptedPassword == null || encryptedPassword.equals(""))
			return false;
		return encryptedPassword.equals(this.encryptPassword(password));
	}
	public static void main(String[] args) {
		DefaultPasswordHashing DefaultPasswordHashing=new DefaultPasswordHashing();
		System.out.println(DefaultPasswordHashing.encryptPassword("123456"));
	}
}