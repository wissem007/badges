package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutExpression;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIExtAttrebutExpressionRender extends UIExtAttrebutComponentRender{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public UIExtAttrebutExpressionRender(UIExtAttrebutExpression renderComponent) {
		super(renderComponent);
	}
	public void render(UIRenderContext context) {
		UIExtAttrebutExpression expression = (UIExtAttrebutExpression) renderComponent;
		StringBuffer sb = new StringBuffer(expression.getWindow().evalExpressionToString(expression.getExpression()));
		context.renderln(sb.toString());
	}
	public void render(UIRenderContext context, UIExtComponentJs componentJs) {
		UIExtAttrebutExpression expression = (UIExtAttrebutExpression) renderComponent;
		StringBuffer sb = new StringBuffer(expression.getWindow().evalExpressionToString(expression.getExpression()));
		if (!expression.getExpected()) {
			componentJs.addObjectValue(expression.getExtAttrebut(), StringUtils.chomp(sb.toString()));
		} else componentJs.addStringValue(expression.getExtAttrebut(), StringUtils.chomp(sb.toString()));
	}
}
