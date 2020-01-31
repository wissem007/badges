package tn.com.smartsoft.commons.utils;

import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import tn.com.smartsoft.commons.exceptions.TechnicalException;

public class OgnlUtil {
	protected OgnlUtil() {
	}

	public static Object getValue(Object exp, Object root) {
		return getValue(exp, root, null, 0);
	}

	public static Object getValue(Object exp, Object root, String path, int lineNumber) {
		return getValue(exp, null, root, path, lineNumber);
	}

	public static Object getValue(Object exp, Map ctx, Object root) {
		return getValue(exp, ctx, root, null, 0);
	}

	public static Object getValue(Object exp, Map ctx, Object root, String path, int lineNumber) throws TechnicalException {
		try {
			if (ctx != null) {
				return Ognl.getValue(exp, ctx, root);
			} else {
				return Ognl.getValue(exp, root);
			}
		} catch (OgnlException ex) {
			throw new TechnicalException(path + lineNumber, ex.getReason() == null ? ex : ex.getReason());
		} catch (Exception ex) {
			throw new TechnicalException(path + lineNumber, ex);
		}
	}

	public static Object parseExpression(String expression) {
		return parseExpression(expression, null, 0);
	}

	public static Object parseExpression(String expression, String path, int lineNumber) throws TechnicalException {
		try {
			return Ognl.parseExpression(expression);
		} catch (Exception ex) {
			throw new TechnicalException(path + lineNumber, ex);
		}
	}

	private static interface Foo {
		String getBar();
	}

	public static void main(String[] args) {
		Object exp = parseExpression("new java.util.Date()");
		System.out.println(getValue(exp, null));
		exp = OgnlUtil.parseExpression("foo.bar");
		Map root = new HashMap();
		root.put("foo", new Foo() {
			public String getBar() {
				return "s";
			}
		});
		System.out.println(getValue(exp, root));
		root = new HashMap();
		root.put("currentDate", new java.util.Date());
		exp = OgnlUtil.parseExpression("currentDate");
		System.out.println(getValue(exp, root));
		exp = OgnlUtil.parseExpression("this.getLibelle(sValue0)");
		 
	}
}
