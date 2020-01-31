package tn.com.smartsoft.framework.presentation.view.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	public static final String		PDF					= "pdf";
	public static final String		EXCEL				= "xls";
	public static final String		HTML				= "html";
	public static final String		CSV					= "csv";
	private String					propertyDataSource;
	private String					fileName;
	protected boolean				isImprimerAlert		= true;
	private String					path;
	private Map<String, Object>		paramValues			= new HashMap<String, Object>();
	private ByteArrayOutputStream	source;
	private boolean					responseToWeb		= true;
	private InputStream				reportStream;
	private Boolean					isReportStream		= false;
	
	public ReportModel(String fileName, String path) {
		super();
		this.fileName = fileName;
		this.path = path;
		this.isImprimerAlert = false;
	}
	
	public ReportModel(String propertyDataSource, String fileName, String path) {
		super();
		this.propertyDataSource = propertyDataSource;
		this.fileName = fileName;
		this.path = path;
		this.isImprimerAlert = false;
	}
	
	public ReportModel(String fileName, String path, InputStream reportStream) {
		super();
		this.fileName = fileName;
		this.path = path;
		this.isImprimerAlert = false;
		this.isReportStream = true;
		this.reportStream = reportStream;
	}
	
	public ReportModel(String propertyDataSource, String fileName, String path, InputStream reportStream) {
		super();
		this.propertyDataSource = propertyDataSource;
		this.fileName = fileName;
		this.path = path;
		this.isImprimerAlert = false;
		this.isReportStream = true;
		this.reportStream = reportStream;
	}
	
	public boolean isResponseToWeb() {
		return responseToWeb;
	}
	
	public void setResponseToWeb(boolean responseToWeb) {
		this.responseToWeb = responseToWeb;
	}
	
	public ByteArrayOutputStream getSource() {
		return source;
	}
	
	public void setSource(ByteArrayOutputStream source) {
		this.source = source;
	}
	
	public void addParam(String key, Object value) {
		this.paramValues.put(key, value);
	}
	
	public Map<String, Object> getParamValues() {
		return paramValues;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPropertyDataSource() {
		return propertyDataSource;
	}
	
	public void setPropertyDataSource(String propertyDataSource) {
		this.propertyDataSource = propertyDataSource;
	}
	
	public boolean isImprimerAlert() {
		return isImprimerAlert;
	}
	
	public void setImprimerAlert(boolean isImprimerAlert) {
		this.isImprimerAlert = isImprimerAlert;
	}
	
	public InputStream getReportStream() {
		return reportStream;
	}
	
	public void setReportStream(InputStream reportStream) {
		this.reportStream = reportStream;
	}
	
	public Boolean getIsReportStream() {
		return isReportStream;
	}
	
	public void setIsReportStream(Boolean isReportStream) {
		this.isReportStream = isReportStream;
	}
	
}
