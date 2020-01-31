package tn.com.smartsoft.framework.apiThecallr;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import tn.com.digivoip.comman.jsonrpc2.JSONRPC2Error;
import tn.com.digivoip.comman.jsonrpc2.JsonRpcClient;
import tn.com.digivoip.framework.exception.TechnicalException;
import tn.com.smartsoft.commons.data.DateBorne;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.DateFormaterUtils;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.FormatException;
import tn.com.smartsoft.commons.utils.NumberUtils;

@SuppressWarnings("unchecked")
public class ApiClientRpc implements Serializable {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String METHODE_SYSTEM_GET_TIMESTAMP = "system.get_timestamp";
	private static final String METHODE_CDR_GET = "cdr.get";
	private static final String METHODE_SMS_SEND = "sms.send";
	private static final String METHODE_SMS_GET_LIST = "sms.get_list";
	private static final String METHODE_ANALYTICS_CALLS_HISTORY = "analytics/calls.history";
	private static final String METHODE_ANALYTICS_CALLS_OUTBOUND_DESTINATIONS = "analytics/calls.outbound_destinations";
	private static final String METHODE_ANALYTICS_CALLS_INBOUND_DID = "analytics/calls.inbound_did";
	private static final String METHODE_ANALYTICS_CALLS_OUTBOUND_COUNTRIES = "analytics/calls.outbound_countries";
	private static final String METHODE_BILLING_GET_PREPAID_CREDIT = "billing.get_prepaid_credit";
	private static final String METHODE_ANALYTICS_GET_OUTBOUND_TYPES = "analytics.get_outbound_types";
	private static final String METHODE_TRUNK_GET_LIST = "apps.get_list";
	private static final String METHODE_DID_GET_LIST = "apps.get_dids";
	
	private static final String METHODE_COMPTE_EDIT = "account/sub.edit";
	private static final String METHODE_TRANSFER_CREDIT = "account/sub.transfer_credit";
	private static final String METHODE_COMPTE_GET_LIST = "account/sub.search";
	private static final String METHODE_COMPTE_GET_MASTER = "account/sub.get_quota";
	private static final String METHODE_COMPTE_DISABLE_MASTER = "account/sub.disable";
	private static final String METHODE_COMPTE_ENABLE_MASTER = "account/sub.enable";
	public static final String HTTPS_API_THECALLR_COM = "https://api.callr.com/json-rpc/v1.1/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String SORT_COUNT = "COUNT";
	public static String SORT_DURATION = "DURATION";
	public static String CALL_CDR_IN = "IN";
	public static String CALL_CDR_OUT = "OUT";
	private JsonRpcClient jsonRpcClient;
	private boolean isOpen = false;
	
	public ApiClientRpc(String serverURL, String login, String password) {
		this.jsonRpcClient = new JsonRpcClient(serverURL, login, password);
	}
	
	public ApiClientRpc(String login, String password) {
		this.jsonRpcClient = new JsonRpcClient(HTTPS_API_THECALLR_COM, login, password);
	}
	
	public JSONArray getOutboundTypes(String sort, DateBorne dateBorne) {
		open();
		return (JSONArray) jsonRpcClient.execute(METHODE_ANALYTICS_GET_OUTBOUND_TYPES, sort, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()), null);
	}
	
	public JSONArray getOutboundTypes(DateBorne dateBorne) {
		return getOutboundTypes(SORT_DURATION, dateBorne);
	}
	
	public boolean disableCompteVal(String hash) {
		open();
		try {
			Object resultat = jsonRpcClient.execute(METHODE_COMPTE_DISABLE_MASTER, hash);
			return StringUtils.equalsIgnoreCase(resultat.toString(), "true");
		} catch (TechnicalException e) {
			if (e.getCause() instanceof JSONRPC2Error) {
				JSONRPC2Error error = (JSONRPC2Error) e.getCause();
				if (error.getCode() == 0)
					return true;
			}
			throw e;
		}
	}
	
	public boolean enableCompteVal(String hash) {
		open();
		try {
			Object resultat = jsonRpcClient.execute(METHODE_COMPTE_ENABLE_MASTER, hash);
			return StringUtils.equalsIgnoreCase(resultat.toString(), "true");
		} catch (TechnicalException e) {
			if (e.getCause() instanceof JSONRPC2Error) {
				JSONRPC2Error error = (JSONRPC2Error) e.getCause();
				if (error.getCode() == 0)
					return true;
			}
			throw e;
		}
	}
	
	public Double getPrepaidCredit() {
		open();
		String resultat = (String) jsonRpcClient.execute(METHODE_BILLING_GET_PREPAID_CREDIT);
		return formateNumber(resultat);
	}
	
	public List<OutboundCountrie> getOutboundCountries(String sort, DateBorne dateBorne) {
		open();
		JSONArray execute = (JSONArray) jsonRpcClient.execute(METHODE_ANALYTICS_CALLS_OUTBOUND_COUNTRIES, sort, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()), 1000000000);
		List<OutboundCountrie> list = (List<OutboundCountrie>) BeanObjectUtils.toListJson(OutboundCountrie.class, execute);
		return list;
	}
	
	public List<OutboundCountrie> getOutboundCountries(DateBorne dateBorne) {
		return getOutboundCountries(SORT_DURATION, dateBorne);
	}
	
	public List<Inbound> getInbound(String sort, DateBorne dateBorne) {
		open();
		JSONArray execute = (JSONArray) jsonRpcClient.execute(METHODE_ANALYTICS_CALLS_INBOUND_DID, sort, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()), 1000000000);
		List<Inbound> list = (List<Inbound>) BeanObjectUtils.toListJson(Inbound.class, execute);
		return list;
	}
	
	public List<Inbound> getInbound(DateBorne dateBorne) {
		return getInbound(SORT_DURATION, dateBorne);
	}
	
	public List<Inbound> getOutboundDestinations(String sort, DateBorne dateBorne) {
		open();
		JSONArray execute = (JSONArray) jsonRpcClient.execute(METHODE_ANALYTICS_CALLS_OUTBOUND_DESTINATIONS, sort, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()), 1000000000);
		List<Inbound> list = (List<Inbound>) BeanObjectUtils.toListJson(Inbound.class, execute);
		return list;
	}
	
	public List<Inbound> getOutboundDestinations(DateBorne dateBorne) {
		return getOutboundDestinations(SORT_DURATION, dateBorne);
	}
	
	public CallsHistory getCallsHistory(DateBorne dateBorne) {
		open();
		JSONObject execute = (JSONObject) jsonRpcClient.execute(METHODE_ANALYTICS_CALLS_HISTORY, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()));
		CallsHistory callsHistory = new CallsHistory();
		callsHistory.setType((String) execute.get("type"));
		JSONArray in = (JSONArray) execute.get("IN");
		callsHistory.setIn((List<Inbound>) BeanObjectUtils.toListJson(Inbound.class, in));
		JSONArray out = (JSONArray) execute.get("OUT");
		callsHistory.setOut((List<Inbound>) BeanObjectUtils.toListJson(Inbound.class, out));
		return callsHistory;
	}
	
	public JSONArray getSmsOut(DateBorne dateBorne) {
		open();
		return (JSONArray) jsonRpcClient.execute(METHODE_SMS_GET_LIST, CALL_CDR_OUT, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()));
	}
	
	public JSONArray getSmsIn(DateBorne dateBorne) {
		open();
		return (JSONArray) jsonRpcClient.execute(METHODE_SMS_GET_LIST, CALL_CDR_IN, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()));
	}
	
	public Object sendSms(String from, String to, String text) {
		open();
		return jsonRpcClient.execute(METHODE_SMS_SEND, from, to, text);
	}
	
	public List<CdrItem> getCdrList(String type, DateBorne dateBorne) {
		open();
		JSONArray execute = (JSONArray) jsonRpcClient.execute(METHODE_CDR_GET, type, formateDate(dateBorne.getFrom()), formateDate(dateBorne.getTo()), null, null);
		System.out.println(execute);
		List<CdrItem> list = (List<CdrItem>) BeanObjectUtils.toListJson(CdrItem.class, execute);
		return list;
		
	}
	
	public List<CdrItem> getCdrList(DateBorne dateBorne) {
		return getCdrList(CALL_CDR_OUT, dateBorne);
	}
	
	public List<Did> getDidList(Boolean av) {
		open();
		JSONArray orig = (JSONArray) jsonRpcClient.execute(METHODE_DID_GET_LIST, av);
		List<Did> list = new ArrayList<Did>();
		for (int i = 0; i < orig.size(); i++) {
			Map<String, Object> did = (Map<String, Object>) orig.get(i);
			Did didObject = new Did();
			didObject.setIntl_number((String) did.get("intl_number"));
			didObject.setHash((String) did.get("hash"));
			didObject.setType((String) did.get("type"));
			didObject.setClasse((String) did.get("class"));
			didObject.setLocal_number((String) did.get("local_number"));
			didObject.setCountry_code((String) did.get("country_code"));
			list.add(didObject);
		}
		return list;
	}
	
	public List<Trunk> getTrunkList() {
		open();
		JSONArray orig = (JSONArray) jsonRpcClient.execute(METHODE_TRUNK_GET_LIST, Boolean.TRUE);
		System.out.println(orig);
		List<Trunk> list = new ArrayList<Trunk>();
		for (int i = 0; i < orig.size(); i++) {
			Map<String, Object> cap = (Map<String, Object>) orig.get(i);
			Trunk trunk = new Trunk();
			Date asDateUtil = null;
			Object anObject = cap.get("date_creation");
			if (!"0000-00-00 00:00:00".equals(anObject) && anObject != null)
				asDateUtil = DateUtils.getAsDateUtil(anObject.toString(), "yyyy-MM-dd HH:mm:ss");
			trunk.setDate_creation(asDateUtil);
			Map<String, Object> packages = (Map<String, Object>) cap.get("package");
			trunk.setHas_did((Boolean) packages.get("has_did"));
			trunk.setHash((String) packages.get("hash"));
			trunk.setType((String) packages.get("name"));
			Map<String, Object> p = (Map<String, Object>) cap.get("p");
			if (p != null && p.containsKey("username"))
				trunk.setUsername((String) p.get("username"));
			else
				trunk.setUsername((String) cap.get("name"));
			if (trunk.getType().startsWith("CALLFORWARDING")) {
				trunk.setType("SIP");
			} else if (trunk.getType().startsWith("TRUNKTERMINATION")) {
				trunk.setType("SIP");
			}
			trunk.setType(StringUtils.equals(trunk.getType(), "SIP") ? "3" : "2");
			trunk.setName((String) cap.get("name"));
			trunk.setHash((String) cap.get("hash"));
			JSONArray dids = (JSONArray) cap.get("did");
			for (int j = 0; j < dids.size(); j++) {
				Map<String, Object> did = (Map<String, Object>) dids.get(j);
				Did didObject = new Did();
				didObject.setIntl_number((String) did.get("intl_number"));
				didObject.setHash((String) did.get("hash"));
				didObject.setType((String) did.get("type"));
				didObject.setClasse((String) did.get("class"));
				didObject.setContract_auto_renew((Boolean) did.get("contract_auto_renew"));
				didObject.setLocal_number((String) did.get("local_number"));
				didObject.setCountry_code((String) did.get("country_code"));
				trunk.addDid(didObject);
			}
			list.add(trunk);
		}
		return list;
	}
	
	public boolean transferCredit(String fromId, String toId, Double value) {
		open();
		HashMap<String, Object> options = new HashMap<String, Object>();
		DecimalFormat decimalformatter = new DecimalFormat("#.00");
		decimalformatter.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
		Boolean res = (Boolean) jsonRpcClient.execute(METHODE_TRANSFER_CREDIT, fromId, toId, decimalformatter.format(NumberUtils.multiplier(value, 100).doubleValue()), options);
		return res;
	}
	
	public Boolean editStateCompteEnable(String hash) {
		return editStateCompte(hash, true);
	}
	
	public Boolean editStateCompteDisable(String hash) {
		return editStateCompte(hash, false);
	}
	
	//
	public Boolean editStateCompte(String hash, Boolean allow) {
		open();
		HashMap<String, Object> propertiesParam = new HashMap<String, Object>();
		HashMap<String, Object> optionsParam = new HashMap<String, Object>();
		HashMap<String, Object> state = new HashMap<String, Object>();
		state.put("allow_calls_in", allow);
		state.put("allow_calls_out", allow);
		state.put("allow_sms_in", allow);
		state.put("allow_sms_out", allow);
		propertiesParam.put("state", state);
		jsonRpcClient.execute(METHODE_COMPTE_EDIT, hash, propertiesParam, optionsParam);
		return true;
	}
	
	public List<CompteApi> getInfoCompte(int offset, int quantity) {
		open();
		HashMap<String, Object> filters = new HashMap<String, Object>();
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("offset", offset);
		options.put("quantity", quantity);
		JSONObject res = (JSONObject) jsonRpcClient.execute(METHODE_COMPTE_GET_LIST, filters, options);
		JSONArray orig = (JSONArray) res.get("hits");
		List<CompteApi> list = new ArrayList<CompteApi>();
		for (int i = 0; i < orig.size(); i++) {
			Map<String, Object> cap = (Map<String, Object>) orig.get(i);
			CompteApi compteApi = new CompteApi();
			compteApi.setHash((String) cap.get("hash"));
			Map<String, Object> credentials = (Map<String, Object>) cap.get("credentials");
			compteApi.setUsername((String) credentials.get("login"));
			Map<String, Object> state = (Map<String, Object>) cap.get("state");
			compteApi.setDisabled((Boolean) state.get("disabled"));
			compteApi.setArchived((Boolean) state.get("archived"));
			compteApi.setCredit(Double.parseDouble(StringUtils.isEmpty((String) state.get("credit")) ? "0" : (String) state.get("credit")));
			list.add(compteApi);
		}
		return list;
	}
	
	public List<CompteApi> getInfoMastCompte(int offset, int quantity) {
		open();
		JSONObject res = (JSONObject) jsonRpcClient.execute(METHODE_COMPTE_GET_MASTER);
		JSONArray orig = (JSONArray) res.get("hits");
		List<CompteApi> list = new ArrayList<CompteApi>();
		for (int i = 0; i < orig.size(); i++) {
			Map<String, Object> cap = (Map<String, Object>) orig.get(i);
			CompteApi compteApi = new CompteApi();
			compteApi.setHash((String) cap.get("hash"));
			Map<String, Object> credentials = (Map<String, Object>) cap.get("credentials");
			compteApi.setUsername((String) credentials.get("login"));
			Map<String, Object> state = (Map<String, Object>) cap.get("state");
			compteApi.setDisabled((Boolean) state.get("disabled"));
			compteApi.setArchived((Boolean) state.get("archived"));
			compteApi.setCredit(Double.parseDouble(StringUtils.isEmpty((String) state.get("credit")) ? "0" : (String) state.get("credit")));
			list.add(compteApi);
		}
		return list;
	}
	
	public Timestamp getTimestamp() {
		open();
		Long execute = (Long) jsonRpcClient.execute(METHODE_SYSTEM_GET_TIMESTAMP);
		return new Timestamp(execute);
	}
	
	public static String formateDate(Timestamp date) {
		try {
			return DateFormaterUtils.getAsString(date, DATE_FORMAT);
		} catch (FormatException e) {
			throw new TechnicalException(e);
		}
	}
	
	public static Double formateNumber(String resultat) {
		BigDecimal value = new BigDecimal(resultat);
		value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return value.doubleValue();
	}
	
	private void open() {
		if (!isOpen) {
			jsonRpcClient.open();
			isOpen = true;
		}
	}
	
	public void close() {
		if (isOpen) {
			jsonRpcClient.close();
		}
	}
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		ApiClientRpc apiClientRpc = new ApiClientRpc("digivoip.bank", "dvqlJjKYWANTjTez1m6x");
		
		// Timestamp from = DateUtils.getTimestamp(2016, 9, 25, 0, 0, 0);
		// Timestamp to = DateUtils.getTimestamp(2016, 9, 25, 10, 0, 0);
		// getOutboundTypes getPrepaidCredit getOutboundCountries,getInbound
		// getOutboundDestinations getCallsHistory getListSmsOut getCdrList
		// List<OutboundCountrie> list = apiClientRpc.getOutboundCountries(from,
		// to);
		// Object prepaidCredit = apiClientRpc.getTimestamp();
		// JSONArray outboundTypes = apiClientRpc.getOutboundTypes(new
		// DateBorne(from, to));
		;
		System.out.println(apiClientRpc.editStateCompte("PROINVES", false));
		// System.out.println(apiClientRpc.enableCompte("digivoip"));
		
	}
}