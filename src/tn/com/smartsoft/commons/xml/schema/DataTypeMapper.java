package tn.com.smartsoft.commons.xml.schema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

public class DataTypeMapper {
	private static Map<String, Class<?>> DATA_TYPE_MAPPER = new HashMap<String, Class<?>>();
	private static Map<Class<?>, String> DATA_TYPE_MAPPER1 = new HashMap<Class<?>, String>();

	public static void addDataTypeMapper(String xsd, Class<?> java) {
		DATA_TYPE_MAPPER.put(xsd, java);
		DATA_TYPE_MAPPER1.put(java, xsd);
	}

	static {
		addDataTypeMapper("xsd:string", String.class);
		addDataTypeMapper("xsd:string", Class.class);
		addDataTypeMapper("xsd:string", Boolean.TYPE);
		addDataTypeMapper("xsd:string", Boolean.class);

		addDataTypeMapper("xsd:integer", BigInteger.class);

		addDataTypeMapper("xsd:int", Integer.class);
		addDataTypeMapper("xsd:int", Integer.TYPE);

		addDataTypeMapper("xsd:long", Long.TYPE);
		addDataTypeMapper("xsd:long", Long.class);

		addDataTypeMapper("xsd:short", Short.TYPE);
		addDataTypeMapper("xsd:short", Short.class);

		addDataTypeMapper("xsd:float", Float.TYPE);
		addDataTypeMapper("xsd:float", Float.class);

		addDataTypeMapper("xsd:decimal", BigDecimal.class);

		addDataTypeMapper("xsd:double", Double.TYPE);
		addDataTypeMapper("xsd:double", Double.class);

		addDataTypeMapper("xsd:byte", Byte.TYPE);
		addDataTypeMapper("xsd:byte", Byte.class);

		addDataTypeMapper("xsd:dateTime", java.util.Date.class);
		addDataTypeMapper("xsd:date", java.sql.Date.class);
		addDataTypeMapper("xsd:time", java.sql.Time.class);

	}

	public static boolean isXsdType(String type) {
		return DATA_TYPE_MAPPER.containsKey(type);
	}

	public static boolean isJavaSimpleType(Class<?> javaType) {
		return DATA_TYPE_MAPPER1.containsKey(javaType);
	}

	public static String getSetterProperty(String name) {
		return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	public static String getGetterProperty(String name) {
		return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	public static String attributeToJavaName(String name) {
		StringBuffer buf = new StringBuffer(name.length());
		int length = name.length();
		char c = name.charAt(0);
		buf.append(Character.toLowerCase(c));
		boolean uppercaseNext = false;
		for (int i = 1; i < length; i++) {
			c = name.charAt(i);
			if (c == '-')
				uppercaseNext = true;
			else if (uppercaseNext) {
				buf.append(Character.toUpperCase(c));
				uppercaseNext = false;
			} else
				buf.append(Character.toLowerCase(c));
		}
		return buf.toString();
	}

	public static String classToString(Class<?> javaType) {
		if (javaType.isPrimitive()) {
			ClassUtils.primitiveToWrapper(javaType).getName();
		}
		return javaType.getName();
	}

	public static String classToTypeName(Class<?> javaType) {
		return classToTypeName(javaType.getName());
	}

	public static String classToTypeName(String name) {
		int length = name.length();
		int start = 0;
		int start1 = 0;
		for (int i = 0; i < length; i++) {
			char c = name.charAt(i);
			if (c == '.') {
				start = start1;
				start1 = i + 1;
			}
		}
		StringBuffer buf = new StringBuffer(name.length());
		char c = name.charAt(0);
		for (int i = start; i < length; i++) {
			c = name.charAt(i);
			if (c == '.') {
				buf.append("-");
				buf.append(Character.toLowerCase(name.charAt(i + 1)));
				i++;
			} else
				buf.append(c);
		}
		return buf.toString();
	}

	public static String classToName(Class<?> javaType) {
		String name = javaType.getName();
		return attributeToJavaName(name.substring(name.lastIndexOf(".") + 1));
	}

	public static String classListToName(Class<?> javaType) {
		String name = javaType.getName();
		return "list-" + attributeToJavaName(name.substring(name.lastIndexOf(".") + 1));
	}

	public static String propertyToAttributeName(String name) {
		StringBuffer buf = new StringBuffer(name.length());
		int length = name.length();
		char c = name.charAt(0);
		for (int i = 0; i < length; i++) {
			c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				buf.append("-");
			}
			buf.append(Character.toLowerCase(c));
		}
		return buf.toString();
	}

	public static String javaToXsdType(Class<?> type) {
		return DATA_TYPE_MAPPER1.containsKey(type) ? DATA_TYPE_MAPPER1.get(type) : "xsd:string";
	}

	public static Class<?> xsdToJavaType(String type) {
		return DATA_TYPE_MAPPER.containsKey(type) ? DATA_TYPE_MAPPER.get(type) : String.class;
	}

}
