package tn.com.smartsoft.commons.xml.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ParserDefinitionUtils {
	static Pattern PATTERN = Pattern.compile("(\\$\\{)((\\w|\\|\\|)+)(\\})");

	public static String parse(String value, ParserDefinition parser) {
		return parse(value, parser, PATTERN);
	}

	private static String parseElement(String value, ParserDefinition parser, Pattern pattern) {
		if (StringUtils.isBlank(value))
			return value;
		Matcher match = pattern.matcher(value);
		StringBuffer sb = new StringBuffer();
		int start = 0;
		String defaultValue = "";
		while (match.find()) {
			sb.append(value.substring(start, match.start()));
			String sysValue = StringUtils.trim(match.group(2));
			if (StringUtils.isNotBlank(sysValue) && StringUtils.indexOf(sysValue, "||") > 0) {
				defaultValue = StringUtils.trim(sysValue.substring(StringUtils.indexOf(sysValue, "||") + 2));
				sysValue = StringUtils.trim(sysValue.substring(0, StringUtils.indexOf(sysValue, "||")));
			}
			sysValue = parser.getVariableSystem(sysValue);
			if (sysValue == null)
				sysValue = defaultValue;
			sb.append(sysValue);
			start = match.end();
		}
		if (start < value.length())
			sb.append(value.substring(start, value.length()));
		return sb.toString();
	}

	public static String parse(String value, ParserDefinition parser, Pattern pattern) {
		if (StringUtils.isBlank(value))
			return value;
		String values = "";
		while (!value.equals(values)) {
			values = value;
			value = parseElement(value, parser, pattern);
		}
		return value;
	}

}
