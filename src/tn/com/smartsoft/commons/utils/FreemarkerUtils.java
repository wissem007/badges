package tn.com.smartsoft.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

public class FreemarkerUtils {
	private static Configuration cfg = null;

	public static String process(Map context, String templateSnippet) throws TechnicalException {
		try {
			if (context == null)
				context = new HashMap();
			StringReader reader = new StringReader(templateSnippet);
			Template fmTemplate = null;
			fmTemplate = new Template("snippet", reader, getCfg());
			String result = processTemplate(context, fmTemplate);
			return result;
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

	public static TemplateHashModel getStaticModel(String className) throws TechnicalException {
		try {
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();
			return (TemplateHashModel) staticModels.get(className);
		} catch (TemplateModelException e) {
			throw new TechnicalException(e);
		}
	}

	private static String processTemplate(Map context, Template fmTemplate) throws TechnicalException {
		OutputStreamWriter osw = null;
		ByteArrayOutputStream baos = null;

		try {
			baos = new ByteArrayOutputStream();
			osw = new OutputStreamWriter(baos);
			fmTemplate.process(context, osw);
			osw.flush();
			String result = new String(baos.toByteArray());

			return result;

		} catch (IOException ioe) {
			throw new TechnicalException(ioe);
		} catch (freemarker.template.TemplateException te) {
			throw new TechnicalException(te);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception e) {}
			}
		}
	}

	private static Configuration getCfg() {
		if (cfg == null) {
			cfg = new Configuration();
			cfg.setTemplateUpdateDelay(0);
		}
		return cfg;
	}

}
