package tn.com.digivoip.comman.js;

public class JSEncodeUtil{

	public static String encodeJavaScript(String s) {
		if (null == s) { return ""; }
		StringBuffer stringbuffer = new StringBuffer();
		char ac[] = s.toCharArray();
		for (int i = 0; i < ac.length; i++) {
			if (ac[i] == '"') {
				stringbuffer.append("\\\"");
			} else if (ac[i] == '\'') {
				stringbuffer.append("\\'");
			} else if (ac[i] == '\\') {
				stringbuffer.append("\\\\");
			} else {
				stringbuffer.append(ac[i]);
			}
		}
		return stringbuffer.toString();
	}
}
