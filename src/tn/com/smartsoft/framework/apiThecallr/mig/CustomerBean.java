package tn.com.smartsoft.framework.apiThecallr.mig;

import java.io.Serializable;
import java.sql.Timestamp;

public class CustomerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idCustomer;
	private Long idCategory;
	private String name;
	private String adress;
	private String tel1;
	private String tel2;
	private String fax;
	private String email;
	private String matriculeFiscale;
	private String registr;
	private String managerName;
	private String managerTel;
	private String managerRmail;
	private String loginProvider;
	private String passwordProvider;
	private String login;
	private String password;
	private String ipAutorized;
	private Timestamp dateCreation;
	private Double balance = 0.0;
	private Double initialRefil = 0.0;
	private Boolean isActive = true;
	
	public CustomerBean() {
		super();
	}
	
	public CustomerBean(Long idCustomer, Long idCategory, String name, String adress, String tel1, String tel2, String fax, String email, String matriculeFiscale, String registr, String managerName,
			String managerTel, String managerRmail, String loginProvider, String passwordProvider, String login, String password, String ipAutorized, Timestamp dateCreation, Double initialRefil,
			Boolean isActive) {
		this.idCustomer = idCustomer;
		this.idCategory = idCategory;
		this.name = name;
		this.adress = adress;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.email = email;
		this.matriculeFiscale = matriculeFiscale;
		this.registr = registr;
		this.managerName = managerName;
		this.managerTel = managerTel;
		this.managerRmail = managerRmail;
		this.loginProvider = loginProvider;
		this.passwordProvider = passwordProvider;
		this.login = login;
		this.password = password;
		this.ipAutorized = ipAutorized;
		this.dateCreation = dateCreation;
		this.balance = 0.0;
		this.initialRefil = initialRefil;
		this.isActive = isActive;
	}
	
	public Long getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public Long getIdCategory() {
		return idCategory;
	}
	
	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAdress() {
		return adress;
	}
	
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public String getTel1() {
		return tel1;
	}
	
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	
	public String getTel2() {
		return tel2;
	}
	
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMatriculeFiscale() {
		return matriculeFiscale;
	}
	
	public void setMatriculeFiscale(String matriculeFiscale) {
		this.matriculeFiscale = matriculeFiscale;
	}
	
	public String getRegistr() {
		return registr;
	}
	
	public void setRegistr(String registr) {
		this.registr = registr;
	}
	
	public String getManagerName() {
		return managerName;
	}
	
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	public String getManagerTel() {
		return managerTel;
	}
	
	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}
	
	public String getManagerRmail() {
		return managerRmail;
	}
	
	public void setManagerRmail(String managerRmail) {
		this.managerRmail = managerRmail;
	}
	
	public String getLoginProvider() {
		return loginProvider;
	}
	
	public void setLoginProvider(String loginProvider) {
		this.loginProvider = loginProvider;
	}
	
	public String getPasswordProvider() {
		return passwordProvider;
	}
	
	public void setPasswordProvider(String passwordProvider) {
		this.passwordProvider = passwordProvider;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getIpAutorized() {
		return ipAutorized;
	}
	
	public void setIpAutorized(String ipAutorized) {
		this.ipAutorized = ipAutorized;
	}
	
	public Timestamp getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Double getInitialRefil() {
		return initialRefil;
	}
	
	public void setInitialRefil(Double initialRefil) {
		this.initialRefil = initialRefil;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
