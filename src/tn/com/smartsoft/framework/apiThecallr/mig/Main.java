package tn.com.smartsoft.framework.apiThecallr.mig;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.csv.CsvManager;
import tn.com.smartsoft.commons.csv.CsvPopulateRow;
import tn.com.smartsoft.commons.csv.CsvSettings;
import tn.com.smartsoft.commons.csv.setting.CvsSettingDelimiterRow;

public class Main {
	public static GenerateurDB generateurDBToPost = MigUtils.createPgConnection("137.74.90.103", "bo_digi_db", "postgres",
			"postgres");

	public static void main(String[] args) throws Exception {
		FileInputStream fileCsv = new FileInputStream(
				"C:\\Users\\DIGIVOIP_KALI\\Documents\\Grille Tarifaire Terminaison 20170510.csv");
		CsvSettings csvSettings = new CsvSettings(fileCsv, ';');
		csvSettings.setDecimalFormat("##0.000");
		CsvManager csvManager = CsvManager.createCsvManager(csvSettings);
		CvsSettingDelimiterRow settingRow = csvSettings.addDelimiterSettingRow();
		settingRow.addColumn();
		settingRow.addColumn();
		final Map<String, Long> articleVoixs = getMappedArticle();
		settingRow.setPopulateRow(new CsvPopulateRow() {
			String liblle;

			public void populate(int row, int col, boolean isValidData, Object value) {
				if (col == 0)
					liblle = (String) value;
				else
					insertPrixAchat(articleVoixs, liblle, Double.parseDouble((String) value));
			}
		});
		csvManager.runIterator();

	}

	public static void insertPrixAchat(Map<String, Long> articleVoixs, String libelle, Double prix) {
		String insetRequest = "INSERT INTO sss_backoffice_db.bo_art_voix_prix_achat_prix(bo_art_voix_prix_achat_prd_id, bo_article_voix_id, mnt_prix) VALUES (4,?,?)";
		libelle = StringUtils.replaceChars(libelle, "-", " ");
		libelle = libelle.toUpperCase();
		Long id = articleVoixs.get(libelle);
		if (id == null) {
			System.out.println(libelle.toUpperCase());
		}
		Object[] valuesService = new Object[] { id, prix };
		SqlUtils.updateData(insetRequest, valuesService, generateurDBToPost);
	}

	public static Map<String, Long> getMappedArticle() {
		final Map<String, Long> articleVoixs = new HashMap<String, Long>();
		SqlUtils.selectData("SELECT bo_article_voix_id, libelle FROM sss_backoffice_db.bo_article_voix",
				new Object[] {}, new IExecutorRow() {
					public void run(ResultSet rs) throws SQLException {
						String libelle = rs.getString("libelle").trim().toUpperCase();
						libelle = StringUtils.replaceChars(libelle, "-", " ");
						articleVoixs.put(libelle, rs.getLong("bo_article_voix_id"));
					}
				}, generateurDBToPost);
		return articleVoixs;
	}

	public final static void add(int row, int col, boolean isValidData, Object value) {
		System.out.println("row: " + row + " col: " + col + "  isValidData: " + isValidData + " value: " + value);
	}
}
