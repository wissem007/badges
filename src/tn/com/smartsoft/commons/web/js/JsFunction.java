package tn.com.smartsoft.commons.web.js;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class JsFunction implements Serializable, JsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] parameterNames;
	private String name;
	private StringBuffer implementation;
	private boolean isAttrebute = false;

	public JsFunction(String name, String[] parameterNames, String implementation) {
		super();
		this.name = name;
		this.parameterNames = parameterNames;
		this.implementation = new StringBuffer(implementation);
	}

	public JsFunction(String name, ArrayList<String> parameterNames, String implementation) {
		this.name = name;
		this.parameterNames = new String[parameterNames.size()];
		System.arraycopy(parameterNames.toArray(), 0, this.parameterNames, 0, parameterNames.size());
		this.implementation = new StringBuffer(implementation);
	}

	public JsFunction(String name, ArrayList<String> parameterNames) {
		this(name, parameterNames, "");
	}

	public JsFunction(String name, String[] parameterNames) {
		this(name, parameterNames, "");
	}

	public JsFunction(String name) {
		this(name, new String[0], "");
	}

	public JsFunction(String name, String param) {
		this(name, new String[] { param });
	}

	public JsFunction(String name, String param1, String param2) {
		this(name, new String[] { param1, param2 });
	}

	public JsFunction(String name, String param1, String param2, String param3) {
		this(name, new String[] { param1, param2, param3 });
	}

	public void addImplement(String impl) {
		this.implementation.append(impl);
	}

	public void addImplementLigne(String impl) {
		this.implementation.append("  ").append(impl).append(";\n");
	}

	public String[] getParameterNames() {
		return parameterNames;
	}

	public boolean isAttrebute() {
		return isAttrebute;
	}

	public void setAttrebute(boolean isAttrebute) {
		this.isAttrebute = isAttrebute;
	}

	public String getName() {
		return name;
	}

	public StringBuffer getImplementation() {
		return implementation;
	}

	public String invokeFunction(JsTable params, boolean isReturnLigne) {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("(");
		sb.append(params.toJsParams());
		sb.append(")");
		if (isReturnLigne)
			sb.append(";\n");
		return sb.toString();
	}

	public String invokeFunction(JsTable params) {
		return invokeFunction(params, true);
	}

	public String createCallback(String[] values, boolean[] expectedTypes, boolean isReturnLigne) {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append(".createCallback(");
		for (int i = 0; i < expectedTypes.length; i++) {
			String va = values[i];
			if (StringUtils.isBlank(va) && !expectedTypes[i])
				va = "null";
			if (i != 0)
				sb.append(",");
			if (expectedTypes[i] && va != null)
				sb.append("\"");
			sb.append(va);
			if (expectedTypes[i] && va != null)
				sb.append("\"");
		}
		sb.append(")");
		if (isReturnLigne)
			sb.append(";\n");
		return sb.toString();
	}

	public String createCallback(String[] values, boolean isReturnLigne) {
		boolean[] expectedTypes = new boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			expectedTypes[i] = false;
		}
		return createCallback(values, expectedTypes, isReturnLigne);
	}

	public String createCallback(String[] values) {
		return createCallback(values, false);
	}

	public String invokeFunction(String[] values, boolean[] expectedTypes, boolean isReturnLigne) {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("(");
		for (int i = 0; i < expectedTypes.length; i++) {
			String va = values[i];
			if (StringUtils.isBlank(va) && !expectedTypes[i])
				va = "null";
			if (i != 0)
				sb.append(",");
			if (expectedTypes[i] && va != null)
				sb.append("\"");
			sb.append(va);
			if (expectedTypes[i] && va != null)
				sb.append("\"");
		}
		sb.append(")");
		if (isReturnLigne)
			sb.append(";\n");
		return sb.toString();
	}

	public String createInvokeFunction(JsTable params) {
		return invokeFunction(params, false);
	}

	public String createInvokeFunction(String[] values, boolean[] expectedTypes) {
		return invokeFunction(values, expectedTypes, false);
	}

	public String createInvokeFunction(String value1, boolean expectedType1) {
		return createInvokeFunction(new String[] { value1 }, new boolean[] { expectedType1 });
	}

	public String createInvokeFunction(String value1, String value2, boolean expectedType1, boolean expectedType2) {
		return createInvokeFunction(new String[] { value1, value2 }, new boolean[] { expectedType1, expectedType2 });
	}

	public String createInvokeFunction(String value1, String value2, String value3, boolean expectedType1, boolean expectedType2, boolean expectedType3) {
		return createInvokeFunction(new String[] { value1, value2, value3 }, new boolean[] { expectedType1, expectedType2, expectedType3 });
	}

	public String invokeFunction(String[] values, boolean[] expectedTypes) {
		return invokeFunction(values, expectedTypes, true);
	}

	public String invokeFunction(String value1, boolean expectedType1) {
		return invokeFunction(new String[] { value1 }, new boolean[] { expectedType1 });
	}

	public String invokeFunction(String value1, String value2, boolean expectedType1, boolean expectedType2) {
		return invokeFunction(new String[] { value1, value2 }, new boolean[] { expectedType1, expectedType2 });
	}

	public String invokeFunction(String value1, String value2, String value3, boolean expectedType1, boolean expectedType2, boolean expectedType3) {
		return invokeFunction(new String[] { value1, value2, value3 }, new boolean[] { expectedType1, expectedType2, expectedType3 });
	}

	public String invokeFunction(String varName, boolean isNewVar, String value1, boolean expectedType1) {
		return invokeFunction(varName, isNewVar, new String[] { value1 }, new boolean[] { expectedType1 });
	}

	public String invokeFunction(String varName, boolean isNewVar, String value1, String value2, boolean expectedType1, boolean expectedType2) {
		return invokeFunction(varName, isNewVar, new String[] { value1, value2 }, new boolean[] { expectedType1, expectedType2 });
	}

	public String invokeFunction(String varName, boolean isNewVar, String value1, String value2, String value3, boolean expectedType1, boolean expectedType2, boolean expectedType3) {
		return invokeFunction(varName, isNewVar, new String[] { value1, value2, value3 }, new boolean[] { expectedType1, expectedType2, expectedType3 });
	}

	public String invokeFunction(String varName, boolean isNewVar, String[] values, boolean[] expectedTypes) {
		StringBuffer sb = new StringBuffer();
		if (isNewVar)
			sb.append("var ");
		sb.append(varName).append("=");
		sb.append(invokeFunction(values, expectedTypes));
		return sb.toString();
	}

	public String toJsMethode(boolean isVer) {
		StringBuffer sb = new StringBuffer("  ");
		sb.append(name).append(":").append("function ").append("(");
		for (int i = 0; i < parameterNames.length; i++) {
			if (i != 0)
				sb.append(",");
			sb.append(parameterNames[i]);
		}
		sb.append("){\n");
		sb.append(implementation);
		sb.append("  }");
		if (isVer)
			sb.append(",");
		sb.append("\n");
		return sb.toString();
	}

	public String toJsMethode() {
		return toJsMethode(false);
	}

	public static String formatScript(String implementation) {
		if (StringUtils.isBlank(implementation))
			return "";
		String[] rowS = StringUtils.split(implementation.toString(), "\n");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rowS.length; i++) {
			String row = StringUtils.trim(rowS[i]);
			if (StringUtils.isNotBlank(row))
				sb.append("        " + row).append("\n");
		}
		return sb.toString();
	}

	public void formatScript() {
		implementation = new StringBuffer(formatScript(implementation.toString()));
	}

	public String toStartJs() {
		StringBuffer sb = new StringBuffer();
		if (!isAttrebute)
			formatScript();
		sb.append("function ");
		if (StringUtils.isNotBlank(name))
			sb.append(name);
		sb.append("(");
		for (int i = 0; i < parameterNames.length; i++) {
			if (i != 0)
				sb.append(",");
			sb.append(parameterNames[i]);
		}
		sb.append("){");
		if (!isAttrebute)
			sb.append("\n");
		return sb.toString();
	}

	public String toEndJs() {
		StringBuffer sb = new StringBuffer();
		if (!isAttrebute)
			sb.append("\n");
		sb.append("}");
		if (!isAttrebute)
			sb.append("\n");
		return sb.toString();
	}

	public String toJs() {
		StringBuffer sb = new StringBuffer();
		if (!isAttrebute)
			formatScript();
		sb.append(toStartJs());
		sb.append(implementation);
		sb.append(toEndJs());
		return sb.toString();
	}
}
