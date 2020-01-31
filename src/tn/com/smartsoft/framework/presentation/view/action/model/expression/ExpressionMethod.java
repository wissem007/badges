package tn.com.smartsoft.framework.presentation.view.action.model.expression;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

public class ExpressionMethod implements Serializable, Expression {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methode;
	private Pattern pattern;
	private Class<?>[] paramsType;
	private boolean IsWindowExp;
	
	public ExpressionMethod(String methode, Class<?>[] paramsType, String prefix, boolean IsWindowExp) {
		super();
		this.IsWindowExp = IsWindowExp;
		this.methode = methode;
		this.paramsType = paramsType;
		if (paramsType.length > 0)
			this.pattern = Pattern.compile("(\\$" + prefix + "\\{)(.*)(\\})");
		else
			this.pattern = Pattern.compile("(\\$" + prefix + ")");
	}
	
	public ExpressionMethod(String methode, Class<?>[] paramsType, String prefix) {
		this(methode, paramsType, prefix, false);
	}
	
	public boolean eval(ExpressionContext expressionContext) {
		String expression = expressionContext.getExpression();
		if (StringUtils.isBlank(expression))
			return false;
		Matcher match = pattern.matcher(expression.trim());
		StringBuffer sb = new StringBuffer();
		int start = 0;
		boolean is = false;
		while (match.find()) {
			is = true;
			sb.append(expression.substring(start, match.start()));
			sb.append(methode).append("(");
			if (paramsType.length > 0) {
				String[] params = StringUtils.split(match.group(2).trim(), ",");
				for (int i = 0; i < params.length; i++) {
					String paramName = expressionContext.generateName();
					String paramValue = params[i];
					if (StringUtils.isNotBlank(expressionContext.getWindowId()) && IsWindowExp)
						paramValue = expressionContext.getWindowId() + "." + paramValue;
					if (i < paramsType.length) {
						sb.append("values[\"").append(paramName).append("\"]");
						expressionContext.addContextValue(paramName, ConvertUtils.convert(paramValue, paramsType[i]));
					}
				}
			}
			sb.append(")");
			start = match.end();
		}
		if (start < expression.length())
			sb.append(expression.substring(start, expression.length()));
		expressionContext.setExpression(sb.toString());
		return is;
	}
	
	public static void main(String[] args) {
		ExpressionMethod g = new ExpressionMethod("getValue", new Class[] { String.class }, "PV");
		ExpressionContext expressionContext = new ExpressionContext("$PV{aaa}");
		System.out.println(g.eval(expressionContext));
		System.out.println(expressionContext.getExpression());
		System.out.println(expressionContext.getContextValues());
	}
}
