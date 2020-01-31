package tn.com.smartsoft.framework.presentation.view.action.model.expression;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tst {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, String> ms = new HashMap<String, String>();
		ms.put("PV", "getValue");
		ms.put("PC", "getConst");
		matc(ms, "$PV{ddb} + $PC{add} ");
	}
	
	public static void matc(Map<String, String> methode, String expression) {
		Pattern pattern = Pattern.compile(createPatternWithParams(methode));
		Matcher match = pattern.matcher(expression);
		StringBuffer sb = new StringBuffer();
		Map<Object, Object> contextValues = new HashMap<Object, Object>();
		int conter = 0;
		int start = 0;
		while (match.find()) {
			sb.append(expression.substring(start, match.start(1)));
			contextValues.put("value" + conter, match.group(4));
			sb.append(methode.get(match.group(2))).append("(").append("values[\"").append("value" + conter).append("\"]").append(")");
			start = match.end();
			conter++;
		}
		if (start < expression.length())
			sb.append(expression.substring(start, expression.length()));
		System.out.println(sb);
		System.out.println(contextValues);
	}
	
	public static String createPatternWithParams(Map<String, String> ms) {
		StringBuffer sb = new StringBuffer("(\\$)(");
		Object[] prefix = ms.keySet().toArray();
		for (int i = 0; i < prefix.length; i++) {
			sb.append(i != 0 ? "|" : "").append(prefix[i]);
		}
		sb.append(")(\\{)(\\S*)(\\})");
		return sb.toString();
	}
}
