package tn.com.smartsoft.framework.presentation.view.response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.report.RDataSource;
import tn.com.smartsoft.framework.presentation.view.report.RTemplatetModel;
import tn.com.smartsoft.framework.presentation.view.report.RUtils;
import tn.com.smartsoft.framework.presentation.view.report.ReportModel;

public class JasperResponse extends ResponseView {
	private static FileLocator	fileLocator						= new FileLocator();
	private static final String	PARAM_EXTENSION					= "extension";
	private static final String	PARAM_JASPER_EXPORTER			= "jasperExporter";
	private static final String	OUTPUT_BYTE_ARRAY_INITIAL_SIZE	= "size";
	
	public void response(Object model) {
		PrintWriter pw = null;
		try {
			if (model instanceof RTemplatetModel) {
				RTemplatetModel template = (RTemplatetModel) model;
				JasperReport report = JasperCompileManager.compileReport(template.getTemplate().build());
				response(template, report);
			} else {
				
				if (model instanceof ReportModel) {
					ReportModel reportModel = (ReportModel) model;
					JasperReport report;
					if (reportModel.getIsReportStream()) {
						report = (JasperReport) JRLoader.loadObject(reportModel.getReportStream());
					} else {
						report = (JasperReport) JRLoader.loadObject(fileLocator.getConfURL(reportModel.getPath()));
					}
					response(reportModel, report);
				} else {
					
					List<?> reportModels = (List<?>) model;
					List<JasperReport> reports = new ArrayList<JasperReport>();
					
					for (int i = 0; i < reportModels.size(); i++) {
						
						if (reportModels.get(i) instanceof ReportModel) {
							ReportModel template = (ReportModel) reportModels.get(i);
							JasperReport report = null;
							if (template instanceof RTemplatetModel)
								report = JasperCompileManager.compileReport(((RTemplatetModel) template).getTemplate().build());
							else {
								if (template.getIsReportStream()) {
									report = (JasperReport) JRLoader.loadObject(template.getReportStream());
								} else {
									report = (JasperReport) JRLoader.loadObject(fileLocator.getConfURL(template.getPath()));
								}
							}
							reports.add(report);
						} else {
							
							RTemplatetModel template = (RTemplatetModel) reportModels.get(i);
							JasperReport report = JasperCompileManager.compileReport(template.getTemplate().build());
							reports.add(report);
						}
						
					}
					
					response(reportModels, reports);
				}
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void response(List<?> reportModels, List<JasperReport> reports) throws Exception {
		JasperPrint jasperPrint = null;
		ReportModel masterModel = (ReportModel) reportModels.get(0);
		String extension = getParameter(PARAM_EXTENSION);
		generateHeader(masterModel, extension);
		for (int i = 0; i < reportModels.size(); i++) {
			ReportModel reportModel = (ReportModel) reportModels.get(i);
			JasperReport report = reports.get(i);
			if (i == 0)
				jasperPrint = generateJasperPrint(reportModel, report, extension);
			else {
				JasperPrint jasperPrint2 = generateJasperPrint(reportModel, report, extension);
				List<JRPrintPage> pages = jasperPrint2.getPages();
				for (int j = 0; j < pages.size(); j++) {
					JRPrintPage object = (JRPrintPage) pages.get(j);
					jasperPrint.addPage(object);
				}
			}
		}
		generateOutputStream(masterModel, jasperPrint, extension);
	}
	
	private void response(ReportModel reportModel, JasperReport report) throws Exception {
		String extension = getParameter(PARAM_EXTENSION);
		generateHeader(reportModel, extension);
		JasperPrint jasperPrint = generateJasperPrint(reportModel, report, extension);
		generateOutputStream(reportModel, jasperPrint, extension);
	}
	
	private void generateHeader(ReportModel reportModel, String extension) throws Exception {
		if (extension.equalsIgnoreCase("pdf")) {
			if (!StringUtils.isEmpty(reportModel.getFileName()))
				populateFileName(true, reportModel.getFileName(), extension);
		} else
			populateFileName(reportModel.getFileName(), extension);
		populateContentType();
		populateHeaders();
	}
	
	private void generateOutputStream(ReportModel reportModel, JasperPrint jasperPrint, String extension) throws Exception {
		int fileSeze = Integer.parseInt(getParameter(OUTPUT_BYTE_ARRAY_INITIAL_SIZE));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(fileSeze);
		JRAbstractExporter exporter = (JRAbstractExporter) Class.forName(getParameter(PARAM_JASPER_EXPORTER)).newInstance();
		if (extension.equalsIgnoreCase("pdf")) {
			exporter.setParameter(JRPdfExporterParameter.METADATA_TITLE, reportModel.getFileName());
			if (reportModel.isImprimerAlert())
				exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print(true);\r");
		} else if (extension.equalsIgnoreCase("xls")) {
			// exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		}
		RUtils.render(exporter, jasperPrint, outputStream);
		reportModel.setSource(outputStream);
		OutputStream out = context.response().getOutputStream();
		outputStream.writeTo(out);
		out.flush();
		
	}
	
	private JasperPrint generateJasperPrint(ReportModel reportModel, JasperReport report, String extension) throws Exception {
		Map<String, Object> params = RUtils.populateParameters(reportModel.getParamValues(), context.userDesktop().curentUserAction(), report.getParameters());
		JRDataSource dataSource = new JREmptyDataSource();
		String dataSourceName = reportModel.getPropertyDataSource();
		if (StringUtils.isBlank(dataSourceName)) {
			dataSourceName = (String) params.get("dataSourceName");
		}
		if (StringUtils.isNotBlank(reportModel.getPropertyDataSource())) {
			dataSource = new RDataSource(context.userDesktop().curentUserAction(), reportModel.getPropertyDataSource());
		}
		if (StringUtils.isNotBlank(reportModel.getPath())) {
			File file = new File(reportModel.getPath());
			params.put("BaseDir", file.getParent());
		}
		params.put("reportType", extension);
		params.put("File_SEP", System.getProperty("file.separator"));
		params.put("userAction", context.userDesktop().curentUserAction());
		params.put("userText", context.userDesktop().userContext().getUser().getDisplayName());
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
		return jasperPrint;
	}
}
