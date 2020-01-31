package tn.com.smartsoft.framework.apiThecallr.mig;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.data.DateBorne;
import tn.com.smartsoft.commons.utils.NumberUtils;
import tn.com.smartsoft.framework.apiThecallr.Did;
import tn.com.smartsoft.framework.apiThecallr.Inbound;
import tn.com.smartsoft.framework.apiThecallr.Trunk;

public class MigrationDao {
	public static final String PASSWORD_DB = "digivoip";
	public static String IP_SERVEUR = "5.196.210.169";
	public static String DATABASE = "db__digivoip_tn";
	public static String REQUEST_RATES_GET_DATE_BORN = "SELECT DATE_DEBUT FROM customer_rates_exep WHERE ID_CUSTOMER = ?  AND DATE_DEBUT BETWEEN ? AND ? GROUP BY DATE_DEBUT ORDER BY DATE_DEBUT";
	public static String REQUEST_USER_GET = "SELECT ID_CUSTOMER, ID_CATEGORY, NAME, ADRESS, TEL_1, TEL_2, FAX, EMAIL,MATRICULE_FISCALE,REGISTRE_COMMERCE,MANAGER_NAME,MANAGER_TEL,MANAGER_EMAIL,LOGIN_PROVIDER,PASSWORD_PROVIDER,LOGIN,PASSWORD,IP_AUTORIZED,DATE_CREATION,INITIAL_REFIL,IS_ACTIVE FROM customers  WHERE LOGIN=?  and PASSWORD=?";
	public static String REQUEST_USER_GET_BY_ID = "SELECT ID_CUSTOMER, ID_CATEGORY, NAME, ADRESS, TEL_1, TEL_2, FAX, EMAIL,MATRICULE_FISCALE,REGISTRE_COMMERCE,MANAGER_NAME,MANAGER_TEL,MANAGER_EMAIL,LOGIN_PROVIDER,PASSWORD_PROVIDER,LOGIN,PASSWORD,IP_AUTORIZED,DATE_CREATION,INITIAL_REFIL,IS_ACTIVE FROM customers  WHERE ID_CUSTOMER=?";
	public static String REQUEST_USER_GET_ALL = "SELECT ID_CUSTOMER, ID_CATEGORY, NAME, ADRESS, TEL_1, TEL_2, FAX, EMAIL,MATRICULE_FISCALE,REGISTRE_COMMERCE,MANAGER_NAME,MANAGER_TEL,MANAGER_EMAIL,LOGIN_PROVIDER,PASSWORD_PROVIDER,LOGIN,PASSWORD,IP_AUTORIZED,DATE_CREATION,INITIAL_REFIL,IS_ACTIVE FROM customers WHERE IS_ACTIVE='1'";
	public static String REQUEST_USER_GET_SOLDE = "SELECT SUM(REFILL_AMOUNT) AS SOLDE FROM customer_refill WHERE ID_CUSTOMER=? AND REFILL_DATE>=? AND IS_ACTIVE=1";
	public static String REQUEST_TRANSACTION = "SELECT CUST_REFILL_ID,REFILL_AMOUNT,REFILL_DATE,TAUX_CHANGE,STATUS,PAY_MODE,REF_PAY,DESCRIPTION FROM  customer_refill WHERE ID_CUSTOMER=? order by CUST_REFILL_ID";
	public static GenerateurDB generateurDBTo = null; // MigUtils.createMySqlConnection(IP_SERVEUR,
														// DATABASE, "root",
														// PASSWORD_DB);
	public static GenerateurDB generateurDBToPost = MigUtils.createPgConnection("127.0.0.1", "bo_digi_db", "postgres", "postgres");
	
	public static void insertCustomer(ArrayList<CustomerBean> cots, Map<String, CustomerBean> cpts) {
		String insetRequest = "INSERT INTO sss_backoffice_db.bo_clients(bo_client_id, cr_categorie_client_id, name, adress, tel1, tel2,fax, email, matricule_fiscale, registre_commerce, manager_name,manager_tel, manager_email, date_creation, is_active,ncpt,cnum)  VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
		String insetRequestCpt = "INSERT INTO sss_backoffice_db.bo_compte_client_voix(bo_compte_voix_id, bo_client_id, initial_refil, date_creation,start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
		String insetRequestAgent = "INSERT INTO sss_backoffice_db.bo_agent_clients(bo_agent_client_id, bo_client_id, name, adress, tel1, tel2, email,login, password, date_creation, is_active)   VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
		String insetRequestAgentService = "INSERT INTO sss_backoffice_db.bo_service_agent_clients(bo_service_web_id, bo_agent_client_id, is_active)  VALUES (?, ?, ?)";
		String insetRequestService = "INSERT INTO sss_backoffice_db.bo_service_clients(bo_service_web_id, bo_client_id, is_active)  VALUES (?, ?, ?)";
		for (int i = 0; i < cots.size(); i++) {
			CustomerBean cot = cots.get(i);
			int cpt = i + 1;
			Object[] values = new Object[] { cpt, 1, cot.getName(), cot.getAdress(), cot.getTel1(), cot.getTel2(), cot.getFax(), cot.getEmail(), cot.getMatriculeFiscale(), cot.getRegistr(), cot.getManagerName(), cot.getManagerTel(),
					cot.getManagerRmail(), cot.getDateCreation(), cot.getIsActive(), cot.getLoginProvider(), cot.getIdCustomer() };
			SqlUtils.updateData(insetRequest, values, generateurDBToPost);
			Object[] valuesAgent = new Object[] { cpt, cpt, StringUtils.isEmpty(cot.getManagerName()) ? cot.getName() : cot.getManagerName(), cot.getAdress(), cot.getManagerTel(), null, cot.getManagerRmail(), cot.getLogin(), cot.getPassword(),
					cot.getDateCreation(), cot.getIsActive() };
			SqlUtils.updateData(insetRequestAgent, valuesAgent, generateurDBToPost);
			Object[] valuesAgentService = new Object[] { 1, cpt, true };
			SqlUtils.updateData(insetRequestAgentService, valuesAgentService, generateurDBToPost);
			Object[] valuesService = new Object[] { 1, cpt, true };
			SqlUtils.updateData(insetRequestService, valuesService, generateurDBToPost);
			CustomerBean customerBean = cpts.get(cot.getLoginProvider());
			if (customerBean != null) {
				Object[] valuesCpt = new Object[] { customerBean.getIdCustomer(), cpt, cot.getInitialRefil(), cot.getDateCreation(), cot.getDateCreation(), null };
				SqlUtils.updateData(insetRequestCpt, valuesCpt, generateurDBToPost);
			}
		}
	}
	
	public static void insertPrixAchat() {
		String insetRequest = "INSERT INTO sss_backoffice_db.bo_art_voix_prix_achat_prix(bo_art_voix_prix_achat_prd_id, bo_article_voix_id, mnt_prix,default_taux_vente) VALUES (?, ?, ?,?)";
		final Map<String, Long> articleVoixs = getMappedArticle();
		final ArrayList<DestinationCallSummary> cots = new ArrayList<DestinationCallSummary>();
		SqlUtils.selectData("SELECT `LIBELLE`,`PRIX`,`TAUX`,`BO_COMPTE_VOIX_PRIOD_ID`FROM `db__digivoip_tn`.`BO_ARTICLE_VOIX`", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				DestinationCallSummary d = new DestinationCallSummary();
				d.setLibelle(rs.getString("LIBELLE"));
				d.setPrixAchat(rs.getDouble("PRIX"));
				d.setPrixVente(rs.getDouble("TAUX"));
				d.setIdPriod(rs.getLong("BO_COMPTE_VOIX_PRIOD_ID"));
				cots.add(d);
			}
		}, generateurDBTo);
		for (int i = 0; i < cots.size(); i++) {
			DestinationCallSummary cot = cots.get(i);
			Long id = articleVoixs.get(cot.getLibelle());
			if (id == null) {
				System.out.println(cot.getLibelle());
			}
			Object[] valuesService = new Object[] { cot.getIdPriod(), id, cot.getPrixAchat(), cot.getPrixVente() };
			SqlUtils.updateData(insetRequest, valuesService, generateurDBToPost);
		}
	}
	
	public static Map<String, Long> getMappedArticle() {
		final Map<String, Long> articleVoixs = new HashMap<String, Long>();
		SqlUtils.selectData("SELECT bo_article_voix_id, libelle FROM sss_backoffice_db.bo_article_voix", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				String libelle = rs.getString("libelle").trim().toUpperCase();
				libelle = StringUtils.replaceChars(libelle, "-", " ");
				articleVoixs.put(libelle, rs.getLong("bo_article_voix_id"));
			}
		}, generateurDBToPost);
		return articleVoixs;
	}
	
	public static void insertDestination(ArrayList<DestinationCallSummary> cots) {
		String insetRequest = "INSERT INTO sss_backoffice_db.bo_article_voix(bo_article_voix_id, bo_article_voix_type_id, cr_pays_id, libelle)  VALUES (?, ?, ?, ?)";
		final Map<String, Long> cptPaysByCode = new HashMap<String, Long>();
		final Map<String, Long> cptPaysByName = new HashMap<String, Long>();
		SqlUtils.selectData("SELECT cr_pays_id, code_iso,libelle FROM sss_config_general_db.cr_pays", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				cptPaysByCode.put(StringUtils.upperCase(rs.getString("code_iso")), rs.getLong("cr_pays_id"));
				cptPaysByName.put(StringUtils.upperCase(rs.getString("libelle")), rs.getLong("cr_pays_id"));
			}
		}, generateurDBToPost);
		for (int i = 0; i < cots.size(); i++) {
			DestinationCallSummary cot = cots.get(i);
			String libelle = cot.getLibelle();
			String prf = libelle;
			if (StringUtils.indexOf(libelle, "-") > 0) {
				prf = StringUtils.substring(libelle, 0, StringUtils.indexOf(libelle, "-"));
			}
			Long cr_pays_id = cptPaysByCode.get(prf);
			if (cr_pays_id == null)
				cr_pays_id = cptPaysByName.get(prf);
			Object[] valuesService = new Object[] { cot.getId(), StringUtils.indexOf(libelle, "MOBILE") > 0 ? 2 : 1, cr_pays_id, libelle };
			SqlUtils.updateData(insetRequest, valuesService, generateurDBToPost);
		}
	}
	
	public static void insertTrunk(List<Trunk> trs, Long compteVoixI_id, Map<String, Integer> ids) {
		String insetRequestrunk = "INSERT INTO sss_backoffice_db.bo_trunks(bo_trunk_id, bo_compte_voix_id, rf_trunk_type_id, trunk_login,trunk_password, name, date_creation) VALUES (?, ?, ?, ?,?, ?, ?)";
		String insetRequesdid = "INSERT INTO sss_backoffice_db.bo_dids(bo_did_number, rf_did_type_id, cr_pays_id, local_number, did_class,did_hash,bo_compte_voix_id)VALUES (?, ?, ?, ?, ?,?,?)";
		String insetRequestCptClientDid = "INSERT INTO sss_backoffice_db.bo_compte_trunk_dids(bo_trunk_id, bo_compte_voix_id, bo_client_id, bo_did_number,ratio, bo_compte_trunk_did_id, start_date, end_date) VALUES (?, ?, ?, ?,?, ?, ?, ?)";
		final Map<Long, Long> cptClients = new HashMap<Long, Long>();
		SqlUtils.selectData("SELECT bo_compte_voix_id, bo_client_id  FROM sss_backoffice_db.bo_compte_client_voix where end_date is null order by bo_compte_voix_id", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				cptClients.put(rs.getLong("bo_compte_voix_id"), rs.getLong("bo_client_id"));
			}
		}, generateurDBToPost);
		final Map<String, Long> cptPays = new HashMap<String, Long>();
		SqlUtils.selectData("SELECT cr_pays_id, code_iso FROM sss_config_general_db.cr_pays", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				cptPays.put(rs.getString("code_iso"), rs.getLong("cr_pays_id"));
			}
		}, generateurDBToPost);
		int t = ids.get("t");
		int d = ids.get("d");
		for (int i = 0; i < trs.size(); i++) {
			Trunk tr = (Trunk) trs.get(i);
			t = t + 1;
			Object[] values = new Object[] { t, compteVoixI_id, Integer.parseInt(tr.getType()), tr.getUsername(), tr.getUsername(), tr.getName(), tr.getDateCreationSql() };
			SqlUtils.updateData(insetRequestrunk, values, generateurDBToPost);
			for (int j = 0; j < tr.getDids().size(); j++) {
				Did did = tr.getDids().get(j);
				d = d + 1;
				Object[] valuesCpt = new Object[] { did.getIntl_number(), StringUtils.equals(did.getType(), "GEOGRAPHIC") ? 1 : 2, cptPays.get(did.getCountry_code()), did.getLocal_number(), did.getClasse(), did.getHash(), compteVoixI_id };
				SqlUtils.updateData(insetRequesdid, valuesCpt, generateurDBToPost);
				if (cptClients.containsKey(compteVoixI_id)) {
					Object[] valuesCptDid = new Object[] { t, compteVoixI_id, cptClients.get(compteVoixI_id), did.getIntl_number(), 2, d, tr.getDateCreationSql(), null };
					SqlUtils.updateData(insetRequestCptClientDid, valuesCptDid, generateurDBToPost);
				}
			}
		}
		ids.put("t", t);
		ids.put("d", d);
	}
	
	public static void insertCompteApi(ArrayList<CustomerBean> cots) {
		String insetRequest = "INSERT INTO sss_backoffice_db.bo_compte_voix(bo_compte_voix_id, date_creation, api_login, api_password, name_cpt,is_active)  VALUES (?, ?, ?, ?, ?,?)";
		for (int i = 0; i < cots.size(); i++) {
			CustomerBean cot = cots.get(i);
			int cpt = i + 1;
			Object[] values = new Object[] { cpt, new Date(System.currentTimeMillis()), cot.getLoginProvider(), cot.getPasswordProvider(), cot.getName(), true };
			SqlUtils.updateData(insetRequest, values, generateurDBToPost);
		}
	}
	
	public static ArrayList<CustomerBean> getCompteAllPgApi() {
		final ArrayList<CustomerBean> cots = new ArrayList<CustomerBean>();
		
		SqlUtils.selectData("SELECT bo_compte_voix_id, date_creation, api_login, api_password FROM sss_backoffice_db.bo_compte_voix", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				CustomerBean customerBean = new CustomerBean();
				customerBean.setIdCustomer(rs.getLong("bo_compte_voix_id"));
				customerBean.setLoginProvider(rs.getString("api_login"));
				customerBean.setPasswordProvider(rs.getString("api_password"));
				customerBean.setDateCreation(rs.getTimestamp("date_creation"));
				cots.add(customerBean);
			}
		}, generateurDBToPost);
		return cots;
	}
	
	public static void insertHistApiDestinationCall(final List<DestinationCallSummary> callSummarys, Long compteId) {
		String insetRequest = "INSERT INTO sss_backoffice_db.hist_api_destination_calls(bo_compte_voix_id, bo_article_voix_id, duration, calls, price,date_call) VALUES (?, ?, ?, ?, ?, ?)";
		for (int i = 0; i < callSummarys.size(); i++) {
			DestinationCallSummary callSummary = callSummarys.get(i);
			Object[] valus = new Object[] { compteId, callSummary.getId(), callSummary.getDuration(), callSummary.getCount(), callSummary.getPrixAchatApi(), new Date(callSummary.getDateBorne().getFrom().getTime()) };
			SqlUtils.updateData(insetRequest, valus, generateurDBToPost);
		}
		
	}
	
	public static CustomerBean getCustomerByUserNameAndPassword(String user, String pass) {
		final CustomerBean customerBean = new CustomerBean();
		SqlUtils.selectData(REQUEST_USER_GET, new Object[] { user, pass }, new IExecutorRow() {
			
			public void run(ResultSet rs) throws SQLException {
				customerBean.setIdCustomer(rs.getLong("ID_CUSTOMER"));
				customerBean.setIdCategory(rs.getLong("ID_CATEGORY"));
				customerBean.setName(rs.getString("NAME"));
				customerBean.setAdress(rs.getString("ADRESS"));
				customerBean.setTel1(rs.getString("TEL_1"));
				customerBean.setTel2(rs.getString("TEL_2"));
				customerBean.setFax(rs.getString("FAX"));
				customerBean.setEmail(rs.getString("EMAIL"));
				customerBean.setMatriculeFiscale(rs.getString("MATRICULE_FISCALE"));
				customerBean.setRegistr(rs.getString("REGISTRE_COMMERCE"));
				customerBean.setManagerName(rs.getString("MANAGER_NAME"));
				customerBean.setManagerTel(rs.getString("MANAGER_TEL"));
				customerBean.setManagerRmail(rs.getString("MANAGER_EMAIL"));
				customerBean.setLoginProvider(rs.getString("LOGIN_PROVIDER"));
				customerBean.setPasswordProvider(rs.getString("PASSWORD_PROVIDER"));
				customerBean.setLogin(rs.getString("LOGIN"));
				customerBean.setPassword(rs.getString("PASSWORD"));
				customerBean.setIpAutorized(rs.getString("IP_AUTORIZED"));
				customerBean.setDateCreation(rs.getTimestamp("DATE_CREATION"));
				customerBean.setInitialRefil(rs.getDouble("INITIAL_REFIL"));
				customerBean.setIsActive(rs.getInt("IS_ACTIVE") == 1);
			}
		}, generateurDBTo);
		return customerBean;
	}
	
	public static void addDestinationCallSummarysHistory(final List<DestinationCallSummary> callSummarys, Long customerId) {
		String insetRequest = "INSERT INTO CALL_SUMMARY_HISTORY" + "(ID_CUSTOMER,ID_DISTINATION,LIBELLE,DURATION,CALLS,PRIX_ACHAT,PRIX_ACHAT_API,PRIX_VENTE,PRIX_UNITAIR_VENTE,"
				+ "PRIX_UNITAIR_ACHAT,PRIX_UNITAIR_ACHAT_API,DATE_FROM,DATE_TO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		for (int i = 0; i < callSummarys.size(); i++) {
			DestinationCallSummary callSummary = callSummarys.get(i);
			Object[] valus = new Object[] { customerId, callSummary.getId(), callSummary.getLibelle(), callSummary.getDuration(), callSummary.getCount(), callSummary.getPrixAchat(), callSummary.getPrixAchatApi(), callSummary.getPrixVente(),
					callSummary.getPrixUnitairVente(), callSummary.getPrixUnitairAchat(), callSummary.getPrixUnitairAchatApi(), new Date(callSummary.getDateBorne().getFrom().getTime()), new Date(callSummary.getDateBorne().getTo().getTime()) };
			SqlUtils.updateData(insetRequest, valus, generateurDBTo);
		}
		
	}
	
	public static CustomerBean getCustomerById(Long id) {
		final CustomerBean customerBean = new CustomerBean();
		SqlUtils.selectData(REQUEST_USER_GET_BY_ID, new Object[] { id }, new IExecutorRow() {
			
			public void run(ResultSet rs) throws SQLException {
				customerBean.setIdCustomer(rs.getLong("ID_CUSTOMER"));
				customerBean.setIdCategory(rs.getLong("ID_CATEGORY"));
				customerBean.setName(rs.getString("NAME"));
				customerBean.setAdress(rs.getString("ADRESS"));
				customerBean.setTel1(rs.getString("TEL_1"));
				customerBean.setTel2(rs.getString("TEL_2"));
				customerBean.setFax(rs.getString("FAX"));
				customerBean.setEmail(rs.getString("EMAIL"));
				customerBean.setMatriculeFiscale(rs.getString("MATRICULE_FISCALE"));
				customerBean.setRegistr(rs.getString("REGISTRE_COMMERCE"));
				customerBean.setManagerName(rs.getString("MANAGER_NAME"));
				customerBean.setManagerTel(rs.getString("MANAGER_TEL"));
				customerBean.setManagerRmail(rs.getString("MANAGER_EMAIL"));
				customerBean.setLoginProvider(rs.getString("LOGIN_PROVIDER"));
				customerBean.setPasswordProvider(rs.getString("PASSWORD_PROVIDER"));
				customerBean.setLogin(rs.getString("LOGIN"));
				customerBean.setPassword(rs.getString("PASSWORD"));
				customerBean.setIpAutorized(rs.getString("IP_AUTORIZED"));
				customerBean.setDateCreation(rs.getTimestamp("DATE_CREATION"));
				customerBean.setInitialRefil(rs.getDouble("INITIAL_REFIL"));
				customerBean.setIsActive(rs.getInt("IS_ACTIVE") == 1);
			}
		}, generateurDBTo);
		return customerBean;
	}
	
	public static Double getCustomerSolde(Long customerId, Timestamp customerDate) {
		final CustomerBean customerBean = new CustomerBean();
		SqlUtils.selectData(REQUEST_USER_GET_SOLDE, new Object[] { customerId, customerDate }, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				customerBean.setInitialRefil(rs.getDouble("SOLDE"));
			}
		}, generateurDBTo);
		return customerBean.getInitialRefil();
	}
	
	public static ArrayList<CustomerBean> getAllCustomerApi() {
		final ArrayList<CustomerBean> cots = new ArrayList<CustomerBean>();
		SqlUtils.selectData("SELECT BO_COMPTE_VOIX_ID, API_LOGIN, API_PASSWORD,NAME_CPT FROM BO_COMPTE_VOIX", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				CustomerBean customerBean = new CustomerBean();
				customerBean.setIdCustomer(rs.getLong("BO_COMPTE_VOIX_ID"));
				customerBean.setName(rs.getString("NAME_CPT"));
				customerBean.setLoginProvider(rs.getString("API_LOGIN"));
				customerBean.setPasswordProvider(rs.getString("API_PASSWORD"));
				cots.add(customerBean);
			}
		}, generateurDBTo);
		return cots;
	}
	
	public static ArrayList<CustomerBean> getAllPgCustomerApi() {
		final ArrayList<CustomerBean> cots = new ArrayList<CustomerBean>();
		SqlUtils.selectData("SELECT bo_compte_voix_id, date_creation, api_login, api_password, name_cpt,is_active FROM sss_backoffice_db.bo_compte_voix", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				CustomerBean customerBean = new CustomerBean();
				customerBean.setIdCustomer(rs.getLong("bo_compte_voix_id"));
				customerBean.setName(rs.getString("name_cpt"));
				customerBean.setLoginProvider(rs.getString("api_login"));
				customerBean.setPasswordProvider(rs.getString("api_password"));
				cots.add(customerBean);
			}
		}, generateurDBToPost);
		return cots;
	}
	
	public static Map<String, CustomerBean> getAllMapCustomerApi() {
		final Map<String, CustomerBean> cpts = new HashMap<String, CustomerBean>();
		SqlUtils.selectData("SELECT bo_compte_voix_id, date_creation, api_login, api_password, name_cpt,is_active FROM sss_backoffice_db.bo_compte_voix", new Object[] {}, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				CustomerBean customerBean = new CustomerBean();
				customerBean.setIdCustomer(rs.getLong("bo_compte_voix_id"));
				customerBean.setName(rs.getString("name_cpt"));
				customerBean.setLoginProvider(rs.getString("api_login"));
				customerBean.setPasswordProvider(rs.getString("api_password"));
				cpts.put(rs.getString("api_login"), customerBean);
			}
		}, generateurDBToPost);
		return cpts;
	}
	
	public static ArrayList<CustomerBean> getAllCustomer() {
		final ArrayList<CustomerBean> cots = new ArrayList<CustomerBean>();
		SqlUtils.selectData(REQUEST_USER_GET_ALL, new Object[] {}, new IExecutorRow() {
			
			public void run(ResultSet rs) throws SQLException {
				CustomerBean customerBean = new CustomerBean();
				customerBean.setIdCustomer(rs.getLong("ID_CUSTOMER"));
				customerBean.setIdCategory(rs.getLong("ID_CATEGORY"));
				customerBean.setName(rs.getString("NAME"));
				customerBean.setAdress(rs.getString("ADRESS"));
				customerBean.setTel1(rs.getString("TEL_1"));
				customerBean.setTel2(rs.getString("TEL_2"));
				customerBean.setFax(rs.getString("FAX"));
				customerBean.setEmail(rs.getString("EMAIL"));
				customerBean.setMatriculeFiscale(rs.getString("MATRICULE_FISCALE"));
				customerBean.setRegistr(rs.getString("REGISTRE_COMMERCE"));
				customerBean.setManagerName(rs.getString("MANAGER_NAME"));
				customerBean.setManagerTel(rs.getString("MANAGER_TEL"));
				customerBean.setManagerRmail(rs.getString("MANAGER_EMAIL"));
				customerBean.setLoginProvider(rs.getString("LOGIN_PROVIDER"));
				customerBean.setPasswordProvider(rs.getString("PASSWORD_PROVIDER"));
				customerBean.setLogin(rs.getString("LOGIN"));
				customerBean.setPassword(rs.getString("PASSWORD"));
				customerBean.setIpAutorized(rs.getString("IP_AUTORIZED"));
				customerBean.setDateCreation(rs.getTimestamp("DATE_CREATION"));
				customerBean.setInitialRefil(rs.getDouble("INITIAL_REFIL"));
				customerBean.setIsActive(rs.getInt("IS_ACTIVE") == 1);
				cots.add(customerBean);
			}
		}, generateurDBTo);
		return cots;
	}
	
	public static ArrayList<DestinationCallSummary> getAllDestination() {
		final ArrayList<DestinationCallSummary> cots = new ArrayList<DestinationCallSummary>();
		SqlUtils.selectData("SELECT`LIBELLE` FROM `db__digivoip_tn`.`BO_ARTICLE_VOIX` GROUP BY `LIBELLE`", new Object[] {}, new IExecutorRow() {
			long x = 0;
			
			public void run(ResultSet rs) throws SQLException {
				DestinationCallSummary destination = new DestinationCallSummary();
				x = x + 1;
				destination.setId(x);
				destination.setLibelle(rs.getString("LIBELLE"));
				cots.add(destination);
			}
		}, generateurDBTo);
		return cots;
	}
	
	public static ArrayList<TransactionBean> getListTransaction(Long customerId) {
		final ArrayList<TransactionBean> cots = new ArrayList<TransactionBean>();
		SqlUtils.selectData(REQUEST_TRANSACTION, new Object[] { customerId }, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				TransactionBean transactionBean = new TransactionBean();
				transactionBean.setTransactionId(rs.getLong("CUST_REFILL_ID"));
				transactionBean.setAmount(rs.getDouble("REFILL_AMOUNT"));
				transactionBean.setTauxChange(rs.getDouble("TAUX_CHANGE"));
				transactionBean.setDateTransaction(rs.getTimestamp("REFILL_DATE"));
				transactionBean.setStatus(rs.getLong("STATUS"));
				transactionBean.setPayMode(rs.getLong("PAY_MODE"));
				transactionBean.setRefPay(rs.getString("REF_PAY"));
				transactionBean.setDescription(rs.getString("DESCRIPTION"));
				cots.add(transactionBean);
			}
		}, generateurDBTo);
		return cots;
	}
	
	public static List<DateBorne> getRatesDateBorne(Long customerId, final DateBorne dateBorne) {
		final List<DateBorne> bornes = new ArrayList<DateBorne>();
		SqlUtils.selectData(REQUEST_RATES_GET_DATE_BORN, new Object[] { customerId, dateBorne.getFrom(), dateBorne.getTo() }, new IExecutorRow() {
			Timestamp from = dateBorne.getFrom();
			
			public void run(ResultSet rs) throws SQLException {
				bornes.add(new DateBorne(from, rs.getTimestamp("DATE_DEBUT")));
				from = rs.getTimestamp("DATE_DEBUT");
			}
		}, generateurDBTo);
		if (bornes.size() > 0)
			bornes.add(new DateBorne(bornes.get(bornes.size() - 1).getTo(), dateBorne.getTo()));
		else {
			bornes.add(new DateBorne(dateBorne.getFrom(), dateBorne.getTo()));
		}
		System.out.println(bornes);
		return bornes;
	}
	
	public static Inbound getRatesTauxValueByDateBorneByDestination(Long customerId, Timestamp to, String distination) {
		String request = getRequestRatesTauxByDateBorn(false);
		final Inbound inboundRow = new Inbound();
		SqlUtils.selectData(request, new Object[] { customerId, to, distination, distination }, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				inboundRow.setBilling_label(rs.getString("LIBELLE"));
				inboundRow.setId(rs.getLong("DESTINATION_ID"));
				inboundRow.setCredit(NumberUtils.multiplier(rs.getDouble("TAUX"), rs.getDouble("PRIX")));
				
			}
		}, generateurDBTo);
		if (inboundRow.getCredit() == null) {
			inboundRow.setBilling_label(distination);
			inboundRow.setId(0L);
			inboundRow.setCredit(2.5);
		}
		return inboundRow;
	}
	
	public static void addDestinationCallSummarysHistoryApi(final List<DestinationCallSummary> callSummarys, Long customerId) {
		String insetRequest = "INSERT INTO  BO_CALL_SUMMARY_HISTORYS" + "(ID_COMPTE_API,ID_DISTINATION,LIBELLE,DURATION,CALLS," + "COST_ACHAT_API,PRIX_ACHAT_API,COST_ACHAT,PRIX_ACHAT,DATE_FROM,DATE_TO) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		for (int i = 0; i < callSummarys.size(); i++) {
			DestinationCallSummary callSummary = callSummarys.get(i);
			Object[] valus = new Object[] { customerId, callSummary.getId(), callSummary.getLibelle(), callSummary.getDuration(), callSummary.getCount(), callSummary.getPrixAchatApi(), callSummary.getPrixUnitairAchatApi(), callSummary.getPrixAchat(),
					callSummary.getPrixUnitairAchat(), new Date(callSummary.getDateBorne().getFrom().getTime()), new Date(callSummary.getDateBorne().getTo().getTime()) };
			SqlUtils.updateData(insetRequest, valus, generateurDBTo);
		}
		
	}
	
	public static Map<String, RateBean> getRatesTauxApi(int priod) {
		final Map<String, RateBean> rates = new HashMap<String, RateBean>();
		String request = "SELECT BO_ARTICLE_VOIX_ID,TRIM(LIBELLE) AS LIBELLE, PRIX, TAUX FROM  BO_ARTICLE_VOIX WHERE BO_COMPTE_VOIX_PRIOD_ID=?";
		SqlUtils.selectData(request, new Object[] { priod }, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				String libelle = rs.getString("LIBELLE").trim().toUpperCase();
				libelle = StringUtils.replaceChars(libelle, "-", " ");
				RateBean rateBean = new RateBean(rs.getLong("BO_ARTICLE_VOIX_ID"), rs.getString("LIBELLE"), rs.getDouble("PRIX"), rs.getDouble("TAUX"));
				rates.put(libelle, rateBean);
			}
		}, generateurDBTo);
		return rates;
	}
	
	public static Map<String, RateBean> getRatesTauxValueByDateBorne(Long customerId, Timestamp to) {
		final Map<String, RateBean> rates = new HashMap<String, RateBean>();
		String request = getRequestRatesTauxByDateBorn(true);
		SqlUtils.selectData(request, new Object[] { customerId, to }, new IExecutorRow() {
			public void run(ResultSet rs) throws SQLException {
				String libelle = rs.getString("LIBELLE").trim().toUpperCase();
				libelle = StringUtils.replaceChars(libelle, "-", " ");
				RateBean rateBean = new RateBean(rs.getLong("DESTINATION_ID"), rs.getString("LIBELLE"), rs.getDouble("PRIX"), rs.getDouble("TAUX"));
				rates.put(libelle, rateBean);
			}
		}, generateurDBTo);
		return rates;
	}
	
	public static String getRequestRatesTauxByDateBorn(boolean isAll) {
		String request = "";
		request = request + "SELECT ";
		request = request + "  IFNULL(r_exep.TAUX, rate.TAUX) AS TAUX , rate.PRIX AS PRIX ,TRIM(rate.LIBELLE) AS LIBELLE ,rate.DESTINATION_ID AS DESTINATION_ID";
		request = request + " FROM";
		request = request + "  destination_rates rate ";
		request = request + "  LEFT JOIN customer_rates_exep r_exep ";
		request = request + "    ON r_exep.DESTINATION_ID = rate.DESTINATION_ID ";
		request = request + "    AND r_exep.ID_CUSTOMER = ? ";
		request = request + "    AND r_exep.DATE_DEBUT = ";
		request = request + "    (SELECT ";
		request = request + "      MAX(r_exep_m.DATE_DEBUT) ";
		request = request + "    FROM";
		request = request + "      customer_rates_exep r_exep_m ";
		request = request + "    WHERE r_exep_m.ID_CUSTOMER = r_exep.ID_CUSTOMER ";
		request = request + "      AND r_exep_m.DESTINATION_ID = rate.DESTINATION_ID ";
		request = request + "      AND r_exep_m.DATE_DEBUT < ?) ";
		if (!isAll)
			request = request + "WHERE UCASE(rate.LIBELLE) = UCASE(?) OR REPLACE(UCASE(rate.LIBELLE),' ','-')=REPLACE(UCASE(?),' ','-')";
		return request;
	}
	
}
