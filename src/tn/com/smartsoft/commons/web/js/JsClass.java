package tn.com.smartsoft.commons.web.js;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class JsClass implements Serializable, JsObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] parameterNames;
	private String name;
	private HashMap<String, JsFunction> methods;

	public JsClass(String name, String[] parameterNames) {
		super();
		this.name = name;
		this.methods = new HashMap<String, JsFunction>();
		this.parameterNames = parameterNames;
	}

	public JsClass(String name) {
		this(name, new String[0]);
	}

	public JsClass(String name, String att) {
		this(name, new String[] { att });
	}

	public JsClass(String name, String att1, String att2) {
		this(name, new String[] { att1, att2 });
	}

	public JsClass(String name, String att1, String att2, String att3) {
		this(name, new String[] { att1, att2, att3 });
	}

	public void addMethod(JsFunction function) {
		methods.put(function.getName(), function);
	}

	public String invokeNewInstance(String varName, boolean isNewVar) {
		return invokeNewInstance(varName, isNewVar, new String[] {}, new boolean[] {});
	}

	public String invokeNewInstance(String varName, boolean isNewVar, String value1, boolean expectedType1) {
		return invokeNewInstance(varName, isNewVar, new String[] { value1 }, new boolean[] { expectedType1 });
	}

	public String invokeNewInstance(String varName, boolean isNewVar, String value1, String value2, boolean expectedType1, boolean expectedType2) {
		return invokeNewInstance(varName, isNewVar, new String[] { value1, value2 }, new boolean[] { expectedType1, expectedType2 });
	}

	public String invokeNewInstance(String varName, boolean isNewVar, String value1, String value2, String value3, boolean expectedType1, boolean expectedType2,
			boolean expectedType3) {
		return invokeNewInstance(varName, isNewVar, new String[] { value1, value2, value3 }, new boolean[] { expectedType1, expectedType2, expectedType3 });
	}

	public String invokeNewInstance(String varName, boolean isNewVar, String[] values, boolean[] expectedTypes) {
		StringBuffer sb = new StringBuffer();
		if (isNewVar)
			sb.append("var ");
		sb.append(varName);
		sb.append("=");
		sb.append(invokeNewInstance(values, expectedTypes));
		return sb.toString();
	}

	public String invokeNewInstance(String[] values, boolean[] expectedTypes) {
		StringBuffer sb = new StringBuffer();
		sb.append("new ");
		sb.append(name).append("(");
		for (int i = 0; i < expectedTypes.length; i++) {
			if (i != 0)
				sb.append(",");
			if (expectedTypes[i] && values[i] != null)
				sb.append("'");
			sb.append(values[i]);
			if (expectedTypes[i] && values[i] != null)
				sb.append("'");
		}
		sb.append(");\n");
		return sb.toString();
	}

	public String invokeMethod(String instanceName, String methodName, JsTable params) {
		StringBuffer sb = new StringBuffer(instanceName);
		sb.append(".");
		JsFunction function = methods.get(methodName);
		sb.append(function.invokeFunction(params));
		return sb.toString();
	}

	public String invokeMethod(String instanceName, String methodName, String[] values, boolean[] expectedTypes) {
		StringBuffer sb = new StringBuffer(instanceName);
		sb.append(".");
		JsFunction function = methods.get(methodName);
		sb.append(function.invokeFunction(values, expectedTypes));
		return sb.toString();
	}

	public String invokeMethod(String instanceName, String methodName) {
		return invokeMethod(instanceName, methodName, new String[] {}, new boolean[] {});
	}

	public String invokeMethod(String instanceName, String methodName, String value1, boolean expectedType1) {
		return invokeMethod(instanceName, methodName, new String[] { value1 }, new boolean[] { expectedType1 });
	}

	public String invokeMethod(String instanceName, String methodName, String value1, String value2, boolean expectedType1, boolean expectedType2) {
		return invokeMethod(instanceName, methodName, new String[] { value1, value2 }, new boolean[] { expectedType1, expectedType2 });
	}

	public String invokeMethod(String instanceName, String methodName, String value1, String value2, String value3, boolean expectedType1, boolean expectedType2,
			boolean expectedType3) {
		return invokeMethod(instanceName, methodName, new String[] { value1, value2, value3 }, new boolean[] { expectedType1, expectedType2, expectedType3 });
	}

	public String invokeMethod(String instanceName, String methodName, String value1, String value2, String value3, String value4, boolean expectedType1,
			boolean expectedType2, boolean expectedType3, boolean expectedType4) {
		return invokeMethod(instanceName, methodName, new String[] { value1, value2, value3, value4 }, new boolean[] { expectedType1, expectedType2, expectedType3,
				expectedType4 });
	}
	public String createInvokeFunction(String instanceName, String methodName, JsTable params) {
		StringBuffer sb = new StringBuffer(instanceName);
		sb.append(".");
		JsFunction function = methods.get(methodName);
		sb.append(function.createInvokeFunction(params));
		return sb.toString();
	}

	public String createInvokeFunction(String instanceName, String methodName, String[] values, boolean[] expectedTypes) {
		StringBuffer sb = new StringBuffer(instanceName);
		sb.append(".");
		JsFunction function = methods.get(methodName);
		sb.append(function.createInvokeFunction(values, expectedTypes));
		return sb.toString();
	}

	public String createInvokeFunction(String instanceName, String methodName) {
		return createInvokeFunction(instanceName, methodName, new String[] {}, new boolean[] {});
	}

	public String createInvokeFunction(String instanceName, String methodName, String value1, boolean expectedType1) {
		return createInvokeFunction(instanceName, methodName, new String[] { value1 }, new boolean[] { expectedType1 });
	}

	public String createInvokeFunction(String instanceName, String methodName, String value1, String value2, boolean expectedType1, boolean expectedType2) {
		return createInvokeFunction(instanceName, methodName, new String[] { value1, value2 }, new boolean[] { expectedType1, expectedType2 });
	}

	public String createInvokeFunction(String instanceName, String methodName, String value1, String value2, String value3, boolean expectedType1, boolean expectedType2,
			boolean expectedType3) {
		return createInvokeFunction(instanceName, methodName, new String[] { value1, value2, value3 }, new boolean[] { expectedType1, expectedType2, expectedType3 });
	}

	public String createInvokeFunction(String instanceName, String methodName, String value1, String value2, String value3, String value4, boolean expectedType1,
			boolean expectedType2, boolean expectedType3, boolean expectedType4) {
		return createInvokeFunction(instanceName, methodName, new String[] { value1, value2, value3, value4 }, new boolean[] { expectedType1, expectedType2, expectedType3,
				expectedType4 });
	}
	public String toJs() {
		StringBuffer sb = new StringBuffer();
		sb.append("var ").append(name).append("= Class.create();\n");
		sb.append(name).append(".prototype = { \n");
		JsFunction jsFunction = new JsFunction("initialize", parameterNames);
		for (int i = 0; i < parameterNames.length; i++) {
			jsFunction.addImplementLigne(" this." + parameterNames[i] + "=" + parameterNames[i]);
		}
		sb.append(jsFunction.toJsMethode(true));
		Collection<JsFunction> mds = methods.values();
		for (Iterator<JsFunction> iterator = mds.iterator(); iterator.hasNext();) {
			jsFunction = (JsFunction) iterator.next();
			sb.append(jsFunction.toJsMethode(iterator.hasNext()));
		}
		sb.append("}\n");
		return sb.toString();
	}

	public static void main(String[] args) {
		JsTable params = new JsTable();
		params.addObjectValue("value");
		params.addStringOption("value");
		params.addBooleanOption(true);
		System.out.println(params.toJsParams());

	}
}
