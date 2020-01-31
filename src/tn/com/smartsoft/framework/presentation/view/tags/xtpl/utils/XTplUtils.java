package tn.com.smartsoft.framework.presentation.view.tags.xtpl.utils;

import org.apache.commons.lang.StringUtils;

public class XTplUtils {
	public static String formatValue(String value) {
		String attributeValue = StringUtils.replace(value, "${", "${xxx}{");
		attributeValue = StringUtils.replace(attributeValue, "#{", "${");
		return attributeValue;
	}

	public static String formatAttributeValue(String value) {
		String attributeValue = StringUtils.remove(value, "\n");
		attributeValue = StringUtils.remove(attributeValue, "\r");
		attributeValue = StringUtils.strip(attributeValue);
		attributeValue = formatValue(attributeValue);
		return attributeValue;
	}
}
