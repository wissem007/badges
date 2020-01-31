package tn.com.smartsoft.framework.presentation.view.action.model.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.com.smartsoft.commons.utils.OgnlUtil;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;

public class ExpressionEvalidator{

	public static List<Expression>	EXPRESSION_LIST	= new ArrayList<Expression>();
	static {
		EXPRESSION_LIST.add(new ExpressionMethod("getProperty", new Class[] { String.class }, "P"));
		EXPRESSION_LIST.add(new ExpressionMethod("getValue", new Class[] { String.class }, "PV"));
		EXPRESSION_LIST.add(new ExpressionMethod("getCustomValue", new Class[] { String.class }, "CV"));
		EXPRESSION_LIST.add(new ExpressionMethod("getLibelle", new Class[] { String.class }, "L"));
		EXPRESSION_LIST.add(new ExpressionMethod("getUser", new Class[] { String.class }, "PU"));
		EXPRESSION_LIST.add(new ExpressionMethod("getEditerPar", new Class[] {}, "UE"));
		EXPRESSION_LIST.add(new ExpressionMethod("getOrganisme", new Class[] { String.class }, "PO"));
		EXPRESSION_LIST.add(new ExpressionMethod("getSociete", new Class[] { String.class }, "PS"));
		EXPRESSION_LIST.add(new ExpressionMethod("getViewLibelle", new Class[] { String.class }, "LV", true));
		EXPRESSION_LIST.add(new ExpressionMethod("getPropertyLibelle", new Class[] { String.class }, "LP"));
		EXPRESSION_LIST.add(new ExpressionMethod("getViewComponetProperty", new Class[] { String.class }, "VCP", true));
		EXPRESSION_LIST.add(new ExpressionMethod("getPropertyApplication", new Class[] { String.class }, "PA"));
		EXPRESSION_LIST.add(new ExpressionMethod("getRandom", new Class[] {}, "RDM"));
	}

	public static Object evaluate(String expression, UserActionModel root, Map<Object, Object> values, String windowId) {
		ExpressionContext expressionContext = new ExpressionContext(expression);
		expressionContext.setWindowId(windowId);
		boolean is = false;
		for (int i = 0; i < EXPRESSION_LIST.size(); i++) {
			Expression expressionValue = (Expression) EXPRESSION_LIST.get(i);
			if (expressionValue.eval(expressionContext)) is = true;
		}
		if (!is) return expression;
		if (values != null) expressionContext.addContextValues(values);
		ActionModelContext context = new OgnlActionModelContext(root, expressionContext.getContextValues());
		return OgnlUtil.getValue(OgnlUtil.parseExpression(expressionContext.getExpression()), context);
	}
	public static Object evaluate(String expression, UserActionModel root, String windowId) {
		return evaluate(expression, root, null, windowId);
	}
	public static Object evaluate(String expression, UserActionModel root, Map<Object, Object> values) {
		return evaluate(expression, root, values, null);
	}
	public static Object evaluate(String expression, UserActionModel root) {
		return evaluate(expression, root, null, null);
	}
}
