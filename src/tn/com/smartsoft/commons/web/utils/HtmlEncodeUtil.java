package tn.com.smartsoft.commons.web.utils;

public abstract class HtmlEncodeUtil {

	public HtmlEncodeUtil() {
	}

	private static final String HTMLCODE[] = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, "&nbsp;", "&iexcl;", "&cent;", "&pound;", "&curren;", "&yen;", "&brvbar;", "&sect;",
			"&uml;", "&copy;", "&ordf;", "&laquo;", "&not;", "&shy;", "&reg;", "&macr;", "&deg;", "&plusmn;", "&sup2;", "&sup3;", "&acute;", "&micro;", "&para;",
			"&middot;", "&cedil;", "&sup1;", "&ordm;", "&raquo;", "&frac14;", "&frac12;", "&frac34;", "&iquest;", "&Agrave;", "&Aacute;", "&Acirc;", "&Atilde;",
			"&Auml;", "&Aring;", "&AElig;", "&Ccedil;", "&Egrave;", "&Eacute;", "&Ecirc;", "&Euml;", "&Igrave;", "&Iacute;", "&Icirc;", "&Iuml;", "&ETH;", "&Ntilde;",
			"&Ograve;", "&Oacute;", "&Ocirc;", "&Otilde;", "&Ouml;", "&times;", "&Oslash;", "&Ugrave;", "&Uacute;", "&Ucirc;", "&Uuml;", "&Yacute;", "&THORN;",
			"&szlig;", "&agrave;", "&aacute;", "&acirc;", "&atilde;", "&auml;", "&aring;", "&aelig;", "&ccedil;", "&egrave;", "&eacute;", "&ecirc;", "&euml;",
			"&igrave;", "&iacute;", "&icirc;", "&iuml;", "&eth;", "&ntilde;", "&ograve;", "&oacute;", "&ocirc;", "&otilde;", "&ouml;", "&divide;", "&oslash;",
			"&ugrave;", "&uacute;", "&ucirc;", "&uuml;", "&yacute;", "&thorn;", "&yuml;" };

	public static final String encodeHtml(Object obj) {
		if (obj == null)
			return "&#160;";
		if (obj instanceof String)
			return encodeHtml((String) obj);
		else
			return encodeHtml(obj.toString());
	}

	public static final String encodeHtml(Object obj, boolean flag) {
		if (flag)
			return encodeHtml(obj);
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	private static final String encodeHtml(String s) {
		if (s == null || "".equals(s.trim()))
			return "&#160;";
		StringBuffer stringbuffer = new StringBuffer();
		int i = s.length();
		for (int j = 0; j < i;) {
			char c = s.charAt(j++);
			if (c == '\r') {
				if (j + 1 < i && s.charAt(j + 1) == '\n')
					j++;
				stringbuffer.append("<br>");
			} else if (c < HTMLCODE.length) {
				if (null == HTMLCODE[c])
					stringbuffer.append(c);
				else
					stringbuffer.append(HTMLCODE[c]);
			} else {
				stringbuffer.append(c);
			}
		}

		return stringbuffer.toString();
	}

	public static final String encodeHtml(char c) {
		if (c < HTMLCODE.length) {
			if (null == HTMLCODE[c])
				return String.valueOf(c);
			else
				return HTMLCODE[c];
		} else {
			return String.valueOf(c);
		}
	}

}
