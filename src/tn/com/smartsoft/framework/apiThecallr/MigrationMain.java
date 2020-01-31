package tn.com.smartsoft.framework.apiThecallr;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.data.DateBorne;
import tn.com.smartsoft.commons.utils.DateUtils;
import tn.com.smartsoft.commons.utils.NumberUtils;
import tn.com.smartsoft.framework.apiThecallr.mig.CustomerBean;
import tn.com.smartsoft.framework.apiThecallr.mig.DestinationCallSummary;
import tn.com.smartsoft.framework.apiThecallr.mig.MigrationDao;
import tn.com.smartsoft.framework.apiThecallr.mig.RateBean;

public class MigrationMain {
	private static final String HTTPS_API_THECALLR_COM = "https://api.thecallr.com";

	public static void main(String[] args) {
		createAllTrunk();
	}

	public static void insertDestination() {
		MigrationDao.insertDestination(MigrationDao.getAllDestination());
	}

	public static void createAllTrunk() {
		ArrayList<CustomerBean> customerApis = MigrationDao.getAllPgCustomerApi();
		Map<String, Integer> ids = new HashMap<String, Integer>();
		ids.put("t", 0);
		ids.put("d", 0);
		for (int i = 0; i < customerApis.size(); i++) {
			CustomerBean customerApi = customerApis.get(i);
			ApiClientRpc apiClientRpc = new ApiClientRpc(HTTPS_API_THECALLR_COM, customerApi.getLoginProvider(), customerApi.getPasswordProvider());
			MigrationDao.insertTrunk(apiClientRpc.getTrunkList(), customerApi.getIdCustomer(), ids);
		}
	}

	public static void createAllClient() {
		Map<String, CustomerBean> cpts = MigrationDao.getAllMapCustomerApi();
		ArrayList<CustomerBean> cots = MigrationDao.getAllCustomer();
		MigrationDao.insertCustomer(cots, cpts);
	}

	public static void createAllCompteApi() {
		ArrayList<CustomerBean> cots = MigrationDao.getAllCustomerApi();
		MigrationDao.insertCompteApi(cots);
	}

	public static void insertHistApiDestinationCall(Long compteId, String loginProvider, String passwordProvider, DateBorne dateBorne) {
		ApiClientRpc apiClientRpc = new ApiClientRpc(HTTPS_API_THECALLR_COM, loginProvider, passwordProvider);
		DateBorne dateBorneItem = new DateBorne(dateBorne.getFrom(), DateUtils.ajouteJours(dateBorne.getFrom(), 1));
		do {
			List<DestinationCallSummary> destinationCallSummarys = getHistApiDestinationCalls(apiClientRpc, dateBorneItem);
			MigrationDao.insertHistApiDestinationCall(destinationCallSummarys, compteId);
			dateBorneItem = new DateBorne(dateBorneItem.getTo(), DateUtils.ajouteJours(dateBorneItem.getTo(), 1));
		} while (dateBorneItem.getTo().compareTo(dateBorne.getTo()) <= 0);
	}

	public static void insertHistApiDestinationCall() {
		Timestamp to = DateUtils.getCourantTimestamp();
		Timestamp from = DateUtils.getTimestamp(2016, 05, 31, 0, 0, 0);
		DateBorne dateBorne = new DateBorne(from, to);
		ArrayList<CustomerBean> cots = MigrationDao.getCompteAllPgApi();
		boolean isStart = false;
		for (int i = 0; i < cots.size(); i++) {
			CustomerBean customerBean = cots.get(i);
			System.out.println(customerBean.getIdCustomer());
			// if (customerBean.getIdCustomer().equals(new Long(35)))
			isStart = true;
			if (isStart) {
				insertHistApiDestinationCall(customerBean.getIdCustomer(), customerBean.getLoginProvider(), customerBean.getPasswordProvider(), dateBorne);
			}
		}
	}

	public static List<DestinationCallSummary> getHistApiDestinationCalls(ApiClientRpc apiClientRpc, DateBorne dateBorne) {
		List<DestinationCallSummary> callSummarys = new ArrayList<DestinationCallSummary>();
		final Map<String, Long> articleVoixs = MigrationDao.getMappedArticle();
		List<Inbound> response = apiClientRpc.getOutboundDestinations(dateBorne);
		for (int j = 0; j < response.size(); j++) {
			Inbound inbound = response.get(j);
			Long id = getArticle(articleVoixs, inbound.getBilling_label());
			if (id == null) {
				System.out.println(inbound.getBilling_label());
			}
			DestinationCallSummary destinationCallSummary = new DestinationCallSummary(id, inbound.getBilling_label(), dateBorne);
			destinationCallSummary.setDateBorne(dateBorne);
			destinationCallSummary.setDuration(inbound.getDuration().longValue());
			destinationCallSummary.setCount(inbound.getCount().longValue());
			destinationCallSummary.setPrixAchatApi(inbound.getDebit());
			callSummarys.add(destinationCallSummary);
		}
		return callSummarys;
	}

	private static Long getArticle(Map<String, Long> articleVoixs, String libelle) {
		String trim = libelle.toUpperCase().trim();
		Long articleVoixId = articleVoixs.get(trim);
		if (articleVoixId == null) {
			String replace = StringUtils.replaceChars(trim, "-", " ");
			articleVoixId = articleVoixs.get(replace);
		}
		if (articleVoixId == null) {
			String replace = StringUtils.replaceChars(trim, " ", "-");
			articleVoixId = articleVoixs.get(replace);
		}
		return articleVoixId;
	}

	public static void getHistCall1() {
		Timestamp from = DateUtils.getTimestamp(2015, 11, 24, 0, 0, 0);
		Timestamp to = DateUtils.getTimestamp(2016, 03, 20, 0, 0, 0);
		DateBorne dateBorne = new DateBorne(from, to);
		Map<String, RateBean> rates = MigrationDao.getRatesTauxApi(1);
		ArrayList<CustomerBean> cots = MigrationDao.getAllCustomerApi();
		boolean isStart = false;
		for (int i = 0; i < cots.size(); i++) {
			CustomerBean customerBean = cots.get(i);
			System.out.println(customerBean.getIdCustomer());
			// if (customerBean.getIdCustomer().equals(new Long(35)))
			isStart = true;
			if (isStart) {
				List<DestinationCallSummary> callSummarys = getAllCallSummarysHistoryApi(customerBean.getIdCustomer(), customerBean.getLoginProvider(), customerBean.getPasswordProvider(), dateBorne,
						rates);
				MigrationDao.addDestinationCallSummarysHistoryApi(callSummarys, customerBean.getIdCustomer());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void getHistCall2() {
		Timestamp from = DateUtils.getTimestamp(2016, 03, 20, 0, 0, 0);
		Timestamp to = DateUtils.getTimestamp(2016, 05, 31, 0, 0, 0);
		DateBorne dateBorne = new DateBorne(from, to);
		Map<String, RateBean> rates = MigrationDao.getRatesTauxApi(2);
		ArrayList<CustomerBean> cots = MigrationDao.getAllCustomerApi();
		boolean isStart = false;
		for (int i = 0; i < cots.size(); i++) {
			CustomerBean customerBean = cots.get(i);
			System.out.println(customerBean.getIdCustomer());
			if (customerBean.getIdCustomer().equals(new Long(35)))
				isStart = true;
			if (isStart) {
				List<DestinationCallSummary> callSummarys = getAllCallSummarysHistoryApi(customerBean.getIdCustomer(), customerBean.getLoginProvider(), customerBean.getPasswordProvider(), dateBorne,
						rates);
				MigrationDao.addDestinationCallSummarysHistoryApi(callSummarys, customerBean.getIdCustomer());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static void getHistCall3() {
		Timestamp from = DateUtils.getTimestamp(2016, 05, 31, 0, 0, 0);
		Timestamp to = DateUtils.getTimestamp(2016, 11, 21, 0, 0, 0);
		DateBorne dateBorne = new DateBorne(from, to);
		Map<String, RateBean> rates = MigrationDao.getRatesTauxApi(3);
		ArrayList<CustomerBean> cots = MigrationDao.getAllCustomerApi();
		boolean isStart = false;
		for (int i = 0; i < cots.size(); i++) {
			CustomerBean customerBean = cots.get(i);
			System.out.println(customerBean.getIdCustomer());
			// if (customerBean.getIdCustomer().equals(new Long(35)))
			isStart = true;
			if (isStart) {
				List<DestinationCallSummary> callSummarys = getAllCallSummarysHistoryApi(customerBean.getIdCustomer(), customerBean.getLoginProvider(), customerBean.getPasswordProvider(), dateBorne,
						rates);
				MigrationDao.addDestinationCallSummarysHistoryApi(callSummarys, customerBean.getIdCustomer());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/*
	
	 */
	public static Double getBalance(Long idCustomer) {
		return getBalance(MigrationDao.getCustomerById(idCustomer));
	}

	public static Double getBalance(CustomerBean customerBean) {
		Timestamp dateCreation = customerBean.getDateCreation();
		return getBalance(customerBean.getIdCustomer(), dateCreation, customerBean.getLoginProvider(), customerBean.getPasswordProvider(), customerBean.getInitialRefil());
	}

	public static Double getBalance(Long idCustomer, Timestamp dateCreation, String loginProvider, String passwordProvider, Double inialRefill) {
		Timestamp toDay = DateUtils.getCourantTimestamp(3600);
		DateBorne dateBorne = new DateBorne(dateCreation, toDay);
		Double solde = MigrationDao.getCustomerSolde(idCustomer, dateCreation);
		solde = NumberUtils.somme(inialRefill, solde);
		DestinationCallSummary totalCallSummary = getTotalCallSummary(idCustomer, loginProvider, passwordProvider, dateBorne);
		System.out.println(totalCallSummary);
		System.out.println(solde);
		Double debit = totalCallSummary.getPrixVente();
		return NumberUtils.soustrait(solde, debit);
	}

	public static void createDestinationCallSummarysHistory(Long idCustomer, DateBorne dateBorne) {
		List<DestinationCallSummary> callSummarys = getDestinationCallSummarysHistory(idCustomer, dateBorne);
		MigrationDao.addDestinationCallSummarysHistory(callSummarys, idCustomer);
	}

	public static List<DestinationCallSummary> getDestinationCallSummarysHistory(Long idCustomer, DateBorne dateBorne) {
		CustomerBean customerBean = MigrationDao.getCustomerById(idCustomer);
		if (dateBorne.getFrom().compareTo(customerBean.getDateCreation()) > 0) {
			dateBorne = new DateBorne(dateBorne.getFrom(), dateBorne.getTo());
		}
		return getDestinationCallSummarysHistory(customerBean.getIdCustomer(), customerBean.getLoginProvider(), customerBean.getPasswordProvider(), dateBorne);
	}

	public static List<DestinationCallSummary> getAllCallSummarysHistoryApi(Long idCustomer, String loginProvider, String passwordProvider, DateBorne dateBorne, Map<String, RateBean> rates) {
		List<DestinationCallSummary> callSummarys = new ArrayList<DestinationCallSummary>();
		DateBorne dateBorneItem = new DateBorne(dateBorne.getFrom(), DateUtils.ajouteJours(dateBorne.getFrom(), 1));
		do {
			List<DestinationCallSummary> destinationCallSummarys = getDestinationCallSummarysHistoryApi(idCustomer, loginProvider, passwordProvider, dateBorneItem, rates);
			callSummarys.addAll(destinationCallSummarys);
			dateBorneItem = new DateBorne(dateBorneItem.getTo(), DateUtils.ajouteJours(dateBorneItem.getTo(), 1));
		} while (dateBorneItem.getTo().compareTo(dateBorne.getTo()) <= 0);
		return callSummarys;
	}

	public static List<DestinationCallSummary> getDestinationCallSummarysHistoryApi(Long idCustomer, String loginProvider, String passwordProvider, DateBorne dateBorne, Map<String, RateBean> rates) {
		List<DestinationCallSummary> callSummarys = new ArrayList<DestinationCallSummary>();
		ApiClientRpc apiClientRpc = new ApiClientRpc(HTTPS_API_THECALLR_COM, loginProvider, passwordProvider);
		List<Inbound> response = apiClientRpc.getOutboundDestinations(dateBorne);
		for (int j = 0; j < response.size(); j++) {
			Inbound inbound = response.get(j);
			RateBean rate = getTaux(rates, inbound.getBilling_label());
			DestinationCallSummary destinationCallSummary = new DestinationCallSummary(rate.getId(), rate.getLibelle(), dateBorne);
			Double duration = NumberUtils.diviser(inbound.getDuration(), 60);
			Double prixVente = NumberUtils.somme(destinationCallSummary.getPrixVente(), NumberUtils.multiplier(duration, rate.getPrixVente()));
			Double prixAchat = NumberUtils.somme(destinationCallSummary.getPrixAchat(), NumberUtils.multiplier(duration, rate.getPrixAchat()));
			Double prixAchatApi = NumberUtils.somme(destinationCallSummary.getPrixAchatApi(), inbound.getDebit());
			destinationCallSummary.setDuration((destinationCallSummary.getDuration().longValue() + inbound.getDuration().longValue()));
			destinationCallSummary.setCount(destinationCallSummary.getCount().longValue() + inbound.getCount().longValue());
			destinationCallSummary.setPrixAchat(prixAchat);
			destinationCallSummary.setPrixVente(prixVente);
			destinationCallSummary.setPrixAchatApi(prixAchatApi);
			destinationCallSummary.setPrixUnitairVente(rate.getPrixVente());
			destinationCallSummary.setPrixUnitairAchat(rate.getPrixAchat());
			destinationCallSummary.setPrixUnitairAchatApi(NumberUtils.diviser(inbound.getDebit(), NumberUtils.diviser(inbound.getDuration(), 60)));
			callSummarys.add(destinationCallSummary);
		}
		return callSummarys;
	}

	public static List<DestinationCallSummary> getDestinationCallSummarysHistory(Long idCustomer, String loginProvider, String passwordProvider, DateBorne dateBorne) {
		List<DestinationCallSummary> callSummarys = new ArrayList<DestinationCallSummary>();
		DateBorne dateBorneItem = new DateBorne(dateBorne.getFrom(), DateUtils.ajouteJours(dateBorne.getFrom(), 1));
		do {
			List<DestinationCallSummary> destinationCallSummarys = getDestinationCallSummarys(idCustomer, loginProvider, passwordProvider, dateBorneItem);
			callSummarys.addAll(destinationCallSummarys);
			dateBorneItem = new DateBorne(dateBorneItem.getTo(), DateUtils.ajouteJours(dateBorneItem.getTo(), 1));
		} while (dateBorneItem.getTo().compareTo(dateBorne.getTo()) <= 0);
		return callSummarys;
	}

	public static List<DestinationCallSummary> getDestinationCallSummarys(Long idCustomer, DateBorne dateBorne) {
		CustomerBean customerBean = MigrationDao.getCustomerById(idCustomer);
		return getDestinationCallSummarys(customerBean.getIdCustomer(), customerBean.getLoginProvider(), customerBean.getPasswordProvider(), dateBorne);
	}

	public static List<DestinationCallSummary> getDestinationCallSummarys(Long idCustomer, String loginProvider, String passwordProvider, DateBorne dateBorne) {
		ApiClientRpc apiClientRpc = new ApiClientRpc(HTTPS_API_THECALLR_COM, loginProvider, passwordProvider);
		List<DateBorne> bornes = MigrationDao.getRatesDateBorne(idCustomer, dateBorne);
		final Map<String, DestinationCallSummary> destinationCallSummarys = new HashMap<String, DestinationCallSummary>();
		for (int i = 0; i < bornes.size(); i++) {
			DateBorne dateBorneItem = bornes.get(i);
			List<Inbound> response = apiClientRpc.getOutboundDestinations(dateBorneItem);
			Map<String, RateBean> rates = MigrationDao.getRatesTauxValueByDateBorne(idCustomer, dateBorneItem.getTo());
			for (int j = 0; j < response.size(); j++) {
				Inbound inbound = response.get(j);
				RateBean rate = getTaux(rates, inbound.getBilling_label());
				DestinationCallSummary destinationCallSummary = destinationCallSummarys.get(inbound.getBilling_label());
				if (destinationCallSummary == null) {
					destinationCallSummary = new DestinationCallSummary(rate.getId(), rate.getLibelle(), dateBorneItem);
					destinationCallSummarys.put(inbound.getBilling_label(), destinationCallSummary);
				}
				Double duration = NumberUtils.diviser(inbound.getDuration(), 60);
				Double prixVente = NumberUtils.somme(destinationCallSummary.getPrixVente(), NumberUtils.multiplier(duration, rate.getPrixVente()));
				Double prixAchat = NumberUtils.somme(destinationCallSummary.getPrixAchat(), NumberUtils.multiplier(duration, rate.getPrixAchat()));
				Double prixAchatApi = NumberUtils.somme(destinationCallSummary.getPrixAchatApi(), NumberUtils.diviser(inbound.getDebit(), 100));
				destinationCallSummary.setDuration((destinationCallSummary.getDuration().longValue() + inbound.getDuration().longValue()));
				destinationCallSummary.setCount(destinationCallSummary.getCount().longValue() + inbound.getCount().longValue());
				destinationCallSummary.setPrixAchat(prixAchat);
				destinationCallSummary.setPrixVente(prixVente);
				destinationCallSummary.setPrixAchatApi(prixAchatApi);
				destinationCallSummary.setPrixUnitairVente(rate.getPrixVente());
				destinationCallSummary.setPrixUnitairAchat(rate.getPrixAchat());
				destinationCallSummary.setPrixUnitairAchatApi(NumberUtils.diviser(inbound.getDebit(), NumberUtils.diviser(inbound.getDuration(), 60)));
			}
		}
		return new ArrayList<DestinationCallSummary>(destinationCallSummarys.values());
	}

	public static DestinationCallSummary getTotalCallSummary(Long idCustomer, String loginProvider, String passwordProvider, DateBorne dateBorne) {
		ApiClientRpc apiClientRpc = new ApiClientRpc(HTTPS_API_THECALLR_COM, loginProvider, passwordProvider);
		final DestinationCallSummary totalCallSummary = new DestinationCallSummary();
		List<DateBorne> bornes = MigrationDao.getRatesDateBorne(idCustomer, dateBorne);
		Double totalVente = 0.0;
		Double totalAchatApi = 0.0;
		Double totalAchat = 0.0;
		Long totalDuration = 0L;
		Long totalCount = 0L;
		for (int i = 0; i < bornes.size(); i++) {
			DateBorne dateBorneItem = bornes.get(i);
			List<Inbound> response = apiClientRpc.getOutboundDestinations(dateBorneItem);
			Map<String, RateBean> rates = MigrationDao.getRatesTauxValueByDateBorne(idCustomer, dateBorneItem.getTo());
			for (int j = 0; j < response.size(); j++) {
				Inbound inbound = response.get(j);
				RateBean rate = getTaux(rates, inbound.getBilling_label());
				Double duration = NumberUtils.diviser(inbound.getDuration(), 60);
				totalVente = NumberUtils.somme(totalVente, NumberUtils.multiplier(duration, rate.getPrixVente()));
				totalAchat = NumberUtils.somme(totalAchat, NumberUtils.multiplier(duration, rate.getPrixAchat()));
				totalAchatApi = NumberUtils.somme(totalAchatApi, NumberUtils.diviser(inbound.getDebit(), 100));
				totalDuration = (totalDuration + inbound.getDuration().longValue());
				totalCount = (totalCount + inbound.getCount().longValue());
			}
		}
		totalCallSummary.setDuration(totalDuration);
		totalCallSummary.setCount(totalCount);
		totalCallSummary.setPrixAchat(totalAchat);
		totalCallSummary.setPrixVente(totalVente);
		totalCallSummary.setPrixAchatApi(totalAchatApi);
		return totalCallSummary;
	}

	private static RateBean getTaux(Map<String, RateBean> rates, String libelle) {
		RateBean rate = rates.get(libelle.toUpperCase().trim());
		if (rate == null) {
			String replace = StringUtils.replaceChars(libelle.toUpperCase(), "-", " ");
			rate = rates.get(replace);
		}
		if (rate == null) {
			String replace = StringUtils.replaceChars(libelle.toUpperCase(), " ", "-");
			rate = rates.get(replace);
		}
		if (rate == null) {
			rate = new RateBean(0L, libelle, 2.5, 1.0);
		}
		return rate;
	}
}
