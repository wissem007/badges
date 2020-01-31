package tn.com.digivoip.comman;

import java.util.Map;
import ognl.Ognl;
import ognl.OgnlException;
import tn.com.digivoip.framework.exception.TechnicalException;

public class OgnlUtil{

	protected OgnlUtil() {}
	public static Object getValue(Object exp, Object root) {
		return OgnlUtil.getValue(exp, root, null, 0);
	}
	public static Object getValue(Object exp, Object root, String path, int lineNumber) {
		return OgnlUtil.getValue(exp, null, root, path, lineNumber);
	}
	public static Object getValue(Object exp, Map<Object, Object> ctx, Object root) {
		return OgnlUtil.getValue(exp, ctx, root, null, 0);
	}
	public static Object getValue(Object exp, Map<Object, Object> ctx, Object root, String path, int lineNumber) throws TechnicalException {
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
		return OgnlUtil.parseExpression(expression, null, 0);
	}
	public static Object parseExpression(String expression, String path, int lineNumber) throws TechnicalException {
		try {
			return Ognl.parseExpression(expression);
		} catch (Exception ex) {
			throw new TechnicalException(path + lineNumber, ex);
		}
	}
}
