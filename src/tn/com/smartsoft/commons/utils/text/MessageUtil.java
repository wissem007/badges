package tn.com.smartsoft.commons.utils.text;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class MessageUtil {
	public static String substituteParams(String msgtext, Locale locale, Object params[]) {
		String localizedStr = null;
		if ((params == null) || (msgtext == null))
			return msgtext;
		StringBuffer b = new StringBuffer();
		msgtext = StringUtils.replace(msgtext, "'", "''");
		MessageFormat mf = new MessageFormat(msgtext);
		if (locale != null) {
			mf.setLocale(locale);
			b.append(mf.format(((params))));
			localizedStr = b.toString();
		}
		return localizedStr;
	}

	public static String substituteParamsFrLocal(String msgtext, Object params[]) {
		return substituteParams(msgtext, (String) null, params);
	}

	public static String substituteParams(String msgtext, String lang, Object params[]) {
		if (lang == null)
			lang = "fr";
		return substituteParams(msgtext, new Locale(lang.toLowerCase()), params);
	}
}
