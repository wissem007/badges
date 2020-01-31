package tn.com.smartsoft.framework.presentation.view.report;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataSourceProvider;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;

public class RUtils {
	public static Map<String, Object> populateParameters(Map<String, Object> otherValues, UserAction userAction, JRParameter[] parms) {
		Map<String, Object> paramValues = new HashMap<String, Object>(otherValues);
		for (int i = 0; i < parms.length; i++) {
			String desc = parms[i].getDescription();
			String name = parms[i].getName();
			if (StringUtils.isNotBlank(desc))
				paramValues.put(name, userAction.getModel().getExpressionValue(desc));
		}
		return paramValues;
	}

	public static JRDataSource convertReportData(Object value) throws IllegalArgumentException {
		if (value instanceof JRDataSource) {
			return (JRDataSource) value;
		} else if (value instanceof Collection) {
			if (((Collection<?>) value).isEmpty())
				return new JREmptyDataSource();
			return new JRBeanCollectionDataSource((Collection<?>) value);
		} else if (value instanceof Object[]) {
			return new JRBeanArrayDataSource((Object[]) value);
		} else if (value instanceof JRDataSourceProvider) {
			return null;
		} else {
			throw new IllegalArgumentException("Value [" + value + "] cannot be converted to a JRDataSource");
		}
	}

	public static void render(JRExporter exporter, JasperPrint print, Writer writer) throws JRException {
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
		exporter.exportReport();
	}

	public static void render(JRExporter exporter, JasperPrint print, OutputStream outputStream) throws JRException {
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
	}

	public static boolean hasLength(Object str) {
		if (str == null)
			return false;
		return ((str != null) && (str.toString().length() > 0));
	}

	public static boolean isNotEmpty(String string) {
		return (string != null) && (string.length() > 0);
	}

	public static boolean isEmpty(String string) {
		return (string == null) || (string.length() == 0);
	}
}
